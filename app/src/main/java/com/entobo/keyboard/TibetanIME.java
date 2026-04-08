//D/PROGRAMMING/Java/tibetanime/java/com.example.entobo/TibetanIME.java 

package com.entobo.keyboard;

import com.google.android.flexbox.FlexboxLayout;
import android.view.ViewGroup;
import android.graphics.Color;
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
    /*/ Suggestion Bar UI
    private View candidatesView;*/

    // --- Keyboard Layouts ---
    private Keyboard tibetanKeyboard;
    private Keyboard englishKeyboard;
    private Keyboard symbolsKeyboard;

    // --- state management ---
    private boolean isTibetanMode = true;
    private boolean isShifted = false;
    private boolean isSymbolsMode = false;
    
	
	@Override
	public boolean onEvaluateFullscreenMode() {
		return false;
	}
	/*@Override
	public void onComputeInsets(Insets outInsets) {
		super.onComputeInsets(outInsets);

		// Tell Android: candidates view is separate from keyboard
		if (candidatesView != null && candidatesView.isShown()) {
		    int candidatesHeight = candidatesView.getHeight();

		    // This is the KEY line
		    outInsets.contentTopInsets = candidatesHeight;

		    // Optional (helps stability)
		    outInsets.visibleTopInsets = candidatesHeight;
		}
	}*/
	
	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);

		//setCandidatesViewShown(true);
	}

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);
        if (engine != null) {
            engine.clearStack();
        }
        updateSuggestions();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        engine = new TransliterationEngine();
    }

   @Override
	public View onCreateInputView() {
		View root = getLayoutInflater().inflate(R.layout.keyboard_view, null);

		keyboardView = root.findViewById(R.id.keyboard);

		tibetanKeyboard = new Keyboard(this, R.xml.keyboard_tibetan);
		englishKeyboard = new Keyboard(this, R.xml.keyboard_english);
		symbolsKeyboard = new Keyboard(this, R.xml.keyboard_symbols);

		keyboardView.setKeyboard(tibetanKeyboard);
		keyboardView.setOnKeyboardActionListener(this);

		return root;
	}

   /*@Override
	public View onCreateCandidatesView() {
		candidatesView = getLayoutInflater().inflate(R.layout.candidates_view, null);

		candidatesView.setLayoutParams(new ViewGroup.LayoutParams(
		        ViewGroup.LayoutParams.MATCH_PARENT,
		        ViewGroup.LayoutParams.WRAP_CONTENT
		        
		));
		
		//setCandidatesViewShown(true);

		candidatesView.setBackgroundColor(Color.parseColor("#303030"));

		return candidatesView;
	}*/

	private void addCandidateView(FlexboxLayout container, String text, boolean isPrimary) {
		TextView tv = new TextView(this, null, 0, R.style.CandidateTextStyle);

		tv.setText(text);
		tv.setTextSize(isPrimary ? 20 : 18);

		tv.setPadding(20, 12, 24, 12);

		tv.setOnClickListener(v -> pickSuggestion(text));

		FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
		        ViewGroup.LayoutParams.WRAP_CONTENT,
		        ViewGroup.LayoutParams.WRAP_CONTENT
		);
		params.setMargins(8, 4, 8, 4);
		tv.setLayoutParams(params);

		container.addView(tv);
	}

    private void updateSuggestions() {
        if (!isTibetanMode || keyboardView == null) {
			return;
		}

        Log.d("TibetanIME_Debug", "Current Stack: " + engine.getStack());
        SuggestionResult result = engine.getSuggestionsAdvanced();
        Log.d("TibetanIME_Debug", "Structure: " + result.structure.toString());
		Log.d("TibetanIME_Debug", "Words: " + result.words.toString());	

		if (result.structure.isEmpty() && result.words.isEmpty()) {
			//setCandidatesViewShown(false);
		} else {
			//setCandidatesViewShown(true);
		}
		FlexboxLayout container = keyboardView.getRootView().findViewById(R.id.candidatesRoot);
		if (container == null) return;

		container.removeAllViews();

		for (String s : result.structure) {
			addCandidateView(container, s, true);
		}

		for (String s : result.words) {
			addCandidateView(container, s, false);
		}

    }

    private void pickSuggestion(String suggestion) {
		InputConnection ic = getCurrentInputConnection();
		if (ic == null) return;
		ic.commitText(suggestion, 1);
		engine.clearStack();
		ic.finishComposingText();
		updateSuggestions();
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
