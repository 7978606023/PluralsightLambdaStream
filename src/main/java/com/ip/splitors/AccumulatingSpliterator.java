package com.ip.splitors;

/*
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * See the documentation and patterns to be used in this class in the {@link StreamsUtils} factory class.
 * <p>
 * Created by Pratap
 */
public class AccumulatingSpliterator<E> implements Spliterator<E> {

    private BinaryOperator<E> operator;
    private final Spliterator<E> spliterator;
    private AtomicReference<E> accumulator;

    public static <E> AccumulatingSpliterator<E> of(Spliterator<E> spliterator, BinaryOperator<E> operator) {

        Objects.requireNonNull(spliterator);
        Objects.requireNonNull(operator);

        if ((spliterator.characteristics() & Spliterator.ORDERED) == 0) {
            throw new IllegalArgumentException(("Why would you try to accumulate a non-ORDERED spliterator?"));
        }

        return new AccumulatingSpliterator<>(spliterator, operator);
    }

    private AccumulatingSpliterator(Spliterator<E> spliterator, BinaryOperator<E> operator) {
        this.spliterator = spliterator;
        this.operator = operator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super E> action) {
        boolean hasMore = spliterator.tryAdvance(
                e -> {
                    if (accumulator == null) {
                        accumulator = new AtomicReference<>(e);
                        action.accept(e);
                    } else {
                        action.accept(accumulator.accumulateAndGet(e, operator));
                    }
                }
        );

        return hasMore;
    }

    @Override
    public Spliterator<E> trySplit() {
        Spliterator<E> splitSpliterator = spliterator.trySplit();
        return splitSpliterator == null ? null : new AccumulatingSpliterator<>(splitSpliterator, operator);
    }

    @Override
    public long estimateSize() {
        return spliterator.estimateSize();
    }

    @Override
    public int characteristics() {
        return this.spliterator.characteristics();
    }
}