package ru.fisher.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Напишите тестовый класс, в котором создаётся объект BankAccount,
// выполняются различные операции и выводится результат баланса.
class BankAccountTest {

    @Test
    void simpleTest() {
        BankAccount bankAccount = new BankAccount(0);
        System.out.println("Изначальный баланс: " + bankAccount.getBalance());
        bankAccount.deposit(100);
        bankAccount.deposit(500);
        System.out.println("Текущий баланс после добавления 100 и 500: " + bankAccount.getBalance());
        bankAccount.withdraw(1000);
        System.out.println("Текущий баланс, после снятия 1000: " + bankAccount.getBalance());
        bankAccount.deposit(1000000000);
        System.out.println("Текущий баланс, после добавления очень большого числа: " + bankAccount.getBalance());

    }

}