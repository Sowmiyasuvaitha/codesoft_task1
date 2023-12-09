import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String pin;
    private double balance;

    public BankAccount(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public String deposit(double amount) {
        balance += amount;
        return "Deposited $" + amount + ". New balance: $" + balance;
    }

    public String withdraw(double amount) {
        if (amount > balance) {
            return "Insufficient funds";
        }
        balance -= amount;
        return "Withdrew $" + amount + ". New balance: $" + balance;
    }

    public String checkBalance() {
        return "Current balance: $" + balance;
    }
}

class ATM {
    private BankAccount[] bankAccounts;
    private BankAccount currentAccount;

    public ATM(BankAccount[] bankAccounts) {
        this.bankAccounts = bankAccounts;
        this.currentAccount = null;
    }

    private BankAccount validatePin(String accountNumber, String enteredPin) {
        for (BankAccount account : bankAccounts) {
            if (accountNumber.equals(account.getAccountNumber()) && enteredPin.equals(account.getPin())) {
                return account;
            }
        }
        return null;
    }

    public void displayMenu() {
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Change Account");
        System.out.println("5. Quit");
    }

    public void processOption(int option) {
        Scanner scanner = new Scanner(System.in);

        switch (option) {
            case 1:
                System.out.print("Enter withdrawal amount: $");
                double withdrawAmount = scanner.nextDouble();
                System.out.println(currentAccount.withdraw(withdrawAmount));
                break;
            case 2:
                System.out.print("Enter deposit amount: $");
                double depositAmount = scanner.nextDouble();
                System.out.println(currentAccount.deposit(depositAmount));
                break;
            case 3:
                System.out.println(currentAccount.checkBalance());
                break;
            case 4:
                currentAccount = null;
                System.out.println("Logged out. Please log in again.");
                break;
            case 5:
                System.out.println("Exiting ATM. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (currentAccount == null) {
                System.out.print("Enter account number: ");
                String accountNumber = scanner.nextLine();
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();
                currentAccount = validatePin(accountNumber, pin);

                if (currentAccount == null) {
                    System.out.println("Invalid account number or PIN. Please try again.");
                    continue;
                }
            }

            displayMenu();
            System.out.print("Choose an option (1-5): ");
            int option = scanner.nextInt();

            if (option >= 1 && option <= 5) {
                processOption(option);
            } else {
                System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount[] accounts = {
                new BankAccount("123456", "1234", 1000),
                new BankAccount("789012", "5678", 500)
        };

        ATM atm = new ATM(accounts);
        atm.run();
    }
}
