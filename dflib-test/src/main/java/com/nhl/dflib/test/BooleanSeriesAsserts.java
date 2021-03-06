package com.nhl.dflib.test;

import com.nhl.dflib.BooleanSeries;

import static org.junit.Assert.*;

/**
 * @since 0.6
 */
public class BooleanSeriesAsserts {

    private boolean[] data;

    public BooleanSeriesAsserts(BooleanSeries series) {
        assertNotNull("Series is null", series);

        this.data = new boolean[series.size()];
        series.copyToBoolean(data, 0, 0, series.size());
    }

    public BooleanSeriesAsserts expectData(boolean... expectedValues) {

        assertEquals("Unexpected BooleanSeries length", expectedValues.length, data.length);

        for (int i = 0; i < expectedValues.length; i++) {

            boolean a = data[i];
            boolean e = expectedValues[i];
            assertEquals("Unexpected value at " + i, e, a);
        }

        return this;
    }
}
