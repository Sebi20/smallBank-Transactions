package Assignment7a;

import java.util.*;
import java.io.*;

public class Assignment7{
	public static void main(String[] args) throws IOException{
		PrintWriter output = new PrintWriter("/Users/sebiseb/eclipse-workspace/Assignments/src/Assignment7a/Assignment7.txt");
		Scanner input = new Scanner(System.in);
		
		// The Arrays
		final int MAX_NUM = 100; // declares the maximum accounts the bank will take
		int [] acctNum = new int[MAX_NUM];// Array that collects the account numbers
		double [] balance = new double[MAX_NUM];// Array that collects the balances of the accounts
		
		//Declaring method into the main
		int numAccts = readAccts(acctNum, balance, MAX_NUM);// The number of accounts being processed
		output.println("Intial Bank Data Base");
		output.println("-----------------------");
		printAccts(acctNum, balance, numAccts, output);// Prints the accounts and balances
		output.println("");
		menu();// Prints out the menu and prompting a response
		
		String prompt  = input.next();// Collect the characters for a type of transaction
		do {
			switch(prompt) {// checks if a character is valid
			case "w": withdrawal(acctNum, balance, numAccts, input, output);
				break;
			case "d": deposit(acctNum, balance, numAccts, input, output);
				break;
			case "n": numAccts = newAcct(acctNum, balance, numAccts, input, output);
				break;
			case "b": balance(acctNum, balance, numAccts, input, output);
				break;
			default: output.println(prompt + " is an invalid input");
				System.out.println("Invalid input"); // Default message is for the 
				output.println("------------------");
		}// end of Switch statement
		// Repeat prompt
		System.out.println();
		menu();
		prompt = input.next();
		
		}while(!prompt.equals("q"));// End of the do-while loop
		
		
		output.println("--------------------------");
		output.println("Processed Bank Data Base");
		output.println("--------------------------");
		printAccts(acctNum, balance, numAccts, output);// Prints the accounts and balances
		
		output.close();
	}// end of the main
	
	public static int readAccts(int[] acctNum, double[] balance, int maxAccts) throws IOException{// Counts the amount of accounts being processed
		Scanner scan = new Scanner(new File("/Users/sebiseb/eclipse-workspace/Assignments/src/Assignment7a/Assignment7.txt"));// Find the file where the numbers are stored
		int cnt = 0;// Declare and initialize the count
		int i = 0;// To get each individual number out the array(Acts as the index)
		
		while(scan.hasNext()) {// Reads data until there's no more left
			acctNum[i] = scan.nextInt();// Takes the account numbers
			balance[i] = scan.nextDouble();// Takes the account balances
			cnt++; // counts how many accounts are being processed
			i++;
		}
		return cnt;// returns the count of the accounts
	}// end of readAccts method
	
	public static void menu() {// the menu responsible for the options that users have
		
		System.out.println("Type a letter");
		System.out.println("W-Withdraw \nD-Deposit \nN-New Account \nB-Balance \nQ-Quit");
	}// end of menu method
	
	public static int findAcct(int[] acctNum, int numAccts, int account) { // Finds the Account index
    	int i;
		for (i = 0; i < numAccts; i++) {
    		if (account == acctNum[i]) {
    			return i;
    		} 
		}// end of for loop
		return -1;
    }// end of findAcct method
    
	public static void withdrawal(int[] acctNum, double[] balance, int numAccts, Scanner input, PrintWriter output) throws IOException{// This method allows user to make a withdrawal
		
		int account;// Declaring the account
		double takeout;// The amount of money you want to take out
		double newbalance;// The new balance after withdrawal
		int exist;// To test if account exists
		
		System.out.println("Type account number or -1 to stop");
		account = input.nextInt();// Initialize the account number and filling it
				
		do {
			exist = findAcct(acctNum, numAccts, account);// This tests if the account number is truly real
			if (exist != -1) {
				System.out.println("Type the amount you want to withdraw");
				takeout = input.nextDouble();// Assigning the number the person wants to take out
				
				if(balance[exist] >= takeout) {
					newbalance = balance[exist] - takeout;// New balance after processing 
					output.println("Transaction Type: Withdrawal");
					output.println("Account Number: " + acctNum[exist]);
					output.println("Current Balance: " + balance[exist]);
					balance[exist] = newbalance;
					output.printf("New Balance: $%.2f\n", newbalance);
					output.println("------------------");
					System.out.println();
				} else if (balance[exist] < takeout){
					output.println("Transaction Type: Withdrawal");
					output.println("Account Number: " + acctNum[exist]);
					output.println("Current Balance: " + balance[exist]);
					output.println("Amount to WithDraw: $" + takeout);
					output.printf("Error: Insufficient Funds Available\n");
					output.println("------------------");
					System.out.println();
				}
			}else {
				output.println(account + " is not found");
			}// End of nested if statement
			
			System.out.println("Type account number or -1 to stop");
			account = input.nextInt();
		}while (account > 0);// End of the while loop
		output.flush();
	}// End of withdrawal method

