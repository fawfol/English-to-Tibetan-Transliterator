package com.example.entobo;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TibetanIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private TransliterationEngine engine;

    //suggestion Bar UI
    private View candidatesView;
    private List<TextView> suggestionTextViews;

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
        candidatesView = getLayoutInflater().inflate(R.layout.candidates_view, null);

        //find all our suggestion TextViews and store them in a list
        suggestionTextViews = new ArrayList<>();
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion1));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion2));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion3));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion4));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion5));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion6));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion7));
        suggestionTextViews.add(candidatesView.findViewById(R.id.suggestion8));

        //set the click listener for each TextView
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

        List<String> suggestions = engine.getSuggestions();

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

        //hide any unused suggestion slots
        for (int i = suggestionCount; i < suggestionTextViews.size(); i++) {
            suggestionTextViews.get(i).setVisibility(View.GONE);
        }
    }

    private void pickSuggestion(String suggestion) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            commitComposingText(); //clear any partial text first
            ic.commitText(suggestion + "་", 1); //commit the suggestion and a tseg
            engine.clearStack();
            updateSuggestions(); //hide the bar after selection
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
            commitComposingText();
            keyboardView.setKeyboard(englishKeyboard);
        }
        updateSuggestions();
    }

    private void commitComposingText() {
        if (isTibetanMode) {
            InputConnection ic = getCurrentInputConnection();
            if (ic == null) return;

            //new ssmarter method to transliterate the whole stack
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
            case -1: //shift key
                if (!isTibetanMode) {
                    isShifted = !isShifted;
                    englishKeyboard.setShifted(isShifted);
                    keyboardView.invalidateAllKeys();
                }
                break;
            case -5: //backspace
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
            case 32: //spacee
                commitComposingText();
                ic.commitText(" ", 1);
                break;
            case 10: //enter key
                commitComposingText();
                EditorInfo editorInfo = getCurrentInputEditorInfo();
                int actionId = editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION;
                if (actionId != EditorInfo.IME_ACTION_NONE) {
                    ic.performEditorAction(actionId);
                } else {
                    ic.commitText("\n", 1);
                }
                break;
            default: //everty  other characters
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
                } else {
                    if (Character.isLetter(code) && isShifted) {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code), 1);
                }
        }
        updateSuggestions();
    }

    private void updateInput() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        //this just shows the user what they are typing
        ic.setComposingText(engine.getStack(), 1);
    }

    @Override public void onPress(int primaryCode) {}
    @Override public void onRelease(int primaryCode) {}
    @Override public void onText(CharSequence text) {}
    @Override public void swipeLeft() {}
    @Override public void swipeRight() {}
    @Override public void swipeDown() {}
    @Override public void swipeUp() {}
}
