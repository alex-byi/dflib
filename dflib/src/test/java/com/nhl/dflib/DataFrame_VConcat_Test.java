package com.nhl.dflib;

import com.nhl.dflib.join.JoinType;
import org.junit.Test;

public class DataFrame_VConcat_Test extends BaseDataFrameTest {

    @Test
    public void testVConcat_Default() {

        Index i = Index.withLabels("a");
        DataFrame df1 = createDf(i, 1, 2);
        DataFrame df2 = createDf(i, 10, 20);


        DataFrame df = df1.vConcat(df2);

        new DFAsserts(df, "a")
                .expectHeight(4)
                .expectRow(0, 1)
                .expectRow(1, 2)
                .expectRow(2, 10)
                .expectRow(3, 20);
    }

    @Test
    public void testVConcat_Default_Multiple() {

        Index i = Index.withLabels("a");
        DataFrame df1 = createDf(i, 1, 2);
        DataFrame df2 = createDf(i, 10);
        DataFrame df3 = createDf(i, 20);

        DataFrame df = df1.vConcat(df2, df3);

        new DFAsserts(df, "a")
                .expectHeight(4)
                .expectRow(0, 1)
                .expectRow(1, 2)
                .expectRow(2, 10)
                .expectRow(3, 20);
    }

    @Test
    public void testVConcat_Default_Left() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                1, 2,
                3, 4);

        Index i2 = Index.withLabels("c", "b");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40);

        DataFrame df = df1.vConcat(df2);

        new DFAsserts(df, "a", "b")
                .expectHeight(4)
                .expectRow(0, 1, 2)
                .expectRow(1, 3, 4)
                .expectRow(2, null, 20)
                .expectRow(3, null, 40);
    }

    @Test
    public void testVConcat_Left() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                1, 2,
                3, 4);

        Index i2 = Index.withLabels("c", "b");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40);

        DataFrame df = df1.vConcat(JoinType.left, df2);

        new DFAsserts(df, "a", "b")
                .expectHeight(4)
                .expectRow(0, 1, 2)
                .expectRow(1, 3, 4)
                .expectRow(2, null, 20)
                .expectRow(3, null, 40);
    }

    @Test
    public void testVConcat_Right() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                1, 2,
                3, 4);

        Index i2 = Index.withLabels("c", "b");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40);

        DataFrame df = df1.vConcat(JoinType.right, df2);

        new DFAsserts(df, "c", "b")
                .expectHeight(4)
                .expectRow(0, null, 2)
                .expectRow(1, null, 4)
                .expectRow(2, 10, 20)
                .expectRow(3, 30, 40);
    }

    @Test
    public void testVConcat_Inner_Multiple() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                1, 2,
                3, 4);

        Index i2 = Index.withLabels("c", "b");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40);

        Index i3 = Index.withLabels("b", "d");
        DataFrame df3 = createDf(i3,
                100, 200,
                300, 400);

        DataFrame df = df1.vConcat(JoinType.inner, df2, df3);

        new DFAsserts(df, "b")
                .expectHeight(6)
                .expectRow(0, 2)
                .expectRow(1, 4)
                .expectRow(2, 20)
                .expectRow(3, 40)
                .expectRow(4, 100)
                .expectRow(5, 300);
    }

    @Test
    public void testVConcat_Full() {

        Index i1 = Index.withLabels("a", "b");
        DataFrame df1 = createDf(i1,
                1, 2,
                3, 4);

        Index i2 = Index.withLabels("c", "b");
        DataFrame df2 = createDf(i2,
                10, 20,
                30, 40);

        DataFrame df = df1.vConcat(JoinType.full, df2);

        new DFAsserts(df, "a", "b", "c")
                .expectHeight(4)
                .expectRow(0, 1, 2, null)
                .expectRow(1, 3, 4, null)
                .expectRow(2, null, 20, 10)
                .expectRow(3, null, 40, 30);
    }

}
