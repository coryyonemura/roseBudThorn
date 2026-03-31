package domain;

import java.io.Serializable;
import java.util.Date;

public class Summary implements Serializable {
    private static final long serialVersionUID = 1L;

    private int entryId;
    private int userId;
    private int daysIncludedInSummary;
    private String summaryText;
    private Date createdAt;

    public Summary(int entryId, int userId, int daysIncludedInSummary, String summaryText, Date createdAt) {
        this.entryId = entryId;
        this.userId = userId;
        this.daysIncludedInSummary = daysIncludedInSummary;
        this.summaryText = summaryText;
        this.createdAt = createdAt;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDaysIncludedInSummary() {
        return daysIncludedInSummary;
    }

    public void setDaysIncludedInSummary(int daysIncludedInSummary) {
        this.daysIncludedInSummary = daysIncludedInSummary;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

