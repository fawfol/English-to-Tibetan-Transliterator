package com.example.entobo;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.inputmethod.InputMethodManager;

public class CustomKeyboardView extends KeyboardView {
    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw globe icon manually for space key
        for (Keyboard.Key key : getKeyboard().getKeys()) {
            if (key.codes[0] == 32 && key.icon != null) { // space key
                int gap = 30; // ib px
                int iconRight = key.x + key.width - gap;
                int iconLeft = iconRight - key.icon.getIntrinsicWidth();
                int iconTop = key.y + (key.height - key.icon.getIntrinsicHeight()) / 2;

                key.icon.setBounds(iconLeft, iconTop,
                        iconRight,
                        iconTop + key.icon.getIntrinsicHeight());
                key.icon.draw(canvas);
            }
        }
    }

    @Override
    protected boolean onLongPress(Keyboard.Key key) {
        if (key.codes[0] == 32) { // space key long press
            performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showInputMethodPicker();
            return true; // consume long press
        }
        return super.onLongPress(key);
    }
}
