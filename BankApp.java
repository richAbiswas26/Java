package Java;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class BankApp {

    public static void main (String [] args){
        Scanner input = new Scanner(System.in);

        System.out.println("Choose what you want : (type what you want from below instruction)");
        System.out.println("Want to make account : (Account)");
        System.out.println("Want to Search your account : (Search)");
        System.out.println("Want to Set Password : (set Password)");
        System.out.println("Check Bank Balance : (check balance)");

        System.out.print("Type : ");
        String userInput = input.nextLine();

        Bank user = new Bank();

        if(userInput.equalsIgnoreCase("Account")){
            user.createAccount(user, input);
        }
        else if(userInput.equalsIgnoreCase("Search")) {
            user.searchAccount(input);
        }
        else if(userInput.equalsIgnoreCase("set Password")){
            System.out.print("Account Number: ");
            String str = input.nextLine();

            user.resetPassword(user, input, str);
        }
        else if(userInput.equalsIgnoreCase("check balance")){
            System.out.print("Account Number: ");
            String str = input.nextLine();

            user.checkBalance(user, input, str);
        }

    }
}

class Bank {
    public String userName;
    private String account_Number;
    private int balance;
    private String password;

    // Set value
    protected void setAccountNumber(String account_Number){
        this.account_Number = account_Number;
    }
    protected void setBalance(int balance){
        this.balance = balance;
    }
    protected void setPassword(String password){
        this.password = password;
    }

    // Get Value
    protected String getAccountNumber(){
        return this.account_Number;
    }
    protected int getBalance(){
        return this.balance;
    }
    protected String getPassword(){
        return this.password;
    }

    // Create Account
    protected void createAccount(Bank user, Scanner input){

        System.out.print("Enter Name: ");
        user.userName = input.nextLine();

        System.out.print("Enter Account Number: ");
        user.setAccountNumber(input.nextLine());

        System.out.print("Enter Password: ");
        user.setPassword(input.nextLine());

        System.out.print("Enter Balance: ");
        user.setBalance(Integer.parseInt(input.nextLine()));
        // input.nextLine(); // clear buffer (important)

        // Convert object → string (CSV format)
        String data = user.userName + "," +
                    user.getAccountNumber() + "," +
                    user.getPassword() + "," +
                    user.getBalance();

        try {
            FileWriter fw = new FileWriter("users.txt", true); // append mode
            fw.write(data + "\n");
            fw.close();

            System.out.println("Account Created Successfully!");

        } catch (IOException e) {
            System.out.println("Error saving data");
            System.out.println(e);
        }
    }

    // User Search their account 
    protected void searchAccount(Scanner input){
        System.out.print("Type Name / Account Number: ");
        String str = input.nextLine();

        try {
            Scanner fileReader = new Scanner(new java.io.File("users.txt"));

            while(fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String[] data = line.split(",");

                if(str.equals(data[0]) || str.equals(data[1])){
                    System.out.println("----------------------");
                    System.out.println("Name: " + data[0]);
                    System.out.println("Account No: " + data[1]);
                    System.out.println("----------------------");

                    fileReader.close();
                    return;
                }

                // System.out.println("Name: " + data[0]);
                // System.out.println("Account No: " + data[1]);
                // System.out.println("Password: " + data[2]);
                // System.out.println("Balance: " + data[3]);
            }
            fileReader.close();
            System.out.println("Sorry No Account Find (Enter Correct and full details. )");

        } catch (Exception e) {
            System.out.println("Error reading file");
        }
    }

    // User reset their password
    protected void resetPassword(Bank user, Scanner input, String str){

        try {
            Scanner fileReader = new Scanner(new java.io.File("users.txt"));

            while(fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String[] data = line.split(",");

                if(str.equals(data[1])){
                    System.out.print("Type password : ");
                    String pass = input.nextLine();

                    data[2] = pass;

                    fileReader.close();
                    System.out.println("Account Password Set Successfully!");

                    return;
                }
                else{
                    System.out.println("No account exists of this account number.");
                }
            }
            fileReader.close();

        } catch (Exception e) {
            System.out.println("Error set Password");
            System.out.println(e);
        }
    }

    // Check Balance
    protected void checkBalance(Bank user, Scanner input, String str){
        try {
            Scanner fileReader = new Scanner(new java.io.File("users.txt"));

            while(fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String[] data = line.split(",");

                if(str.equals(data[1])){
                    System.out.print("Type password : ");
                    String pass = input.nextLine();

                    if(pass.equals(data[2])){
                        System.out.println("Bank Balance : " + data[3]);
                    }
                    else{
                        System.out.println("Wrong Password");
                    }
                    fileReader.close();

                    return;
                }
                else{
                    System.out.println("No account exists of this account number.");
                }
            }
            fileReader.close();

        } catch (Exception e) {
            System.out.println("Error set Password");
            System.out.println(e);
        }
    }
}
