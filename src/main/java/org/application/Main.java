package org.application;

import org.structs.MethodsMenuItem;

public class Main {

    public static void main(String[] args) {
        while(true){
            MethodsMenuItem methodAction = MenuHandler.menu();
            if (methodAction.equals(MethodsMenuItem.EXIT)){
                break;
            }
            switch (methodAction){
                case ADD_USER -> MenuHandler.addUser();
                case GET_USER -> MenuHandler.getUser();
                case UPDATE_USER -> MenuHandler.updateUser();
                case REMOVE_USER -> MenuHandler.removeUser();
                case ADD_ROLES_FROM_FILE -> MenuHandler.addRolesFromFile();
                case ADD_PERMISSIONS_FROM_FILE -> MenuHandler.addPermissionsFromFile();
                case ADD_ROLE -> MenuHandler.addRole();
                case SHOW_ROLES -> MenuHandler.showRoles();
                case UPDATE_ROLE -> MenuHandler.updateRole();
                case REMOVE_ROLE -> MenuHandler.removeRole();
                case ADD_PERMISSION -> MenuHandler.addPermission();
                case SHOW_PERMISSION -> MenuHandler.showPermissions();
                case UPDATE_PERMISSION -> MenuHandler.updatePermission();
                case REMOVE_PERMISSION -> MenuHandler.removePermission();
                case ADD_ACCOUNT_TO_USER -> MenuHandler.addAccountToUser();
                case GET_ACCOUNT -> MenuHandler.getAccount();
                case REMOVE_ACCOUNT_FROM_USER -> MenuHandler.removeAccountFromUser();
                case GET_PERMISSIONS_BY_ROLE_NAME -> MenuHandler.getPermissionsByRoleName();
                case ADD_PERMISSIONS_TO_ROLE -> MenuHandler.addPermissionsToRole();
                case REMOVE_PERMISSIONS_FROM_ROLE -> MenuHandler.removePermissionsFromRole();
                case GRANT_USER_ROLE -> MenuHandler.grantUserRole();
                case REMOVE_USER_ROLE -> MenuHandler.removeUserRole();
                case DEPOSIT -> MenuHandler.deposit();
                case WITHDRAW_FUNDS -> MenuHandler.withdrawFunds();
                case TRANSFER_FUNDS -> MenuHandler.transferFunds();
                default -> System.out.println("Enter valid method");
            }
        }
    }
}
