package com.example.tripplanningmobile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class AddEditTripActivity extends AppCompatActivity {

    private EditText etDestination, etStartDate, etEndDate, etNotes;
    private Button btnLowBudget, btnMediumBudget, btnHighBudget;
    private CheckBox cbHiking, cbSwimming, cbSightseeing, cbCompleted;
    private Button btnSave;
    private SharedPrefManager prefManager;
    private Trip currentTrip;
    private boolean isEditMode = false;
    private Calendar startCalendar, endCalendar;
    private String selectedBudget = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);

        prefManager = new SharedPrefManager(this);
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        initViews();
        setupDatePickers();
        setupBudgetButtons();

        String tripId = getIntent().getStringExtra("TRIP_ID");
        if (tripId != null) {
            isEditMode = true;
            setTitle("Edit Trip");
            loadTrip(tripId);
        } else {
            setTitle("Add New Trip");
        }

        setupSaveButton();
    }

    private void initViews() {
        etDestination = findViewById(R.id.etDestination);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etNotes = findViewById(R.id.etNotes);
        btnLowBudget = findViewById(R.id.btnLowBudget);
        btnMediumBudget = findViewById(R.id.btnMediumBudget);
        btnHighBudget = findViewById(R.id.btnHighBudget);
        cbHiking = findViewById(R.id.cbHiking);
        cbSwimming = findViewById(R.id.cbSwimming);
        cbSightseeing = findViewById(R.id.cbSightseeing);
        cbCompleted = findViewById(R.id.cbCompleted);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupDatePickers() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        etStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(
                        AddEditTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                startCalendar.set(year, month, day);
                                etStartDate.setText(sdf.format(startCalendar.getTime()));
                            }
                        },
                        startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH)
                );
                picker.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(
                        AddEditTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                endCalendar.set(year, month, day);
                                etEndDate.setText(sdf.format(endCalendar.getTime()));
                            }
                        },
                        endCalendar.get(Calendar.YEAR),
                        endCalendar.get(Calendar.MONTH),
                        endCalendar.get(Calendar.DAY_OF_MONTH)
                );
                picker.show();
            }
        });
    }

    private void setupBudgetButtons() {
        btnLowBudget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectBudget("Low");
            }
        });

        btnMediumBudget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectBudget("Medium");
            }
        });

        btnHighBudget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectBudget("High");
            }
        });
    }

    private void selectBudget(String budget) {
        selectedBudget = budget;

        // reset all buttons to default color
        btnLowBudget.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        btnMediumBudget.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        btnHighBudget.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // highlight the selected one
        if (budget.equals("Low")) {
            btnLowBudget.setBackgroundColor(getResources().getColor(R.color.blue));
        } else if (budget.equals("Medium")) {
            btnMediumBudget.setBackgroundColor(getResources().getColor(R.color.blue));
        } else if (budget.equals("High")) {
            btnHighBudget.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    private void loadTrip(String tripId) {
        for (Trip trip : prefManager.getTrips()) {
            if (trip.getId().equals(tripId)) {
                currentTrip = trip;
                break;
            }
        }

        if (currentTrip != null) {
            etDestination.setText(currentTrip.getDestination());
            etStartDate.setText(currentTrip.getStartDate());
            etEndDate.setText(currentTrip.getEndDate());
            etNotes.setText(currentTrip.getNotes());

            selectBudget(currentTrip.getBudget());

            cbHiking.setChecked(currentTrip.isHiking());
            cbSwimming.setChecked(currentTrip.isSwimming());
            cbSightseeing.setChecked(currentTrip.isSightseeing());
            cbCompleted.setChecked(currentTrip.isCompleted());
        }
    }

    private void setupSaveButton() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validateInputs()) {
                    saveTrip();
                }
            }
        });
    }

    private boolean validateInputs() {
        if (etDestination.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter destination", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etStartDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select start date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etEndDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select end date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedBudget.isEmpty()) {
            Toast.makeText(this, "Please select budget", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveTrip() {
        String destination = etDestination.getText().toString().trim();
        String startDate = etStartDate.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        boolean hiking = cbHiking.isChecked();
        boolean swimming = cbSwimming.isChecked();
        boolean sightseeing = cbSightseeing.isChecked();
        boolean completed = cbCompleted.isChecked();

        if (isEditMode && currentTrip != null) {
            currentTrip.setDestination(destination);
            currentTrip.setStartDate(startDate);
            currentTrip.setEndDate(endDate);
            currentTrip.setBudget(selectedBudget);
            currentTrip.setHiking(hiking);
            currentTrip.setSwimming(swimming);
            currentTrip.setSightseeing(sightseeing);
            currentTrip.setCompleted(completed);
            currentTrip.setNotes(notes);
            prefManager.updateTrip(currentTrip);
            Toast.makeText(this, "Trip updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            String id = UUID.randomUUID().toString();
            Trip newTrip = new Trip(id, destination, startDate, endDate, selectedBudget,
                    hiking, swimming, sightseeing, completed, notes);
            prefManager.addTrip(newTrip);
            Toast.makeText(this, "Trip added successfully", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}