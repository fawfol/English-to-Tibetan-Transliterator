import java.util.*;

public class TibetanTransliterator {
    
    // Fill in your Tibetan Map here (copy all from Python dict)
    private static final Map<String, String> TIBETAN_MAP = new HashMap<>();
   static {
    TIBETAN_MAP.put("ka", "ཀ");    TIBETAN_MAP.put("kha", "ཁ");    TIBETAN_MAP.put("gha", "ག");    TIBETAN_MAP.put("nga", "ང");
    TIBETAN_MAP.put("ca", "ཅ");    TIBETAN_MAP.put("cha", "ཆ");    TIBETAN_MAP.put("ja", "ཇ");    TIBETAN_MAP.put("jha", "ཇ");
    TIBETAN_MAP.put("nya", "ཉ");    TIBETAN_MAP.put("ta", "ཏ");    TIBETAN_MAP.put("tha", "ཐ");    TIBETAN_MAP.put("dha", "ད");
    TIBETAN_MAP.put("na", "ན");    TIBETAN_MAP.put("pa", "པ");    TIBETAN_MAP.put("pha", "ཕ");    TIBETAN_MAP.put("ba", "བ");    TIBETAN_MAP.put("wa", "བ");    TIBETAN_MAP.put("bha", "བ");    TIBETAN_MAP.put("ma", "མ");
    TIBETAN_MAP.put("tza", "ཙ");    TIBETAN_MAP.put("tzsa", "ཙ");    TIBETAN_MAP.put("tsza", "ཙ");    TIBETAN_MAP.put("tssa", "ཙ");    TIBETAN_MAP.put("tsa", "ཚ");    TIBETAN_MAP.put("za", "ཛ");    TIBETAN_MAP.put("wwa", "ཝ");    TIBETAN_MAP.put("waa", "ཝ");
    TIBETAN_MAP.put("zha", "ཞ");    TIBETAN_MAP.put("sza", "ཟ");    TIBETAN_MAP.put("ya", "ཡ");    TIBETAN_MAP.put("ra", "ར");
    TIBETAN_MAP.put("la", "ལ");    TIBETAN_MAP.put("sha", "ཤ");    TIBETAN_MAP.put("sa", "ས");    TIBETAN_MAP.put("ha", "ཧ");
    TIBETAN_MAP.put("a", "ཨ");    
    
    TIBETAN_MAP.put("g", "ག");    TIBETAN_MAP.put("ng", "ང");    TIBETAN_MAP.put("n", "ན");    TIBETAN_MAP.put("b", "བ");    TIBETAN_MAP.put("aa", "འ");    TIBETAN_MAP.put("m", "མ");    TIBETAN_MAP.put("l", "ལ");
    TIBETAN_MAP.put("r", "ར");    TIBETAN_MAP.put("s", "ས");    TIBETAN_MAP.put("p", "པ");    TIBETAN_MAP.put("k", "ག");    TIBETAN_MAP.put("dh", "ད");    TIBETAN_MAP.put("e", "ཨེ");    TIBETAN_MAP.put("d", "ད");
    
    
    TIBETAN_MAP.put("ki", "ཀི");    TIBETAN_MAP.put("ku", "ཀུ");    TIBETAN_MAP.put("ke", "ཀེ");    TIBETAN_MAP.put("ko", "ཀོ");
    TIBETAN_MAP.put("khi", "ཁི");    TIBETAN_MAP.put("khu", "ཁུ");    TIBETAN_MAP.put("khe", "ཁེ");    TIBETAN_MAP.put("kho", "ཁོ");
    TIBETAN_MAP.put("gi", "གི");    TIBETAN_MAP.put("gu", "གུ");    TIBETAN_MAP.put("ge", "གེ");    TIBETAN_MAP.put("go", "གོ");    TIBETAN_MAP.put("ghi", "གི");    TIBETAN_MAP.put("ghu", "གུ");    TIBETAN_MAP.put("ghe", "གེ");    TIBETAN_MAP.put("gho", "གོ");
    TIBETAN_MAP.put("ngi", "ངི");    TIBETAN_MAP.put("ngu", "ངུ");    TIBETAN_MAP.put("nge", "ངེ");    TIBETAN_MAP.put("ngo", "ངོ");
    TIBETAN_MAP.put("chhi", "ཅི");    TIBETAN_MAP.put("chhu", "ཅུ");    TIBETAN_MAP.put("chhe", "ཅེ");    TIBETAN_MAP.put("chho", "ཅོ");
    TIBETAN_MAP.put("chi", "ཆི");    TIBETAN_MAP.put("chu", "ཆུ");    TIBETAN_MAP.put("che", "ཆེ");    TIBETAN_MAP.put("cho", "ཆོ");
    TIBETAN_MAP.put("jhi", "ཇི");    TIBETAN_MAP.put("jhu", "ཇུ");    TIBETAN_MAP.put("jhe", "ཇེ");    TIBETAN_MAP.put("jho", "ཇོ");
    TIBETAN_MAP.put("nyi", "ཉི");    TIBETAN_MAP.put("nyu", "ཉུ");    TIBETAN_MAP.put("nye", "ཉེ");    TIBETAN_MAP.put("nyo", "ཉོ");
    TIBETAN_MAP.put("ti", "ཏི");    TIBETAN_MAP.put("tu", "ཏུ");    TIBETAN_MAP.put("te", "ཏེ");    TIBETAN_MAP.put("to", "ཏོ");
    TIBETAN_MAP.put("thi", "ཐི");    TIBETAN_MAP.put("thu", "ཐུ");    TIBETAN_MAP.put("the", "ཐེ");    TIBETAN_MAP.put("tho", "ཐོ");    
    TIBETAN_MAP.put("di", "གྲི");    TIBETAN_MAP.put("du", "གྲུ");    TIBETAN_MAP.put("de", "གྲེ");    TIBETAN_MAP.put("do", "གྲོ");
    TIBETAN_MAP.put("dhi", "དི");    TIBETAN_MAP.put("dhu", "དུ");    TIBETAN_MAP.put("dhe", "དེ");    TIBETAN_MAP.put("dho", "དོ");
    TIBETAN_MAP.put("ni", "ནི");    TIBETAN_MAP.put("nu", "ནུ");    TIBETAN_MAP.put("ne", "ནེ");    TIBETAN_MAP.put("no", "ནོ");
    TIBETAN_MAP.put("pi", "པི");    TIBETAN_MAP.put("pu", "པུ");    TIBETAN_MAP.put("pe", "པེ");    TIBETAN_MAP.put("po", "པོ");
    TIBETAN_MAP.put("phi", "ཕི");    TIBETAN_MAP.put("phu", "ཕུ");    TIBETAN_MAP.put("phe", "ཕེ");    TIBETAN_MAP.put("pho", "ཕོ");
    TIBETAN_MAP.put("bhi", "བི");    TIBETAN_MAP.put("bhu", "བུ");    TIBETAN_MAP.put("bhe", "བེ");    TIBETAN_MAP.put("bho", "བོ");
    TIBETAN_MAP.put("mi", "མི");    TIBETAN_MAP.put("mu", "མུ");    TIBETAN_MAP.put("me", "མེ");    TIBETAN_MAP.put("mo", "མོ");
    TIBETAN_MAP.put("tzi", "ཙི");    TIBETAN_MAP.put("tzu", "ཙུ");    TIBETAN_MAP.put("tze", "ཙེ");    TIBETAN_MAP.put("tzo", "ཙོ");
    TIBETAN_MAP.put("tssi", "ཙི");    TIBETAN_MAP.put("tssu", "ཙུ");    TIBETAN_MAP.put("tsse", "ཙེ");    TIBETAN_MAP.put("tsso", "ཙོ");
    TIBETAN_MAP.put("tzsi", "ཙི");    TIBETAN_MAP.put("tzsu", "ཙུ");    TIBETAN_MAP.put("tzse", "ཙེ");    TIBETAN_MAP.put("tzso", "ཙོ");    TIBETAN_MAP.put("tszi", "ཙི");    TIBETAN_MAP.put("tszu", "ཙུ");    TIBETAN_MAP.put("tsze", "ཙེ");    TIBETAN_MAP.put("tszo", "ཙོ");
    TIBETAN_MAP.put("tsi", "ཚི");    TIBETAN_MAP.put("tsu", "ཚུ");    TIBETAN_MAP.put("tse", "ཚེ");    TIBETAN_MAP.put("tso", "ཚོ");
    TIBETAN_MAP.put("zi", "ཛི ");    TIBETAN_MAP.put("zu", "ཛུ");    TIBETAN_MAP.put("ze", "ཛེ");    TIBETAN_MAP.put("zo", "ཛོ");
    TIBETAN_MAP.put("zhi", "ཞི");    TIBETAN_MAP.put("zhu", "ཞུ");    TIBETAN_MAP.put("zhe", "ཞེ");    TIBETAN_MAP.put("zho", "ཞོ");
    TIBETAN_MAP.put("szi", "ཟི");    TIBETAN_MAP.put("szu", "ཟུ");    TIBETAN_MAP.put("sze", "ཟེ");    TIBETAN_MAP.put("szo", "ཟོ");
    TIBETAN_MAP.put("wi", "ཝི");    TIBETAN_MAP.put("wu", "ཝུ");    TIBETAN_MAP.put("we", "ཝེ");    TIBETAN_MAP.put("wo", "ཝོ");
    TIBETAN_MAP.put("yi", "ཡི");    TIBETAN_MAP.put("yu", "ཡུ");    TIBETAN_MAP.put("ye", "ཡེ");    TIBETAN_MAP.put("yo", "ཡོ");
    TIBETAN_MAP.put("ri", "རི");    TIBETAN_MAP.put("ru", "རུ");    TIBETAN_MAP.put("re", "རེ");    TIBETAN_MAP.put("ro", "རོ");
    TIBETAN_MAP.put("li", "ལི");    TIBETAN_MAP.put("lu", "ལུ");    TIBETAN_MAP.put("le", "ལེ");    TIBETAN_MAP.put("lo", "ལོ");
    TIBETAN_MAP.put("shi", "ཤི");    TIBETAN_MAP.put("shu", "ཤུ");    TIBETAN_MAP.put("she", "ཤེ");    TIBETAN_MAP.put("sho", "ཤོ");
    TIBETAN_MAP.put("si", "སི");    TIBETAN_MAP.put("su", "སུ");    TIBETAN_MAP.put("se", "སེ");    TIBETAN_MAP.put("so", "སོ");
    TIBETAN_MAP.put("hi", "ཧི");    TIBETAN_MAP.put("hu", "ཧུ");    TIBETAN_MAP.put("he", "ཧེ");    TIBETAN_MAP.put("ho", "ཧོ");
    TIBETAN_MAP.put("i", "ཨི");    TIBETAN_MAP.put("u", "ཨུ");    TIBETAN_MAP.put("e", "ཨེ");    TIBETAN_MAP.put("o", "ཨོ");

    TIBETAN_MAP.put("tra", "ཀྲ");    TIBETAN_MAP.put("tta", "ཁྲ");    TIBETAN_MAP.put("tda", "ཁྲ");    TIBETAN_MAP.put("da", "གྲ");
    TIBETAN_MAP.put("dra", "དྲ");    TIBETAN_MAP.put("ssa", "སྲ");    TIBETAN_MAP.put("sra", "སྲ");    TIBETAN_MAP.put("nra", "ནྲ");
    TIBETAN_MAP.put("thra", "ཐྲ");    TIBETAN_MAP.put("hra", "ཧྲ");    
    TIBETAN_MAP.put("tri", "ཀྲི");    TIBETAN_MAP.put("tru", "ཀྲུ");    TIBETAN_MAP.put("tre", "ཀྲེ");    TIBETAN_MAP.put("tro", "ཀྲོ");
    TIBETAN_MAP.put("tdri", "ཁྲི");    TIBETAN_MAP.put("tdru", "ཁྲུ");    TIBETAN_MAP.put("tdre", "ཁྲེ");    TIBETAN_MAP.put("tdro", "ཁྲོ");    
    TIBETAN_MAP.put("dri", "དྲི");    TIBETAN_MAP.put("dru", "དྲུ");    TIBETAN_MAP.put("dre", "དྲེ");    TIBETAN_MAP.put("dro", "དྲོ");    TIBETAN_MAP.put("ddi", "དྲི");    TIBETAN_MAP.put("ddu", "དྲུ");    TIBETAN_MAP.put("dde", "དྲེ");    TIBETAN_MAP.put("ddo", "དྲོ");
    TIBETAN_MAP.put("ssi", "སྲི");    TIBETAN_MAP.put("ssu", "སྲུ");    TIBETAN_MAP.put("sse", "སྲེ");    TIBETAN_MAP.put("sso", "སྲོ");
    TIBETAN_MAP.put("hri", "ཧྲི");    TIBETAN_MAP.put("hru", "ཧྲུ");    TIBETAN_MAP.put("hre", "ཧྲེ");    TIBETAN_MAP.put("hro", "ཧྲོ");

    TIBETAN_MAP.put("1", "༡");
    TIBETAN_MAP.put("2", "༢");
    TIBETAN_MAP.put("3", "༣");
    TIBETAN_MAP.put("4", "༤");
    TIBETAN_MAP.put("5", "༥");
    TIBETAN_MAP.put("6", "༦");
    TIBETAN_MAP.put("7", "༧");
    TIBETAN_MAP.put("8", "༨");
    TIBETAN_MAP.put("9", "༩");
    TIBETAN_MAP.put("0", "༠");
}

