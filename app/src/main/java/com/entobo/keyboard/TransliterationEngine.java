package com.entobo.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransliterationEngine {
    public final Map<String, String> tibetanMap = new LinkedHashMap<>();
    private final Set<String> endingLetters = new HashSet<>(Arrays.asList(
            "g", "ng", "n", "b", "m", "r", "l", "s", "p", "k", "dh" , "q"
    ));
    private final StringBuilder stack = new StringBuilder();
	private String getBaseLetter(String tibetan) {
		if (tibetan == null || tibetan.isEmpty()) return null;

		// first character only (root letter)
		return String.valueOf(tibetan.charAt(0));
	}
    private static final List<String> NGON_JUG_PREFIXES = Arrays.asList(
            "ག", "ད", "བ", "འ", "མ"  //སྟོད་གཞས་གཉིས་གསར་གཏོད་མཛད་མཁན་རྡོ་རིང་པཎྜི་ཏ་
    );

    private static final List<String> STACKERS = Arrays.asList(
            "ར", "ལ", "ས"
    );
	
	private static final Map<String, List<String>> VALID_PREFIX_MAP = new HashMap<>();
	static {
		VALID_PREFIX_MAP.put("ཀ", Arrays.asList("ད", "བ", "འ"));
		VALID_PREFIX_MAP.put("ཁ", Arrays.asList("མ", "འ"));
		VALID_PREFIX_MAP.put("ག", Arrays.asList("ད", "བ", "མ", "འ"));
		VALID_PREFIX_MAP.put("ང", Arrays.asList("ད", "བ", "མ"));
		VALID_PREFIX_MAP.put("ཅ", Arrays.asList("ག", "བ"));
		VALID_PREFIX_MAP.put("ཆ", Arrays.asList("འ", "མ"));
		VALID_PREFIX_MAP.put("ཇ", Arrays.asList("བ", "འ", "མ", "འ"));
		VALID_PREFIX_MAP.put("ཉ", Arrays.asList("ག", "མ", "བ"));
		VALID_PREFIX_MAP.put("ཏ", Arrays.asList("ག", "བ"));
		VALID_PREFIX_MAP.put("ཐ", Arrays.asList("འ", "མ"));
		VALID_PREFIX_MAP.put("ད", Arrays.asList("ག", "བ", "མ", "འ"));
		VALID_PREFIX_MAP.put("ན", Arrays.asList("ག", "མ", "བ"));
		VALID_PREFIX_MAP.put("པ", Arrays.asList("ད"));
		VALID_PREFIX_MAP.put("ཕ", Arrays.asList("འ"));
		VALID_PREFIX_MAP.put("བ", Arrays.asList("ག", "ད", "འ"));
		VALID_PREFIX_MAP.put("མ", Arrays.asList("ད"));
		VALID_PREFIX_MAP.put("ཙ", Arrays.asList("ག", "བ"));
		VALID_PREFIX_MAP.put("ཚ", Arrays.asList("འ"));
		VALID_PREFIX_MAP.put("ཛ", Arrays.asList("བ", "མ", "འ"));
		VALID_PREFIX_MAP.put("ཝ", new ArrayList<>());
		VALID_PREFIX_MAP.put("ཞ", Arrays.asList("ག", "བ", "འ"));
		VALID_PREFIX_MAP.put("ཟ", Arrays.asList("ག", "བ"));
		VALID_PREFIX_MAP.put("འ", new ArrayList<>());
		VALID_PREFIX_MAP.put("ཡ", Arrays.asList("ག"));
		VALID_PREFIX_MAP.put("ར", Arrays.asList("བ"));
		VALID_PREFIX_MAP.put("ལ", new ArrayList<>());
		VALID_PREFIX_MAP.put("ཤ", Arrays.asList("ག", "བ"));
		VALID_PREFIX_MAP.put("ས", Arrays.asList("ག", "བ"));
		VALID_PREFIX_MAP.put("ཧ", new ArrayList<>());
		VALID_PREFIX_MAP.put("ཨ", new ArrayList<>());
	}
	
	private static final Map<String, List<String>> VALID_STACK_MAP = new HashMap<>();
	static {
		VALID_STACK_MAP.put("ཀ", Arrays.asList("ར", "ལ", "ས"));
		VALID_STACK_MAP.put("ག", Arrays.asList("ར", "ལ", "ས"));
		VALID_STACK_MAP.put("ང", Arrays.asList("ར", "ལ", "ས"));
		VALID_STACK_MAP.put("ཅ", Arrays.asList("ལ"));
		VALID_STACK_MAP.put("ཇ", Arrays.asList("ར", "ལ"));
		VALID_STACK_MAP.put("ཉ", Arrays.asList("ར", "ས"));
		VALID_STACK_MAP.put("ཏ", Arrays.asList("ར", "ལ", "ས"));
		VALID_STACK_MAP.put("ད", Arrays.asList("ར", "ལ", "ས"));
		VALID_STACK_MAP.put("ན", Arrays.asList("ར", "ས"));
		VALID_STACK_MAP.put("པ", Arrays.asList("ས", "ལ"));
		VALID_STACK_MAP.put("བ", Arrays.asList("ར", "ལ", "ས"));
		VALID_STACK_MAP.put("མ", Arrays.asList("ར", "ས"));		
		VALID_STACK_MAP.put("ཙ", Arrays.asList("ར", "ས"));	
		VALID_STACK_MAP.put("ཛ", Arrays.asList("ར"));
		VALID_STACK_MAP.put("ཧ", Arrays.asList("ལ"));
	}

    public TransliterationEngine() {
        initializeMap();
    }

    private void initializeMap() {

        // Base Consonants
        tibetanMap.put("ka", "ཀ");
        tibetanMap.put("kha", "ཁ");
        tibetanMap.put("ca", "ཁ");
        tibetanMap.put("gha", "ག");
        tibetanMap.put("nga", "ང");
        tibetanMap.put("cha", "ཅ");
        tibetanMap.put("chha", "ཆ");
        tibetanMap.put("ja", "ཇ");
        tibetanMap.put("jha", "ཇ");
        tibetanMap.put("nya", "ཉ");
        tibetanMap.put("ta", "ཏ");
        tibetanMap.put("tha", "ཐ");
        tibetanMap.put("dha", "ད");
        tibetanMap.put("na", "ན");
        tibetanMap.put("pa", "པ");
        tibetanMap.put("pha", "ཕ");
        tibetanMap.put("ba", "བ");
        tibetanMap.put("wa", "ཝ");
        tibetanMap.put("bha", "བ");
        tibetanMap.put("ma", "མ");
        tibetanMap.put("tza", "ཙ");
        tibetanMap.put("tzsa", "ཙ");
        tibetanMap.put("tsza", "ཙ");
        tibetanMap.put("tssa", "ཙ");
        tibetanMap.put("tsa", "ཚ");
        tibetanMap.put("za", "ཛ");
        tibetanMap.put("waa", "ཝ");
        tibetanMap.put("wa", "ཝ");
        tibetanMap.put("zha", "ཞ");
        tibetanMap.put("sza", "ཟ");
        tibetanMap.put("ya", "ཡ");
        tibetanMap.put("ra", "ར");
        tibetanMap.put("la", "ལ");
        tibetanMap.put("sha", "ཤ");
        tibetanMap.put("sa", "ས");
        tibetanMap.put("ha", "ཧ");
        tibetanMap.put("a", "ཨ");

        //syllable-final (ending) letters ('jejug chuu')
        tibetanMap.put("q", "ག");
        tibetanMap.put("g", "ག");
        tibetanMap.put("ng", "ང");
        tibetanMap.put("n", "ན");
        tibetanMap.put("b", "བ");
        tibetanMap.put("aa", "འ");
        tibetanMap.put("m", "མ");
        tibetanMap.put("l", "ལ");
        tibetanMap.put("r", "ར");
        tibetanMap.put("s", "ས");
        tibetanMap.put("p", "པ");
        tibetanMap.put("k", "ག");
        tibetanMap.put("dh", "ད");
        tibetanMap.put("d", "ད");

        // Vowel diacritics (applied to consonants)
        tibetanMap.put("ki", "ཀི");
        tibetanMap.put("ku", "ཀུ");
        tibetanMap.put("ke", "ཀེ");
        tibetanMap.put("ko", "ཀོ");
        tibetanMap.put("khi", "ཁི");
        tibetanMap.put("khu", "ཁུ");
        tibetanMap.put("khe", "ཁེ");
        tibetanMap.put("kho", "ཁོ");
        tibetanMap.put("gi", "གི");
        tibetanMap.put("gu", "གུ");
        tibetanMap.put("ge", "གེ");
        tibetanMap.put("go", "གོ");
        tibetanMap.put("ghi", "གི");
        tibetanMap.put("ghu", "གུ");
        tibetanMap.put("ghe", "གེ");
        tibetanMap.put("gho", "གོ");
        tibetanMap.put("ngi", "ངི");
        tibetanMap.put("ngu", "ངུ");
        tibetanMap.put("nge", "ངེ");
        tibetanMap.put("ngo", "ངོ");
        tibetanMap.put("chi", "ཅི");
        tibetanMap.put("chu", "ཅུ");
        tibetanMap.put("che", "ཅེ");
        tibetanMap.put("cho", "ཅོ");
        tibetanMap.put("chhi", "ཆི");
        tibetanMap.put("chhu", "ཆུ");
        tibetanMap.put("chhe", "ཆེ");
        tibetanMap.put("chho", "ཆོ");
        tibetanMap.put("jhi", "ཇི");
        tibetanMap.put("jhu", "ཇུ");
        tibetanMap.put("jhe", "ཇེ");
        tibetanMap.put("jho", "ཇོ");
        tibetanMap.put("nyi", "ཉི");
        tibetanMap.put("nyu", "ཉུ");
        tibetanMap.put("nye", "ཉེ");
        tibetanMap.put("nyo", "ཉོ");
        tibetanMap.put("ti", "ཏི");
        tibetanMap.put("tu", "ཏུ");
        tibetanMap.put("te", "ཏེ");
        tibetanMap.put("to", "ཏོ");
        tibetanMap.put("thi", "ཐི");
        tibetanMap.put("thu", "ཐུ");
        tibetanMap.put("the", "ཐེ");
        tibetanMap.put("tho", "ཐོ");
        tibetanMap.put("dhi", "དི");
        tibetanMap.put("dhu", "དུ");
        tibetanMap.put("dhe", "དེ");
        tibetanMap.put("dho", "དོ");
        tibetanMap.put("ni", "ནི");
        tibetanMap.put("nu", "ནུ");
        tibetanMap.put("ne", "ནེ");
        tibetanMap.put("no", "ནོ");
        tibetanMap.put("pi", "པི");
        tibetanMap.put("pu", "པུ");
        tibetanMap.put("pe", "པེ");
        tibetanMap.put("po", "པོ");
        tibetanMap.put("phi", "ཕི");
        tibetanMap.put("phu", "ཕུ");
        tibetanMap.put("phe", "ཕེ");
        tibetanMap.put("pho", "ཕོ");
        tibetanMap.put("bhi", "བི");
        tibetanMap.put("bhu", "བུ");
        tibetanMap.put("bhe", "བེ");
        tibetanMap.put("bho", "བོ");
        tibetanMap.put("bi", "བི");
        tibetanMap.put("bu", "བུ");
        tibetanMap.put("be", "བེ");
        tibetanMap.put("bo", "བོ");
        tibetanMap.put("mi", "མི");
        tibetanMap.put("mu", "མུ");
        tibetanMap.put("me", "མེ");
        tibetanMap.put("mo", "མོ");
        tibetanMap.put("tzi", "ཙི");
        tibetanMap.put("tzu", "ཙུ");
        tibetanMap.put("tze", "ཙེ");
        tibetanMap.put("tzo", "ཙོ");
        tibetanMap.put("tssi", "ཙི");
        tibetanMap.put("tssu", "ཙུ");
        tibetanMap.put("tsse", "ཙེ");
        tibetanMap.put("tsso", "ཙོ");
        tibetanMap.put("tzsi", "ཙི");
        tibetanMap.put("tzsu", "ཙུ");
        tibetanMap.put("tzse", "ཙེ");
        tibetanMap.put("tzso", "ཙོ");
        tibetanMap.put("tszi", "ཙི");
        tibetanMap.put("tszu", "ཙུ");
        tibetanMap.put("tsze", "ཙེ");
        tibetanMap.put("tszo", "ཙོ");
        tibetanMap.put("tsi", "ཚི");
        tibetanMap.put("tsu", "ཚུ");
        tibetanMap.put("tse", "ཚེ");
        tibetanMap.put("tso", "ཚོ");
        tibetanMap.put("zi", "ཛི");
        tibetanMap.put("zu", "ཛུ");
        tibetanMap.put("ze", "ཛེ");
        tibetanMap.put("zo", "ཛོ");
        tibetanMap.put("zhi", "ཞི");
        tibetanMap.put("zhu", "ཞུ");
        tibetanMap.put("zhe", "ཞེ");
        tibetanMap.put("zho", "ཞོ");
        tibetanMap.put("szi", "ཟི");
        tibetanMap.put("szu", "ཟུ");
        tibetanMap.put("sze", "ཟེ");
        tibetanMap.put("szo", "ཟོ");
        tibetanMap.put("wi", "ཝི");
        tibetanMap.put("wu", "ཝུ");
        tibetanMap.put("we", "ཝེ");
        tibetanMap.put("wo", "ཝོ");
        tibetanMap.put("yi", "ཡི");
        tibetanMap.put("yu", "ཡུ");
        tibetanMap.put("ye", "ཡེ");
        tibetanMap.put("yo", "ཡོ");
        tibetanMap.put("ri", "རི");
        tibetanMap.put("ru", "རུ");
        tibetanMap.put("re", "རེ");
        tibetanMap.put("ro", "རོ");
        tibetanMap.put("li", "ལི");
        tibetanMap.put("lu", "ལུ");
        tibetanMap.put("le", "ལེ");
        tibetanMap.put("lo", "ལོ");
        tibetanMap.put("shi", "ཤི");
        tibetanMap.put("shu", "ཤུ");
        tibetanMap.put("she", "ཤེ");
        tibetanMap.put("sho", "ཤོ");
        tibetanMap.put("si", "སི");
        tibetanMap.put("su", "སུ");
        tibetanMap.put("se", "སེ");
        tibetanMap.put("so", "སོ");
        tibetanMap.put("hi", "ཧི");
        tibetanMap.put("hu", "ཧུ");
        tibetanMap.put("he", "ཧེ");
        tibetanMap.put("ho", "ཧོ");
        tibetanMap.put("i", "ཨི");
        tibetanMap.put("u", "ཨུ");
        tibetanMap.put("e", "ཨེ");
        tibetanMap.put("o", "ཨོ");

        // Subjoined "ra" ('ratak')
        tibetanMap.put("tra", "ཀྲ");
        tibetanMap.put("tta", "ཁྲ");
        tibetanMap.put("tda", "ཁྲ");
        tibetanMap.put("da", "གྲ");
        tibetanMap.put("dra", "དྲ");
        tibetanMap.put("ssa", "སྲ");
        tibetanMap.put("sra", "སྲ");
        tibetanMap.put("nra", "ནྲ");
        tibetanMap.put("thra", "ཐྲ");
        tibetanMap.put("hra", "ཧྲ");
        tibetanMap.put("tri", "ཀྲི");
        tibetanMap.put("tru", "ཀྲུ");
        tibetanMap.put("tre", "ཀྲེ");
        tibetanMap.put("tro", "ཀྲོ");
        tibetanMap.put("tdri", "ཁྲི");
        tibetanMap.put("tdru", "ཁྲུ");
        tibetanMap.put("tdre", "ཁྲེ");
        tibetanMap.put("tdro", "ཁྲོ");
        tibetanMap.put("di", "གྲི");
        tibetanMap.put("du", "གྲུ");
        tibetanMap.put("de", "གྲེ");
        tibetanMap.put("do", "གྲོ");
        tibetanMap.put("dri", "དྲི");
        tibetanMap.put("dru", "དྲུ");
        tibetanMap.put("dre", "དྲེ");
        tibetanMap.put("dro", "དྲོ");
        tibetanMap.put("ddi", "དྲི");
        tibetanMap.put("ddu", "དྲུ");
        tibetanMap.put("dde", "དྲེ");
        tibetanMap.put("ddo", "དྲོ");
        tibetanMap.put("ssi", "སྲི");
        tibetanMap.put("ssu", "སྲུ");
        tibetanMap.put("sse", "སྲེ");
        tibetanMap.put("sso", "སྲོ");
        tibetanMap.put("nri", "ནྲི");
        tibetanMap.put("nru", "ནྲུ");
        tibetanMap.put("nre", "ནྲེ");
        tibetanMap.put("nro", "ནྲོ");
        tibetanMap.put("pra", "པྲ");
        tibetanMap.put("pri", "པྲི");
        tibetanMap.put("pru", "པྲུ");
        tibetanMap.put("pre", "པྲེ");
        tibetanMap.put("pro", "པྲོ");
        tibetanMap.put("phra", "ཕྲ");
        tibetanMap.put("phri", "ཕྲི");
        tibetanMap.put("phru", "ཕྲུ");
        tibetanMap.put("phre", "ཕྲེ");
        tibetanMap.put("phro", "ཕྲོ");
        tibetanMap.put("bra", "བྲ");
        tibetanMap.put("bri", "བྲི");
        tibetanMap.put("bru", "བྲུ");
        tibetanMap.put("bre", "བྲེ");
        tibetanMap.put("bro", "བྲོ");
        tibetanMap.put("tara", "ཏྲ");
        tibetanMap.put("tari", "ཏྲི");
        tibetanMap.put("taru", "ཏྲུ");
        tibetanMap.put("tare", "ཏྲེ");
        tibetanMap.put("taro", "ཏྲོ");
        tibetanMap.put("hri", "ཧྲི");
        tibetanMap.put("hru", "ཧྲུ");
        tibetanMap.put("hre", "ཧྲེ");
        tibetanMap.put("hro", "ཧྲོ");

        // Subjoined "la" (latak)
        tibetanMap.put("kla", "ཀླ");
        tibetanMap.put("kli", "ཀླི");
        tibetanMap.put("klu", "ཀླུ");
        tibetanMap.put("kle", "ཀླེ");
        tibetanMap.put("klo", "ཀློ");
        tibetanMap.put("gla", "གླ");
        tibetanMap.put("gli", "གླི");
        tibetanMap.put("glu", "གླུ");
        tibetanMap.put("gle", "གླེ");
        tibetanMap.put("glo", "གློ");
        tibetanMap.put("bla", "བླ");
        tibetanMap.put("bli", "བླི");
        tibetanMap.put("blu", "བླུ");
        tibetanMap.put("ble", "བླེ");
        tibetanMap.put("blo", "བློ");
        tibetanMap.put("rla", "རླ");
        tibetanMap.put("rli", "རླི");
        tibetanMap.put("rlu", "རླུ");
        tibetanMap.put("rle", "རླེ");
        tibetanMap.put("rlo", "རློ");
        tibetanMap.put("zla", "ཟླ");
        tibetanMap.put("zli", "ཟླི");
        tibetanMap.put("zlu", "ཟླུ");
        tibetanMap.put("zle", "ཟླེ");
        tibetanMap.put("zlo", "ཟློ");
        tibetanMap.put("sla", "སླ");
        tibetanMap.put("sli", "སླི");
        tibetanMap.put("slu", "སླུ");
        tibetanMap.put("sle", "སླེ");
        tibetanMap.put("slo", "སློ");

        // Subjoined 'ya' ('yatak')
        tibetanMap.put("kya", "ཀྱ");
        tibetanMap.put("kyi", "ཀྱི");
        tibetanMap.put("kyu", "ཀྱུ");
        tibetanMap.put("kye", "ཀྱེ");
        tibetanMap.put("kyo", "ཀྱོ");
        tibetanMap.put("khya", "ཁྱ");
        tibetanMap.put("khyi", "ཁྱི");
        tibetanMap.put("khyu", "ཁྱུ");
        tibetanMap.put("khye", "ཁྱེ");
        tibetanMap.put("khy", "ཁྱི");
        tibetanMap.put("gya", "གྱ");
        tibetanMap.put("gyi", "གྱི");
        tibetanMap.put("ghyi", "གྱི");
        tibetanMap.put("gyu", "གྱུ");
        tibetanMap.put("gye", "གྱེ");
        tibetanMap.put("gyo", "གྱོ");
        tibetanMap.put("chya", "པྱ");
        tibetanMap.put("chyi", "པྱི");
        tibetanMap.put("chyu", "པྱུ");
        tibetanMap.put("chye", "པྱེ");
        tibetanMap.put("chyo", "པྱོ");
        tibetanMap.put("chhya", "ཕྱ");
        tibetanMap.put("chhyi", "ཕྱི");
        tibetanMap.put("chhyu", "ཕྱུ");
        tibetanMap.put("chhye", "ཕྱེ");
        tibetanMap.put("chhyo", "ཕྱོ");
        tibetanMap.put("jhya", "བྱ");
        tibetanMap.put("jhyi", "བྱི");
        tibetanMap.put("jhyu", "བྱུ");
        tibetanMap.put("jhye", "བྱེ");
        tibetanMap.put("jhyo", "བྱོ");
        tibetanMap.put("nyya", "མྱ");
        tibetanMap.put("nyyi", "མྱི");
        tibetanMap.put("nyyu", "མྱུ");
        tibetanMap.put("nyye", "མྱེ");
        tibetanMap.put("nyyo", "མྱོ");

        // NUMBERS
        tibetanMap.put("1", "༡");
        tibetanMap.put("2", "༢");
        tibetanMap.put("3", "༣");
        tibetanMap.put("4", "༤");
        tibetanMap.put("5", "༥");
        tibetanMap.put("6", "༦");
        tibetanMap.put("7", "༧");
        tibetanMap.put("8", "༨");
        tibetanMap.put("9", "༩");
        tibetanMap.put("0", "༠");
    }



///????????????????????????????????????????????????????????????????????????????????????????????????????????????????///
///??????????????????????????????                                          ????????????????????????????????????????///
///?????????????????????????????? SUGGESTION GENERATION FOR CANDIDATE VIEW ????????????????????????????????????????///
///??????????????????????????????                                          ????????????????????????????????????????///
///????????????????????????????????????????????????????????????????????????????????????????????????????????????????///

    public List<String> getSuggestions() {
        String currentSyllable = getStack();
        List<String> suggestions = new ArrayList<>();

        //we only want to generate suggestions for single base consonants.
        //this is much simpler and more effective.
        if (currentSyllable.length() != 1) {
            return suggestions; // Return empty list
        }

        if (!tibetanMap.containsKey(currentSyllable)) {
            return suggestions; //return empty if the character is not in the map (e.g., 'q')
        }

        String baseTibetan = tibetanMap.get(currentSyllable);
        if (baseTibetan == null || baseTibetan.isEmpty()) {
            return suggestions;
        }

        char baseChar = baseTibetan.charAt(0);

        //generate Prefixed (ngonjug) suggestions
        for (String prefix : NGON_JUG_PREFIXES) {
            suggestions.add(prefix + baseTibetan);
        }

        //generate Stacked suggestions
        if (baseChar >= 0x0F40 && baseChar <= 0x0F6C) {
            char subjoined = (char) (0x0F90 + (baseChar - 0x0F40));
            for (String stacker : STACKERS) {
                suggestions.add(stacker + String.valueOf(subjoined));
            }
        }

        return new ArrayList<>(new HashSet<>(suggestions));
    }
    
	public class SuggestionResult {
		public List<String> structure = new ArrayList<>();
		public List<String> words = new ArrayList<>();
	}

	public SuggestionResult getSuggestionsAdvanced() {
		SuggestionResult result = new SuggestionResult();

		String input = getStack();
		if (input.isEmpty()) return result;

		result.structure = generateStructureSuggestions(input);
		result.words = generateWordSuggestions(input);

		return result;
	}

	private List<String> generateStructureSuggestions(String input) {
		List<String> output = new ArrayList<>();

		String base = getLongestMatch();
		if (base.isEmpty()) return output;

		String baseTibetan = tibetanMap.get(base);
		if (baseTibetan == null || baseTibetan.isEmpty()) return output;

		String rootLetter = getBaseLetter(baseTibetan);
		if (rootLetter == null) return output;

		char baseChar = rootLetter.charAt(0);

		List<String> validPrefixes = VALID_PREFIX_MAP.get(rootLetter);
		if (validPrefixes != null) {
		    for (String prefix : validPrefixes) {
		        if (prefix != null && !prefix.isEmpty()) {
		            output.add(prefix + rootLetter);
		        }
		    }
		}

		List<String> validStackers = VALID_STACK_MAP.get(rootLetter);
		if (validStackers != null) {
		    if (baseChar >= 0x0F40 && baseChar <= 0x0F6C) {
		        char subjoined = (char) (0x0F90 + (baseChar - 0x0F40));
		        for (String stacker : validStackers) {
		            if (stacker != null && !stacker.isEmpty()) {
		                output.add(stacker + subjoined);
		            }
		        }
		    }
		}

		return output;
	}
	
	private List<String> generateWordSuggestions(String input) {
		List<String> output = new ArrayList<>();
		Set<String> seen = new HashSet<>();
		String inputTibetan = tibetanMap.get(input);
		if (inputTibetan != null && seen.add(inputTibetan)) {
		    output.add(inputTibetan);
		}
		for (String key : tibetanMap.keySet()) {
		    if (key.startsWith(input) && key.length() > input.length()) {
		        String tib = tibetanMap.get(key);
		        if (tib != null && seen.add(tib)) {
		            output.add(tib);
		        }
		    }
		}
		if (inputTibetan != null && !inputTibetan.isEmpty()) {
		    String rootLetter = getBaseLetter(inputTibetan);

		    if (rootLetter != null) {
		        for (String key : tibetanMap.keySet()) {
		            String tib = tibetanMap.get(key);
		            if (tib == null || tib.isEmpty()) continue;

		            String tibRoot = getBaseLetter(tib);
		            if (rootLetter.equals(tibRoot)) {
		                if (seen.add(tib)) {
		                    output.add(tib);
		                }
		            }
		        }
		    }
		}
		return output;
	}
	

///????????????????????????????????????????????????????????????????????????????????????????????????????????????????///
///??????????????????????????????                                          ????????????????????????????????????????///
///??????????????????????????????       MULTI SYLLABLE HANDLER             ????????????????????????????????????????///
///??????????????????????????????                                          ????????????????????????????????????????///
///????????????????????????????????????????????????????????????????????????????????????????????????????????????????///

	private List<String> splitSyllables(String input) {
		List<String> syllables = new ArrayList<>();

		StringBuilder current = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
		    current.append(input.charAt(i));

		    // if next char starts a new consonant cluster → split
		    if (i < input.length() - 1) {
		        String next = input.substring(i + 1);

		        // if current chunk is a valid syllable end
		        if (isEndingLetter(String.valueOf(input.charAt(i)))) {
		            syllables.add(current.toString());
		            current.setLength(0);
		        }
		    }
		}

		if (current.length() > 0) {
		    syllables.add(current.toString());
		}

		return syllables;
	}
	
	private String transliterateSingleSyllable(String input) {
		StringBuilder output = new StringBuilder();

		int i = 0;

		while (i < input.length()) {
		    boolean matchFound = false;

		    for (int j = input.length(); j > i; j--) {
		        String sub = input.substring(i, j);

		        if (tibetanMap.containsKey(sub)) {
		            output.append(tibetanMap.get(sub));
		            i = j;
		            matchFound = true;
		            break;
		        }
		    }

		    if (!matchFound) {
		        i++;
		    }
		}

		return output.toString();
	}

