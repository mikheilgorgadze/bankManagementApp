package org.security;


import org.utils.User;

import java.util.ArrayList;

public class Role {
    private String role;

    public Role(String role){
        this.role= role;
    }

    public String name(){
        return role;
    }

    public void setRoleName(String role){
        this.role = role;
    }

}
