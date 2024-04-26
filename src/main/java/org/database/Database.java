package org.database;

import org.security.Role;
import org.utils.MyArrayList;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

    public static void main(String[] args) {
        Database db = new Database();
        System.out.println(db.getRoles());
    }
    public void addRole(Role role) {
        try{
            String roleName = role.name();
            Connection connection = DatabaseSetup.setupConnection();
            String sql = "INSERT INTO bms_schema.roles(role_name) values(?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, roleName);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public MyArrayList<Role> getRoles() {
        try{
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
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
