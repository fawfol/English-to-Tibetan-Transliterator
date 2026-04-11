package com.entobo.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    int count = 0;
    int countWord = 0;

    // Razorpay links
    private static final String DONATE_LINK_MAIN = "https://rzp.io/rzp/rf4rUTS8";
    private static final String DONATE_LINK_12 = "https://rzp.io/rzp/00mLS1h";
    private static final String DONATE_LINK_25 = "https://rzp.io/rzp/i1Lfu5d";
    private static final String DONATE_LINK_50 = "https://rzp.io/rzp/rf4rUTS8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enableBtn = findViewById(R.id.enableKeyboardBtn);
        Button switchBtn = findViewById(R.id.switchKeyboardBtn);
        Button showMapButton = findViewById(R.id.showTranslitMapButton);

        Button donate12Btn = findViewById(R.id.donate12Btn);
        Button donate25Btn = findViewById(R.id.donate25Btn);
        Button donate50Btn = findViewById(R.id.donate50Btn);
        Button showQrBtn = findViewById(R.id.showQrBtn);
        Button copyUpiBtn = findViewById(R.id.copyUpiBtn);

        if (enableBtn != null) {
            enableBtn.setOnClickListener(v -> {
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                startActivity(intent);
            });
        }

        if (switchBtn != null) {
            switchBtn.setOnClickListener(v -> {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showInputMethodPicker();
                }
            });
        }

        if (showMapButton != null) {
            showMapButton.setOnClickListener(v -> {
                TransliterationEngine engine = new TransliterationEngine();
                StringBuilder mapBuilder = new StringBuilder("Roman → Tibetan\n\n");
                count = 0;
                countWord = 0;

                for (Map.Entry<String, String> entry : engine.tibetanMap.entrySet()) {
                    count++;
                    countWord++;

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

        TextView contactTextView = findViewById(R.id.contactInfoTextView);
        if (contactTextView != null) {
            String contactHtml = getString(R.string.contact_info);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                contactTextView.setText(Html.fromHtml(contactHtml, Html.FROM_HTML_MODE_COMPACT));
            } else {
                contactTextView.setText(Html.fromHtml(contactHtml));
            }

            contactTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }


        if (donate12Btn != null) {
            donate12Btn.setOnClickListener(v -> openDonationLink(DONATE_LINK_12));
        }

        if (donate25Btn != null) {
            donate25Btn.setOnClickListener(v -> openDonationLink(DONATE_LINK_25));
        }

        if (donate50Btn != null) {
            donate50Btn.setOnClickListener(v -> openDonationLink(DONATE_LINK_50));
        }

        if (showQrBtn != null) {
            showQrBtn.setOnClickListener(v -> showQrDialog());
        }

        if (copyUpiBtn != null) {
            copyUpiBtn.setOnClickListener(v -> copyUpiId());
        }
    }

    private void openDonationLink(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No app found to open payment link", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open donation link", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyUpiId() {
        String upiId = BuildConfig.UPI_ID;

        ClipboardManager clipboard =
                (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("UPI ID", upiId);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "UPI ID copied", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Clipboard not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void showQrDialog() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.my_upi_qr);
        imageView.setAdjustViewBounds(true);

        int paddingPx = (int) (24 * getResources().getDisplayMetrics().density);
        imageView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(imageView);

        new AlertDialog.Builder(this)
                .setTitle("Scan to Support")
                .setView(scrollView)
                .setPositiveButton("Close", null)
                .show();
    }
}
