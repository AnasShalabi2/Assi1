package com.example.tripplanningmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> tripList;
    private List<Trip> tripListFull;
    private OnTripClickListener listener;

    public interface OnTripClickListener {
        void onTripClick(Trip trip);
        void onTripLongClick(Trip trip);
    }

    public TripAdapter(List<Trip> trips, OnTripClickListener listener) {
        this.tripList = trips;
        this.tripListFull = new ArrayList<>(trips);
        this.listener = listener;
    }

    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);

        holder.tvDestination.setText(trip.getDestination());
        holder.tvDates.setText(trip.getStartDate() + " - " + trip.getEndDate());
        holder.tvBudget.setText("Budget: " + trip.getBudget());

        if (trip.isCompleted()) {
            holder.tvStatus.setText("✓ Completed");
        } else {
            holder.tvStatus.setText("⏳ Planned");
        }
    }

    public int getItemCount() {
        return tripList.size();
    }

    public void filter(String query) {
        tripList.clear();
        if (query.isEmpty()) {
            tripList.addAll(tripListFull);
        } else {
            String searchQuery = query.toLowerCase();
            for (Trip trip : tripListFull) {
                if (trip.getDestination().toLowerCase().contains(searchQuery) ||
                        trip.getStartDate().toLowerCase().contains(searchQuery)) {
                    tripList.add(trip);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateData(List<Trip> newList) {
        this.tripList = newList;
        this.tripListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvDestination, tvDates, tvBudget, tvStatus;

        public TripViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvDates = itemView.findViewById(R.id.tvDates);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onTripClick(tripList.get(position));
                    }
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onTripLongClick(tripList.get(position));
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}