package repository;

import java.util.Date;
import java.util.List;
import domain.Entry;

public interface EntryRepository {

    Entry save(Entry entry);

    Entry findByUserIdAndDate(int userId, Date date);

    List<Entry> findByUserIdAndDateRange(int userId, Date from, Date to);
}

