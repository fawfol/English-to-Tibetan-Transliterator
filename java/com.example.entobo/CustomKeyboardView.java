package com.example.entobo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
            for (Keyboard.Key key : getKeyboard().getKeys()) {
                if (key.codes[0] == 32) {
                    continue;
                }
            }

            super.onDraw(canvas);

            for (Keyboard.Key key : getKeyboard().getKeys()) {
                if (key.codes[0] == 32 && key.icon != null && key.label != null) {
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(0xFFFFFFFF); //
                    paint.setTextSize(getContext().getResources().getDimension(R.dimen.key_text_size));

                    //calculate positioning of text
                    float textWidth = paint.measureText(key.label.toString());
                    float textX = key.x + (key.width - textWidth - key.icon.getIntrinsicWidth() - 8) / 2;
                    float textY = key.y + (key.height / 2f) + (paint.getTextSize() / 3);

                    //draw globe icon after text
                    int iconLeft = (int) (textX + textWidth + 30);
                    int iconTop = key.y + (key.height - key.icon.getIntrinsicHeight()) / 2;
                    key.icon.setBounds(iconLeft, iconTop,
                            iconLeft + key.icon.getIntrinsicWidth(),
                            iconTop + key.icon.getIntrinsicHeight());
                    key.icon.draw(canvas);
                }
            }
        }



    @Override
    protected boolean onLongPress(Keyboard.Key key) {
        if (key.codes[0] == 32) { //SPACE key code
            performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showInputMethodPicker();
            }
            return true; //consume long press event
        }
        return super.onLongPress(key);
    }
}
