package org.user;

import org.utils.Account;
import org.utils.AcctIdGenerator;
import org.utils.User;


public class BusinessAccount implements Account {
    private Double balance = 0.0;
    private Long accountId;
    private User accountHolder;
    private Double commisPrct = 0.08;
    private Double maxCreditLimit = 20000.0;

    private final Double maxDebitLimit = 200000.0;

    public BusinessAccount(User accountHolder){
        this.accountId = AcctIdGenerator.generateAcctId();
        accountHolder.addAccount(this);
        this.accountHolder = accountHolder;
    }
    @Override
    public boolean debit(Double amount) {
        if (amount < 0 || amount > maxDebitLimit){
            return false;
        }
        balance += amount;
        return true;
    }

    @Override
    public boolean credit(Double amount) {
        Double totalAmount = amount + amount * commisPrct;
        if (balance < totalAmount || amount > maxCreditLimit) {
            return false;
        } else {
            balance -= totalAmount;
            return true;
        }
    }

    @Override
    public boolean transferTo(Double amount, Account otherAccount) {
        Double totalAmount = amount + amount * commisPrct;
        if (credit(amount)){
            otherAccount.debit(totalAmount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void showBalance() {
        System.out.println("Balance of business account is: " + balance);
    }

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public Double getBalance(){
        return balance;
    }

    public User getAccountHolder(){
        return accountHolder;
    }
}
