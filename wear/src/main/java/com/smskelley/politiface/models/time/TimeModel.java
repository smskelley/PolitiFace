package com.smskelley.politiface.models.time;

public interface TimeModel {
  float getHour();

  float getMinute();

  float getSecond();

  void update(long msSinceEpochUtc);
}
