/*
 * Copyright (c) 2010-2021 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */

package smile.neighbor;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import smile.math.MathEx;
import smile.math.distance.EuclideanDistance;
import smile.test.data.USPS;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Haifeng Li
 */
@SuppressWarnings("rawtypes")
public class MPLSHTest {
    double[][] x = USPS.x;
    double[][] testx = USPS.testx;
    MPLSH<double[]> lsh;
    LinearSearch<double[], double[]> naive = LinearSearch.of(x, new EuclideanDistance());

    public MPLSHTest() {
        MathEx.setSeed(19650218); // to get repeatable results.

        lsh = new MPLSH<>(256, 100, 3, 4.0);
        for (double[] xi : x) {
            lsh.put(xi, xi);
        }
        
        double[][] train = new double[500][];
        int[] index = MathEx.permutate(x.length);
        for (int i = 0; i < train.length; i++) {
            train[i] = x[index[i]];
        }
        lsh.fit(naive, train, 8.0);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNearest() {
        System.out.println("nearest");

        int recall = 0;
        double error = 0.0;
        int hit = 0;
        for (double[] xi : testx) {
            Neighbor neighbor = lsh.nearest(xi);
            if (neighbor != null) {
                hit++;

                Neighbor truth = naive.nearest(xi);
                if (neighbor.index == truth.index) {
                    recall++;
                } else {
                    error += Math.abs(neighbor.distance - truth.distance) / truth.distance;
                }
            }
        }

        error /= (hit - recall);

        assertEquals(1722, recall);
        assertEquals(2007, hit);
        assertEquals(0.0687, error, 1E-4);
        System.out.format("recall is %.2f%%%n", 100.0 * recall / testx.length);
        System.out.format("error when miss is %.2f%%%n", 100.0 * error);
        System.out.format("null rate is %.2f%%%n", 100.0 - 100.0 * hit / testx.length);
    }

    @Test
    public void testKnn() {
        System.out.println("knn");

        int[] recall = new int[testx.length];
        for (int i = 0; i < testx.length; i++) {
            int k = 7;
            Neighbor[] n1 = lsh.search(testx[i], k, 0.95, 50);
            Neighbor[] n2 = naive.search(testx[i], k);
            for (Neighbor m2 : n2) {
                for (Neighbor m1 : n1) {
                    if (m1.index == m2.index) {
                        recall[i]++;
                        break;
                    }
                }
            }
        }

        System.out.format("q1     of recall is %d%n", MathEx.q1(recall));
        System.out.format("median of recall is %d%n", MathEx.median(recall));
        System.out.format("q3     of recall is %d%n", MathEx.q3(recall));
    }

    @Test
    public void testRange() {
        System.out.println("range");

        int[] recall = new int[testx.length];
        for (int i = 0; i < testx.length; i++) {
            ArrayList<Neighbor<double[], double[]>> n1 = new ArrayList<>();
            ArrayList<Neighbor<double[], double[]>> n2 = new ArrayList<>();
            lsh.search(testx[i], 8.0, n1, 0.95, 50);
            naive.search(testx[i], 8.0, n2);

            for (Neighbor m2 : n2) {
                for (Neighbor m1 : n1) {
                    if (m1.index == m2.index) {
                        recall[i]++;
                        break;
                    }
                }
            }
        }

        System.out.format("q1     of recall is %d%n", MathEx.q1(recall));
        System.out.format("median of recall is %d%n", MathEx.median(recall));
        System.out.format("q3     of recall is %d%n", MathEx.q3(recall));
    }

    @Test
    public void testSpeed() {
        System.out.println("Speed");

        long start = System.currentTimeMillis();
        for (double[] xi : testx) {
            lsh.nearest(xi);
        }
        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("NN: %.2fs%n", time);

        start = System.currentTimeMillis();
        for (double[] xi : testx) {
            lsh.search(xi, 10);
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("10-NN: %.2fs%n", time);

        start = System.currentTimeMillis();
        List<Neighbor<double[], double[]>> n = new ArrayList<>();
        for (double[] xi : testx) {
            lsh.search(xi, 8.0, n);
            n.clear();
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("Range: %.2fs%n", time);
    }
}
