//D/PROGRAMMING/Java/tibetanime/java/com.example.entobo/TibetanIME.java 

package com.example.entobo;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TibetanIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView keyboardView;
    private TransliterationEngine engine;
    // Suggestion Bar UI
    private View candidatesView;
    private List<TextView> suggestionTextViews;

    // --- Keyboard Layouts ---
    private Keyboard tibetanKeyboard;
    private Keyboard englishKeyboard;
    private Keyboard symbolsKeyboard;

    // --- State Management ---
    private boolean isTibetanMode = true;
    private boolean isShifted = false;
    private boolean isSymbolsMode = false;


    @Override
    public void onCreate() {
        super.onCreate();
        engine = new TransliterationEngine();
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);

        // --- Initialize all your keyboards ---
        tibetanKeyboard = new Keyboard(this, R.xml.keyboard_tibetan);
        englishKeyboard = new Keyboard(this, R.xml.keyboard_english);
        symbolsKeyboard = new Keyboard(this, R.xml.keyboard_symbols);

        keyboardView.setKeyboard(tibetanKeyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }
    @Override
    public View onCreateCandidatesView() {
        candidatesView = getLayoutInflater().inflate(R.layout.candidates_view, null);

        suggestionTextViews = new ArrayList<>();
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion1));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion2));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion3));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion4));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion5));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion6));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion7));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion8));

        for (TextView textView : suggestionTextViews) {
            textView.setOnClickListener(v -> {
                TextView clickedSuggestion = (TextView) v;
                pickSuggestion(clickedSuggestion.getText().toString());
            });
        }

        return candidatesView;
    }

    private void updateSuggestions() {
        if (!isTibetanMode || candidatesView == null) {
            setCandidatesViewShown(false);
            return;
        }

        Log.d("TibetanIME_Debug", "Current Stack: " + engine.getStack());
        List<String> suggestions = engine.getSuggestions();
        Log.d("TibetanIME_Debug", "Suggestions generated: " + suggestions.toString());

        if (suggestions.isEmpty()) {
            setCandidatesViewShown(false);
            return;
        }

        setCandidatesViewShown(true);
        int suggestionCount = Math.min(suggestions.size(), suggestionTextViews.size());
        for (int i = 0; i < suggestionCount; i++) {
            TextView textView = suggestionTextViews.get(i);
            textView.setText(suggestions.get(i));
            textView.setVisibility(View.VISIBLE);
        }

        for (int i = suggestionCount; i < suggestionTextViews.size(); i++) {
            suggestionTextViews.get(i).setVisibility(View.GONE);
        }
    }

    private void pickSuggestion(String suggestion) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            commitComposingText();
            ic.commitText(suggestion + "་", 1);
            engine.clearStack();
            updateSuggestions();
        }
    }

    private void switchKeyboardLayout() {
        isTibetanMode = !isTibetanMode;
        isShifted = false;
        isSymbolsMode = false; //always reset to letters when changing language
        if (englishKeyboard != null) {
            englishKeyboard.setShifted(false);
        }
        if (isTibetanMode) {
            keyboardView.setKeyboard(tibetanKeyboard);
        } else {
            commitComposingText();
            keyboardView.setKeyboard(englishKeyboard);
        }
        updateSuggestions();
    }

    private void commitComposingText() {
        if (isTibetanMode && !isSymbolsMode) {
            InputConnection ic = getCurrentInputConnection();
            if (ic == null) return;
            String tibetanWord = engine.transliterateStack();
            if (!tibetanWord.isEmpty()) {
                ic.commitText(tibetanWord, 1);
            }
            engine.clearStack();
            ic.setComposingText("", 1);
        }
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        switch (primaryCode) {
            case -1: //shift key (for English layout)
                if (!isTibetanMode) {
                    isShifted = !isShifted;
                    englishKeyboard.setShifted(isShifted);
                    keyboardView.invalidateAllKeys();
                }
                break;

            case -2: //switch to numebr symbol
                commitComposingText();
                isSymbolsMode = !isSymbolsMode;
                if (isSymbolsMode) {
                    //in Tibetan language mode but showing symbols
                    keyboardView.setKeyboard(symbolsKeyboard);
                } else {
                    //switch back to the main Tibetan keyboard
                    keyboardView.setKeyboard(tibetanKeyboard);
                }
                break;

            case -5: //backspace
                if (isTibetanMode && !isSymbolsMode && engine.getStackLength() > 0) {
                    engine.backspace();
                } else {
                    ic.deleteSurroundingText(1, 0);
                }
                break;
            case -103: //language switch key
                switchKeyboardLayout();
                break;
            case -101: //shae
                if(isTibetanMode) {
                    commitComposingText();
                    ic.commitText("། ", 1);
                }
                break;
            case -102: //tseg
                if(isTibetanMode) {
                    commitComposingText();
                    ic.commitText("་", 1);
                }
                break;
            case 32: // Space
                commitComposingText();
                ic.commitText(" ", 1);
                break;
            case 10: // Enter key
                commitComposingText();
                EditorInfo editorInfo = getCurrentInputEditorInfo();
                int actionId = editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION;
                if (actionId != EditorInfo.IME_ACTION_NONE) {
                    ic.performEditorAction(actionId);
                } else {
                    ic.commitText("\n", 1);
                }
                break;
            default: //all other characters
                char code = (char) primaryCode;
                if (isTibetanMode) {
                    //check if we inthe symbols layout first
                    if (isSymbolsMode) {
                        ic.commitText(String.valueOf(code), 1);
                    } else if (Character.isDigit(code)) {
                        //this handles the long-press popup number input
                        commitComposingText();
                        String tibetanNumber = engine.getTibetan(String.valueOf(code));
                        if(tibetanNumber != null) {
                            ic.commitText(tibetanNumber, 1);
                        }
                    } else {
                        //this is for normal Tibetan transliteration
                        engine.push(String.valueOf(code));
                    }
                } else { //english Mode
                    if (Character.isLetter(code) && isShifted) {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code), 1);
                }
        }
        //only update composing text and suggestions when in Tibetan letter mode
        if (isTibetanMode && !isSymbolsMode) {
            updateInput();
        }
    }

    private void updateInput() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.setComposingText(engine.getStack(), 1);
        updateSuggestions();
    }
    @Override public void onPress(int primaryCode) {}
    @Override public void onRelease(int primaryCode) {}
    @Override public void onText(CharSequence text) {}
    @Override public void swipeLeft() {}
    @Override public void swipeRight() {}
    @Override public void swipeDown() {}
    @Override public void swipeUp() {}
}
