import java.util.HashMap;
import java.util.Map;

public class TibetanMap {
    private static final Map<String, String> tibetanMap = new HashMap<>();

    static {
        tibetanMap.put("e", "ད");
        tibetanMap.put("ka", "ཀ");        tibetanMap.put("kha", "ཁ");        tibetanMap.put("gha", "ག");       tibetanMap.put("nga", "ང");        
        tibetanMap.put("ca", "ཅ");        tibetanMap.put("cha", "ཆ");        tibetanMap.put("ja", "ཇ");        tibetanMap.put("jha", "ཇ");        tibetanMap.put("nya", "ཉ");
        tibetanMap.put("ta", "ཏ");        tibetanMap.put("tha", "ཐ");        tibetanMap.put("dha", "ད");       tibetanMap.put("dh", "ད");         tibetanMap.put("na", "ན");
        tibetanMap.put("pa", "པ");        tibetanMap.put("pha", "ཕ");        tibetanMap.put("ba", "བ");        tibetanMap.put("bha", "བ");        tibetanMap.put("ma", "མ");
        tibetanMap.put("tza", "ཙ");       tibetanMap.put("tsa", "ཚ");        tibetanMap.put("za", "ཛ");        tibetanMap.put("wa", "ཝ");         tibetanMap.put("zha", "ཞ");
        tibetanMap.put("sza", "ཟ");       tibetanMap.put("ya", "ཡ");         tibetanMap.put("ra", "ར");        tibetanMap.put("la", "ལ");         tibetanMap.put("sha", "ཤ");
        tibetanMap.put("sa", "ས");        tibetanMap.put("ha", "ཧ");         tibetanMap.put("a", "ཨ");        
        //jejug chuu
        tibetanMap.put("g", "ག");        tibetanMap.put("ng", "ང");        tibetanMap.put("n", "ན");        tibetanMap.put("b", "བ");        tibetanMap.put("aa", "འ");
        tibetanMap.put("m", "མ");        tibetanMap.put("l", "ལ");        tibetanMap.put("r", "ར");        tibetanMap.put("s", "ས");        tibetanMap.put("p", "པ");
        tibetanMap.put("k", "ག");
        // i u e o ི ུ ེ ོ
        tibetanMap.put("ki", "ཀི");    tibetanMap.put("ku", "ཀུ");    tibetanMap.put("ke", "ཀེ");    tibetanMap.put("ko", "ཀོ");    
        tibetanMap.put("khi", "ཁི");    tibetanMap.put("khu", "ཁུ");    tibetanMap.put("khe", "ཁེ");    tibetanMap.put("kho", "ཁོ");
        tibetanMap.put("gi", "གི");    tibetanMap.put("gu", "གུ");    tibetanMap.put("ge", "གེ");    tibetanMap.put("go", "གོ");
        tibetanMap.put("ghi", "གི");    tibetanMap.put("ghu", "གུ");    tibetanMap.put("ghe", "གེ");    tibetanMap.put("gho", "གོ");
        tibetanMap.put("ngi", "ངི");    tibetanMap.put("ngu", "ངུ");    tibetanMap.put("nge", "ངེ");    tibetanMap.put("ngo", "ངོ");
        tibetanMap.put("chhi", "ཅི");    tibetanMap.put("chhu", "ཅུ");    tibetanMap.put("chhe", "ཅེ");    tibetanMap.put("chho", "ཅོ");
        tibetanMap.put("chi", "ཆི");    tibetanMap.put("chu", "ཆུ");    tibetanMap.put("che", "ཆེ");    tibetanMap.put("cho", "ཆོ");
        tibetanMap.put("jhi", "ཇི");    tibetanMap.put("jhu", "ཇུ");    tibetanMap.put("jhe", "ཇེ");    tibetanMap.put("jho", "ཇོ");
        tibetanMap.put("nyi", "ཉི");    tibetanMap.put("nyu", "ཉུ");    tibetanMap.put("nye", "ཉེ");    tibetanMap.put("nyo", "ཉོ");
        tibetanMap.put("ti", "ཏི");     tibetanMap.put("tu", "ཏུ");     tibetanMap.put("te", "ཏེ");     tibetanMap.put("to", "ཏོ");
        tibetanMap.put("thi", "ཐི");    tibetanMap.put("thu", "ཐུ");    tibetanMap.put("the", "ཐེ");    tibetanMap.put("tho", "ཐོ");
        tibetanMap.put("di", "དི");    tibetanMap.put("du", "དུ");    tibetanMap.put("de", "དེ");    tibetanMap.put("do", "དོ");
        tibetanMap.put("dhi", "དི");    tibetanMap.put("dhu", "དུ");    tibetanMap.put("dhe", "དེ");    tibetanMap.put("dho", "དོ");
        tibetanMap.put("ni", "ནི");     tibetanMap.put("nu", "ནུ");     tibetanMap.put("ne", "ནེ");     tibetanMap.put("no", "ནོ");
        tibetanMap.put("pi", "པི");     tibetanMap.put("pu", "པུ");     tibetanMap.put("pe", "པེ");     tibetanMap.put("po", "པོ");
        tibetanMap.put("phi", "ཕི");    tibetanMap.put("phu", "ཕུ");    tibetanMap.put("phe", "ཕེ");    tibetanMap.put("pho", "ཕོ");
        tibetanMap.put("bhi", "བི");    tibetanMap.put("bhu", "བུ");    tibetanMap.put("bhe", "བེ");    tibetanMap.put("bho", "བོ");
        tibetanMap.put("mi", "མི");     tibetanMap.put("mu", "མུ");     tibetanMap.put("me", "མེ");     tibetanMap.put("mo", "མོ");
        tibetanMap.put("tsi", "ཙི");    tibetanMap.put("tsu", "ཙུ");    tibetanMap.put("tse", "ཙེ");    tibetanMap.put("tso", "ཙོ");
        tibetanMap.put("tsi", "ཚི");    tibetanMap.put("tsu", "ཚུ");    tibetanMap.put("tse", "ཚེ");    tibetanMap.put("tso", "ཚོ");
        tibetanMap.put("zi", "ཛི ");     tibetanMap.put("zu", "ཛུ");     tibetanMap.put("ze", "ཛེ");     tibetanMap.put("zo", "ཛོ");
        tibetanMap.put("zhi", "ཞི");    tibetanMap.put("zhu", "ཞུ");    tibetanMap.put("zhe", "ཞེ");    tibetanMap.put("zho", "ཞོ");
        tibetanMap.put("szi", "ཟི");    tibetanMap.put("szu", "ཟུ");    tibetanMap.put("sze", "ཟེ");    tibetanMap.put("szo", "ཟོ");
        tibetanMap.put("wi", "ཝི");     tibetanMap.put("wu", "ཝུ");     tibetanMap.put("we", "ཝེ");     tibetanMap.put("wo", "ཝོ");
        tibetanMap.put("yi", "ཡི");     tibetanMap.put("yu", "ཡུ");    tibetanMap.put("ye", "ཡེ");    tibetanMap.put("yo", "ཡོ");
        tibetanMap.put("ri", "རི");    tibetanMap.put("ru", "རུ");    tibetanMap.put("re", "རེ");    tibetanMap.put("ro", "རོ");
        tibetanMap.put("li", "ལི");    tibetanMap.put("lu", "ལུ");    tibetanMap.put("le", "ལེ");    tibetanMap.put("lo", "ལོ");
        tibetanMap.put("shi", "ཤི");   tibetanMap.put("shu", "ཤུ");   tibetanMap.put("she", "ཤེ");    tibetanMap.put("sho", "ཤོ");
        tibetanMap.put("si", "སི");    tibetanMap.put("su", "སུ");    tibetanMap.put("se", "སེ");    tibetanMap.put("so", "སོ");
        tibetanMap.put("hi", "ཧི");    tibetanMap.put("hu", "ཧུ");    tibetanMap.put("he", "ཧེ");        tibetanMap.put("o", "ཧོ");
        tibetanMap.put("i", "ཨི");    tibetanMap.put("u", "ཨུ");    tibetanMap.put("e", "ཨེ");    tibetanMap.put("o", "ཨོ");
        // yatak
        tibetanMap.put("kya", "ཀྱ");    tibetanMap.put("kyi", "ཀྱི");   tibetanMap.put("kye", "ཀྱེ");   tibetanMap.put("kyu", "ཀྱུ");   tibetanMap.put("kyo", "ཀྱོ");
        tibetanMap.put("khya", "ཁྱ");   tibetanMap.put("khyi", "ཁྱི");  tibetanMap.put("khyu", "ཁྱུ");  tibetanMap.put("khye", "ཁྱེ");  tibetanMap.put("khy", "ཁྱི");
        tibetanMap.put("gya", "གྱ");    tibetanMap.put("gyi", "གྱི");   tibetanMap.put("ghyi", "གྱི");   tibetanMap.put("gye", "གྱེ");   tibetanMap.put("gyu", "གྱུ");   tibetanMap.put("gyo", "གྱོ");
        tibetanMap.put("chya", "ཨི");    tibetanMap.put("chyi", "ཨི");  tibetanMap.put("chyu", "ཨི");  tibetanMap.put("chye", "ཨི");    tibetanMap.put("chyo", "ཨི");
        tibetanMap.put("jhya", "ཨི");    tibetanMap.put("jhyi", "ཨི");  tibetanMap.put( "jhi", " ");   tibetanMap.put("jhyu", "ཨི");  tibetanMap.put("jhye", "ཨི");    tibetanMap.put("jhyo", "ཨི");
        tibetanMap.put("mya", "ཨི");    tibetanMap.put("myi", "ཨི");  tibetanMap.put("myu", "ཨི");  tibetanMap.put("mye", "ཨི");    tibetanMap.put("myo", "ཨི");
        tibetanMap.put("nya", "ཨི");    tibetanMap.put("nyi", "ཨི");  tibetanMap.put("nyu", "ཨི");  tibetanMap.put("nye", "ཨི");    tibetanMap.put("nyo", "ཨི");
    }

    public static String get(String key) {
        return tibetanMap.get(key);
    }

    public static boolean containsKey(String key) {
        return tibetanMap.containsKey(key);
    }
}
