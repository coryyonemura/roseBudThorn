package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import domain.Summary;

@Repository
public class InMemorySummaryRepository implements SummaryRepository {

    private final List<Summary> summaries = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public synchronized Summary save(Summary summary) {
        if (summary.getEntryId() == 0) {
            summary.setEntryId(idGenerator.getAndIncrement());
        } else {
            summaries.removeIf(s -> s.getEntryId() == summary.getEntryId());
        }
        summaries.add(summary);
        return summary;
    }

    @Override
    public synchronized Summary findByUserIdAndCreatedAt(int userId, Date createdAt) {
        if (createdAt == null) {
            return null;
        }
        return summaries.stream()
                .filter(s -> s.getUserId() == userId && sameDay(s.getCreatedAt(), createdAt))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Summary> findByUserId(int userId) {
        return summaries.stream()
                .filter(s -> s.getUserId() == userId)
                .collect(Collectors.toList());
    }

    private boolean sameDay(Date d1, Date d2) {
        long millisPerDay = 24L * 60L * 60L * 1000L;
        long day1 = d1.getTime() / millisPerDay;
        long day2 = d2.getTime() / millisPerDay;
        return day1 == day2;
    }
}

