// File: app/java/com/example/entobo/TransliterationEngine.java

package com.example.entobo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransliterationEngine {
    private final Map<String, String> tibetanMap = new HashMap<>();
    private final Set<String> endingLetters = new HashSet<>(Arrays.asList(
            "g", "ng", "n", "b", "m", "r", "l", "s", "p", "k", "dh"
    ));
    private final StringBuilder stack = new StringBuilder();

    public TransliterationEngine() {
        initializeMap();
    }

    private void initializeMap() {
        // Base Consonants
        tibetanMap.put("ka", "ཀ");
        tibetanMap.put("kha", "ཁ");
        tibetanMap.put("gha", "ག");
        tibetanMap.put("nga", "ང");
        tibetanMap.put("ca", "ཅ");
        tibetanMap.put("cha", "ཆ");
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
        tibetanMap.put("wa", "བ");
        tibetanMap.put("bha", "བ");
        tibetanMap.put("ma", "མ");
        tibetanMap.put("tza", "ཙ");
        tibetanMap.put("tzsa", "ཙ");
        tibetanMap.put("tsza", "ཙ");
        tibetanMap.put("tssa", "ཙ");
        tibetanMap.put("tsa", "ཚ");
        tibetanMap.put("za", "ཛ");
        tibetanMap.put("wwa", "ཝ");
        tibetanMap.put("waa", "ཝ");
        tibetanMap.put("zha", "ཞ");
        tibetanMap.put("sza", "ཟ");
        tibetanMap.put("ya", "ཡ");
        tibetanMap.put("ra", "ར");
        tibetanMap.put("la", "ལ");
        tibetanMap.put("sha", "ཤ");
        tibetanMap.put("sa", "ས");
        tibetanMap.put("ha", "ཧ");
        tibetanMap.put("a", "ཨ");

        // Syllable-final (ending) letters ('jejug chuu')
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
        tibetanMap.put("e", "ད");
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
        tibetanMap.put("chhi", "ཅི");
        tibetanMap.put("chhu", "ཅུ");
        tibetanMap.put("chhe", "ཅེ");
        tibetanMap.put("chho", "ཅོ");
        tibetanMap.put("chi", "ཆི");
        tibetanMap.put("chu", "ཆུ");
        tibetanMap.put("che", "ཆེ");
        tibetanMap.put("cho", "ཆོ");
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
        // ### IMPRT TO SUGGESTIONS ### "ta" could be: པྲ ཕྲ བྲ ཏྲ
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
        // ནི ནྲུ ནྲེ ནྲོ
        // SUGGESTIONS :: པྲི པྲ པྲེ པྲ
        // SUGGESTIONS :: ཕྲི ཕྲུ ཕྲེ ཕྲོ
        // SUGGESTIONS :: བྲི བྲུ བྲེ བྲོ
        // SUGGESTIONS :: ཏྲི ཏྲ ཏྲེ ཏྲ
        // SUGGESTIONS :: ཐྲི ཐྲུ ཐྲེ ཐྲོ
        tibetanMap.put("hri", "ཧྲི");
        tibetanMap.put("hru", "ཧྲུ");
        tibetanMap.put("hre", "ཧྲེ");
        tibetanMap.put("hro", "ཧྲོ");

        // Subjoined "la" (latak)
        // ཀླ ཀླི ཀླུ ཀླེ ཀློ
        // གླ གླི གླུ གླེ གློ
        // བླ བླི བླུ བླེ བློ
        // རླ རླི རླུ རླེ རློ
        // ཟླ ཟླི ཟླུ ཟླེ ཟློ
        // སླ སླི སླུ སླེ སློ

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
        tibetanMap.put("chhya", "པྱ");
        tibetanMap.put("chhyi", "པྱི");
        tibetanMap.put("chhyu", "པྱུ");
        tibetanMap.put("chhye", "པྱེ");
        tibetanMap.put("chhyo", "པྱོ");
        tibetanMap.put("chya", "ཕྱ");
        tibetanMap.put("chyi", "ཕྱི");
        tibetanMap.put("chyu", "ཕྱུ");
        tibetanMap.put("chye", "ཕྱེ");
        tibetanMap.put("chyo", "ཕྱོ");
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

    public String getLongestMatch() {
        String stackString = stack.toString();
        // check for longest possible match first
        for (int i = stackString.length(); i > 0; i--) {
            String sub = stackString.substring(0, i);
            if (tibetanMap.containsKey(sub)) {
                return sub;
            }
        }
        return "";
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