    private static final Map<String, String> TIBETAN_WORD_DICTIONARY = new LinkedHashMap<>();
    static {
        TIBETAN_WORD_DICTIONARY.put("sangs rgyas", "སངས་རྒྱས");
        TIBETAN_WORD_DICTIONARY.put("bod skad", "བོད་སྐད");
        TIBETAN_WORD_DICTIONARY.put("rdo rje", "རྡོ་རྗེ");
        TIBETAN_WORD_DICTIONARY.put("karma", "ཀརྨ");
        TIBETAN_WORD_DICTIONARY.put("bkra shis", "བཀྲ་ཤིས");
        TIBETAN_WORD_DICTIONARY.put("bde legs", "བདེ་ལེགས");
        TIBETAN_WORD_DICTIONARY.put("cho", "ཆོས");
        TIBETAN_WORD_DICTIONARY.put("dharma", "དྷརྨ");
        TIBETAN_WORD_DICTIONARY.put("thugs rje", "ཐུགས་རྗེ");
        TIBETAN_WORD_DICTIONARY.put("nying rje", "སྙིང་རྗེ་");
        TIBETAN_WORD_DICTIONARY.put("om", "ཨོཾ་");
        TIBETAN_WORD_DICTIONARY.put("ma ni", "མ་ཎི་");
        TIBETAN_WORD_DICTIONARY.put("pad me", "པདྨེ་");
        TIBETAN_WORD_DICTIONARY.put("hung", "ཧཱུྃ་");
        TIBETAN_WORD_DICTIONARY.put("de key", "བདེ་སྐྱིད་");
    }

