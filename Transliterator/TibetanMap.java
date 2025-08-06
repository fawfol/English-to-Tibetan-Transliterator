import java.util.*;

public class TibetanTransliterator {

    // Tibetan Transliteration Map (similar to your Python dictionary)
    private static final Map<String, String> TIBETAN_MAP = createTibetanMap();

    // Sorted keys by length descending to match longest first
    private static final List<String> SORTED_KEYS = new ArrayList<>(TIBETAN_MAP.keySet());

    static {
        SORTED_KEYS.sort((a, b) -> b.length() - a.length());
    }

    private static Map<String, String> createTibetanMap() {
        Map<String, String> map = new HashMap<>();

        // Consonants + vowels
        // Consonants + Vowels
        map.put("ka", "ཀ");        map.put("kha", "ཁ");        map.put("gha", "ག");        map.put("nga", "ང");
        map.put("ca", "ཅ");        map.put("cha", "ཆ");        map.put("ja", "ཇ");        map.put("jha", "ཇ");   // same as 'ja'        map.put("nya", "ཉ");       
        map.put("ta", "ཏ");        map.put("tha", "ཐ");        map.put("dha", "ད");        map.put("na", "ན");       
        map.put("pa", "པ");        map.put("pha", "ཕ");        map.put("ba", "བ");        map.put("bha", "བ");   // same as 'ba'        map.put("ma", "མ");        
        map.put("tza", "ཙ");        map.put("tsa", "ཚ");        map.put("za", "ཛ");        map.put("wa", "ཝ");
        map.put("zha", "ཞ");        map.put("sza", "ཟ");        map.put("ya", "ཡ");        map.put("ra", "ར");
        map.put("la", "ལ");        map.put("sha", "ཤ");        map.put("sa", "ས");
        map.put("ha", "ཧ");        map.put("a", "ཨ");

        // Syllable-final (ending) consonants ('jejug chuu')
        map.put("g", "ག");        map.put("ng", "ང");        map.put("n", "ན");        map.put("b", "བ");
        map.put("aa", "འ");        map.put("m", "མ");        map.put("l", "ལ");        map.put("r", "ར");
        map.put("s", "ས");        map.put("p", "པ");        map.put("k", "ག");        map.put("dh", "ད");

        // Vowel diacritics (applied to consonants)
        map.put("ki", "ཀི");        map.put("ku", "ཀུ");        map.put("ke", "ཀེ");        map.put("ko", "ཀོ");

        map.put("khi", "ཁི");        map.put("khu", "ཁུ");        map.put("khe", "ཁེ");        map.put("kho", "ཁོ");

        map.put("gi", "གི");        map.put("gu", "གུ");        map.put("ge", "གེ");        map.put("go", "གོ");

        map.put("ghi", "གི");        map.put("ghu", "གུ");        map.put("ghe", "གེ");        map.put("gho", "གོ");

        map.put("ngi", "ངི");        map.put("ngu", "ངུ");        map.put("nge", "ངེ");        map.put("ngo", "ངོ");

        map.put("chhi", "ཅི");        map.put("chhu", "ཅུ");        map.put("chhe", "ཅེ");        map.put("chho", "ཅོ");

        map.put("chi", "ཆི");        map.put("chu", "ཆུ");        map.put("che", "ཆེ");        map.put("cho", "ཆོ");

        map.put("jhi", "ཇི");        map.put("jhu", "ཇུ");        map.put("jhe", "ཇེ");        map.put("jho", "ཇོ");

        map.put("nyi", "ཉི");        map.put("nyu", "ཉུ");        map.put("nye", "ཉེ");        map.put("nyo", "ཉོ");

        map.put("ti", "ཏི");        map.put("tu", "ཏུ");        map.put("te", "ཏེ");        map.put("to", "ཏོ");

        map.put("thi", "ཐི");        map.put("thu", "ཐུ");        map.put("the", "ཐེ");        map.put("tho", "ཐོ");

        map.put("di", "དི");        map.put("du", "དུ");        map.put("de", "དེ");        map.put("do", "དོ");

        map.put("dhi", "དི");        map.put("dhu", "དུ");        map.put("dhe", "དེ");        map.put("dho", "དོ");

        map.put("ni", "ནི");        map.put("nu", "ནུ");        map.put("ne", "ནེ");        map.put("no", "ནོ");

        map.put("pi", "པི");        map.put("pu", "པུ");        map.put("pe", "པེ");        map.put("po", "པོ");

        map.put("phi", "ཕི");        map.put("phu", "ཕུ");        map.put("phe", "ཕེ");        map.put("pho", "ཕོ");

        map.put("bhi", "བི");        map.put("bhu", "བུ");        map.put("bhe", "བེ");        map.put("bho", "བོ");

        map.put("mi", "མི");        map.put("mu", "མུ");        map.put("me", "མེ");        map.put("mo", "མོ");

        map.put("tsi", "ཚི");        map.put("tsu", "ཚུ");        map.put("tse", "ཚེ");        map.put("tso", "ཚོ");

        map.put("zi", "ཛི ");        map.put("zu", "ཛུ");        map.put("ze", "ཛེ");        map.put("zo", "ཛོ");

        map.put("zhi", "ཞི");        map.put("zhu", "ཞུ");        map.put("zhe", "ཞེ");        map.put("zho", "ཞོ");

        map.put("szi", "ཟི");        map.put("szu", "ཟུ");        map.put("sze", "ཟེ");        map.put("szo", "ཟོ");

        map.put("wi", "ཝི");        map.put("wu", "ཝུ");        map.put("we", "ཝེ");        map.put("wo", "ཝོ");

        map.put("yi", "ཡི");        map.put("yu", "ཡུ");        map.put("ye", "ཡེ");        map.put("yo", "ཡོ");

        map.put("ri", "རི");        map.put("ru", "རུ");        map.put("re", "རེ");        map.put("ro", "རོ");

        map.put("li", "ལི");        map.put("lu", "ལུ");        map.put("le", "ལེ");        map.put("lo", "ལོ");

        map.put("shi", "ཤི");        map.put("shu", "ཤུ");        map.put("she", "ཤེ");        map.put("sho", "ཤོ");

        map.put("si", "སི");        map.put("su", "སུ");        map.put("se", "སེ");        map.put("so", "སོ");

        map.put("hi", "ཧི");        map.put("hu", "ཧུ");        map.put("he", "ཧེ");	map.put("o", "ཧོ");

        map.put("i", "ཨི");        map.put("u", "ཨུ");        map.put("e", "ཨེ");        map.put("o", "ཨོ");

        // Subjoined 'ya' ('yatak')
        map.put("kya", "ཀྱ");        map.put("kyi", "ཀྱི");        map.put("kyu", "ཀྱུ");        map.put("kye", "ཀྱེ");        map.put("kyo", "ཀྱོ");

        map.put("khya", "ཁྱ");        map.put("khyi", "ཁྱི");        map.put("khyu", "ཁྱུ");        map.put("khye", "ཁྱེ");        map.put("khy", "ཁྱི");

        map.put("gya", "གྱ");        map.put("gyi", "གྱི");        map.put("ghyi", "གྱི");        map.put("gyu", "གྱུ");        map.put("gye", "གྱེ");        map.put("gyo", "གྱོ");
        map.put("chhya", "པྱ");		map.put("chhyi", "པྱི");		map.put("chhyu", "པྱུ");		map.put("chhye", "པྱེ");		map.put("chhyo", "པྱོ");  	
        map.put("chya", "ཕྱ");		map.put("chyi", "ཕྱི");		map.put("chyu", "ཕྱུ"); 		map.put("chye", "ཕྱེ");		map.put("chyo", "ཕྱོ");
	map.put("jhya", "བྱ"); 		map.put("jhyi", "བྱི");		map.put("jhyu", "བྱུ");		map.put("jhye", "བྱེ");		map.put("jhyo", "བྱོ");
	map.put("nya", "མྱ");		map.put("nyi", "མྱི");		map.put("nyu", "མྱུ");		map.put("nye", "མྱེ");		map.put("nyo", "མྱོ");
	
	
        return map;
    }

