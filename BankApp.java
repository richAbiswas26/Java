package Java;

import java.util.*;
import java.io.*;
import java.util.*;

public class BankApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        BankService service = new BankService();

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Search Account");
            System.out.println("3. Check Balance");
            System.out.println("4. Delete Account");
            System.out.println("5. Exit");

            System.out.print("Choose 1 to 5 : ");
            int choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1 -> service.createAccount(input);
                case 2 -> service.searchAccount(input);
                case 3 -> service.checkBalance(input);
                case 4 -> service.deleteAccount(input);
                case 5 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

}


class BankUser {
    private String name;
    private String accountNumber;
    private int balance;
    private String password;

    public BankUser(String name, String accountNumber, String password, int balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
    }

    public String getName() { return name; }
    public String getAccountNumber() { return accountNumber; }
    public int getBalance() { return balance; }
    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
    public void setBalance(int balance) { this.balance = balance; }

    public String toFileString() {
        return name + "," + accountNumber + "," + password + "," + balance;
    }
}


class BankService {

    public void createAccount(Scanner input) {
        try {
            System.out.print("Enter Name: ");
            String name = input.nextLine();

            System.out.print("Enter Account Number: ");
            String acc = input.nextLine();

            System.out.print("Enter Password: ");
            String pass = input.nextLine();

            System.out.print("Enter Balance: ");
            int bal = Integer.parseInt(input.nextLine());

            BankUser user = new BankUser(name, acc, pass, bal);
            BankStorage.saveUser(user);

            System.out.println("Account Created Successfully!");

        } catch (Exception e) {
            System.out.println("Error creating account");
        }
    }

    public void searchAccount(Scanner input) {
        try {
            System.out.print("Enter Name or Account No: ");
            String key = input.nextLine();

            for (String[] data : BankStorage.readAll()) {
                if (key.equals(data[0]) || key.equals(data[1])) {
                    System.out.println("Name: " + data[0]);
                    System.out.println("Account: " + data[1]);
                    return;
                }
            }
            System.out.println("Account not found");

        } catch (Exception e) {
            System.out.println("Error searching");
        }
    }

    public void checkBalance(Scanner input) {
        try {
            System.out.print("Account Number: ");
            String acc = input.nextLine();

            System.out.print("Password: ");
            String pass = input.nextLine();

            for (String[] data : BankStorage.readAll()) {
                if (acc.equals(data[1]) && pass.equals(data[2])) {
                    System.out.println("Balance: " + data[3]);
                    return;
                }
            }
            System.out.println("Invalid credentials");

        } catch (Exception e) {
            System.out.println("Error checking balance");
        }
    }

    public void deleteAccount(Scanner input) {
        try {
            System.out.print("Account Number: ");
            String acc = input.nextLine();

            List<String[]> all = BankStorage.readAll();
            List<String> updated = new ArrayList<>();
            boolean found = false;

            for (String[] data : all) {
                if (acc.equals(data[1])) {
                    found = true;
                    continue;
                }
                updated.add(String.join(",", data));
            }

            BankStorage.overwrite(updated);

            if (found) System.out.println("Deleted successfully");
            else System.out.println("Account not found");

        } catch (Exception e) {
            System.out.println("Error deleting");
        }
    }
    
}

class BankStorage {

    private static final String FILE_NAME = "users.txt";

    public static void saveUser(BankUser user) throws IOException {
        FileWriter fw = new FileWriter(FILE_NAME, true);
        fw.write(user.toFileString() + "\n");
        fw.close();
    }

    public static List<String[]> readAll() throws Exception {
        List<String[]> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return list;

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            list.add(sc.nextLine().split(","));
        }
        sc.close();
        return list;
    }

    public static void overwrite(List<String> data) throws IOException {
        FileWriter fw = new FileWriter(FILE_NAME);
        for (String line : data) {
            fw.write(line + "\n");
        }
        fw.close();
    }
}