    // Sorted keys for greedy matching (longest first)
    private static final List<String> SORTED_KEYS;
    static {
        SORTED_KEYS = new ArrayList<>(TIBETAN_MAP.keySet());
        SORTED_KEYS.sort((a, b) -> Integer.compare(b.length(), a.length()));  // descending length
    }

    private static final int MAX_SUGGESTIONS = 5;

    public static String transliterate(String romanString) {
        if (romanString == null) return "";
        romanString = romanString.toLowerCase(Locale.ROOT);

        StringBuilder tibetanOutput = new StringBuilder();
        int i = 0;
        int length = romanString.length();

        while (i < length) {
            char currentChar = romanString.charAt(i);
            
            // Handle spaces as syllable delimiter
            if (currentChar == ' ') {
                tibetanOutput.append("་"); // Tsheg
                i++;
                continue;
            }

            // Handle period as Tibetan punctuation
            if (currentChar == '.') {
                tibetanOutput.append("།");
                i++;
                continue;
            }

            boolean matchFound = false;
            for (String key : SORTED_KEYS) {
                if (romanString.startsWith(key, i)) {
                    // Special handling for single 'e' at syllable end
                    if ("e".equals(key)) {
                        int nextPos = i + key.length();
                        if (nextPos == length || (nextPos < length && romanString.charAt(nextPos) == ' ')) {
                            // Transliterate 'e' as special suffix letter dha
                            tibetanOutput.append("ད");
                            i += key.length();
                            matchFound = true;
                            break;
                        } else {
                            // Otherwise skip standalone 'e' or optionally transliterate as vowel diacritic if mapped
                            i += key.length();
                            matchFound = true;
                            break;
                        }
                    } else {
                        tibetanOutput.append(TIBETAN_MAP.get(key));
                        i += key.length();
                        matchFound = true;
                        break;
                    }
                }
            }

            if (!matchFound) {
                // Skip unknown chars silently
                i++;
            }
        }

        return tibetanOutput.toString();
    }

