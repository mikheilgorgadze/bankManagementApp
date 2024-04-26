package org.database;

import org.security.Role;
import org.utils.MyArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

    public static void main(String[] args) {
        try{
            Role role = new Role("Developer");
            addRole(role);
            System.out.println(getRoles());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void addRole(Role role) throws Exception{
        String roleName = role.name();
        Connection connection = DatabaseSetup.setupConnection();
        String sql = "INSERT INTO bms_schema.roles(role_name) values(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, roleName);
        statement.executeUpdate();
        connection.close();
    }

    public static MyArrayList<Role> getRoles() throws Exception {
        MyArrayList<Role> roles = new MyArrayList<>();
        Connection connection = DatabaseSetup.setupConnection();

        String sql = "SELECT * FROM bms_schema.roles";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet results = statement.executeQuery();

        while(results.next()){
            String roleName = results.getString("role_name");
            Role role = new Role(roleName);
            roles.add(role);
        }

        connection.close();
        return roles;
    }

}
