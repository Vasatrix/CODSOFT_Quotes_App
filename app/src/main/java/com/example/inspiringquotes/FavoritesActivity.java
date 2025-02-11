package com.example.inspiringquotes;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ListView listView;
    private QuoteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        listView = findViewById(R.id.listView);
        dbHelper = new QuoteDatabaseHelper(this);

        loadFavoriteQuotes();
    }

    private void loadFavoriteQuotes() {
        ArrayList<String> quotesList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllQuotes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                quotesList.add(cursor.getString(1)); // Index 1 is COLUMN_QUOTE
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No favorite quotes found!", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quotesList);
        listView.setAdapter(adapter);
    }
}
