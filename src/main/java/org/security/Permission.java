package org.security;

public class Permission {
    private String permission;

    public Permission(String permission){
        this.permission= permission;
    }

    public String name(){
        return permission;
    }

    public void setPermission(String permission){
        this.permission = permission;
    }
}
