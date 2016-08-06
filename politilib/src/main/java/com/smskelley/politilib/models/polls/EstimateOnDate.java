package com.smskelley.politilib.models.polls;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 */
public class EstimateOnDate {
  public static final EstimateOnDate EMPTY = new EstimateOnDate();

  private static final String CLINTON = "clinton";
  private static final String TRUMP = "trump";
  private final List<Estimate> estimates = new ArrayList<>();
  private String date;
  private float clinton = 0.5f;
  private float trump = 0.5f;


  public static EstimateOnDate fromEstimateByDate(Chart.EstimateByDate chartEstimate) {
    EstimateOnDate estimateOnDate = new EstimateOnDate();
    for (Chart.Estimate estimate : chartEstimate.estimates) {
      estimateOnDate.addEstimate(new Estimate(estimate.choice, estimate.value));
    }
    estimateOnDate.setDate(chartEstimate.date);

    return estimateOnDate;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void addEstimate(Estimate estimate) {
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

  /**
   * Clinton's percent of clinton+trump, ignoring other categories.
   */
  public float getLeftPercent() {
    return clinton / (clinton + trump);
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
