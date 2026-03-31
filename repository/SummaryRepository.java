package repository;

import java.util.Date;
import java.util.List;
import domain.Summary;

public interface SummaryRepository {

    Summary save(Summary summary);

    Summary findByUserIdAndCreatedAt(int userId, Date createdAt);

    List<Summary> findByUserId(int userId);
}

