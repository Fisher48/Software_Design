package ru.fisher.task2;

public class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public double getBalance() {
        return balance;
    }


    public static void main(String[] args) {
        // Создаем счет
        BankAccount bankAccount = new BankAccount(0);
        System.out.println("Изначальный баланс: " + bankAccount.getBalance());

        // Пополняем счет на некоторую сумму
        bankAccount.deposit(100);
        bankAccount.deposit(500);
        System.out.println("Текущий баланс после добавления 100 и 500: " + bankAccount.getBalance());

        // Снимаем со счета сумму, больше чем есть на счете
        bankAccount.withdraw(1000);
        System.out.println("Текущий баланс, после снятия 1000: " + bankAccount.getBalance());

        // Вносим очень большую сумму
        bankAccount.deposit(1000000000);
        System.out.println("Текущий баланс, после добавления очень большого числа: " + bankAccount.getBalance());
    }

}


