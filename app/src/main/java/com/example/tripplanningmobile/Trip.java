package com.example.tripplanningmobile;

// Trip data model
public class Trip {
    private String id;
    private String destination;
    private String startDate;
    private String endDate;
    private String budget;
    private boolean hiking, swimming, sightseeing;
    private boolean isCompleted;
    private String notes;

    public Trip(String id, String destination, String startDate, String endDate,
                String budget, boolean hiking, boolean swimming, boolean sightseeing,
                boolean isCompleted, String notes) {
        this.id = id;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.hiking = hiking;
        this.swimming = swimming;
        this.sightseeing = sightseeing;
        this.isCompleted = isCompleted;
        this.notes = notes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getBudget() { return budget; }
    public void setBudget(String budget) { this.budget = budget; }

    public boolean isHiking() { return hiking; }
    public void setHiking(boolean hiking) { this.hiking = hiking; }

    public boolean isSwimming() { return swimming; }
    public void setSwimming(boolean swimming) { this.swimming = swimming; }

    public boolean isSightseeing() { return sightseeing; }
    public void setSightseeing(boolean sightseeing) { this.sightseeing = sightseeing; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}