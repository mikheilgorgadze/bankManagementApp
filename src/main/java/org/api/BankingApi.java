package org.api;

import org.structs.Gender;
import org.structs.UpdateField;
import org.structs.UserCategory;
import org.utils.Account;
import org.utils.MyArrayList;
import org.utils.User;
import org.security.Role;
import org.security.Permission;

import java.util.HashSet;

public interface BankingApi {
    void addUser(String username, String firstName, String lastName, Integer age, Gender gender, String company, UserCategory userType);
    User getUser(String username);
    void updateUser(User user, UpdateField field, Object value);
    void removeUser(User user);
    Role addRole(String roleName);
    MyArrayList<Role> getRoles();
    Role getRole(String roleName);
    void updateRole(Role role, String roleName);
    void removeRole(Role role);
    Permission addPermission(String permissionName);
    Permission getPermission(String permissionName);
    public MyArrayList<Permission> getPermissions();
    void updatePermission(Permission permission, String permissionName);
    void removePermission(Permission permission);
    Long addAccountToUser(User user);
    Account getAccount(User user, Long accountId);
    void removeAccountFromUser(User user, Long accountId);
    HashSet<Permission> getPermissionsByRoleName(Role role);
    void addPermissionsToRole(Role role, MyArrayList<Permission> permissions);
    void removePermissionsFromRole(Role role);
    void grantUserRole(User user, Role role);
    void removeUserRole(User user, Role role);
    boolean deposit(String username, Long accountId, Double amount, String performerUser);
    boolean withdrawFunds(String username, Long accountId, Double amount, String performerUser);
    boolean transferFunds(String sender, String receiver, Long fromAccountId, Long toAccountId, Double amount);
    MyArrayList<Permission> loadPermissions(String filePath);
    public MyArrayList<Role> loadRoles(String filePath);
    void printUsers();
    void printRoles();
    void printPermissions();
}
