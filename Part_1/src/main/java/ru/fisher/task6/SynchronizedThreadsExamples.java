package ru.fisher.task6;


import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedThreadsExamples {

    /** Первый пример с использованием Ключевое слова synchronized, он показывает что если потоки будут не синхронизированы
     * и будут иметь доступ к изменению баланса в одно время (будет состояние гонки). И баланс может измениться не правильно.
     * К примеру работу в банковских операциях.
     */
    public static class FirstExample {

        private static int numOfThreads = 1000;

        private static int balance = 0;

        static synchronized void takeMoney(int amount) {
            if (balance < amount) {
                System.out.println("Недостаточно средств");
                return;
            }
            balance -= amount;
        }

        static synchronized void putMoney(int amount) {
            if (amount < 0) {
                System.out.println("Нельзя положить отрицательную сумму");
                return;
            }
            balance += amount;
        }

        public static void main(String[] args) {

            Thread[] putThreads = new Thread[numOfThreads];
            Thread[] takeThreads = new Thread[numOfThreads];

            // 1. Запускаем потоки на пополнение
            for (int i = 0; i < numOfThreads; i++) {
                putThreads[i] = new Thread(() -> putMoney(100000));
                putThreads[i].start();
            }

            // 2. Ждём завершения ВСЕХ потоков putMoney
            for (Thread thread : putThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // 3. Запускаем потоки на снятие
            for (int i = 0; i < numOfThreads; i++) {
                takeThreads[i] = new Thread(() -> takeMoney(10000));
                takeThreads[i].start();
            }

            // 4. Ждём завершения ВСЕХ потоков takeMoney
            for (Thread thread : takeThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("Финальный баланс: " + balance);

        }
    }

    /**
     * Второй пример работы с Семафором, здесь показан пример работы с БД.
     * Мы ограничиваем кол-во одновременных подключений к БД, чтобы снизить или распределить нагрузку.
     * По мере освобождения доступа к БД освобождаются и доступность к подключению к БД и работы с ней.
     */
    private static class SecondExample {

        private static final int permits = 3;
        private static final int numOfThreads = 10;

        public static void main(String[] args) {
            Semaphore semaphore = new Semaphore(permits);
            System.out.println("Ограничение на одновременное кол-во подключений: " + permits);

            for (int i = 0; i < numOfThreads; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        System.out.println("Идет подключение к БД от " + Thread.currentThread().getName());
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " поток получил доступ к БД. "
                                + "Свободно мест: " + semaphore.availablePermits());

                        Thread.sleep(2000); // работа с БД

                        semaphore.release();
                        System.out.println("Поток " + Thread.currentThread().getName() + " освободил место. "
                                + "Свободно мест: " + semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread.start();

            }
        }

    }

    /**
     * В третьем примере показан работа ExecutorService для распределения задач между потоками и управления ими.
     * Очень удобный способ задать определенную последовательность действий.
     */

    private static class ThirdExample {
        private static int threadsCount = 3;
        private static ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        private static ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        public static void oneMethod(Integer id) {
            map.put(id, "Работа 1-го метода: " + Thread.currentThread().threadId());
            try {
                Thread.sleep(1000);
                System.out.println("Работа 1-го метода: " + Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public static void otherMethod(Integer id) {
            map.put(id, "Работа 2-го метода: " + Thread.currentThread().threadId());
            try {
                Thread.sleep(1000);
                System.out.println("Работа 2-го метода: " + Thread.currentThread().threadId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public static void main(String[] args) {

            for (int i = 0; i < threadsCount; i++) {
                int id = i;
                executorService.submit(() -> {
                    otherMethod(id);
                    oneMethod(id);
                    try {
                        // Имитация промежуточной работы
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Имитация промежуточной работы");
                    otherMethod(id);
                    oneMethod(id);
                });
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(20, TimeUnit.SECONDS); // ждем завершения всех потоков
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Выводим итоговое состояние
            System.out.println("\nИтоговое состояние Map:");
            map.forEach((k, v) -> System.out.println(k + " => " + v));
        }

    }

    /**
     * В четвертом примере используется CountDownLatch, для ограничения работы потоков, пока не наступят 2 события потоки будут ждать.
     * Имитируется работа по запуску сервиса, когда сначала идет подключение к БД, а затем какая-либо загрузка. После чего потоки продолжают работать.
     */
    private static class ForthExample {
        private static final int events = 2;
        public static void main(String[] args) throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(events);

            new Thread(() -> {
                try {
                    System.out.println("Ожидание подключения к БД...");
                    latch.await(); // блокировка
                    System.out.println(Thread.currentThread().getName() + " продолжаем работу");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            new Thread(() -> {
                try {
                    System.out.println("Ожидание загрузки всех компонентов...");
                    latch.await(); // блокировка
                    System.out.println(Thread.currentThread().getName() + " продолжаем работу");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            Thread.sleep(3000); // Симуляция работы с БД
            System.out.println("Соединение установлено");
            latch.countDown(); // уменьшение счетчика событий

            Thread.sleep(2000); // Симуляция загрузки компонентов
            System.out.println("Все компоненты загружены");
            latch.countDown();
        }

    }

    /**
     * Пятый пример связан с блокировкой потока чтения-запись ReadWriteLock.
     * В данном примере создаются несколько потоков для чтения и записи.
     * И в случае блокировки одним потоком записи, другие потоки для записи будут ожидать, когда занятый поток высвободит ресурс для записи.
     * А уже в свою очередь потоки для чтения могут спокойно работать параллельно т.е одновременно.
     */
    private static class FifthExample {
        private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private static final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        private static final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        private static String name = "Исходные данные";

        public static void read() {
            readLock.lock();
            try {
                // Чтение данных
                System.out.println(Thread.currentThread().getName() + " Читает данные: " + name);
                Thread.sleep(1000); // Имитация чтения
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                readLock.unlock();
            }
        }

        public static void write() {
            writeLock.lock();
            try {
                // Запись данных
                name = name + " Изменено: " + LocalDateTime.now().getSecond();
                System.out.println(Thread.currentThread().getName() + " - " + name);
                Thread.sleep(1000); // Имитация записи
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                writeLock.unlock();
            }

        }

        public static void main(String[] args) {
            // Потоки для чтения (могут работать параллельно)
            for (int i = 0; i < 5; i++) {
                new Thread(FifthExample::read).start();
            }

            // Поток для записи (может работать только один поток)
            for (int i = 0; i < 5; i++) {
                new Thread(FifthExample::write).start();
            }

        }
    }


}