    public static List<String> getSuggestions(String currentInput) {
        if (currentInput == null || currentInput.length() == 0 || currentInput.endsWith(" ")) {
            return Collections.emptyList();
        }

        String[] parts = currentInput.split("\\s+");
        String lastWord = parts[parts.length - 1].toLowerCase(Locale.ROOT);
        if (lastWord.isEmpty()) return Collections.emptyList();

        List<String> matches = new ArrayList<>();
        for (String word : TIBETAN_WORD_DICTIONARY.keySet()) {
            if (word.startsWith(lastWord)) {
                matches.add(word);
            }
        }
        return matches.size() > MAX_SUGGESTIONS ? matches.subList(0, MAX_SUGGESTIONS) : matches;
    }

    public static String updateInputWithSuggestion(String currentInput, String suggestionClicked) {
        String wSuggestion = suggestionClicked.split(" ")[0];
        String[] parts = currentInput.trim().split("\\s+");

        if (parts.length == 0) {
            return wSuggestion + " ";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length -1; i++) {
            sb.append(parts[i]).append(" ");
        }
        sb.append(wSuggestion).append(" ");
        return sb.toString();
    }

    // --- Simple command line demo ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tibetan Transliteration Tool (Java Console)\nType 'exit' to quit.");
        while (true) {
            System.out.print("\nInput (Roman Tibetan): ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input.trim())) {
                break;
            }

            String transliterated = transliterate(input);
            System.out.println("Tibetan Output: " + transliterated);

            List<String> suggestions = getSuggestions(input);
            if (!suggestions.isEmpty()) {
                System.out.println("Suggestions:");
                for (int i = 0; i < suggestions.size(); i++) {
                    String sug = suggestions.get(i) + " (" + TIBETAN_WORD_DICTIONARY.get(suggestions.get(i)) + ")";
                    System.out.println((i + 1) + ": " + sug);
                }
                System.out.print("Select suggestion number (or Enter to skip): ");
                String sel = scanner.nextLine();
                if (!sel.trim().isEmpty()) {
                    try {
                        int choice = Integer.parseInt(sel.trim()) - 1;
                        if (choice >= 0 && choice < suggestions.size()) {
                            String updatedInput = updateInputWithSuggestion(input, suggestions.get(choice));
                            System.out.println("Updated input: " + updatedInput);
                            System.out.println("Updated Tibetan output: " + transliterate(updatedInput));
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        System.out.println("Goodbye Sayonara");
        scanner.close();
    }
}
