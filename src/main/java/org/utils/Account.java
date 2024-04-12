package org.utils;

public interface Account {
    public boolean debit(Double amount);
    public boolean credit(Double amount);
    public boolean transferTo(Double amount, Account otherAccount);
    public void showBalance();
    public Long getAccountId();
    public Double getBalance();
}
