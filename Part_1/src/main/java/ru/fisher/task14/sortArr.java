package ru.fisher.task14;

import java.util.Arrays;

/**
 * Задание: Корректность сложного алгоритма.
 *
 * Используйте тройки Хоара для доказательства корректности относительно сложного алгоритма быстрой сортировки.
 * Напишите функцию quickSort() и формально докажите её корректность.
 * Определите пред- и постусловия для основного цикла и рекурсивных вызовов, используя тройки Хоара.
 *
 * P: Предусловие - массив не пустой {arr.length > 0 & arr != null}
 * I:
 * Q: Массив отсортирован в порядке возрастания quickSort(arr).
 *
 * Доказательство инварианта.
 * 1. Инициализация (до цикла):
 *      j = low, i = low - 1 => области [low..i] и [i+1..j-1] пусты => инвариант выполняется.
 *
 * 2. Сохранение инварианта (одна итерация):
 *    Если arr[j] ≤ pivot:
 *      i++ увеличивается, arr[i] и arr[j] меняются ⇒
 *      arr[i] ≤ pivot сохраняется
 *    Если arr[j] > pivot:
 *      просто увеличиваем j++ ⇒ область > pivot расширяется
 *
 * 3. Завершение (после цикла):
 *      Массив отсортирован, все элементы в порядке возрастания,
 *      j == high => все элементы обработаны.
 *      [low..i]: все < pivot
 *      [i+1..high-1]: все ≥ pivot
 *      Делаем swap(arr, i+1, high) и arr[i+1] становится pivot
 *      Результат: [low..i] ≤ pivot < [i+2..high],
 *      Левые элементы ≤ pivot, правые ≥ pivot => условие Q выполнено.
 */

public class sortArr {

    // Главная функция сортировки
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort(arr, 0, arr.length - 1);
    }

    // Рекурсивная часть
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high); // Шаг разбиения
            quickSort(arr, low, pivotIndex - 1);  // Сортируем левую часть
            quickSort(arr, pivotIndex + 1, high); // Сортируем правую часть
        }
    }

    // Разделение массива
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Опорный элемент
        int i = low - 1; // Граница элементов < pivot

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j); // Переносим элемент < pivot влево
            }
        }
        swap(arr, i + 1, high); // Ставим pivot на правильное место
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {-7, 1, 9, -2, 0, -34, 1, -34, 100, 0};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));

        int[] twoElementsArr = new int[] {1, -2};
        quickSort(twoElementsArr);
        System.out.println(Arrays.toString(twoElementsArr));

        int[] oneElementArr = new int[] {0};
        quickSort(oneElementArr);
        System.out.println(Arrays.toString(oneElementArr));

        int[] emptyArr = new int[] {};
        quickSort(emptyArr);
        System.out.println(Arrays.toString(emptyArr));
    }
}
