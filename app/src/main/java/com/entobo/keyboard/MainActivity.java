package com.entobo.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    int count = 0;
    int countWord = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        Button enableBtn = findViewById(R.id.enableKeyboardBtn);
        Button switchBtn = findViewById(R.id.switchKeyboardBtn);
        Button showMapButton = findViewById(R.id.showTranslitMapButton);

        // How-to text
        TextView howToTextView = findViewById(R.id.howToTextView);
        String howToHtml = getString(R.string.how_to_steps);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            howToTextView.setText(Html.fromHtml(howToHtml, Html.FROM_HTML_MODE_COMPACT));
        } else {
            howToTextView.setText(Html.fromHtml(howToHtml));
        }

        // Contact text
        TextView contactTextView = findViewById(R.id.contactInfoTextView);
        String contactHtml = getString(R.string.contact_info);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            contactTextView.setText(Html.fromHtml(contactHtml, Html.FROM_HTML_MODE_COMPACT));
        } else {
            contactTextView.setText(Html.fromHtml(contactHtml));
        }

        contactTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Enable keyboard button
        enableBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(intent);
        });

        // Switch keyboard button
        switchBtn.setOnClickListener(v -> {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showInputMethodPicker();
            }
        });

        // Show transliteration map button
        showMapButton.setOnClickListener(v -> {
            TransliterationEngine engine = new TransliterationEngine();
            StringBuilder mapBuilder = new StringBuilder("Roman → Tibetan\n\n");
            count = 0;
            countWord = 0;

            for (Map.Entry<String, String> entry : engine.tibetanMap.entrySet()) {
                count += 1;
                countWord += 1;

                mapBuilder.append(countWord)
                        .append(") ")
                        .append(entry.getKey())
                        .append(" → ")
                        .append(entry.getValue())
                        .append("                      ");

                if (count == 2) {
                    mapBuilder.append("\n");
                    count = 0;
                }
            }

            ScrollView scrollView = new ScrollView(this);
            TextView textView = new TextView(this);
            textView.setText(mapBuilder.toString());
            textView.setTextIsSelectable(true);

            int paddingPx = (int) (32 * getResources().getDisplayMetrics().density);
            textView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

            scrollView.addView(textView);

            new AlertDialog.Builder(this)
                    .setTitle("Roman–Tibetan Map")
                    .setView(scrollView)
                    .setPositiveButton("Close", null)
                    .show();
        });
    }
}
