package com.smskelley.politilib.models.polls.time;

import com.smskelley.politilib.BuildConfig;
import com.smskelley.politilib.models.time.TimeModelImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TimeModelImplTest {

    private static final float EXPECTED_SECOND = 1;
    private static final float EXPECTED_MINUTE = 2;
    private static final float EXPECTED_HOUR = 3;

    private TimeModelImpl timeModel;

    @Before
    public void setUp() {
        timeModel = new TimeModelImpl();
        timeModel.update(getMillisFor(EXPECTED_HOUR, EXPECTED_MINUTE, EXPECTED_SECOND));
    }

    @Test
    public void testGetCurrentHours() throws Exception {
        assertEquals(EXPECTED_HOUR, timeModel.getHour(), 0);
    }

    @Test
    public void testGetCurrentHours_24HourTreatedAs12Hour() throws Exception {
        timeModel.update(getMillisFor(EXPECTED_SECOND, EXPECTED_MINUTE, EXPECTED_HOUR + 12));
        assertEquals(EXPECTED_HOUR, timeModel.getHour(), 0);
    }

    @Test
    public void testGetCurrentMinutes() throws Exception {
        assertEquals(EXPECTED_MINUTE, timeModel.getMinute(), 0);
    }

    @Test
    public void testGetCurrentSeconds() throws Exception {
        assertEquals(EXPECTED_SECOND, timeModel.getSecond(), 0);
    }

    private long getMillisFor(float hour, float min, float second) {
        return
                new GregorianCalendar(
                        2016, 1, 1,
                        (int) EXPECTED_HOUR,
                        (int) EXPECTED_MINUTE,
                        (int) EXPECTED_SECOND
                ).getTimeInMillis();
    }
}