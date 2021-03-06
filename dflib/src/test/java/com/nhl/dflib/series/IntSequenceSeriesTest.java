package com.nhl.dflib.series;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntSequenceSeriesTest {

    @Test
    public void testSize() {
        IntSequenceSeries s1 = new IntSequenceSeries(-1, 5);
        assertEquals(6, s1.size());

        IntSequenceSeries s2 = new IntSequenceSeries(1, 5);
        assertEquals(4, s2.size());
    }
}
