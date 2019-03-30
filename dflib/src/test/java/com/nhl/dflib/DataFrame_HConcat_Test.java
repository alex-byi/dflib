package com.nhl.dflib;

import com.nhl.dflib.join.JoinType;
import com.nhl.dflib.map.RowCombiner;
import org.junit.Test;

public class DataFrame_HConcat_Test extends BaseDataFrameTest {

    @Test
    public void testZipRows_ImplicitInnerJoin() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                0, 1,
                2, 3);

        Index i2 = Index.withLabels("c", "d");
        DataFrame df2 = createDf(i2,
                10, 20);

        DataFrame df_l = df1.hConcat(df2);

        new DFAsserts(df_l, "a", "b", "c", "d")
                .expectHeight(1)
                .expectRow(0, 0, 1, 10, 20);

        DataFrame df_r = df2.hConcat(df1);

        new DFAsserts(df_r, "c", "d", "a", "b")
                .expectHeight(1)
                .expectRow(0, 10, 20, 0, 1);
    }

    @Test
    public void testZipRows_SparseDF() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                0, 1,
                2, 3)
                .selectColumns("b");

        Index i2 = Index.withLabels("c", "d");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40)
                .selectColumns("c");

        DataFrame df = df1.hConcat(df2);

        new DFAsserts(df, "b", "c")
                .expectHeight(2)
                .expectRow(0, 1, 10)
                .expectRow(1, 3, 30);
    }

    @Test
    public void testZipRows_SparseDF_CustomIndex() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                0, 1,
                2, 3)
                .selectColumns("b");

        Index i2 = Index.withLabels("c", "d");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40)
                .selectColumns("c");


        DataFrame df = df1.hConcat(Index.withLabels("x", "y"), JoinType.inner, df2, RowCombiner.zip(df1.width()));

        new DFAsserts(df, "x", "y")
                .expectHeight(2)
                .expectRow(0, 1, 10)
                .expectRow(1, 3, 30);
    }

    @Test
    public void testZipRows_SparseDF_ReorgColumns() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                0, 1,
                2, 3);

        Index i2 = Index.withLabels("c", "d");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40,
                50, 60);

        RowCombiner c = (lr, rr, tr) -> {

            if (rr != null) {
                tr.set(0, rr.get(1));
            }

            if (lr != null) {
                tr.set(1, lr.get(0));
            }
        };

        DataFrame df = df1.hConcat(Index.withLabels("x", "y"), JoinType.right, df2, c);

        new DFAsserts(df, "x", "y")
                .expectHeight(3)
                .expectRow(0, 20, 0)
                .expectRow(1, 40, 2)
                .expectRow(2, 60, null);
    }

    @Test
    public void testZipRows_InnerJoin() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                0, 1,
                2, 3);

        Index i2 = Index.withLabels("c", "d");
        DataFrame df2 = createDf(i2,
                10, 20);

        DataFrame df_l = df1.hConcat(JoinType.inner, df2);

        new DFAsserts(df_l, "a", "b", "c", "d")
                .expectHeight(1)
                .expectRow(0, 0, 1, 10, 20);

        DataFrame df_r = df2.hConcat(df1);

        new DFAsserts(df_r, "c", "d", "a", "b")
                .expectHeight(1)
                .expectRow(0, 10, 20, 0, 1);
    }

    @Test
    public void testZipRows_Left() {

        Index i1 = Index.withLabels("a");
        DataFrame df1 = createDf(i1,
                0,
                1);

        Index i2 = Index.withLabels("b");
        DataFrame df2 = createDf(i2,
                10);

        DataFrame df_l = df1.hConcat(JoinType.left, df2);

        new DFAsserts(df_l, "a", "b")
                .expectHeight(2)
                .expectRow(0, 0, 10)
                .expectRow(1, 1, null);

        DataFrame df_r = df2.hConcat(JoinType.left, df1);

        new DFAsserts(df_r, "b", "a")
                .expectHeight(1)
                .expectRow(0, 10, 0);
    }

    @Test
    public void testZipRows_Right() {

        Index i1 = Index.withLabels("a");
        DataFrame df1 = createDf(i1,
                0,
                1);

        Index i2 = Index.withLabels("b");
        DataFrame df2 = createDf(i2, 10);

        DataFrame df_l = df1.hConcat(JoinType.right, df2);
        new DFAsserts(df_l, "a", "b")
                .expectHeight(1)
                .expectRow(0, 0, 10);


        DataFrame df_r = df2.hConcat(JoinType.right, df1);
        new DFAsserts(df_r, "b", "a")
                .expectHeight(2)
                .expectRow(0, 10, 0)
                .expectRow(1, null, 1);
    }

    @Test
    public void testZipRows_Full() {

        Index i1 = Index.withLabels("a");
        DataFrame df1 = createDf(i1,
                0,
                1);

        Index i2 = Index.withLabels("b");
        DataFrame df2 = createDf(i2,
                10);

        DataFrame df_l = df1.hConcat(JoinType.full, df2);
        new DFAsserts(df_l, "a", "b")
                .expectHeight(2)
                .expectRow(0, 0, 10)
                .expectRow(1, 1, null);


        DataFrame df_r = df2.hConcat(JoinType.full, df1);
        new DFAsserts(df_r, "b", "a")
                .expectHeight(2)
                .expectRow(0, 10, 0)
                .expectRow(1, null, 1);
    }
}
