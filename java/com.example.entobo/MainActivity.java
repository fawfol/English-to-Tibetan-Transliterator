package com.example.entobo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.app.AlertDialog;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
int count = 0;
int countWord = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showMapButton = findViewById(R.id.showTranslitMapButton);
        showMapButton.setOnClickListener(v -> {
            TransliterationEngine engine = new TransliterationEngine();
            StringBuilder mapBuilder = new StringBuilder("Roman → Tibetan\n\n");
            count = 0; //reset count each time the button is clicked

            for (Map.Entry<String, String> entry : engine.tibetanMap.entrySet()) {
                count += 1; countWord += 1;

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
        TextView contactInfoTextView = findViewById(R.id.contactInfoTextView);
        contactInfoTextView.setText(Html.fromHtml(getString(R.string.contact_info), Html.FROM_HTML_MODE_LEGACY));
        contactInfoTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
