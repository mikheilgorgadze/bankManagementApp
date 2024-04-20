package org.utils;

import org.database.DatabaseSimulation;
import org.exceptions.AccountAlreadyExists;

import java.util.HashSet;
import java.util.Set;

public class AcctIdGenerator {
    private static Long sequence = 1000L;
    private static final Set<Long> existingAccountIds = new HashSet<>();


    public static Long generateAcctId(){
        final int increment = 100;
        sequence += increment;
        if (existingAccountIds.contains(sequence)){
            throw new AccountAlreadyExists("Account already exists");
        }
        existingAccountIds.add(sequence);
        DatabaseSimulation.setMaxAcctId(sequence);
        return sequence;
    }

}
