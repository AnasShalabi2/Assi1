package com.example.tripplanningmobile;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {
    private static final String PREF_NAME = "TripPlannerPrefs";
    private static final String KEY_TRIPS = "trips_data";

    private SharedPreferences sharedPrefs;
    private Gson gson;

    public SharedPrefManager(Context context) {
        sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTrips(List<Trip> tripList) {
        String json = gson.toJson(tripList);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(KEY_TRIPS, json);
        editor.apply();
    }

    public List<Trip> getTrips() {
        String json = sharedPrefs.getString(KEY_TRIPS, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Trip>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void addTrip(Trip newTrip) {
        List<Trip> trips = getTrips();
        trips.add(newTrip);
        saveTrips(trips);
    }

    public void updateTrip(Trip updatedTrip) {
        List<Trip> trips = getTrips();
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getId().equals(updatedTrip.getId())) {
                trips.set(i, updatedTrip);
                break;
            }
        }
        saveTrips(trips);
    }

    public void deleteTrip(String tripId) {
        List<Trip> trips = getTrips();
        for (int i = 0; i < trips.size(); i++) {
            if (trips.get(i).getId().equals(tripId)) {
                trips.remove(i);
                break;
            }
        }
        saveTrips(trips);
    }
}