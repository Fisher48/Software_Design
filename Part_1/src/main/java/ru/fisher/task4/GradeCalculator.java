package ru.fisher.task4;

import java.util.List;

/**
 * Имеется класс GradeCalculator с методом calculateAverage(List grades), который вычисляет среднее значение оценок студентов.
 * Реализуйте этот класс с учётом того, что существуют граничные случаи, которые необходимо правильно обработать, чтобы избежать логических ошибок.
 * Сделайте пять концептуально различающихся тестов для метода calculateAverage.
 * Тесты которые я реализовал:
 * - Стандартный список оценок в диапазоне 0-100
 * - Пустой список (выдается исключение)
 * - Null список (выдается исключение)
 * - Одна оценка в списке
 * - Оценки выше и ниже допустимых значений (< 0 или > 100) выдается исключение
 * - Повторяющийся тест со случайными оценками
 * Тесты в файле GradeCalculatorTest.java
**/
public class GradeCalculator {

   public double calculateAverage(List<Integer> grades) {
       if (grades == null) {
           throw new NullPointerException("Список не может быть null");
       }
       if (grades.isEmpty()) {
           throw new IllegalArgumentException("Список не должен быть пустым");
       }
       double sum = 0;
       for (Integer grade : grades) {
           if (grade < 0 || grade > 100) {
               throw new IllegalArgumentException("Оценка должна быть в диапазоне от 0 до 100");
           }
           sum += grade;
       }
       return sum / grades.size();
   }

}
