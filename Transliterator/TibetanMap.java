import java.util.HashMap;
import java.util.Map;

public class TibetanMap {
    private static final Map<String, String> tibetanMap = new HashMap<>();

    static {
        tibetanMap.put("ka", "ཀ"); 
        tibetanMap.put("kha", "ཁ");
    }

    public static String get(String key) {
        return tibetanMap.get(key);
    }

    public static boolean containsKey(String key) {
        return tibetanMap.containsKey(key);
    }
}
