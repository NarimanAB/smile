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

package smile.math.distance;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Haifeng Li
 */
public class DynamicTimeWarpingTest {
    double[] x = {
        0.02132777, 0.09703978, 0.16255642, 0.22105814, 0.33398156, 0.32564198,
        0.42268295, 0.52269017, 0.48765594, 0.55634671, 0.65624097, 0.68774409,
        0.76433694, 0.80966153, 0.86670319, 0.84257343, 0.92610981, 0.95989335,
        0.99023656, 1.01301426, 1.04399824, 1.03425819, 1.05169307, 1.09366509,
        1.02798314, 1.03288469, 1.02553048, 1.03751515, 1.04873364, 1.05895938,
        0.95813731, 0.93555851, 0.93275008, 0.91673172, 0.89417974, 0.80288556,
        0.81681172, 0.73713563, 0.72504936, 0.62317013, 0.58861397, 0.53925776,
        0.54439887, 0.41068102, 0.44036223, 0.30074895, 0.22783445, 0.16445066,
        0.18642505, 0.07171808, 0.05670306, -0.03006495, -0.15522562, -0.14426558,
        -0.18473828, -0.24693130, -0.31431100, -0.40359915, -0.41440738, -0.53263144,
        -0.52562415, -0.62711854, -0.69283723, -0.69100598, -0.75878944, -0.75375630,
        -0.84794539, -0.83241244, -0.84810036, -0.86027706, -0.91396262, -0.89504004,
        -0.89889403, -0.96119044, -0.92280725, -0.90323615, -0.95914998, -0.95634898,
        -0.88291497, -0.87008581, -0.93374950, -0.85472230, -0.86538045, -0.77472090,
        -0.73646217, -0.77478528, -0.73543266, -0.66507492, -0.58118079, -0.55081741,
        -0.48798299, -0.42903689, -0.36621047, -0.29122441, -0.25365820, -0.17839114,
        -0.13402881, -0.06656910, -0.06113676, 0.08480863
    };

     double[] y = {
        1.00000000, 0.99798872, 0.99196296, 0.98194696, 0.96798102, 0.95012130,
        0.92843966, 0.90302332, 0.87397449, 0.84141005, 0.80546098, 0.76627189,
        0.72400042, 0.67881661, 0.63090222, 0.58044997, 0.52766283, 0.47275313,
        0.41594175, 0.35745722, 0.29753479, 0.23641551, 0.17434523, 0.11157363,
        0.04835322, -0.01506169, -0.07841602, -0.14145491, -0.20392479, -0.26557437,
        -0.32615565, -0.38542496, -0.44314387, -0.49908020, -0.55300895, -0.60471318,
        -0.65398492, -0.70062595, -0.74444867, -0.78527680, -0.82294610, -0.85730504,
        -0.88821542, -0.91555289, -0.93920748, -0.95908406, -0.97510265, -0.98719883,
        -0.99532393, -0.99944528, -0.99954629, -0.99562656, -0.98770186, -0.97580406,
        -0.95998102, -0.94029639, -0.91682936, -0.88967432, -0.85894051, -0.82475155,
        -0.78724498, -0.74657166, -0.70289520, -0.65639130, -0.60724703, -0.55566006,
        -0.50183791, -0.44599708, -0.38836221, -0.32916512, -0.26864394, -0.20704212,
        -0.14460746, -0.08159111, -0.01824655, 0.04517140, 0.10840765, 0.17120782,
        0.23331930, 0.29449224, 0.35448056, 0.41304296, 0.46994386, 0.52495439,
        0.57785325, 0.62842766, 0.67647418, 0.72179953, 0.76422140, 0.80356913,
        0.83968446, 0.87242209, 0.90165036, 0.92725167, 0.94912305, 0.96717652,
        0.98133946, 0.99155489, 0.99778173, 0.99999493
    };

    public DynamicTimeWarpingTest() {
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

    /**
     * Test of d method, of class DynamicTimeWarping.
     */
    @Test
    public void testD() {
        System.out.println("d");
        assertEquals(24.57181, DynamicTimeWarping.d(x, y), 1E-5);
        assertEquals(65.82546, DynamicTimeWarping.d(x, y, 10), 1E-5);
        assertEquals(38.06896, DynamicTimeWarping.d(x, y, 20), 1E-5);
        assertEquals(24.57525, DynamicTimeWarping.d(x, y, 30), 1E-5);
        assertEquals(24.57181, DynamicTimeWarping.d(x, y, 40), 1E-5);
    }
}