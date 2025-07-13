package ru.fisher.task12;

/**
 * Для доказательства maxAbsCalc(x, y) описываем тройкой Хоара:
 * P {x, y} - предусловие даны 2 целых числа
 * abs(x) = |x| - первый вызов функции abs возвращает значение по модулю x
 * abs(y) = |y| - второй вызов функции abs возвращает значение по модулю y
 * max(|x|, |y|) - вызов функции max для каждого числа возвращает большее значение по модулю между x, y
 * |x| >= |y| ? x : y - определяется разница между x и y по модулю
 * Q {max(|x|, |y|)} - результат либо (|x| >= |y|) или (|y| >= |x|)
 */
public class maxAbsCalc {

    // Предусловие: a и b - целые числа
    // Постусловие: либо (a >= b), либо (b >= a)
    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    // Предусловие: x - целое число
    // Постусловие: всегда возвращается модуль числа |x|, т.е должен быть x >= 0
    public static int abs(int x) {
        return Math.abs(x);
    }

    // Предусловие: x и y - целые числа
    // Постусловие: либо (|x| >= |y|) или (|y| >= |x|)
    public static int maxAbs(int x, int y) {
        return max(abs(x), abs(y));
    }

    public static void main(String[] args) {
        int x = -7;
        int y = -5;
        maxAbs(x, y);
        System.out.println("Максимум модуля двух чисел (x = " + x + " и y = " + y + ") это " + maxAbs(x, y));
    }

}
