import java.util.*;
public class Tibetan {
    static Map<String, String> tibetanMap = new HashMap<>();
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
                                    tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                                    tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                        tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                                tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                                tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                                tibetanTransliteration.append(tibetanMap.get(String.valueOf(storeEndingLetter.charAt(0))));
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
                                tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                                tibetanTransliteration.append(tibetanMap.get(String.valueOf(currentInput)));
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
                                tibetanTransliteration.append(tibetanMap.get(String.valueOf(storeEndingLetter.charAt(0))));
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
            String possibleMatch = TibetanMap.get(currentInput.toString());
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
            if (TibetanMap.containsKey(subString)) {
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