	public static void deposit(int[] acctNum, double[] balance, int numAccts, Scanner input, PrintWriter output) throws IOException{// Allows user to make a deposit in their account
		int account;// initializing the account
		int exist;// Initializing to see if a number exists
		double newbalance;// Initializing the new-balance
		double putin;// Initializing the amount a person wants to deposit
		
		System.out.println("Type account number to deposit or -1 to stop");
		account = input.nextInt();// Collects the account number
		
		do {
			exist = findAcct(acctNum, numAccts, account);// The index or -1
			if (exist != -1) {
				System.out.println("Type the amount you want to deposit");
				putin = input.nextDouble();// collects the amount of money a user wants to deposit
				newbalance = balance[exist] + putin;// Initializes the new balance
				output.println("Transaction type: deposit");
				output.println("Account number: " + acctNum[exist]);
				output.println("Account balance: " + balance[exist]);
				output.println("Amount to Deposit: " + putin);
				balance[exist] = newbalance;
				output.printf("New balance: $%.2f\n", balance[exist]);
				output.println("------------------");
				System.out.println();
			} else if (exist == -1){
				output.println(account + " is not found\n");
				output.println("------------------");
				System.out.println();
			}// end of the if statement
			
			System.out.println("Type account number to deposit or -1 to stop");
			account = input.nextInt();// So you can deposit for additional accounts
		
		}while (account > 0);//end of while
		output.flush();
	}// End of deposit method

	
	public static int newAcct(int[] acctNum, double[] balance, int numAccts, Scanner input, PrintWriter output) throws IOException{// Creates a new account
		int account;
		int exist;
		int newCnt = 0;
		
		System.out.println("Create a new Account or -1 to stop");
		account = input.nextInt();
		while(account > 0) {
			exist = findAcct(acctNum, numAccts, account);
			
			if (exist == -1) {
				acctNum[numAccts + newCnt] = account;
				balance[numAccts + newCnt] = 0;
				newCnt++;
				output.println("New Account Number: " + account);
				output.println("Balance: $" + 0 + "\n");
				output.println("------------------");
				System.out.println();
			} else {
				output.println(account + " already exists\n");
				output.println("------------------");
				System.out.println();
			}// End of nested if statement
			output.println("------------------");
			System.out.println();
			System.out.println("Create a new Account or -1 to stop");
			account = input.nextInt();
		
			
		}// End of the while loop
		output.flush();
		return newCnt + numAccts;
	}// End of the newAcct method

	
	public static void balance(int[] acctNum, double[] balance, int numAccts, Scanner input, PrintWriter output)throws IOException {// Gives the balance of the account
		int account;
		int exist;
		
		System.out.println("Type account number to get balance or -1 to stop");
		account = input.nextInt();	
		
		while (account > 0) {
			exist = findAcct(acctNum, numAccts, account);
			if (exist != -1) {
			output.println("The account balance of " + account + " is: " + balance[exist]);
			output.println("------------------");
			System.out.println();
			} else {
				output.println(account + " Account doesn't exist\n");
				output.println("------------------");
				System.out.println();
			}// end of the nested if statement
			output.println("------------------");
			System.out.println();
			System.out.println("Type account number to get balance or -1 to stop");
			account = input.nextInt();
		
		}// End of the while loop
		output.flush();
	}// End of the balance method

	
	public static void printAccts(int[] acctNum, double[] balance, int numAccts, PrintWriter output)throws IOException{// Prints 2 columns of both arrays parallel to each other
		output.println("Account\t\tBalance");
		
		for (int i = 0; i < numAccts; i++) {
			output.println(acctNum[i] + "\t\t" + balance[i]);
		
		}// end of the for loop
	}// end of printAccts method

}// end of the class