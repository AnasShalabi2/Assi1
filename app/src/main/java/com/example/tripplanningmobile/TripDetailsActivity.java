package com.example.tripplanningmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TripDetailsActivity extends AppCompatActivity {

    private TextView tvDestination, tvStartDate, tvEndDate, tvBudget;
    private TextView tvActivities, tvStatus, tvNotes;
    private Button btnEdit;
    private SharedPrefManager prefManager;
    private Trip currentTrip;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        prefManager = new SharedPrefManager(this);

        initViews();
        loadTripDetails();
        setupEditButton();
    }

    private void initViews() {
        tvDestination = findViewById(R.id.tvDestinationDetails);
        tvStartDate = findViewById(R.id.tvStartDateDetails);
        tvEndDate = findViewById(R.id.tvEndDateDetails);
        tvBudget = findViewById(R.id.tvBudgetDetails);
        tvActivities = findViewById(R.id.tvActivitiesDetails);
        tvStatus = findViewById(R.id.tvStatusDetails);
        tvNotes = findViewById(R.id.tvNotesDetails);
        btnEdit = findViewById(R.id.btnEdit);
    }

    private void loadTripDetails() {
        String tripId = getIntent().getStringExtra("TRIP_ID");
        if (tripId != null) {
            for (Trip trip : prefManager.getTrips()) {
                if (trip.getId().equals(tripId)) {
                    currentTrip = trip;
                    break;
                }
            }
        }

        if (currentTrip != null) {
            setTitle(currentTrip.getDestination());
            tvDestination.setText(currentTrip.getDestination());
            tvStartDate.setText("Start: " + currentTrip.getStartDate());
            tvEndDate.setText("End: " + currentTrip.getEndDate());
            tvBudget.setText("Budget: " + currentTrip.getBudget());

            StringBuilder activities = new StringBuilder("Activities: ");
            if (currentTrip.isHiking()) activities.append("🥾 Hiking ");
            if (currentTrip.isSwimming()) activities.append("🏊 Swimming ");
            if (currentTrip.isSightseeing()) activities.append("📸 Sightseeing ");
            if (!currentTrip.isHiking() && !currentTrip.isSwimming() && !currentTrip.isSightseeing()) {
                activities.append("None selected");
            }
            tvActivities.setText(activities.toString());

            tvStatus.setText("Status: " + (currentTrip.isCompleted() ? "✓ Completed" : "⏳ Planned"));

            String notes = currentTrip.getNotes();
            if (notes == null || notes.isEmpty()) {
                tvNotes.setText("Notes: No notes added");
            } else {
                tvNotes.setText("Notes: " + notes);
            }
        }
    }

    private void setupEditButton() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentTrip != null) {
                    Intent intent = new Intent(TripDetailsActivity.this, AddEditTripActivity.class);
                    intent.putExtra("TRIP_ID", currentTrip.getId());
                    startActivity(intent);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        loadTripDetails();
    }
}