    /**
     * Transliterates a Romanized (Wylie-style) string into Tibetan script.
     * @param romanString input string in Romanized Tibetan
     * @return Tibetan script string
     */
    public static String transliterate(String romanString) {
        if (romanString == null) {
            return "";
        }

        String lower = romanString.toLowerCase(Locale.ROOT);
        StringBuilder tibetanOutput = new StringBuilder();

        int i = 0;
        while (i < lower.length()) {
            char currentChar = lower.charAt(i);

            // Handle space => tsheg character (་)
            if (currentChar == ' ') {
                tibetanOutput.append('་'); 
                i++;
                continue;
            }

            boolean matchFound = false;
            for (String key : SORTED_KEYS) {
                if (lower.startsWith(key, i)) {
                    tibetanOutput.append(TIBETAN_MAP.get(key));
                    i += key.length();
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                // Skip unmatched character and move forward
                i++;
            }
        }

        return tibetanOutput.toString();
    }

    // Simple test
    public static void main(String[] args) {
        String[] examples = {
            "sangs rgyas",
            "bod skad",
            "rdo rje",
            "karma",
            "bkra shis bde legs",
            "om ma ni pad me hung"
        };

        for (String example : examples) {
            String tib = transliterate(example);
            System.out.println(example + " => " + tib);
        }
    }

}
