import java.util.*;
public class Tibetan {
    static Map<String, String> tibetanMap = new HashMap<>();
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
    static String[] endingLetters = {"g", "ng", "n", "b", "m", "r", "l" ,"s", "p", "k", "dh"};
    static boolean waitingForNextInput = false; // Flag
    static String storeEndingLetter = ""; //jejug
    static String firstCurrentInput ="";
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        StringBuilder currentInput = new StringBuilder();
        StringBuilder tibetanTransliteration = new StringBuilder();
        System.out.println("Start typing (type 'exit' to stop):");
        while (true) {
            String input = scanner.nextLine();
            if(input.equals("exit")) {
                break;
            }
            if(input.equals("clear")){
                currentInput.setLength(0);
                storeEndingLetter = "";
                firstCurrentInput = "";
                input = "";
                waitingForNextInput = false;
                tibetanTransliteration.setLength(0);
                continue;
            }
            if(input.equals("backspace")) {
                if (currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                } else if (tibetanTransliteration.length() > 0) {
                    tibetanTransliteration.deleteCharAt(tibetanTransliteration.length() - 1);
                }
                System.out.println("Stack letter: " + currentInput.toString());
                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                continue;
            }
            if (input.equals(" ")) {
                if(currentInput.length() == 0){
                    waitingForNextInput = false;
                    tibetanTransliteration.append("་");
                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                    continue;
                }
                if(String.valueOf(currentInput).equals("k")){
                    tibetanTransliteration.append("ག");
                    currentInput.setLength(0);
                    storeEndingLetter = "";
                    input = "";
                    waitingForNextInput = false;
                    tibetanTransliteration.append("་");
                    System.out.println("Stack letter: " + currentInput.toString());
                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                    continue;
                }
                if(matchedCombination(storeEndingLetter) != false){
                    tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                    currentInput.setLength(0);
                    storeEndingLetter = "";
                    input = "";
                    waitingForNextInput = false;
                    tibetanTransliteration.append("་");
                    System.out.println("Stack letter: " + currentInput.toString());
                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                    continue;
                }
            }
            if (waitingForNextInput){
                if((storeEndingLetter.length() > 0) && (String.valueOf(storeEndingLetter.charAt(0)).equals("n"))){
                    if(currentInput.length() > 1){
                        if((String.valueOf(currentInput.charAt(1)).equals("g"))){
                            if(input.equals("a")){
                                currentInput.append(input);
                                if(matchedCombination(currentInput.toString()) != false){
                                    tibetanTransliteration.append(tibetanMap.get(currentInput));
                                    waitingForNextInput = false; // Reset the flag
                                    System.out.println("Stack letter: " + currentInput.toString());
                                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                    storeEndingLetter = "";
                                    tibetanTransliteration.setLength(0);
                                    currentInput.setLength(0);
                                    continue;
                                }
                            }
                            else if(isEndingLetter(input)){
                                if(matchedCombination(storeEndingLetter) != false){
                                    tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                                    currentInput.setLength(0);
                                    storeEndingLetter = "";
                                    currentInput.append(input);
                                    storeEndingLetter = input; // Store the last ending letter
                                    waitingForNextInput = true; // Set flag to wait for next input
                                    System.out.println("Stack letter: " + currentInput.toString());
                                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                    System.out.println("Waiting for next input...");
                                    continue;
                                }
                            }
                            else{
                                if(matchedCombination(storeEndingLetter) != false){
                                    tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                                    waitingForNextInput = false; // Reset the flag
                                    currentInput.deleteCharAt(0);
                                    System.out.println("Stack letter: " + currentInput.toString());
                                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                    storeEndingLetter = "";
                                    currentInput.append(input); // Add the non'a' input to current
                                    continue;
                                }
                            }
                        }
                        else if(String.valueOf(currentInput.charAt(0)).equals("y")){
                            if(input.equals("a") || input.equals("i") || input.equals("u") || input.equals("e") ||input.equals("o")){
                                currentInput.append(input);

                                if(matchedCombination(currentInput.toString()) != false){
                                    tibetanTransliteration.append(tibetanMap.get(currentInput));
                                    waitingForNextInput = false; // Reset the flag
                                    System.out.println("Stack letter: " + currentInput.toString());
                                    System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                    storeEndingLetter = "";
                                    currentInput.setLength(0);
                                    continue;
                                }
                            }

                            if(matchedCombination(storeEndingLetter) != false){
                                tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                                waitingForNextInput = false; // Reset the flag
                                currentInput.deleteCharAt(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                storeEndingLetter = "";
                                currentInput.append(input);
                                continue;
                            }
                        }
                    }
                    else if(input.equals("g")){
                        storeEndingLetter += input;
                        waitingForNextInput = true;
                        System.out.println("Waiting for next input...");
                        continue;
                    }
                }
                if(input.equals("i") || input.equals("u") || input.equals("e") ||input.equals("o")) {
                    currentInput.append(input);

                    if(matchedCombination(currentInput.toString()) != false){
                        tibetanTransliteration.append(tibetanMap.get(currentInput));
                        waitingForNextInput = false;
                        input= "";
                        storeEndingLetter = "";
                        currentInput.setLength(0);
                        System.out.println("Stack letter: " + currentInput);
                        System.out.println("Transliteration: " + tibetanTransliteration.toString());
                        continue;
                    }
                }
                else if(input.equals("y")){
                    currentInput.append(input);
                    waitingForNextInput = true;
                    System.out.println("Waiting for next input...");
                    continue; // Don't process input yet, wait for next input
                }
                else if(input.equals("z")){
                    firstCurrentInput = String.valueOf(currentInput.charAt(0));
                    if(firstCurrentInput.equals("s") || firstCurrentInput.equals("t")){
                        currentInput.append(input);
                        System.out.println("Stack letter: " + currentInput.toString());
                        System.out.println("Waiting for next input... ");
                        input = scanner.nextLine();
                        if(input.equals("a") || input.equals("i") || input.equals("u") || input.equals("e") || input.equals("o")){
                            currentInput.append(input);
                            if(matchedCombination(currentInput.toString()) != false){
                                tibetanTransliteration.append(tibetanMap.get(currentInput));
                                waitingForNextInput = false;
                                input= "";
                                storeEndingLetter = "";
                                firstCurrentInput = "";
                                currentInput.setLength(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                continue;
                            }
                        }
                        else if(isEndingLetter(input)){
                            if(matchedCombination(storeEndingLetter) != false){
                                tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                                currentInput.setLength(0);
                                storeEndingLetter = "";
                                currentInput.append(input);
                                storeEndingLetter += input;
                                firstCurrentInput = "";
                                input = "";
                                waitingForNextInput = true; // Set flag to wait for next input
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                System.out.println("Waiting for next input...");
                                continue;
                            }
                        }
                        else if(input.equals("i") || input.equals("u") || input.equals("e") ||input.equals("o")){
                            currentInput.append(input);
                            if(matchedCombination(currentInput.toString()) != false){
                                tibetanTransliteration.append(tibetanMap.get(currentInput));
                                waitingForNextInput = false;
                                input= "";
                                storeEndingLetter = "";
                                currentInput.setLength(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                continue;
                            }
                        }
                        else{

                            if(matchedCombination(String.valueOf(storeEndingLetter.charAt(0))) != false){
                                tibetanTransliteration.append(tibetanMap.get(storeEndingLetter.charAt(0)));
                                waitingForNextInput = false; // Reset the flag
                                currentInput.deleteCharAt(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                storeEndingLetter = "";
                                currentInput.append(input); // Add the non'a' input to current
                                continue;
                            }
                        }
                    }
                }
                else if(input.equals("h")){
                    firstCurrentInput = String.valueOf(currentInput.charAt(0));
                    if(firstCurrentInput.equals("k")||firstCurrentInput.equals("j")||firstCurrentInput.equals("c")||firstCurrentInput.equals("g")||
                    firstCurrentInput.equals("t")||firstCurrentInput.equals("d")||firstCurrentInput.equals("p")||firstCurrentInput.equals("b")||
                    firstCurrentInput.equals("z")||firstCurrentInput.equals("s")){
                        currentInput.append(input.toString());
                        System.out.println("Stack letter: " + currentInput.toString());
                        System.out.println("Waiting for next input...");
                        input = "";
                        input = scanner.nextLine();
                        if(input.equals("a")){
                            currentInput.append(input.toString());
                            if(matchedCombination(currentInput.toString()) != false){
                                tibetanTransliteration.append(tibetanMap.get(currentInput));
                                waitingForNextInput = false;
                                input= "";
                                storeEndingLetter = "";
                                firstCurrentInput = "";
                                currentInput.setLength(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                continue;
                            }
                        }
                        else if(input.equals("y")){
                            currentInput.append(input);
                            waitingForNextInput = true;
                            System.out.println("Waiting for next input...");
                            continue; // Don't process input yet, wait for next input
                        }
                        else if(isEndingLetter(input)){

                            if(matchedCombination(storeEndingLetter) != false){
                                tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                                currentInput.setLength(0);
                                storeEndingLetter = "";
                                currentInput.append(input);
                                storeEndingLetter += input;
                                firstCurrentInput = "";
                                input = "";
                                waitingForNextInput = true; // Set flag to wait for next input
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                System.out.println("Waiting for next input...");
                                continue;
                            }
                        }
                        else if(input.equals("i") || input.equals("u") || input.equals("e") ||input.equals("o")){
                            currentInput.append(input);

                            if(matchedCombination(currentInput.toString()) != false){
                                tibetanTransliteration.append(tibetanMap.get(currentInput));
                                waitingForNextInput = false;
                                input= "";
                                storeEndingLetter = "";
                                currentInput.setLength(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                continue;
                            }
                        }
                        else{

                            if(matchedCombination(String.valueOf(storeEndingLetter.charAt(0))) != false){
                                tibetanTransliteration.append(tibetanMap.get(storeEndingLetter.charAt(0)));
                                waitingForNextInput = false; // Reset the flag
                                currentInput.deleteCharAt(0);
                                System.out.println("Stack letter: " + currentInput.toString());
                                System.out.println("Transliteration: " + tibetanTransliteration.toString());
                                storeEndingLetter = "";
                                currentInput.append(input); // Add the non'a' input to current
                                continue;
                            }
                        }
                    }
                }
                else if(isEndingLetter(input)){
                    if(matchedCombination(storeEndingLetter) != false){
                        tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                        currentInput.setLength(0);
                        storeEndingLetter = "";
                        currentInput.append(input);
                        storeEndingLetter = input; // Store the last ending letter
                        waitingForNextInput = true; // Set flag to wait for next input
                        System.out.println("Stack letter: " + currentInput.toString());
                        System.out.println("Transliteration: " + tibetanTransliteration.toString());
                        System.out.println("Waiting for next input...");
                        continue;
                    }
                }
                else if(input.equals("a")){
                    storeEndingLetter += input;
                    if(matchedCombination(storeEndingLetter) != false){
                        tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                        storeEndingLetter = "";
                        currentInput.setLength(0);
                        waitingForNextInput = false; // Reset the flag
                        System.out.println("Stack letter: " + currentInput.toString());
                        System.out.println("Transliteration: " + tibetanTransliteration.toString());
                        continue;
                    }
                }
                else{
                    if(matchedCombination(storeEndingLetter) != false){
                        tibetanTransliteration.append(tibetanMap.get(storeEndingLetter));
                        currentInput.setLength(0);
                        storeEndingLetter = "";
                        currentInput.append(input); // Add the non'a' input to currentInput
                        waitingForNextInput = false; // Reset the flag
                        System.out.println("Stack letter: " + currentInput.toString());
                        System.out.println("Transliteration: " + tibetanTransliteration.toString());
                        continue;
                    }
                }
            }
            currentInput.append(input); // Add input to currentInput
            System.out.println("Stack letter: " + currentInput.toString());
            // Check if the currentInput is an "ending letter"
            if (isEndingLetter(input)) {
                storeEndingLetter = input; // Store the last ending letter
                waitingForNextInput = true; // Set flag to wait for next input
                System.out.println("Waiting for next input...");
                continue; // Don't process this input yet, wait for next input
            }
            // Check if any combination of the currentInput exists in tibetanMap
            String possibleMatch = tibetanMap.get(currentInput.toString());
            if (possibleMatch != null) {
                tibetanTransliteration.append(getCombination(possibleMatch)); // Convert the valid match
                currentInput.setLength(0); // Reset currentInput after a match
            }
            System.out.println("Transliteration: " + tibetanTransliteration.toString());
            System.out.println("Valid: " + (currentInput.length() == 0 ? "Y" : "N"));

        }
        scanner.close();
    }
    // Check if the current input is an "ending letter"
    private static boolean isEndingLetter(String input) {
        for (String ending : endingLetters) {
            if (ending.equals(input)) {
                return true;
            }
        }
        return false;
    }
    // Check if the current input forms a valid Tibetan letter combination
    private static String getCombination(String currentInput) {
        // Loop through input lengths starting from the longest possible match
        for (int length = currentInput.length(); length > 0; length--) {
            String subString = currentInput.substring(0, length);
            if (tibetanMap.containsKey(subString)) {
                return subString; // Return the valid match
            }
        }
        return currentInput; // No valid match found
    }

    private static boolean matchedCombination(String currentInput){
        for (int length = currentInput.length(); length > 0; length--) {
            String subString = currentInput.substring(0, length);
            if (tibetanMap.containsKey(subString)) {
                return true; // Return the valid match
            }
        }
        return false;
    }
}
