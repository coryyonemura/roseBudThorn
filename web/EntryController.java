package web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import domain.Entry;
import service.EntryService;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public ResponseEntity<Entry> getEntryForDate(
            @RequestParam("userId") int userId,
            @RequestParam("date") String date) {

        Date parsedDate = parseDate(date);
        Entry entry = entryService.getEntryForDate(userId, parsedDate);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entry);
    }

    @GetMapping("/range")
    public ResponseEntity<List<Entry>> getEntriesForRange(
            @RequestParam("userId") int userId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        Date fromDate = parseDate(from);
        Date toDate = parseDate(to);
        List<Entry> entries = entryService.getEntriesForRange(userId, fromDate, toDate);
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdateEntry(
            @RequestParam("userId") int userId,
            @RequestParam("date") String date,
            @RequestParam("rose") String rose,
            @RequestParam("bud") String bud,
            @RequestParam("thorn") String thorn) {

        Date parsedDate = parseDate(date);
        try {
            Entry saved = entryService.createOrUpdateEntry(userId, parsedDate, rose, bud, thorn);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    private Date parseDate(String value) {
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd.");
        }
    }
}

