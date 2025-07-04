package ru.fisher.task8;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Для данного примера предлагаю использовать AtomicInteger, что делает операцию инкремента потокобезопасной.
 * Она атомарна и не требует явного использования синхронизации и традиционных блокировок.
 * Я увеличил кол-во операций до 100000, чтобы явно продемонстрировать проблему RaceCondition.
 */
public class ThreadExample {
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                counter.incrementAndGet();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + counter);
    }
}
