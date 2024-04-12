package org.structs;

public enum Salary {
    MIN_SALARY(1000),
    MAX_SALARY(10000);

    private final int salary;
    Salary(int salary){
        this.salary = salary;
    }

    public int get() {
        return salary;
    }
}

