package ru.fisher.task4;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    Random random = new Random();

    // Создание массива со случайными элементами
    public List<Integer> createRandomGradesList() {
        int n = 10;
        List<Integer> randomGrades = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            randomGrades.add(random.nextInt(100) + 1);
        }
        return randomGrades;
    }

    @RepeatedTest(1000)
    void randomGradesListRepeatedTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = createRandomGradesList();
        int sum = 0;
        gradeCalculator.calculateAverage(grades);
    }

    @Test
    void classicGradesListTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = Arrays.asList(52, 38, 29, 31, 100, 84);
        System.out.println(grades);
        double avg = gradeCalculator.calculateAverage(grades);
      //  System.out.println("Средняя оценка: " + (int) avg * 100 / 100.0);
        assertEquals(55.66, avg, 0.01);
    }

}