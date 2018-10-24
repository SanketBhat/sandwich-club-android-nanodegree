package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.udacity.sandwichclub.adapter.SandwichListAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.sandwichclub.DetailActivity.EXTRA_SANDWICH;

public class MainActivity extends AppCompatActivity implements SandwichListAdapter.SandwichClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] sandwichStrings = getResources().getStringArray(R.array.sandwich_details);
        List<Sandwich> sandwiches = getSandwiches(sandwichStrings);

        RecyclerView recyclerView = findViewById(R.id.sandwich_list);
        SandwichListAdapter adapter = new SandwichListAdapter(this, sandwiches, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    private List<Sandwich> getSandwiches(String[] sandwichStrings) {
        ArrayList<Sandwich> sandwiches = new ArrayList<>();
        for (String sandwichString :
                sandwichStrings) {
            Sandwich sandwich = JsonUtils.parseSandwichJson(sandwichString);
            sandwiches.add(sandwich);
        }
        return sandwiches;
    }

    private void launchDetailActivity(Sandwich sandwich, ImageView imageView) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, getString(R.string.transition_name));
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_SANDWICH, sandwich);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onClick(Sandwich sandwich, ImageView imageView) {
        launchDetailActivity(sandwich, imageView);
    }

}
