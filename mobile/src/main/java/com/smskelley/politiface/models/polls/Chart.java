package com.smskelley.politiface.models.polls;

import java.util.List;

public class Chart {
    public String title;
    public String slug;
    public String topic;
    public String state;
    public String poll_count;
    public String last_updated;
    public List<Estimate> estimates;
    public List<EstimateByDate> estimates_by_date;

    public static class Estimate {
        public String choice;
        public float value;
    }

    public static class EstimateByDate {
        public String date;
        public List<Estimate> estimates;
    }
}
