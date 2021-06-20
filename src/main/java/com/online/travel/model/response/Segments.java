package com.online.travel.model.response;

public class Segments {
    private String segmentID;
    private String duration;

    public String getSegmentID() {
        return segmentID;
    }

    public void setSegmentID(final String segmentID) {
        this.segmentID = segmentID;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Segments{" +
                "segmentID='" + segmentID + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
