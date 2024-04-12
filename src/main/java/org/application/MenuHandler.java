package org.application;

import org.api.BankingApiImpl;
import org.exceptions.*;
import org.security.Permission;
import org.security.Role;
import org.structs.Gender;
import org.structs.MethodsMenuItem;
import org.structs.UpdateField;
import org.structs.UserCategory;
import org.utils.Account;
import org.utils.MyArrayList;
import org.utils.User;

import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuHandler {
    private static final Scanner userInput = new Scanner(System.in);
    private static final BankingApiImpl api = new BankingApiImpl();


    public static void addUser(){
        menuItemHeader("add user");
        String username = getValueFromPrompt("Enter username: ", String.class);
        String firstName = getValueFromPrompt("Enter first name: ", String.class);
        String lastName = getValueFromPrompt("Enter last name: ", String.class);
        Integer age = getValueFromPrompt("Enter age: ", Integer.class);
        String company = getValueFromPrompt("Enter your company: ", String.class);

        Gender gender;
        do {
            prompt("Choose gender: ");
            gender = getGender();
        } while (gender == null);

        UserCategory userCategory;
        do{
            prompt("Choose your user category: ");
            userCategory = getUserCategory();
        } while (userCategory == null);

        try{
            api.addUser(username, firstName, lastName, age, gender, company, userCategory);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void getUser(){
        menuItemHeader("get user");
        String username = getValueFromPrompt("Enter username: ", String.class);
        User user = api.getUser(username);
        if (user == null){
            System.out.println("User with username: " + username + " not found.");
            return;
        }
        api.printUser(user);
    }


    public static void updateUser(){
        menuItemHeader("update user");
        String username = getValueFromPrompt("Enter existing username: ", String.class);
        User user = api.getUser(username);
        if (user != null){
            UpdateField field;
            Object value = null;
            do {
                prompt("Which field do you want to update? ");
                field = getUserUpdateField();
            } while(field == null);

            switch (field){
                case USERNAME -> value = getValueFromPrompt("Enter new username: ", String.class);
                case FIRST_NAME -> value = getValueFromPrompt("Enter new first name: ", String.class);
                case LAST_NAME -> value = getValueFromPrompt("Enter new last name: ", String.class);
                case AGE -> value = getValueFromPrompt("Enter new age: ", Integer.class);
                case GENDER -> {
                    prompt("Enter a new gender: ");
                    value = getGender();
                }
                case COMPANY -> value = getValueFromPrompt("Enter new company: ", String.class);
            }
            try {
                api.updateUser(user, field, value);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("User not found");
        }
    }

    public static void removeUser(){
        menuItemHeader("remove user");
        String username = getValueFromPrompt("Enter existing username: ", String.class);
        User user = api.getUser(username);
        try{
            api.removeUser(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void addRolesFromFile(){
        menuItemHeader("add roles from file");
        String filePath = getValueFromPrompt("Enter file path for Roles", String.class);
        try {
            MyArrayList<Role> roles = api.loadRoles(filePath);
            if (roles == null){
                System.out.println("Roles couldn't be loaded");
            } else {
                System.out.println("Roles loaded successfully");
            }
        } catch (InvalidFileException e){
            System.out.println(e.getMessage());
        }
    }

    public static void addPermissionsFromFile(){
        menuItemHeader("add permissions from file");
        String filePath = getValueFromPrompt("Enter file path for permissions", String.class);
        try{
            MyArrayList<Permission> permissions = api.loadPermissions(filePath);
            if (permissions == null){
                System.out.println("Permissions couldn't be loaded");
            } else {
                System.out.println("Permissions added successfully");
            }
        } catch (InvalidFileException e){
            System.out.println(e.getMessage());
        }
    }

    public static void addRole(){
        menuItemHeader("add role");
        String roleName = getValueFromPrompt("Enter a role name: ", String.class);
        api.addRole(roleName);
    }

    public static void showRoles(){
        menuItemHeader("show roles");
        api.printRoles();
    }

    public static void updateRole(){
        menuItemHeader("update role");
        String roleName = getValueFromPrompt("Enter a role name: ", String.class);
        Role role = api.getRole(roleName);
        if (role == null){
            System.out.println("Role not found");
            return;
        } else {
            System.out.println("Role: " + role.name());
        }
        String newRoleName = getValueFromPrompt("Enter a new role name: ", String.class);
        try{
            api.updateRole(role, newRoleName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void removeRole(){
        menuItemHeader("remove role");
        String roleName = getValueFromPrompt("Enter existing role name: ", String.class);
        Role role = api.getRole(roleName);
        try{
            api.removeRole(role);
            System.out.println("Role removed successfully");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void addPermission(){
        menuItemHeader("add permission");
        String permissionName = getValueFromPrompt("Enter a permission name: ", String.class);
        api.addPermission(permissionName);
    }

    public static void showPermissions(){
        menuItemHeader("show permissions");
        api.printPermissions();
    }

    public static void updatePermission(){
        menuItemHeader("update permission");
        String permissionName = getValueFromPrompt("Enter existing permission name: ", String.class);
        Permission permission = api.getPermission(permissionName);
        if(permission == null){
            System.out.println("Permission does not exist");
        } else {
            System.out.println("Permission: " + permission.name());
        }

        String newPermissionName = getValueFromPrompt("Enter new permission name: ", String.class);
        try{
            api.updatePermission(permission, newPermissionName);
            System.out.println("Permission updated successfully");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void removePermission(){
        menuItemHeader("remove permission");
        String permissionName = getValueFromPrompt("Enter existing permission name: ", String.class);
        Permission permission = api.getPermission(permissionName);
        try{
            api.removePermission(permission);
            System.out.println("Permission removed successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addAccountToUser(){
        menuItemHeader("add account to user");
        String username = getValueFromPrompt("Enter username to add account to: ", String.class);
        User user = api.getUser(username);
        try{
            Long acctId = api.addAccountToUser(user);
            System.out.println("Account ID for new account is: " + acctId);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void getAccount(){
        menuItemHeader("get account");
        String username = getValueFromPrompt("Enter username: ", String.class);
        Long acctId = getValueFromPrompt("Enter account ID: ", Long.class);
        User user = api.getUser(username);
        if (user == null){
            System.out.println("User not found");
        } else {
            Account account = api.getAccount(user, acctId);
            if (account == null){
                System.out.println("Account does not exist!");
            } else {
                System.out.println("------------------------------------");
                System.out.println("User credentials: " + user.getFirstName() + " " + user.getLastName());
                System.out.println("Account ID: " + account.getAccountId());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("------------------------------------");
            }
        }

    }
    public static void removeAccountFromUser(){
        menuItemHeader("remove account from user");
        String username = getValueFromPrompt("Enter username: ", String.class);
        User user = api.getUser(username);
        if (user == null){
            System.out.println("User not found");
        } else {
            Long acctId = getValueFromPrompt("Enter account ID: ", Long.class);
            Account account = api.getAccount(user, acctId);
            if (account == null){
                System.out.println("Account does not exist!");
            } else {
                try {
                    api.removeAccountFromUser(user, acctId);
                    System.out.println("Account removed successfully");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    public static void getPermissionsByRoleName(){
        menuItemHeader("get permissions by role name");
        String roleName = getValueFromPrompt("Enter role name: ", String.class);
        Role role = api.getRole(roleName);
        if (role == null){
            System.out.println("Role not found");
        } else {
            HashSet<Permission> permissions = api.getPermissionsByRoleName(role);
            if (permissions == null){
                System.out.println("Permissions not found for this role");
            } else {
                System.out.println(permissions.stream()
                        .map(permission -> permission.name())
                        .collect(Collectors.joining(", ", "[", "]")));
            }

        }
    }
    public static void addPermissionsToRole(){
        menuItemHeader("add permissions to role");
        String roleName = getValueFromPrompt("Enter role name: ", String.class);
        Role role = api.getRole(roleName);
        if (role == null){
            System.out.println("Role not found");
        } else {
            MyArrayList<Permission> permissions = new MyArrayList<>();
            boolean askForPermission = true;
            while (askForPermission){
                System.out.println("""
                        1. Add permission
                        2. Quit
                        """);
                Integer option = getValueFromPrompt("Enter 1 to add permission, 2 to quit.", Integer.class);
                if (option == 2){
                    askForPermission = false;
                } else if (option == 1) {
                    String permissionName = getValueFromPrompt("Enter permission name: ", String.class);
                    Permission permission = api.getPermission(permissionName);
                    if (permission == null){
                        System.out.println("Permission with this name not found");
                        continue;
                    }
                    permissions.add(permission);
                }
            }
            api.addPermissionsToRole(role, permissions);
            System.out.println("Permissions added successfully");
        }
    }

    public static void removePermissionsFromRole(){
        menuItemHeader("remove permissions from role");
        String roleName = getValueFromPrompt("Enter role name: ", String.class);
        Role role = api.getRole(roleName);
        if (role == null){
            System.out.println("Role not found");
        } else {
            try{
                api.removePermissionsFromRole(role);
            } catch (InvalidRole e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static void grantUserRole(){
        menuItemHeader("grant user role");
        grantOrRemoveUserRole("grant");
    }
    public static void removeUserRole(){
        menuItemHeader("remove user role");
        grantOrRemoveUserRole("remove");
    }

    private static void grantOrRemoveUserRole(String action){
        String username = getValueFromPrompt("Enter username: ", String.class);
        User user = api.getUser(username);
        if (user == null){
            System.out.println("User not found");
            return;
        }

        String roleName = getValueFromPrompt("Enter role name: ", String.class);
        Role role = api.getRole(roleName);
        if (role == null){
            System.out.println("Role not found");
            return;
        }

        try{
            if (action.equals("grant")){
                api.grantUserRole(user, role);
            } else if (action.equals("remove")) {
                api.removeUserRole(user, role);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void deposit(){
        menuItemHeader("deposit funds");
        depositOrWithdrawFunds("deposit");
    }
    public static void withdrawFunds(){
        menuItemHeader("withdraw funds");
        depositOrWithdrawFunds("withdraw");
    }

    private static void depositOrWithdrawFunds(String action){
        String username = getValueFromPrompt("Enter username: ",  String.class);
        Long accountId = getValueFromPrompt("Enter account ID: ", Long.class);
        Double amount = getValueFromPrompt("Enter amount to deposit: ", Double.class);

        try{
            if (action.equals("deposit")){
                if (api.deposit(username, accountId, amount, username)){
                    System.out.println("Deposited successfully");
                } else {
                    System.out.println("Amount is less than 0 or exceeds maximum deposit limit");
                }

            } else if (action.equals("withdraw")) {
                if (api.withdrawFunds(username, accountId, amount, username)){
                    System.out.println("Withdrew successfully");
                } else {
                    System.out.println("Amount is less than 0 or exceeds maximum withdrawal limit");
                }
            }
        } catch (Exception e){
            ExceptionHandling.handleException(e, username);
        }
    }

    public static void transferFunds(){
        menuItemHeader("transfer funds");
        String senderUsername = getValueFromPrompt("Enter your username: ", String.class);
        String receiverUsername = getValueFromPrompt("Enter receiver username: ", String.class);
        Long senderAccountId = getValueFromPrompt("Enter your account ID: ", Long.class);
        Long receiverAccountId = getValueFromPrompt("Enter receiver account ID: ", Long.class);
        Double amount = getValueFromPrompt("Enter transfer amount", Double.class);

        try {
            boolean result = api.transferFunds(senderUsername, receiverUsername, senderAccountId, receiverAccountId, amount);
            if (result){
                System.out.println(amount + " GEL successfully transferred from " + senderAccountId + " to " + receiverAccountId);
            } else {
                System.out.println("Could not transfer money");
            }
        } catch (Exception e){
            ExceptionHandling.handleException(e);
        }

    }
    private static <T> T getValueFromPrompt(String message, Class<T> type){
        T value = null;
        do {
            prompt(message);
            String input = userInput.nextLine();
            try{
                if (type == Integer.class){
                    value = type.cast(Integer.parseInt(input));
                } else if (type == Double.class) {
                    value = type.cast(Double.parseDouble(input));
                } else if (type == String.class) {
                    value = type.cast(input);
                } else if (type == Long.class) {
                    value = type.cast(Long.parseLong(input));
                } else if (type == Gender.class){
                    value = type.cast(getGender());
                }
                else {
                    System.out.println("Wrong type!");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a valid " + type.getSimpleName());
            }
        } while (value == null);

        return value;
    }

    private static UpdateField getUserUpdateField(){
        return selectEnum(UpdateField.class, """
                1. Username
                2. First Name
                3. Last Name
                4. Age
                5. Gender
                6. Company
                """);
    }
    private static Gender getGender(){
        return selectEnum(Gender.class, """
                1. Male
                2. Female
                3. Other
                """);
    }
    public static MethodsMenuItem menu(){
        System.out.println("-----------------------------------------------");
        System.out.println("| Method Number | Description                  |");
        System.out.println("-----------------------------------------------");
        System.out.println("|      1        | Add User                     |");
        System.out.println("|      2        | Get User                     |");
        System.out.println("|      3        | Update User                  |");
        System.out.println("|      4        | Remove User                  |");
        System.out.println("|      5        | Add Roles from File          |");
        System.out.println("|      6        | Add Permissions from File    |");
        System.out.println("|      7        | Add Role                     |");
        System.out.println("|      8        | Show Roles                   |");
        System.out.println("|      9        | Update Role                  |");
        System.out.println("|     10        | Remove Role                  |");
        System.out.println("|     11        | Add Permission               |");
        System.out.println("|     12        | Show Permissions             |");
        System.out.println("|     13        | Update Permission            |");
        System.out.println("|     14        | Remove Permission            |");
        System.out.println("|     15        | Add Account to User          |");
        System.out.println("|     16        | Get Account                  |");
        System.out.println("|     17        | Remove Account from User     |");
        System.out.println("|     18        | Get Permission by Role Name  |");
        System.out.println("|     19        | Add Permissions to Role      |");
        System.out.println("|     20        | Remove Permissions from Role |");
        System.out.println("|     21        | Grant User Role              |");
        System.out.println("|     22        | Remove User Role             |");
        System.out.println("|     23        | Deposit                      |");
        System.out.println("|     24        | Withdraw                     |");
        System.out.println("|     25        | Transfer Money               |");
        System.out.println("|     26        | Exit Program                 |");
        System.out.println("------------------------------------------------");
        return selectEnum(MethodsMenuItem.class, "Enter method number to call: ");
    }
    private static UserCategory getUserCategory(){
        return selectEnum(UserCategory.class, """
                1. Accountant\s
                2. Customer
                3. It employee
                4. Manager
                """);
    }

    private static void prompt(String message){
        System.out.println(message);
        System.out.print("> ");
    }

    private static <T extends Enum<T>> T selectEnum(Class<T> enumClass, String message){
        System.out.println();
        System.out.println(message);
        System.out.print("> ");

        int selection;
        try{
            selection = userInput.nextInt();
            userInput.nextLine();
            return enumClass.getEnumConstants()[selection - 1];
        }catch (Exception e){
            System.out.println("Invalid selection, please try again!");
            userInput.nextLine();
            return selectEnum(enumClass, message);
        }
    }
    private static void  menuItemHeader(String header){
        System.out.println("--" + header.toUpperCase() + "--");
    }
}
