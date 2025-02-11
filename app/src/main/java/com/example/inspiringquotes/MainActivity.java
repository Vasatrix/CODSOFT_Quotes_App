package com.example.inspiringquotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private QuoteDatabaseHelper dbHelper;
    private String[] sampleQuotes = {
            "The best way to predict the future is to create it.",
            "You are never too old to set another goal or to dream a new dream.",
            "Success is not final, failure is not fatal: It is the courage to continue that counts.",
            "Believe you can and you're halfway there.",
            "The only limit to our realization of tomorrow is our doubts of today."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quoteTextView);
        Button refreshButton = findViewById(R.id.refreshButton);
        Button shareButton = findViewById(R.id.shareButton);
        Button favoriteButton = findViewById(R.id.favoriteButton);
        Button viewFavoritesButton = findViewById(R.id.viewFavoritesButton);

        dbHelper = new QuoteDatabaseHelper(this);
        displayRandomQuote();

        refreshButton.setOnClickListener(view -> displayRandomQuote());

        shareButton.setOnClickListener(view -> {
            String quote = quoteTextView.getText().toString();
            if (!quote.isEmpty()) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, quote);
                startActivity(Intent.createChooser(shareIntent, "Share quote via"));
            }
        });

        favoriteButton.setOnClickListener(view -> {
            String quote = quoteTextView.getText().toString();
            if (!quote.isEmpty()) {
                boolean inserted = dbHelper.insertQuote(quote);
                if (inserted) {
                    Toast.makeText(MainActivity.this, "Quote added to favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Quote already in favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewFavoritesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    private void displayRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(sampleQuotes.length);
        quoteTextView.setText(sampleQuotes[index]);
    }
}
