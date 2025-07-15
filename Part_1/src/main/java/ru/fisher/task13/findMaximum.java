package ru.fisher.task13;

public class findMaximum {


    /**
     *
     * Доказательство корректности функции findMax(arr), которая находит максимальное значение в массиве.
     * {arr.length > 0} findMax(arr) {result = max(arr)}
     *
     * P: предусловие {массив не пустой, длина массива > 0}
     * I: Инвариант - max == максимальный элемент в подмассиве arr[0..i-1] и 1 <= i <= arr.length
     * Q: постусловие {result = max(arr)}
     *
     * Доказательство инварианта.
     * 1. Инициализация:
     * До начала первой итерации цикла: i = 1 и max = arr[0]
     * Подмассив содержит 1 элемент => max = arr[0] верно
     * Проверка инварианта: 1 <= 1 <= arr.length и max = arr[0]
     * Инвариант истинен в начале.
     *
     * 2. Сохранение инварианта:
     * Перед итерацией max содержит максимум в arr[0..i-1]
     * i находится в пределах 1 <= i < arr.length
     * Во время выполнения цикла:
     * Сравниваем текущее max c следующим элементом arr[i]
     * Если arr[i] > max, то обновляем max.
     * Теперь max содержит максимум в arr[0..i]
     * После итерации: i увеличивается на 1, следовательно, i = i + 1
     * Таким образом, инвариант по-прежнему истинен: 1 <= i <= arr.length и max содержит максимум в arr[0..i]
     *
     * Завершение:
     * Цикл завершается, когда i становится равным arr.length
     * Теперь max содержит максимум в arr[0..arr.length-1], т.е во всем массиве
     *
     */

    public static int findMax(int[] arr) {
        if (arr.length == 0) {
            throw new NullPointerException("Массив не должен быть пустым");
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }


    public static void main(String[] args) {
        int[] arr = new int[] {0, 1, 7, -7, 12, -100};
        System.out.println(findMax(arr));

        int[] arr2 = new int[] {0, -10, -7, -75, -12, -100};
        System.out.println(findMax(arr2));

        int[] arr3 = new int[] {5, 10, 7, -7, -120, 200};
        System.out.println(findMax(arr3));

        int[] arr4 = new int[] {0, 0, 0, 0, 0, 0};
        System.out.println(findMax(arr4));
    }


}
