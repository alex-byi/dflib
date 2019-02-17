package com.nhl.dflib.map;

import com.nhl.dflib.row.RowProxy;
import com.nhl.dflib.row.RowBuilder;

@FunctionalInterface
public interface RowCombiner {

    static RowCombiner zip(int rightOffset) {
        return (lr, rr, tr) -> {

            // rows can be null in case of outer joins...

            if (lr != null) {
                lr.copy(tr, 0);
            }

            if (rr != null) {
                rr.copy(tr, rightOffset);
            }
        };
    }


    void combine(RowProxy lr, RowProxy rr, RowBuilder tr);
}
