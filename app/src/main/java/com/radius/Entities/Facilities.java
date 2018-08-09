package com.radius.Entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facilities {

    @SerializedName("facilities")
    @Expose
    private List<Facility> facilities = null;
    @SerializedName("exclusions")
    @Expose
    private List<List<Exclusion>> exclusions = null;

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<List<Exclusion>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<Exclusion>> exclusions) {
        this.exclusions = exclusions;
    }

}
