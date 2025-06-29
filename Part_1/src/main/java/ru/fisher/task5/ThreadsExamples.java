package ru.fisher.task5;


public class ThreadsExamples {

    /**
     * В этом примере, где происходит состояние гонки (RaceCondition), несколько потоков одновременно имеют доступ к одной переменной
     * из-за этого невозможно предсказать какой будет результат, исправить это можно несколькими способами я реализовал через synchronized.
     * Мы блокируем доступ к переменной для каждого потока. Альтернативные варианты это использовать AtomicInteger или интерфейс Lock
     */
    public static class RaceConditionExample {

        private static int counter = 0;
        private static final Object lock = new Object(); // общий объект монитор

        public static void main(String[] args) {
            int numberOfThreads = 10;
            Thread[] threads = new Thread[numberOfThreads];

            for (int i = 0; i < numberOfThreads; i++) {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < 100000; j++) {
                        synchronized (lock) { // Синхронизация на операции инкремента
                            counter++;
                        }
                    }
                });
                threads[i].start();
            }

            for (int i = 0; i < numberOfThreads; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Final counter value: " + counter);
        }
    }


    /**
     * В пример ошибки "deadlock", возникает взаимная блокировка так как потоки начинают ссылаться друг на друга
     * (lock1 -> lock2 и lock2 -> lock1) образуя тем самым замкнутый цикл.
     * Поток 1 захватывает lock1 и ждет освобождения lock2, а Поток 2 захватывает lock2 и ждет lock1.
     * Чтобы этого избежать стоит соблюдать порядок синхронизации (lock1 -> lock2),
     * и для этого я изменил порядок захвата локов во 2-м потоке.
     */
    public static class DeadlockExample {

        private static final Object lock1 = new Object();
        private static final Object lock2 = new Object();

        public static void main(String[] args) {
            Thread thread1 = new Thread(() -> {
                synchronized (lock1) {
                    System.out.println("Thread 1 acquired lock1");

                    try { Thread.sleep(50); }
                    catch (InterruptedException e) { e.printStackTrace(); }

                    synchronized (lock2) {
                        System.out.println("Thread 1 acquired lock2");
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                synchronized (lock1) { // Теперь оба потока захватывают lock1 первым
                    System.out.println("Thread 2 acquired lock1");

                    try { Thread.sleep(50); }
                    catch (InterruptedException e) { e.printStackTrace(); }

                    synchronized (lock2) {
                        System.out.println("Thread 2 acquired lock2");
                    }
                }
            });

            thread1.start();
            thread2.start();

            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Finished");
        }
    }

}

