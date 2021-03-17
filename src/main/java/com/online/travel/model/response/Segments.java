package com.online.travel.model.response;

public class Segments {
    private String segmentID;

    public String getSegmentID() {
        return segmentID;
    }

    public void setSegmentID(final String segmentID) {
        this.segmentID = segmentID;
    }

    @Override
    public String toString() {
        return "Segments{" +
                "segmentID='" + segmentID + '\'' +
                '}';
    }
}
