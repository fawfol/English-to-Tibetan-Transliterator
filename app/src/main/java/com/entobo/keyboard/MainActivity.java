package com.entobo.keyboard;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
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

        // How-to steps container
        LinearLayout container = findViewById(R.id.stepsContainer);

        // Title
        TextView title = new TextView(this);
        title.setText("After installing:");
        title.setTextSize(18);
        title.setTextColor(getResources().getColor(android.R.color.white));
        title.setPadding(0, 0, 0, 16);
        container.addView(title);

        // First set of steps
        String[] steps = {
                "1. Open your phone's Settings.",
                "2. Go to General Management > Keyboard list and default.",
                "3. Turn on En2Bo Transliterator.",
                "4. Open any app with a text field, like Messages or WhatsApp.",
                "5. Tap the keyboard icon or choose the keyboard option.",
                "6. Select En2Bo Transliterator."
        };

        for (String step : steps) {
            TextView tv = new TextView(this);
            tv.setText(step);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(android.R.color.white));
            tv.setPadding(0, 8, 0, 8);
            container.addView(tv);
        }

        // OR section
        TextView orText = new TextView(this);
        orText.setText("Or:");
        orText.setTextSize(16);
        orText.setTextColor(getResources().getColor(android.R.color.white));
        orText.setPadding(0, 24, 0, 16);
        container.addView(orText);

        // Second set of steps
        String[] steps2 = {
                "1. Open your phone's Settings.",
                "2. Search for Keyboard list and default.",
                "3. Turn on En2Bo Transliterator.",
                "4. In the same settings page, tap Default keyboard.",
                "5. Select En2Bo Transliterator."
        };

        for (String step : steps2) {
            TextView tv = new TextView(this);
            tv.setText(step);
            tv.setTextSize(15);
            tv.setTextColor(getResources().getColor(android.R.color.white));
            tv.setPadding(0, 8, 0, 8);
            container.addView(tv);
        }

        // Important note
        TextView note = new TextView(this);
        note.setText("\nImportant: This app contains the keyboard service. If you uninstall the app, the keyboard will also be removed.\n\nThe project is still in progress, and I am currently improving word suggestion logic for the candidate view above the keyboard.\n\nAny contribution or feedback through Gmail or Play Store is deeply appreciated.");
        note.setTextSize(15);
        note.setTextColor(getResources().getColor(android.R.color.white));
        note.setPadding(0, 16, 0, 0);
        container.addView(note);

        // Contact text
        TextView contactTextView = findViewById(R.id.contactInfoTextView);
        String contactHtml = getString(R.string.contact_info);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            contactTextView.setText(Html.fromHtml(contactHtml, Html.FROM_HTML_MODE_COMPACT));
        } else {
            contactTextView.setText(Html.fromHtml(contactHtml));
        }

        contactTextView.setMovementMethod(LinkMovementMethod.getInstance());
        
        Button supportBtn = findViewById(R.id.supportBtn);
		supportBtn.setOnClickListener(v -> {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chai4.me/tenzinkalsang"));
			startActivity(intent);
		});

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
