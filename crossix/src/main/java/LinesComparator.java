import java.util.Comparator;

public class LinesComparator implements Comparator<String> {
    static final int FIELD_NUMBER = 0;
    static final String cvsSplitBy = ",";

    @Override
    public int compare(String raw1, String raw2) {
        String key1 = retrieveKeyOrDefault(raw1);
        String key2 = retrieveKeyOrDefault(raw2);
        return key1.compareTo(key2);
    }

    private String retrieveKeyOrDefault(String raw) {
        try {
            if (raw != null && !raw.isEmpty() &&
                    raw.split(cvsSplitBy).length > 1 && raw.split(cvsSplitBy)[FIELD_NUMBER] != null) {
                return raw.split(cvsSplitBy)[FIELD_NUMBER];
            }
        }
        catch (Exception e) {
            System.out.println("Can't parse the key in the raw: " + raw);
            e.printStackTrace();
            return "";
        }
        return "";
    }
}