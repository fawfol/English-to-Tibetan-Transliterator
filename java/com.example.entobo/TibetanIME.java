// File: app/java/com/example/entobo/TibetanIME.java

package com.example.entobo;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.inputmethod.InputConnection;
import android.view.View;

public class TibetanIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private TransliterationEngine engine;

    @Override
    public void onCreate() {
        super.onCreate();
        engine = new TransliterationEngine();
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    // fucntion handles the repeating logic: finalizes the transliteration of the
    // current composing text and clears the engine for the next word
    private void commitComposingText() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        String match = engine.getLongestMatch();
        if (!match.isEmpty()) {
            // Commit the final Tibetan part of the syllable
            ic.commitText(engine.getTibetan(match), 1);
        }
        // Clean up the engine and the composing view
        engine.clearStack();
        ic.setComposingText("", 1);
    }

	//ON KEY methdod
    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        switch (primaryCode) {
            case -5: // backspace
                if (engine.getStackLength() > 0) {
                    engine.backspace();
                    ic.setComposingText(engine.getStack(), 1);
                } else {
                    ic.deleteSurroundingText(1, 0);
                }
                break;

            //  NEW CASE for Shad '།' 
            case -101:
                commitComposingText();
                ic.commitText("། ", 1);
                break;

            //  NEW CASE for Tseg '་' 
            case -102:
                commitComposingText();        
                ic.commitText("་", 1);    
                break;

            /.MODIFIED CASE for Space 
            case 32:
                commitComposingText(); 
                ic.commitText(" ", 1); 
                break;

            default:
                // this is a normal letter key
                char code = (char) primaryCode;
                engine.push(String.valueOf(code));
                updateInput();
        }
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

    //other required listener methods (leave them empty)
    @Override public void onPress(int primaryCode) {}
    @Override public void onRelease(int primaryCode) {}
    @Override public void onText(CharSequence text) {}
    @Override public void swipeLeft() {}
    @Override public void swipeRight() {}
    @Override public void swipeDown() {}
    @Override public void swipeUp() {}
}
