package ru.fisher.task9;

import java.util.Arrays;
import java.util.Random;

/**
 * Чтобы существенно упростить данный код я использовал стрим,
 * в котором создается поток элементов массива, включается параллельная обработка,
 * и в конце идет складывание элементов массива.
 */
public class ComplexMultiThreadProcessing {
    private static final int SIZE = 1000000;
    private static final int[] data = new int[SIZE];

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            data[i] = random.nextInt(100);
        }

        System.out.println("Sum of all elements: " + Arrays.stream(data).parallel().sum());
    }
}
