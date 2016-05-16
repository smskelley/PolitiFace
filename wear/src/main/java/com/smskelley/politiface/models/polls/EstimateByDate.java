package com.smskelley.politiface.models.polls;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class EstimateByDate {
  public static final EstimateByDate EMPTY = new EstimateByDate();

  private static final String CLINTON = "clinton";
  private static final String TRUMP = "trump";

  private String date;
  private float clinton;
  private float trump;
  private final List<Estimate> estimates = new ArrayList<>();

  public void setDate(String date) {
    Log.d("ASDF", "Setting date: " + date);
    this.date = date;
  }

  public String getDate() {
    return date;
  }

  public void addEstimate(Estimate estimate) {
    Log.d("ASDF", "Adding estimate: " + estimate.person + " - " + estimate.probability);
    if (CLINTON.equals(estimate.person.toLowerCase())) {
      clinton = estimate.probability;
    } else if (TRUMP.equals(estimate.person.toLowerCase())) {
      trump = estimate.probability;
    }
    estimates.add(estimate);
  }

  public float getClinton() {
    return clinton;
  }

  public float getTrump() {
    return trump;
  }

  public List<Estimate> getEstimates() {
    return estimates;
  }

  public static class Estimate {
    public final String person;
    public final float probability;

    public Estimate(String person, float probability) {
      this.person = person;
      this.probability = probability;
    }
  }
}
