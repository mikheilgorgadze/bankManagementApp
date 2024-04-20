package org.api;

import org.database.DatabaseSimulation;
import org.exceptions.*;
import org.security.Permission;
import org.security.Role;
import org.structs.Gender;
import org.structs.UpdateField;
import org.structs.UserCategory;
import org.user.*;
import org.utils.Account;
import org.utils.MyArrayList;
import org.utils.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class BankingApiImpl implements BankingApi{
    private static final DatabaseSimulation db = new DatabaseSimulation();
    @Override
    public void addUser(String username,
                        String firstName,
                        String lastName,
                        Integer age,
                        Gender gender,
                        String company,
                        UserCategory userType) {

        if (age == null || age < 0 ){
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        User user = switch (userType) {
            case MANAGER -> new Manager(username, firstName, lastName);
            case CUSTOMER -> new Customer(username, firstName, lastName);
            case ACCOUNTANT -> new Accountant(username, firstName, lastName);
            case IT_EMPLOYEE -> new ItEmployee(username, firstName, lastName);
            default -> throw new NoSuchUserType("User type " + userType + " is invalid");
        };
        user.setAge(age);
        user.setGender(gender);
        user.setCompany(company);

        db.addUser(user);
    }

    @Override
    public User getUser(String username) {
        return db.getUser(username);
    }

    @Override
    public void updateUser(User user, UpdateField field, Object value) {
        validateUser(user);
        if (field == UpdateField.AGE) {
            if ((Integer) value < 0 ){
                throw new IllegalArgumentException("Invalid age " + value);
            }
        }

        switch (field){
            case USERNAME -> {
                user.removeFromUsernames(user.getUsername());
                user.setUsername((String)value);
            }
            case FIRST_NAME -> user.setFirstName((String)value);
            case LAST_NAME -> user.setLastName((String)value);
            case AGE -> user.setAge((Integer) value);
            case GENDER -> {
                try {
                    user.setGender((Gender) value);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid gender " + value);
                }
            }
            case COMPANY -> user.setCompany((String) value);
            default -> throw new IllegalArgumentException("Unknown field");
        }
    }

    @Override
    public void removeUser(User user) {
        validateUser(user);
        db.deleteUserFromUserList(user);
    }

    @Override
    public Role addRole(String roleName) {
        Role role = new Role(roleName);
        db.addRole(role);
        return role;
    }

    @Override
    public Role getRole(String roleName) {
        MyArrayList<Role> roles = db.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            if (role.name().equals(roleName)){
                return role;
            }

        }
        return null;
    }

    @Override
    public MyArrayList<Role> getRoles(){
        return db.getRoles();
    }




    @Override
    public void updateRole(Role role, String roleName) {
        validateRole(role);
        role.setRoleName(roleName);
    }

    @Override
    public void removeRole(Role role) {
        validateRole(role);
        HashMap<User, HashSet<Role>> userRoleLink = db.getUserRoleMap();
        for (User user : userRoleLink.keySet()){
            HashSet<Role> roles = userRoleLink.get(user);
            roles.remove(role);

            if (roles.isEmpty()){
                userRoleLink.remove(user);
            }
        }
        db.getRoles().remove(role);
    }

    @Override
    public Permission addPermission(String permissionName) {
        Permission permission = new Permission(permissionName);
        db.addPermission(permission);
        return permission;
    }

    @Override
    public MyArrayList<Permission> getPermissions(){
        return db.getPermissions();
    }
    @Override
    public Permission getPermission(String permissionName) {
        MyArrayList<Permission> permissions = db.getPermissions();
        for (int i = 0; i < permissions.size(); i++) {
            Permission permission = permissions.get(i);
            if (permission.name().equals(permissionName)){
                return permission;
            }
        }
        return null;
    }

    @Override
    public void updatePermission(Permission permission, String permissionName) {
        validatePermission(permission);
        permission.setPermission(permissionName);
    }

    @Override
    public void removePermission(Permission permission) {
        validatePermission(permission);
        HashMap<Role, HashSet<Permission>> rolePermissionLink = db.getRolePermissionMap();
        for(Role role : rolePermissionLink.keySet()){
            HashSet<Permission> permissions = rolePermissionLink.get(role);
            permissions.remove(permission);

            if (permissions.isEmpty()){
                rolePermissionLink.remove(role);
            }
        }
        db.getPermissions().remove(permission);
    }

    @Override
    public Long addAccountToUser(User user) {
        validateUser(user);
        Account account = new StandardAccount(user);
        user.increaseAccountCount(1);
        if (user.getAccountCount() > 3){
            throw new AccountLimitReached("You reached account limit");
        }
        user.addAccount(account);
        return account.getAccountId();
    }

    @Override
    public Account getAccount(User user, Long accountId) {
        ArrayList<Account> userAccounts = user.getAccounts();
        for (Account account : userAccounts){
            if (account.getAccountId().equals(accountId)){
                return account;
            }
        }
        return null;
    }

    @Override
    public void removeAccountFromUser(User user, Long accountId) {
        validateUser(user);
        ArrayList<Account> userAccounts = user.getAccounts();
        userAccounts.removeIf(account -> account.getAccountId().equals(accountId));
    }

    @Override
    public HashSet<Permission> getPermissionsByRoleName(Role role) {
        return db.getPermissionsByRole(role);
    }

    @Override
    public void addPermissionsToRole(Role role, MyArrayList<Permission> permissions) {
        db.addPermissionsToRole(role, permissions);
    }

    @Override
    public void removePermissionsFromRole(Role role) {
        validateRole(role);
        db.getRolePermissionMap().remove(role);
    }

    @Override
    public void grantUserRole(User user, Role role) {
        validateUser(user);
        validateRole(role);
        db.addRoleToUser(user, role);
    }

    @Override
    public void removeUserRole(User user, Role role) {
        validateRole(role);
        validateUser(user);
        db.removeRoleFromUser(user, role);
    }

    @Override
    public boolean deposit(String username, Long accountId, Double amount, String performerUser) {
        User user = db.getUser(username);
        User performer = db.getUser(performerUser);
        Account acct = getUserAccountByAcctId(user, accountId);
        validateUser(user);
        validateUser(performer);
        validateAccount(acct, accountId);
        boolean hasPermission = checkPerformerUserPermission(performer, "Approve Deposit");
        if (hasPermission){
            return acct.debit(amount);
        } else {
            throw new NotEnoughPermission("User has not enough permission for deposit");
        }
    }

    @Override
    public boolean withdrawFunds(String username, Long accountId, Double amount, String performerUser) {
        User user = db.getUser(username);
        User performer = db.getUser(performerUser);
        Account acct = getUserAccountByAcctId(user, accountId);
        validateUser(user);
        validateUser(performer);
        validateAccount(acct, accountId);
        boolean  hasPermission = checkPerformerUserPermission(performer, "Approve Withdrawal");
        if(hasPermission){
            return acct.credit(amount);
        } else {
            throw new NotEnoughPermission("User has not enough permission for withdrawal");
        }
    }

    @Override
    public boolean transferFunds(String sender, String receiver, Long fromAccountId, Long toAccountId, Double amount) {
        User senderUser = db.getUser(sender);
        User receiverUser = db.getUser(receiver);
        Account fromAcct = getUserAccountByAcctId(senderUser, fromAccountId);
        Account toAcct = getUserAccountByAcctId(receiverUser, toAccountId);
        validateUser(senderUser);
        validateUser(receiverUser);
        validateAccount(fromAcct, fromAccountId);
        validateAccount(toAcct, toAccountId);
        boolean hasPermission = checkPerformerUserPermission(senderUser, "Approve Transfer");
        if(hasPermission){
            return fromAcct.transferTo(amount, toAcct);
        } else {
            throw new NotEnoughPermission("User has not enough permission for transfer");
        }
    }

    @Override
    public MyArrayList<Permission> loadPermissions(String filePath){
        return db.readPermissions(filePath);
    }

    @Override
    public MyArrayList<Role> loadRoles(String filePath){
        return db.readRoles(filePath);
    }

    @Override
    public void printUsers() {
        db.getUsers().forEach(this::printUser);
    }

    @Override
    public void printRoles() {
        MyArrayList<Role> roles = db.getRoles();
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < roles.size(); i++) {
            str.append(roles.get(i).name());
            if (i < roles.size() - 1) {
                str.append(", ");
            }
        }
        str.append("]");
        System.out.println("Roles: " + str);
    }

    @Override
    public void printPermissions() {
        MyArrayList<Permission> permissions = db.getPermissions();
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < permissions.size(); i++) {
            str.append(permissions.get(i).name());
            if (i < permissions.size() - 1) {
                str.append(", ");
            }
        }
        str.append("]");
        System.out.println("Permissions: " + str);
    }

    private void validateUser(User user){
        if(!db.getUsers().contains(user) || user == null) {
            throw new UserNotFoundException("User not found");
        }
    }

    private void validateRole(Role role){
        if (!db.getRoles().contains(role) || role == null) {
            throw new InvalidRole("Role does not exist");
        }
    }

    private void validatePermission(Permission permission){
        if(!db.getPermissions().contains(permission) || permission == null){
            throw new PermissionNotFound("Permission not found!");
        }
    }

    private void validateAccount(Account acct, Long accountId){
        if (acct == null){
            throw new AccountNotFound("Account with id " + accountId + " not found");
        }
    }

    private boolean checkPerformerUserPermission(User performer, String permissionName){
        HashSet<Role> performerRoles = db.getUserRoleMap().get(performer);

        return performerRoles != null && performerRoles.stream()
                .anyMatch(role -> db.getPermissionsByRole(role) != null && db.getPermissionsByRole(role)
                        .stream()
                        .anyMatch(permission -> permission.name()
                                .equals(permissionName)));
    }

    private Account getUserAccountByAcctId(User user, Long accountId){
        return user.getAccounts().stream()
                .filter(account -> account.getAccountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    public void printUser(User user){
        try{
            validateUser(user);
        } catch (Exception e){
            System.out.println("User not found!");
            return;
        }
        System.out.println("--------------------");
        System.out.println("Username: " + user.getUsername());
        System.out.println("First name: " + user.getFirstName());
        System.out.println("Last name: " + user.getLastName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Company: " + user.getCompany());
        System.out.println("--------------------");
    }
}
