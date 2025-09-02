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

    // This is your complete map of rules
    private void initializeMap() {
        // Base Consonants + 'a'
        tibetanMap.put("ka", "ཀ");    tibetanMap.put("kha", "ཁ");   tibetanMap.put("ga", "ག");     tibetanMap.put("gha", "ག");    tibetanMap.put("nga", "ང");
        tibetanMap.put("ca", "ཅ");    tibetanMap.put("cha", "ཆ");   tibetanMap.put("ja", "ཇ");     tibetanMap.put("jha", "ཇ");    tibetanMap.put("nya", "ཉ");
        tibetanMap.put("ta", "ཏ");    tibetanMap.put("tha", "ཐ");   tibetanMap.put("da", "ད");     tibetanMap.put("dha", "ད");    tibetanMap.put("na", "ན");
        tibetanMap.put("pa", "པ");    tibetanMap.put("pha", "ཕ");   tibetanMap.put("ba", "བ");     tibetanMap.put("bha", "བ");    tibetanMap.put("ma", "མ");
        tibetanMap.put("tsa", "ཙ");   tibetanMap.put("tsha", "ཚ");  tibetanMap.put("dza", "ཛ");    tibetanMap.put("wa", "ཝ");     tibetanMap.put("zha", "ཞ");
        tibetanMap.put("za", "ཟ");    tibetanMap.put("ya", "ཡ");    tibetanMap.put("ra", "ར");     tibetanMap.put("la", "ལ");     tibetanMap.put("sha", "ཤ");
        tibetanMap.put("sa", "ས");    tibetanMap.put("ha", "ཧ");    tibetanMap.put("a", "ཨ");

        // Jejugu (Ending letters without vowels)
        tibetanMap.put("g", "ག");     tibetanMap.put("ng", "ང");    tibetanMap.put("n", "ན");      tibetanMap.put("b", "བ");
        tibetanMap.put("m", "མ");     tibetanMap.put("r", "ར");     tibetanMap.put("l", "ལ");      tibetanMap.put("s", "ས");
        tibetanMap.put("p", "པ");     tibetanMap.put("k", "ག");     tibetanMap.put("d", "ད");     tibetanMap.put("dh", "ད");

        // Vowel combinations (ki, ku, ke, ko, etc.)
        tibetanMap.put("ki", "ཀི");   tibetanMap.put("ku", "ཀུ");   tibetanMap.put("ke", "ཀེ");    tibetanMap.put("ko", "ཀོ");
        tibetanMap.put("khi", "ཁི");  tibetanMap.put("khu", "ཁུ");  tibetanMap.put("khe", "ཁེ");   tibetanMap.put("kho", "ཁོ");
        tibetanMap.put("gi", "གི");   tibetanMap.put("gu", "གུ");   tibetanMap.put("ge", "གེ");    tibetanMap.put("go", "གོ");
        // ... Add all your other vowel combinations here ...
        tibetanMap.put("shi", "ཤི");  tibetanMap.put("shu", "ཤུ");  tibetanMap.put("she", "ཤེ");   tibetanMap.put("sho", "ཤོ");
        tibetanMap.put("si", "སི");   tibetanMap.put("su", "སུ");   tibetanMap.put("se", "སེ");    tibetanMap.put("so", "སོ");
        tibetanMap.put("i", "ཨི");    tibetanMap.put("u", "ཨུ");    tibetanMap.put("e", "ཨེ");    tibetanMap.put("o", "ཨོ");

        // Yatak combinations (kya, khya, etc.)
        tibetanMap.put("kya", "ཀྱ");  tibetanMap.put("khya", "ཁྱ"); tibetanMap.put("gya", "གྱ");
        tibetanMap.put("kyi", "ཀྱི");  tibetanMap.put("kyu", "ཀྱུ");  tibetanMap.put("kye", "ཀྱེ");   tibetanMap.put("kyo", "ཀྱོ");
        // ... Add all other Yatak combinations ...
    }

    public String getLongestMatch() {
        String stackString = stack.toString();
        // Check for longest possible match first
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