///????????????????????????????????????????????????????????????????????????????????????????????????????????????????///
///??????????????????????????????                                          ????????????????????????????????????????///
///??????????????????????????????       MATHCING WORD FUNCITONS            ????????????????????????????????????????///
///??????????????????????????????                                          ????????????????????????????????????????///
///????????????????????????????????????????????????????????????????????????????????????????????????????????????????///
    public String getLongestMatch() {
        String stackString = stack.toString();
        for (int i = stackString.length(); i > 0; i--) {
            String sub = stackString.substring(0, i);
            if (tibetanMap.containsKey(sub)) {
                return sub;
            }
        }
        return "";
    }

    public String transliterateStack() {
        String stackToProcess = stack.toString();
        StringBuilder output = new StringBuilder();
        int i = 0;
        while (i < stackToProcess.length()) {
            boolean matchFound = false;
            //;ook for the longest possible match from the current position
            for (int j = stackToProcess.length(); j > i; j--) {
                String sub = stackToProcess.substring(i, j);
                if (tibetanMap.containsKey(sub)) {
                    output.append(tibetanMap.get(sub));
                    i = j; //move the index past the matched part
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                //if no match was found for the character(s) at index i skip them
                i++;
            }
        }
        return output.toString();
    }

    public void push(String input) {
        stack.append(input);
    }

    public void backspace() {
        if (stack.length() > 0) {
            stack.deleteCharAt(stack.length() - 1);
        }
    }

    public void clearStack() {
        stack.setLength(0);
    }

    public boolean isEndingLetter(String input) {
        return endingLetters.contains(input);
    }

    public String getTibetan(String roman) {
        return tibetanMap.get(roman);
    }

    public int getStackLength() {
        return stack.length();
    }

    public String getStack() {
        return stack.toString();
    }
}
