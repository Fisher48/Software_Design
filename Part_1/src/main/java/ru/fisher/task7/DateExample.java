package ru.fisher.task7;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * В данном примере использовался не потокобезопасный SimpleDateFormat, а также Date, который не хранит информацию о временных зонах.
 * Сейчас все чаще используется LocalDateTime или ZonedDateTime, т.к. с ними удобно определять время в зависимости от таймзоны.
 * Также для определения паттерна форматирования даты и времени следует использовать DateTimeFormatter.
 * Используя данные классы вместе удобно формировать вывод времени с нужными деталями и преобразовывать время в другие часовые пояса.
 */
public class DateExample {
    public static void main(String[] args) {
        String dateString = "2024-05-13 14:30:00";

        // Для парсинга строки (как в исходном примере)
        DateTimeFormatter parserFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, parserFormatter);
        System.out.println("Дата: " + parsedDateTime.format(parserFormatter));

        // Для работы с временными зонами
        ZoneId zone = ZoneId.of("Europe/Moscow");
        ZonedDateTime zonedDateTime = parsedDateTime.atZone(zone);
        System.out.println("Временная зона: " + zonedDateTime.getZone());

        // Полный локализованный вывод времени для другой зоны
        DateTimeFormatter fullFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.FULL)
                .withZone(ZoneId.of("Asia/Tokyo"));

        System.out.println("Форматированное время с другой зоной: " + zonedDateTime.format(fullFormatter));

    }
}
