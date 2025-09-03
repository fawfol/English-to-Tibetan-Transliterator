package com.example.entobo;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class TibetanIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private TransliterationEngine engine;
    private LinearLayout candidatesView;

    private Keyboard tibetanKeyboard;
    private Keyboard englishKeyboard;

    private boolean isTibetanMode = true;
    private boolean isShifted = false;

    @Override
    public void onCreate() {
        super.onCreate();
        engine = new TransliterationEngine();
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        tibetanKeyboard = new Keyboard(this, R.xml.keyboard_tibetan);
        englishKeyboard = new Keyboard(this, R.xml.keyboard_english);
        keyboardView.setKeyboard(tibetanKeyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        candidatesView = (LinearLayout) getLayoutInflater().inflate(R.layout.candidates_view, null);
        return candidatesView;
    }

    private void updateSuggestions() {
        if (!isTibetanMode || candidatesView == null) {
            setCandidatesViewShown(false);
            return;
        }

        List<String> suggestions = engine.getSuggestions();
        if (suggestions.isEmpty()) {
            setCandidatesViewShown(false);
            return;
        }

        setCandidatesViewShown(true);
        candidatesView.removeAllViews();

        for (final String suggestion : suggestions) {
            TextView suggestionView = new TextView(this);
            suggestionView.setText(suggestion);
            suggestionView.setTextColor(0xFFFFFFFF);
            suggestionView.setGravity(Gravity.CENTER);
            suggestionView.setPadding(20, 10, 20, 10);
            suggestionView.setTextSize(20);
            suggestionView.setBackgroundColor(0x00000000);
            suggestionView.setOnClickListener(v -> pickSuggestion(suggestion));
            candidatesView.addView(suggestionView);
        }
    }

    private void pickSuggestion(String suggestion) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            engine.clearStack();
            ic.commitText(suggestion, 1);
            updateSuggestions();
        }
    }

    private void switchKeyboardLayout() {
        isTibetanMode = !isTibetanMode;
        isShifted = false;
        if (englishKeyboard != null) {
            englishKeyboard.setShifted(false);
        }

        if (isTibetanMode) {
            keyboardView.setKeyboard(tibetanKeyboard);
        } else {
            commitComposingText(); //finish any Tibetan word before switching
            keyboardView.setKeyboard(englishKeyboard);
        }
        updateSuggestions(); //update suggestions after switching
    }

    private void commitComposingText() {
        if (isTibetanMode) {
            InputConnection ic = getCurrentInputConnection();
            if (ic == null) return;

            String match = engine.getLongestMatch();
            if (!match.isEmpty()) {
                ic.commitText(engine.getTibetan(match), 1);
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
            case -1: //shift key
                isShifted = !isShifted;
                englishKeyboard.setShifted(isShifted);
                keyboardView.invalidateAllKeys();//redrraw all keys to show uppercase/lowercase
                break;

            case -5: // backspace
                if (isTibetanMode && engine.getStackLength() > 0) {
                    engine.backspace();
                    ic.setComposingText(engine.getStack(), 1);
                } else {
                    ic.deleteSurroundingText(1, 0);
                }
                break;

            case -103: //language switch key
                switchKeyboardLayout();
                break;

            case -101: // Shae
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

            case 32: //space
                commitComposingText();
                ic.commitText(" ", 1);
                break;

            case 10: //enter key
                commitComposingText(); //finish any pending word first

                //chekk if the app has a special action (like "Send" or "Search")
                EditorInfo editorInfo = getCurrentInputEditorInfo();
                int actionId = editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION;

                if (actionId != EditorInfo.IME_ACTION_NONE) {
                    //if there's an action, perform it
                    ic.performEditorAction(actionId);
                } else {
                    //otherwise, just insert a new line
                    ic.commitText("\n", 1);
                }
                break;
                

            default: //all other characters
                char code = (char) primaryCode;
                if (isTibetanMode) {
                    if (Character.isDigit(code)) {
                        commitComposingText();
                        String tibetanNumber = engine.getTibetan(String.valueOf(code));
                        if(tibetanNumber != null) {
                            ic.commitText(tibetanNumber, 1);
                        }
                    } else {
                        engine.push(String.valueOf(code));
                        updateInput();
                    }
                } else { //upadatedEnglish mode logic
                    if (Character.isLetter(code) && isShifted) {
                        code = Character.toUpperCase(code); //make it uppercase if shift is on
                    }
                    ic.commitText(String.valueOf(code), 1);
                    
                }
        }
        //after every key press update the suggestions
        updateSuggestions();
    }

    private void updateInput() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        String stackContent = engine.getStack();
        String longestMatch = engine.getLongestMatch();

        if (longestMatch.isEmpty()) {
            ic.setComposingText(stackContent, 1);
            return;
        }

        String remaining = stackContent.substring(longestMatch.length());
        if (engine.isEndingLetter(remaining)) {
            ic.commitText(engine.getTibetan(longestMatch), 1);
            engine.clearStack();
            engine.push(remaining);
            ic.setComposingText(engine.getStack(), 1);
        } else {
            ic.setComposingText(stackContent, 1);
        }
    }
	
	
    //other required listener methods(leave them empty)
    @Override public void onPress(int primaryCode) {}
    @Override public void onRelease(int primaryCode) {}
    @Override public void onText(CharSequence text) {}
    @Override public void swipeLeft() {}
    @Override public void swipeRight() {}
    @Override public void swipeDown() {}
    @Override public void swipeUp() {}
}
