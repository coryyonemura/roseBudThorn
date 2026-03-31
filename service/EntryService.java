package service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import domain.Entry;
import repository.EntryRepository;
import validation.EntryValidator;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public Entry createOrUpdateEntry(int userId, Date date, String rose, String bud, String thorn) {
        Date normalizedDate = normalizeDate(date);

        Entry existing = entryRepository.findByUserIdAndDate(userId, normalizedDate);
        int id = existing != null ? existing.getId() : 0;

        Entry entry = new Entry(id, userId, rose, bud, thorn, normalizedDate);

        if (!EntryValidator.validateEntry(entry)) {
            throw new IllegalArgumentException("Each of rose, bud, and thorn must be non-null and at most 280 characters.");
        }

        return entryRepository.save(entry);
    }

    public Entry getEntryForDate(int userId, Date date) {
        Date normalizedDate = normalizeDate(date);
        return entryRepository.findByUserIdAndDate(userId, normalizedDate);
    }

    public List<Entry> getEntriesForRange(int userId, Date from, Date to) {
        Date fromNormalized = normalizeDate(from);
        Date toNormalized = normalizeDate(to);
        return entryRepository.findByUserIdAndDateRange(userId, fromNormalized, toNormalized);
    }

    private Date normalizeDate(Date date) {
        if (date == null) {
            return null;
        }

        // Normalize using local timezone boundaries (not epoch/UTC day boundaries).
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}

