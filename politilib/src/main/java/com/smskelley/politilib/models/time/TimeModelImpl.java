package com.smskelley.politilib.models.time;

import java.util.Calendar;

public class TimeModelImpl implements TimeModel {
  private Calendar calendar = Calendar.getInstance();

  @Override
  public float getHour() {
    return (calendar.get(Calendar.HOUR) % 12) + getMinute() / 60;
  }

  @Override
  public float getMinute() {
    return calendar.get(Calendar.MINUTE) + getSecond() / 60;
  }

  @Override
  public float getSecond() {
    return calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND) / 1000f;
  }

  @Override
  public void update(long msSinceEpochUtc) {
    calendar.setTimeInMillis(msSinceEpochUtc);
  }
}
