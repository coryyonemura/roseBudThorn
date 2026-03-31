package repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import domain.Entry;

@Repository
public class InMemoryEntryRepository implements EntryRepository {

    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = DATA_DIR + "/entries.ser";

    private final List<Entry> entries = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public InMemoryEntryRepository() {
        loadFromDisk();
    }

    @Override
    public synchronized Entry save(Entry entry) {
        if (entry != null && entry.getDate() != null) {
            entry.setDate(startOfDayLocal(entry.getDate()));
        }
        if (entry.getId() == 0) {
            entry.setId(idGenerator.getAndIncrement());
        } else {
            entries.removeIf(e -> e.getId() == entry.getId());
        }
        entries.add(entry);
        persistToDisk();
        return entry;
    }

    @Override
    public synchronized Entry findByUserIdAndDate(int userId, Date date) {
        if (date == null) {
            return null;
        }
        return entries.stream()
                .filter(e -> e.getUserId() == userId && sameDay(e.getDate(), date))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Entry> findByUserIdAndDateRange(int userId, Date from, Date to) {
        if (from == null || to == null) {
            return List.of();
        }
        return entries.stream()
                .filter(e -> e.getUserId() == userId && !e.getDate().before(from) && !e.getDate().after(to))
                .collect(Collectors.toList());
    }

    private boolean sameDay(Date d1, Date d2) {
        return startOfDayLocal(d1).getTime() == startOfDayLocal(d2).getTime();
    }

    private Date startOfDayLocal(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @SuppressWarnings("unchecked")
    private synchronized void loadFromDisk() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (!(obj instanceof List<?>)) {
                return;
            }
            List<?> rawList = (List<?>) obj;
            entries.clear();
            int maxId = 0;
            for (Object o : rawList) {
                if (o instanceof Entry e) {
                    if (e.getDate() != null) {
                        e.setDate(startOfDayLocal(e.getDate()));
                    }
                    entries.add(e);
                    if (e.getId() > maxId) {
                        maxId = e.getId();
                    }
                }
            }
            idGenerator.set(maxId + 1);
        } catch (Exception ignored) {
            // If persistence fails, fall back to an empty list.
            entries.clear();
            idGenerator.set(1);
        }
    }

    private synchronized void persistToDisk() {
        try {
            File dir = new File(DATA_DIR);
            dir.mkdirs();

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                out.writeObject(entries);
            }
        } catch (Exception ignored) {
            // Best-effort persistence; app still works with in-memory data.
        }
    }
}

