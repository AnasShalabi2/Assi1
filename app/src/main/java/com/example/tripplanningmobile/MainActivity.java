package com.example.tripplanningmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TripAdapter.OnTripClickListener {

    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private SharedPrefManager prefManager;
    private EditText etSearch;
    private Button btnAdd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefManager = new SharedPrefManager(this);

        initViews();
        setupRecyclerView();
        setupSearch();
        setupAddButton();

        loadTrips();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etSearch);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Trip> trips = prefManager.getTrips();
        adapter = new TripAdapter(trips, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupAddButton() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTripActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadTrips() {
        List<Trip> trips = prefManager.getTrips();
        adapter.updateData(trips);
    }

    protected void onResume() {
        super.onResume();
        loadTrips();
    }

    public void onTripClick(Trip trip) {
        Intent intent = new Intent(MainActivity.this, TripDetailsActivity.class);
        intent.putExtra("TRIP_ID", trip.getId());
        startActivity(intent);
    }

    public void onTripLongClick(final Trip trip) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Trip");
        builder.setMessage("Are you sure you want to delete this trip?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                prefManager.deleteTrip(trip.getId());
                loadTrips();
                Toast.makeText(MainActivity.this, "Trip deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}