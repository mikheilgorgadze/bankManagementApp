package org.user;
import org.exceptions.SalaryNotInRange;
import org.structs.Salary;
import org.utils.User;

public class Manager extends User {
    private Integer salary;
    public Manager(String username, String firstName, String lastName) {
        super(username, firstName, lastName);
    }

    @Override
    public boolean isBankEmployee() {
        return false;
    }

    public void setSalary(Integer salary){
        if (salary < Salary.MIN_SALARY.get() || salary > Salary.MAX_SALARY.get()) {
            throw new SalaryNotInRange("Salary is not in range of allowed salary");
        } else {
            this.salary = salary;
        }
    }

    public Integer getSalary() {
        return salary;
    }
}
