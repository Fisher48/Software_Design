package ru.fisher.task2;

import java.util.logging.Logger;

public class BankAccount {

    Logger log = Logger.getLogger(BankAccount.class.getName());

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        if (amount < 0) {
            log.warning("Нельзя внести отрицательную сумму");
            return;
        }
        this.balance += amount;
    }

    public void withdraw(int amount) {
        if (amount < 0) {
            log.warning("Нельзя снять отрицательное значение");
            return;
        }
        if (this.balance < amount) {
            log.warning("На счете недостаточно средств для снятия");
            return;
        }
        this.balance -= amount;
    }

    public double getBalance() {
        return balance;
    }


    public static void main(String[] args) {
        // Создаем счет
        BankAccount bankAccount = new BankAccount(5000);
        System.out.println("Изначальный баланс: " + bankAccount.getBalance());

        // Пополняем счет на некоторую сумму
        bankAccount.deposit(100);
        bankAccount.deposit(500);
        System.out.println("Текущий баланс после добавления 100 и 500: "
                + bankAccount.getBalance());

        // Снимаем обычную сумму в пределах баланса счета
        bankAccount.withdraw(1000);
        System.out.println("Текущий баланс после снятия 1000: "
                + bankAccount.getBalance());

        // Снимаем со счета сумму, больше чем есть на счете
        bankAccount.withdraw(6000);
        System.out.println("Текущий баланс, после попытки снять 6000: "
                + bankAccount.getBalance());

        bankAccount.deposit(-100); // Некорректный депозит
        System.out.println("Баланс после некорректного депозита -100: "
                + bankAccount.getBalance());

        bankAccount.withdraw(-50); // Некорректное снятие
        System.out.println("Баланс после некорректного снятия -50: "
                + bankAccount.getBalance());
    }

}


