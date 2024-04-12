package org.database;

import org.exceptions.InvalidFileException;
import org.exceptions.RoleNotAllowed;
import org.security.Permission;
import org.security.Role;
import org.utils.MyArrayList;
import org.utils.User;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;

public class DatabaseSimulation {
    private static Long maxAcctId;
    private MyArrayList<User> users = new MyArrayList<>();
    private MyArrayList<Role> roles = new MyArrayList<>();
    private MyArrayList<Permission> permissions = new MyArrayList<>();

    public class RolePermissionLink{
        private static final HashMap<Role, HashSet<Permission>> roleToPermission = new HashMap<>();

        public RolePermissionLink(){

        }
        public RolePermissionLink(Role role, HashSet<Permission> permissions){
            roleToPermission.put(role, permissions);
        }

        public void addRole(Role role){
            HashSet<Permission> permissions = new HashSet<>();
            roleToPermission.put(role, permissions);
        }

        public void addPermissionToRole(Role role, Permission permission){

            if(!roleToPermission.containsKey(role)){
                addRole(role);
                HashSet<Permission> newPermissions = roleToPermission.get(role);
                newPermissions.add(permission);
                roleToPermission.put(role, newPermissions);
            } else {
                HashSet<Permission> newPermissions = roleToPermission.get(role);
                newPermissions.add(permission);
                roleToPermission.put(role, newPermissions);
            }
        }

    }

    public class UserRoleLink{
        private static final HashMap<User, HashSet<Role>> userRoleMap = new HashMap<>();


        public void addUser(User user){
            HashSet<Role> roles = new HashSet<>();
            userRoleMap.put(user, roles);
        }

        public void removeRoleFromUser(User user, Role role){
            HashSet<Role> roles = userRoleMap.get(user);
            roles.remove(role);
            userRoleMap.put(user, roles);
        }
        public void addRoleToUser(User user, Role role){
            if(!userRoleMap.containsKey(user)){
                addUser(user);
                HashSet<Role> newRoleSet = userRoleMap.get(user);
                newRoleSet.add(role);
                userRoleMap.put(user, newRoleSet);
            }else {
                HashSet<Role> newRoleSet = userRoleMap.get(user);
                newRoleSet.add(role);
                userRoleMap.put(user, newRoleSet);
            }
        }

    }
    public MyArrayList<Role> readRoles(String filePath){
        MyArrayList<String> tokens = getTokens(filePath);
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            Role role = new Role(token);
            roles.add(role);
        }
        return roles;
    }

    public MyArrayList<Permission> readPermissions(String filePath){
        MyArrayList<String> tokens = getTokens(filePath);
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            Permission permission= new Permission(token);
            permissions.add(permission);
        }
        return permissions;
    }

    private MyArrayList<String> getTokens(String filePath){
        Reader reader;
        MyArrayList<String> tokens = new MyArrayList<>();
        try{
            reader = new FileReader(filePath);
            StringBuilder line = new StringBuilder();
            int charRead;

            while ((charRead = reader.read()) != -1){
                char c = (char) charRead;
                if (c == ';'){
                   tokens.add(line.toString());
                   line.setLength(0);
                } else {
                    line.append(c);
                }
            }

            if (!line.isEmpty()){
                tokens.add(line.toString());
            }
            reader.close();

        } catch (IOException e){
            throw new InvalidFileException("Error during reading file from " + filePath);
        }
        return tokens;
    }

    public static Long getMaxAcctId(){
        return maxAcctId;
    }

    public static void setMaxAcctId(Long maxAcctId) {
        DatabaseSimulation.maxAcctId = maxAcctId;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void deleteUserFromUserList(User user){
        users.remove(user);
    }

    public User getUser(String username){
        User foundUser = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)){
                foundUser = users.get(i);
            }
        }
        return foundUser;
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public MyArrayList<Role> getRoles(){
        return roles;
    }

    public void deleteRole(Role role){
        roles.remove(role);
    }

    public void addPermission(Permission permission){
        permissions.add(permission);
    }

    public MyArrayList<Permission> getPermissions(){
        return permissions;
    }
    public void addPermissionToRole(Role role, Permission permission){
        RolePermissionLink rpl = new RolePermissionLink();
        if(!roles.contains(role)){
            addRole(role);
        }
        rpl.addPermissionToRole(role, permission);
    }

    public void addPermissionsToRole(Role role, MyArrayList<Permission> rolePermissions){
        RolePermissionLink rlp = new RolePermissionLink();
        if(!roles.contains(role)){
            addRole(role);
        }
        rolePermissions.forEach(permission -> {
            rlp.addPermissionToRole(role, permission);
        });
    }

    public HashSet<Permission> getPermissionsByRole(Role role){
         return RolePermissionLink.roleToPermission.get(role);
    }

    public void addRoleToUser(User user, Role role){
        if (user.isBankEmployee()){
            UserRoleLink urlink = new UserRoleLink();
            urlink.addRoleToUser(user, role);
        } else {
            throw new RoleNotAllowed("Role " + role.name() + " is not allowed for user " + user.getUsername());
        }
    }

    public void removeRoleFromUser(User user, Role role){
        UserRoleLink urlink = new UserRoleLink();
        urlink.removeRoleFromUser(user, role);
    }

    public HashSet<Role> getRolesFromUser(User user){
        return UserRoleLink.userRoleMap.get(user);
    }

    public HashMap<User, HashSet<Role>> getUserRoleMap(){
        return UserRoleLink.userRoleMap;
    }

    public HashMap<Role, HashSet<Permission>> getRolePermissionMap(){
        return RolePermissionLink.roleToPermission;
    }

    public MyArrayList<User> getUsers(){
        return users;
    }
}
