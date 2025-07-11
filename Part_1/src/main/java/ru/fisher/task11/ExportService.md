Как был ранее реализован сервис по экспорту данных:

```java
public class ExportService {

    private final ObjectMapper objectMapper;

    public ExportService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public String exportToJson(Enterprise enterprise,
                               List<VehicleDTO> vehicles,
                               List<TripDTO> trips) throws Exception {
        // Реализация экспорта в JSON ...
    }

    // Экспорт в CSV
    public String exportToCsv(Enterprise enterprise,
                              List<VehicleDTO> vehicles,
                              List<TripDTO> trips) throws Exception {
        // Реализация экспорта в CSV ...
    }
    
}

@RestController
@RequestMapping("/api/export")
public class ExportController {

    //...//

    @GetMapping(value = "/enterprise/{enterpriseId}", produces = {MediaType.APPLICATION_JSON_VALUE, "text/csv"})
    public ResponseEntity<?> exportEnterpriseData(
            @PathVariable Long enterpriseId,
            @RequestParam String format,
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo) {
        try {
            
            //...//

            // Экспортируем данные согласно формата
            if ("json".equalsIgnoreCase(format)) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(exportService.exportToJson(enterprise, vehicles, trips));
            } else if ("csv".equalsIgnoreCase(format)) {
                return ResponseEntity.ok()
                        .contentType(MediaType.valueOf("text/csv"))
                        .body(exportService.exportToCsv(enterprise, vehicles, trips));
            } else {
                return ResponseEntity.badRequest().body("Неподдерживаемый формат: " + format);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка экспорта данных: " + e.getMessage());
        }
    }

}
```

Как стало после того, как я добавил общий интерфейс для форматов экспорта.
Теперь можно просто добавить новый форматер без изменения основного кода.

```java
public interface ExportFormatter<T> {
    String getFormat(); // "json", "csv", ...
    String format(T data) throws Exception;
}

@Component
public class EnterpriseJsonFormatter implements ExportFormatter<ExportDTO> {

    private final ObjectMapper objectMapper;

    public EnterpriseJsonFormatter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getFormat() {
        return "json";
    }

    @Override
    public String format(ExportDTO data) throws Exception {
        // Реализация экспорта в JSON
    }
}

@Component
public class EnterpriseCsvFormatter implements ExportFormatter<ExportDTO> {

    @Override
    public String getFormat() {
        return "csv";
    }

    @Override
    public String format(ExportDTO data) throws Exception {
        // Реализация экспорта в CSV
    }
}

@Service
public class ExportService {

    private final Map<String, ExportFormatter<ExportDTO>> formatters;

    @SuppressWarnings("unchecked")
    public ExportService(List<ExportFormatter<?>> formatterList) {
        this.formatters = formatterList.stream()
                .filter(Objects::nonNull)
                .map(f -> (ExportFormatter<ExportDTO>) f)
                .collect(Collectors.toMap(ExportFormatter::getFormat,f -> f));
    }

    public String export(ExportDTO exportDTO, String format) throws Exception {
        ExportFormatter<ExportDTO> formatter = formatters.get(format.toLowerCase());
        if (formatter == null) {
            throw new IllegalArgumentException("Неподдерживаемый формат: " + format);
        }
        return formatter.format(exportDTO);
    }
}


@GetMapping(value = "/enterprise/{enterpriseId}", produces = {MediaType.APPLICATION_JSON_VALUE, "text/csv"})
public ResponseEntity<?> exportEnterpriseData(
        @PathVariable Long enterpriseId,
        @RequestParam String format,
        @RequestParam(required = false) LocalDateTime dateFrom,
        @RequestParam(required = false) LocalDateTime dateTo) {
    try {

        // Получаем списки машин и поездок ... //
        
        // Экспортируем данные согласно формата
        ExportDTO exportDTO = new ExportDTO(enterprise, vehicles, trips);
        String result = exportService.export(exportDTO, format);

        MediaType mediaType = switch (format.toLowerCase()) {
            case "json" -> MediaType.APPLICATION_JSON;
            case "csv" -> MediaType.valueOf("text/csv");
            default -> MediaType.TEXT_PLAIN;
        };

        return ResponseEntity.ok().contentType(mediaType).body(result);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Ошибка экспорта данных: " + e.getMessage());
    }
}
```