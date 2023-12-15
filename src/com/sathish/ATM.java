package com.sathish;
import java.util.Scanner;

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private StringBuilder transactionHistory;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new StringBuilder("Transaction History:\n");
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        addToTransactionHistory("Deposit: +" + amount);
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addToTransactionHistory("Withdrawal: -" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            addToTransactionHistory("Transfer to " + recipient.getUserId() + ": -" + amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Invalid transfer amount or insufficient funds.");
        }
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }

    private void addToTransactionHistory(String transaction) {
        transactionHistory.append(transaction).append("\n");
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating a sample account
        Account userAccount = new Account("123456", "1234", 1000.0);

        // ATM login
        System.out.print("Enter User ID: ");
        String enteredUserId = scanner.next();
        System.out.print("Enter PIN: ");
        String enteredPin = scanner.next();

        if (enteredUserId.equals(userAccount.getUserId()) && userAccount.authenticate(enteredPin)) {
            // Successful login
            int choice;
            do {
                // Display menu
                System.out.println("\nATM Menu:");
                System.out.println("1. Transactions History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Balance");
                System.out.println("6. Quit");

                // Get user choice
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                // Perform selected operation
                switch (choice) {
                    case 1:
                        System.out.println(userAccount.getTransactionHistory());
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        userAccount.withdraw(withdrawalAmount);
                        break;
                    case 3:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        userAccount.deposit(depositAmount);
                        System.out.println("Amount deposited successfully");
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientUserId = scanner.next();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        // For simplicity, assuming recipient account is same as userAccount
                        userAccount.transfer(userAccount, transferAmount);
                        break;
                    case 5:
                        System.out.println("Account Balance:"+userAccount.getBalance());
                        break;
                    case 6:
                        System.out.println("Exiting ATM. Thankyou Visit Again!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } while (choice != 6);
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }

        scanner.close();
    }
}
