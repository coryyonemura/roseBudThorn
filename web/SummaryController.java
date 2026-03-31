package web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import domain.Summary;
import service.SummaryService;

@RestController
@RequestMapping("/api/summaries")
public class SummaryController {

    private final SummaryService summaryService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/weekly")
    public ResponseEntity<Summary> getWeeklySummary(
            @RequestParam("userId") int userId,
            @RequestParam("weekStart") String weekStart) {

        Date weekStartDate = parseDate(weekStart);
        Summary summary = summaryService.generateWeeklySummary(userId, weekStartDate);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/monthly")
    public ResponseEntity<Summary> getMonthlySummary(
            @RequestParam("userId") int userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {

        Summary summary = summaryService.generateMonthlySummary(userId, year, month);
        return ResponseEntity.ok(summary);
    }

    private Date parseDate(String value) {
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }
    }
}

