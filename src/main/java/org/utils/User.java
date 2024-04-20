package org.utils;

import org.exceptions.UserAlreadyExistsException;
import org.structs.Gender;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class User {
    private String username;
    private static final HashSet<String> usernames = new HashSet<>();
    private String firstName;
    private String lastName;
    private Integer age;
    private Gender gender;
    private String company;
    private final ArrayList<Account> accounts = new ArrayList<>();
    private int accountCount = 0;

    public User(String username, String firstName, String lastName){
        if (isUsernameAlreadyUsed(username)) {
            throw new UserAlreadyExistsException("Username already used");
        } else {
            addToUsernames(username);
            this.username = username;
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public abstract boolean isBankEmployee();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (isUsernameAlreadyUsed(username)) {
            throw new UserAlreadyExistsException("Username already used");
        } else {
            addToUsernames(username);
            this.username = username;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    private void addToUsernames(String username) {
        usernames.add(username);
    }

    public void removeFromUsernames(String username){
        usernames.remove(username);
    }

    private boolean isUsernameAlreadyUsed(String username) {
        return usernames.contains(username);
    }

    public int getAccountCount(){
        return accountCount;
    }

    public void setAccountCount(int accountCount){
        this.accountCount = accountCount;
    }

    public void increaseAccountCount(int increment){
        accountCount += increment;
    }

}
