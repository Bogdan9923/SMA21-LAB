package com.upt.cti.smartwallet;

public class MonthlyExpenses {
    public String month;
    private float income,expenses;

    public MonthlyExpenses(){

    }

    public MonthlyExpenses(String month, float income, float expenses)
    {
        this.month = month;
        this.expenses = expenses;
        this.income = income;

    }

    public String getMonth()
    {
        return month;
    }

    public float getExpenses()
    {
        return expenses;
    }

    public float getIncome()
    {
        return income;
    }

}
