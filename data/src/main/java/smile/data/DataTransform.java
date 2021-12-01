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

package smile.data;

import java.util.function.Function;

/**
 * Data transformation interface.
 *
 * @param <T> the type of input data objects.
 * @param <R> the type of output data objects.
 *
 * @author Haifeng Li
 */
public interface DataTransform<T, R> extends Function<T, R> {
    /**
     * Applies this transform to the given argument.
     * @param dataset the input dataset.
     * @return the transformed dataset.
     */
    default Dataset<R> apply(Dataset<T> dataset) {
        return dataset.stream().map(this::apply).collect(Dataset.Collectors.toDataset());
    }

    /**
     * Returns a composed function that first applies this function
     * to its input, and then applies the <code>after</code> function
     * to the result.
     *
     * @param after the transform to apply after this transform is applied.
     * @param <V> the type of output of the <code>after</code> transform,
     *            and of the composed transform.
     * @return a composed transform that first applies this transform and
     *         then applies the <code>after</code> transform.
     */
    default <V> DataTransform<T, V> andThen(Function<? super R, ? extends V> after) {
        return (T t) -> after.apply(apply(t));
    }

    /**
     * Returns a composed function that first applies the <code>before</code>
     * function to its input, and then applies this function to the result.
     *
     * @param before the transform to apply before this transform is applied.
     * @param <V> the type of input to the <code>before</code> transform,
     *          and to the composed transform.
     * @return a composed transform that first applies the <code>before</code>
     *         transform and then applies this transform.
     */
    default <V> DataTransform<V, R> compose(Function<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }
}