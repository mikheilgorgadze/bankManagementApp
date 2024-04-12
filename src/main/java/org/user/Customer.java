package org.user;

import org.utils.User;

public class Customer extends User {
    public Customer(String username, String firstName, String lastName){
        super(username, firstName, lastName);
    }

    @Override
    public boolean isBankEmployee() {
        return false;
    }
}
