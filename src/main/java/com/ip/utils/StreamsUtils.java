package com.ip.utils;

/*
 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.ip.splitors.AccumulatingEntriesSpliterator;
import com.ip.splitors.AccumulatingSpliterator;
import com.ip.splitors.CrossProductOrderedSpliterator;
import com.ip.splitors.CyclingSpliterator;
import com.ip.splitors.FilteringAllMaxSpliterator;
import com.ip.splitors.FilteringMaxKeysSpliterator;

import static java.util.function.Function.identity;

/**
 * <p>A factory class used to create streams from other streams. There are currently seven ways of rearranging streams.
 * </p>
 * <p>Here is a first example of what can be done:</p>
 * <pre>{@code
 *     // Create an example Stream
 *     Stream<String> stream = Stream.of("a0", "a1", "a2", "a3");
 *     Stream<Stream<String>> groupingStream = StreamsUtils.group(stream, 2);
 *     List<List<String>> collect = groupingStream.map(st -> st.collect(Collectors.toList())).collect(Collectors.toList());
 *     // The collect list is [["a0", "a1"]["a2", "a3"]]
 * }</pre>
 * <p>See the documentation of each factory method for more information. </p>
 *
 */
public class StreamsUtils {

    /**
     * <p>Generates a stream by repeating the elements of the provided stream forever. This stream is not bounded. </p>
     * <pre>{@code
     *     Stream<String> stream = Stream.of("tick", "tock");
     *     Stream<String> cyclingStream = StreamsUtils.cycle(stream);
     *     List<String> collect = cyclingStream.limit(9).collect(Collectors.toList());
     *     // The collect list is ["tick", "tock", "tick", "tock", "tick", "tock", "tick", "tock", "tick"]
     * }</pre>
     * <p>The returned spliterator is <code>ORDERED</code>.</p>
     *
     * @param stream The stream to cycle on. Will throw a <code>NullPointerException</code> if <code>null</code>.
     * @param <E>    The type of the elements of the provided stream.
     * @return A cycling stream.
     */
    public static <E> Stream<E> cycle(Stream<E> stream) {
        Objects.requireNonNull(stream);

        CyclingSpliterator<E> spliterator = CyclingSpliterator.of(stream.spliterator());
        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close).flatMap(identity());
    }

    /**
     * <p>Generates a stream of <code>Map.Entry&lt;E, E&gt;</code> elements with all the cartesian product of the
     * elements of the provided stream with itself. </p>
     * <p>For a stream <code>{a, b, c}</code>, a stream with the following elements is created:
     * <code>{(a, a), (a, b), (a, c), (b, a), (b, b), (b, c), (c, a), (c, b), (c, c)}</code>, where
     * <code>(a, b)</code> is the <code>Map.Entry</code> with key <code>a</code> and value <code>b</code>.</p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream is null.</p>
     *
     * @param stream the processed stream
     * @param <E>    the type of the provided stream
     * @return a stream of the cartesian product
     */
    public static <E> Stream<Map.Entry<E, E>> crossProduct(Stream<E> stream) {
        Objects.requireNonNull(stream);

        CrossProductOrderedSpliterator<E> spliterator =
                CrossProductOrderedSpliterator.of(stream.spliterator());

        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream of <code>Map.Entry&lt;E, E&gt;</code> elements with all the cartesian product of the
     * elements of the provided stream with itself, without the entries in which the key and the
     * value is equal.</p>
     * <p>For a stream <code>{a, b, c}</code>, a stream with the following elements is created:
     * <code>{(a, b), (a, c), (b, a), (b, c), (c, a), (c, b)}</code>, where
     * <code>(a, b)</code> is the <code>Map.Entry</code> with key <code>a</code> and value <code>b</code>.</p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream is null.</p>
     *
     * @param stream the processed stream
     * @param <E>    the type of the provided stream
     * @return a stream of the cartesian product
     */
    public static <E> Stream<Map.Entry<E, E>> crossProductNoDoubles(Stream<E> stream) {
        Objects.requireNonNull(stream);

        CrossProductOrderedSpliterator<E> spliterator =
                CrossProductOrderedSpliterator.noDoubles(stream.spliterator());

        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream of <code>Map.Entry&lt;E, E&gt;</code> elements with all the cartesian product of the
     * elements of the provided stream with itself, in which the entries are such that the key is
     * strictly lesser than the value, using the provided comparator.</p>
     * <p>For a stream <code>{a, b, c}</code>, a stream with the following elements is created:
     * <code>{(a, b), (a, c), (b, c)}</code>, where
     * <code>(a, b)</code> is the <code>Map.Entry</code> with key <code>a</code> and value <code>b</code>.</p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream or comparator is null.</p>
     *
     * @param stream     the processed stream
     * @param comparator the comparator or the elements of the provided stream
     * @param <E>        the type of the provided stream
     * @return a stream of the cartesian product
     */
    public static <E> Stream<Map.Entry<E, E>> crossProductOrdered(Stream<E> stream, Comparator<E> comparator) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(comparator);

        CrossProductOrderedSpliterator<E> spliterator =
                CrossProductOrderedSpliterator.ordered(stream.spliterator(), comparator);

        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream of <code>Map.Entry&lt;E, E&gt;</code> elements with all the cartesian product of the
     * elements of the provided stream with itself, in which the entries are such that the key is
     * strictly lesser than the value, using the natural order of <code>E</code>.</p>
     * <p>For a stream <code>{a, b, c}</code>, a stream with the following elements is created:
     * <code>{(a, b), (a, c), (b, c)}</code>, where
     * <code>(a, b)</code> is the <code>Map.Entry</code> with key <code>a</code> and value <code>b</code>.</p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream is null.</p>
     *
     * @param stream the processed stream
     * @param <E>    the type of the provided stream
     * @return a stream of the cartesian product
     */
    public static <E extends Comparable<? super E>> Stream<Map.Entry<E, E>> crossProductNaturallyOrdered(Stream<E> stream) {
        Objects.requireNonNull(stream);

        CrossProductOrderedSpliterator<E> spliterator =
                CrossProductOrderedSpliterator.ordered(stream.spliterator(), Comparator.naturalOrder());

        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream only composed of the greatest elements of the provided stream, compared using the provided
     * comparator. </p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream or the comparator is null. </p>
     *
     * @param stream     the processed stream
     * @param comparator the comparator used to compare the elements of the stream
     * @param <E>        the type of the provided stream
     * @return a filtered stream
     */
    public static <E> Stream<E> filteringAllMax(Stream<E> stream, Comparator<? super E> comparator) {

        Objects.requireNonNull(stream);
        Objects.requireNonNull(comparator);

        FilteringAllMaxSpliterator<E> spliterator = FilteringAllMaxSpliterator.of(stream.spliterator(), comparator);
        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream only composed of the greatest elements of the provided stream, compared using their
     * natural order. </p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream is null. </p>
     *
     * @param stream the processed stream
     * @param <E>    the type of the provided stream
     * @return a filtered stream
     */
    public static <E extends Comparable<? super E>> Stream<E> filteringAllMax(Stream<E> stream) {

        Objects.requireNonNull(stream);

        return filteringAllMax(stream, Comparator.naturalOrder());
    }


   


    /**
     * <p>Generates a stream composed of the N greatest different values of the provided stream, compared using the
     * provided comparator. If there are no duplicates in the provided stream, then the returned stream will have
     * N values, assuming that the input stream has more than N values. </p>
     * <p>All the duplicates are removed in the returned stream, so in this case the number of elements in the
     * returned stream may be lesser than N. In this case, the total number of values is not guaranteed, and
     * may be lesser than N.</p>
     * <p>Since this operator extract maxes according to the provided comparator, the result is sorted from the
     * greatest element to the smallest, thus in the decreasing order, according to the provided comparator. </p>
     * <p>The provided implementation uses and insertion buffer of size N to keep the N maxes.
     * This implementation becomes less and less efficient as N grows. </p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream or the comparator is null. </p>
     * <p>An <code>IllegalArgumentException</code> is thrown if N is lesser than 1. </p>
     *
     * @param stream        the processed stream
     * @param numberOfMaxes the number of different max values that should be returned. Note that the total number of
     *                      values returned may be larger if there are duplicates in the stream
     * @param comparator    the comparator used to compare the elements of the stream
     * @param <E>           the type of the provided stream
     * @return the filtered stream
     */
    public static <E> Stream<E> filteringMaxKeys(Stream<E> stream, int numberOfMaxes, Comparator<? super E> comparator) {

        Objects.requireNonNull(stream);
        Objects.requireNonNull(comparator);

        FilteringMaxKeysSpliterator<E> spliterator = FilteringMaxKeysSpliterator.of(stream.spliterator(), numberOfMaxes, comparator);
        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream composed of the N greatest different values of the provided stream, compared using the
     * natural order. This method calls the <code>filteringMaxKeys()</code> with the natural order comparator,
     * please refer to this javadoc for details. </p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream is null. </p>
     * <p>An <code>IllegalArgumentException</code> is thrown if N is lesser than 1. </p>
     *
     * @param stream        the processed stream
     * @param numberOfMaxes the number of different max values that should be returned. Note that the total number of
     *                      values returned may be larger if there are duplicates in the stream
     * @param <E>           the type of the provided stream
     * @return the filtered stream
     */
    public static <E extends Comparable<? super E>> Stream<E> filteringMaxKeys(Stream<E> stream, int numberOfMaxes) {

        return filteringMaxKeys(stream, numberOfMaxes, Comparator.naturalOrder());
    }

    /**
     * <p>Generates a stream composed of the accumulation of its elements, through the use of the provided binary
     * operator. </p>
     * <p>For the stream {@code Stream.of(1, 1, 1, 1)}, and the {@code Integer::sum} operator,
     * the following stream is returned: {@code Stream.of(1, 2, 3, 4)}</p>
     * <p>For the stream {@code Stream.of(1, 2, 5, 3)}, and the {@code Integer::max} operator,
     * the following stream is returned: {@code Stream.of(1, 2, 5, 5)}</p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream or the operator is null. </p>
     * <p>A <code>IllegalArgumentException</code> will be thrown if the provided stream is not ordered. </p>
     *
     * @param stream   the processed stream
     * @param operator the binary operator used to accumulate the elements of the stream
     * @param <E>      the type of the provided stream
     * @return the accumulated stream
     */
    public static <E> Stream<E> accumulate(Stream<E> stream, BinaryOperator<E> operator) {

        Objects.requireNonNull(stream);
        Objects.requireNonNull(operator);

        AccumulatingSpliterator<E> spliterator = AccumulatingSpliterator.of(stream.spliterator(), operator);
        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }

    /**
     * <p>Generates a stream composed of the accumulation of its elements, through the use of the provided binary
     * operator. </p>
     * <p>For the stream {@code Stream.of(1, 1, 1, 1)}, and the {@code Integer::sum} operator,
     * the following stream is returned: {@code Stream.of(1, 2, 3, 4)}</p>
     * <p>For the stream {@code Stream.of(1, 2, 5, 3)}, and the {@code Integer::max} operator,
     * the following stream is returned: {@code Stream.of(1, 2, 5, 5)}</p>
     * <p>A <code>NullPointerException</code> will be thrown if the provided stream or the operator is null. </p>
     * <p>A <code>IllegalArgumentException</code> will be thrown if the provided stream is not ordered. </p>
     *
     * @param stream   the processed stream of entries
     * @param operator the binary operator used to accumulate the values of the stream
     * @param <K>      the type of the keys of the provided stream
     * @param <V>      the type of the values provided stream
     * @return the accumulated stream
     */
    public static <K, V> Stream<Map.Entry<K, V>> accumulateEntries(Stream<Map.Entry<K, V>> stream, BinaryOperator<V> operator) {

        Objects.requireNonNull(stream);
        Objects.requireNonNull(operator);

        AccumulatingEntriesSpliterator<K, V> spliterator = AccumulatingEntriesSpliterator.of(stream.spliterator(), operator);
        return StreamSupport.stream(spliterator, stream.isParallel()).onClose(stream::close);
    }
}