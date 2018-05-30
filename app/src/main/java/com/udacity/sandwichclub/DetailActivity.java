package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView tvAlsoKnownAs, tvOrigin, tvIngredients, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvDescription = findViewById(R.id.description_tv);

        // if intent is null or position of the sandwich is -1 finish the activity with
        // a toast message.
        Intent intent = getIntent();
        int position;
        if (intent == null) {
            closeOnError();
            return;
        } else if ((position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION)) == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        //Getting string array from strings.xml file stored at compile time
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

        //Getting string at the position user clicked
        String json = sandwiches[position];

        //Parse the JSON string and creating Sandwich object from it
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        //Load the image with picasso library
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .into(ingredientsIv);

        //Set activity name to the sandwich name
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String origin = sandwich.getPlaceOfOrigin();
        String ingredients = getFormattedString(sandwich.getIngredients());
        String knownAs = getFormattedString(sandwich.getAlsoKnownAs());
        String desc = sandwich.getDescription();

        if (origin == null || origin.isEmpty()) tvOrigin.setText(R.string.data_absent);
        else tvOrigin.setText(origin);

        if (knownAs.isEmpty()) tvAlsoKnownAs.setText(R.string.data_absent);
        else tvAlsoKnownAs.setText(getFormattedString(sandwich.getAlsoKnownAs()));

        if (ingredients.isEmpty()) tvIngredients.setText(R.string.data_absent);
        else tvIngredients.setText(getFormattedString(sandwich.getIngredients()));

        if (desc == null || desc.isEmpty()) tvDescription.setText(R.string.data_absent);
        else tvDescription.setText(sandwich.getDescription());
    }

    private String getFormattedString(@NonNull List<String> stringList) {
        //Build a string for multiple items in the list, separated by (,)
        StringBuilder stringBuilder = new StringBuilder("");
        if (stringList.size() <= 0) return stringBuilder.toString();
        else if (stringList.size() == 1) {
            return stringBuilder.append(stringList.get(0)).toString();
        }

        stringBuilder.append(stringList.get(0));
        for (int i = 1; i < stringList.size() - 1; i++) {
            stringBuilder.append(", ").append(stringList.get(i));
        }
        stringBuilder.append(" and ").append(stringList.get(stringList.size() - 1));

        return stringBuilder.toString();
    }
}
