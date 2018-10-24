package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_SANDWICH = "extra_sandwich";

    private TextView tvAlsoKnownAs, tvOrigin, tvIngredients, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Enable actionBar up button. It navigates to the parent activity
        ActionBar actionBar;
        if ((actionBar = getSupportActionBar()) != null) actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvDescription = findViewById(R.id.description_tv);

        // if intent is null or position of the sandwich is -1 finish the activity with
        // a toast message.
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        } else if (!intent.hasExtra(EXTRA_SANDWICH)) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        Sandwich sandwich = intent.getParcelableExtra(EXTRA_SANDWICH);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        //Load the image with picasso library
        Picasso.with(this)
                .load(sandwich.getImage())
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

        /* I could use only TextUtils.join() but I want to join last element with "and"
           Passing array {"A","B","C"} this function returns string "A, B and C" */

        if (stringList.size() <= 0) {
            //Empty list
            return "";
        } else if (stringList.size() == 1) {
            //List contains only one element
            return stringList.get(0);
        } else {
            //Removing last element because concatenating it with "and"
            String lastElement = stringList.get(stringList.size() - 1);
            stringList.remove(stringList.size() - 1);

            //Joining elements with the delimiter ", "
            String otherElements = TextUtils.join(", ", stringList);

            //Join the last element and return
            return otherElements + " and " + lastElement;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
