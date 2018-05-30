package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_INGREDIENTS = "ingredients";

    @Nullable
    public static Sandwich parseSandwichJson(String json) {
        try {
            //Getting main JSON object and initializing new Sandwich object
            JSONObject jsonObject = new JSONObject(json);
            Sandwich sandwich = new Sandwich();

            //Set name and alsoKnownAs fields
            JSONObject name = jsonObject.getJSONObject(KEY_NAME);
            sandwich.setMainName(name.getString(KEY_MAIN_NAME));
            sandwich.setAlsoKnownAs(getStringList(name.getJSONArray(KEY_ALSO_KNOWN_AS)));

            //Set other fields from the main JSON Object
            sandwich.setPlaceOfOrigin(jsonObject.getString(KEY_PLACE_OF_ORIGIN));
            sandwich.setDescription(jsonObject.getString(KEY_DESCRIPTION));
            sandwich.setImage(jsonObject.getString(KEY_IMAGE));
            sandwich.setIngredients(getStringList(jsonObject.getJSONArray(KEY_INGREDIENTS)));

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //In case of any errors return null
        return null;
    }

    @NonNull
    private static List<String> getStringList(@NonNull JSONArray jsonArray) throws JSONException {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            stringList.add((String) jsonArray.get(i));
        }
        return stringList;
    }
}
