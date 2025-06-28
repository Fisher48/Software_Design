package ru.fisher.task4;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        for (Integer i : grades) {
            sum += i;
        }
        double avg = sum * 1.0 / grades.size();
        gradeCalculator.calculateAverage(grades);
        assertEquals(avg, gradeCalculator.calculateAverage(grades));
    }

    @Test
    void classicGradesListTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = List.of(52, 38, 29, 31, 100, 84);
        System.out.println(grades);
        double avg = gradeCalculator.calculateAverage(grades);
        System.out.println("Средняя оценка: " + Math.round(avg * 100.0) / 100.0);
        assertEquals(55.66, avg, 0.01);
    }

    @Test
    void oneGradeTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = List.of(55);
        System.out.println(grades);
        assertEquals(55, gradeCalculator.calculateAverage(grades));
    }

    @Test
    void emptyListTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> emptyList = List.of();
        System.out.println(emptyList);
        assertThrows(IllegalArgumentException.class, () -> gradeCalculator.calculateAverage(emptyList));
    }

    @Test
    void nullListTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = null;
        assertThrows(NullPointerException.class, () -> gradeCalculator.calculateAverage(grades));
    }

    @Test
    void gradesLessThanZeroTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = List.of(-1, -17, -200, -300);
        System.out.println(grades);
        assertThrows(IllegalArgumentException.class, () -> gradeCalculator.calculateAverage(grades));
    }

    @Test
    void gradesHigherThanOneHundredTest() {
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Integer> grades = List.of(99, 17, 200, 300);
        System.out.println(grades);
        assertThrows(IllegalArgumentException.class, () -> gradeCalculator.calculateAverage(grades));
    }

}