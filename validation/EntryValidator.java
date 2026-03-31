package validation;

import domain.Entry;

public class EntryValidator{

    public static boolean validateEntry(Entry entry) {
        if (entry.getRose() == null || entry.getRose().length() > 280) {
            return false;
        }
        if (entry.getBud() == null || entry.getBud().length() > 280) {
            return false;
        }
        if (entry.getThorn() == null || entry.getThorn().length() > 280) {
            return false;
        }
        return true;
    }
}