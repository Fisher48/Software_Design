package ru.fisher.task4;

import java.util.List;

/**
 * Имеется класс GradeCalculator с методом calculateAverage(List grades), который вычисляет среднее значение оценок студентов.
 * Реализуйте этот класс с учётом того, что существуют граничные случаи, которые необходимо правильно обработать, чтобы избежать логических ошибок.
 * Сделайте пять концептуально различающихся тестов для метода calculateAverage.
**/
public class GradeCalculator {

   public double calculateAverage(List<Integer> grades) {
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
