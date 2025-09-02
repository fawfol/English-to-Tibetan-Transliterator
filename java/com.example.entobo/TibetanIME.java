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

    //create an instance of trransliteration logic engine
    private TransliterationEngine engine;

    @Override
    public void onCreate() {
        super.onCreate();
        engine = new TransliterationEngine(); //initialize the engine
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        //using ASCII code to handle the key press
        switch (primaryCode) {
            case -5: //htis is the code for Backspace
                if (engine.getStackLength() > 0) {
                    engine.backspace();
                    // update the underlined "composing" text
                    ic.setComposingText(engine.getStack(), 1);
                } else {
                    //if our stack is empty, just do a normal backspace
                    ic.deleteSurroundingText(1, 0);
                }
                break;
            case 32: //this is the code for Space
                String match = engine.getLongestMatch();
                if (!match.isEmpty()) {
                    //commit the final Tibetan part of the syllable
                    ic.commitText(engine.getTibetan(match), 1);
                }
                //append the tsheg '་' (syllable-separating dot)
                ic.commitText("་", 1);
                engine.clearStack(); //clear the stack for the next word
                break;
            default:
                char code = (char) primaryCode;
                engine.push(String.valueOf(code));
                updateInput(); //call the main logic handler
        }
    }

    private void updateInput() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        String stackContent = engine.getStack();
        String longestMatch = engine.getLongestMatch();

        if (longestMatch.isEmpty()) {
            //no valid Tibetan match yet, so just show the English letters the user is typing
            ic.setComposingText(stackContent, 1);
            return;
        }

        String remaining = stackContent.substring(longestMatch.length());

        if (engine.isEndingLetter(remaining)) {
            //a syllable is complete. commit the matched Tibetan text
            ic.commitText(engine.getTibetan(longestMatch), 1);
            // and start a new stack with the remaining ending letter
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
