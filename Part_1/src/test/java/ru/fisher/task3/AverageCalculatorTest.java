package ru.fisher.task3;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AverageCalculatorTest {

    Random random = new Random();

    // Создание массива со случайными элементами
    public int[] createRandomArray() {
        int n = 10;
        int[] arrayWithRandomNums = new int[n];
        for (int i = 0; i < n; i++) {
            arrayWithRandomNums[i] = random.nextInt(100) + 1;
        }
        return arrayWithRandomNums;
    }

    // Вывод в консоль
    public void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    // Повторяющийся тест со случайными значениями
    @RepeatedTest(1000)
    void randomValuesRepeatedTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        createRandomArray();
        int[] arrayWithRandomNums = createRandomArray();
        double sum = Arrays.stream(arrayWithRandomNums).sum();
        double avg = sum / arrayWithRandomNums.length;
        printArray(arrayWithRandomNums);
        System.out.println(averageCalculator.calculateAverage(arrayWithRandomNums));
        assertEquals(averageCalculator.calculateAverage(arrayWithRandomNums), avg);
    }

    // Проверка с нулями
    @Test
    void arrayWithZerosTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {0, 0, 0, 0, 0};
        double sum = Arrays.stream(array).sum();
        double avg = sum / array.length;
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(averageCalculator.calculateAverage(array), avg);
    }

    // Все отрицательные значения
    @Test
    void arrayWithNegativeNumbersTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {-3, -15, -10, -1, -8};
        double sum = Arrays.stream(array).sum();
        double avg = sum / array.length;
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(averageCalculator.calculateAverage(array), avg);
    }

    // Обычные значения
    @Test
    void arrayWithDefaultNumbersTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {5, 9, 8, 101, 52, 12, 34, 65};
        double sum = Arrays.stream(array).sum();
        double avg = sum / array.length;
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(averageCalculator.calculateAverage(array), avg);
    }

    // Половина отрицательных и половина положительных
    @Test
    void arrayWithHalfNegativeNumbersAndHalfPositiveTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {-51, 92, -85, 101, -52, 12, -342, 65};
        double sum = Arrays.stream(array).sum();
        double avg = sum / array.length;
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(averageCalculator.calculateAverage(array), avg);
    }

    // Проверка с одним элементом
    @Test
    void singleElementArrayTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {1827};
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(1827, averageCalculator.calculateAverage(array));
    }

    // Проверка с одинаковыми значениями
    @Test
    void sameElementsArrayTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {5, 5, 5, 5, 5, 5, 5};
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(5, averageCalculator.calculateAverage(array));
    }

    // Проверка пустого массива
    @Test
    void emptyArrayTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {};
        assertThrows(NoSuchElementException.class,() -> averageCalculator.calculateAverage(array));
    }

    // Проверка для больших значений
    @Test
    void bigValuesArrayTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        int[] array = new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        printArray(array);
        System.out.println(averageCalculator.calculateAverage(array));
        assertEquals(Integer.MAX_VALUE, averageCalculator.calculateAverage(array));
    }

    // Проверка на работу с null
    @Test
    void nullArrayTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        assertThrows(NullPointerException.class, () -> averageCalculator.calculateAverage(null));
    }

    // Проверка на очень большой массив
    @Test
    void veryLargeArrayTest() {
        AverageCalculator averageCalculator = new AverageCalculator();
        try {
            int[] array = new int[Integer.MAX_VALUE - 1]; // Это вызовет OutOfMemoryError
            assertThrows(OutOfMemoryError.class, () -> averageCalculator.calculateAverage(array));
        } catch (OutOfMemoryError o) {
            System.out.println("Недостаточно памяти для такого размера массива");
        }
    }
}