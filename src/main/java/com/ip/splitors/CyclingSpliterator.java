package com.ip.splitors;

/*
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * Created by pratap
 */
public class CyclingSpliterator<E> implements Spliterator<Stream<E>> {

    private final List<E> list;

    public static <E> CyclingSpliterator<E> of(Spliterator<E> spliterator) {
        Objects.requireNonNull(spliterator);
        return new CyclingSpliterator<>(spliterator);
    }

    private CyclingSpliterator(List<E> list) {
        this.list = list;
    }

    private CyclingSpliterator(Spliterator<E> spliterator) {
        this.list = StreamSupport.stream(spliterator, false).collect(toList());
    }

    @Override
    public boolean tryAdvance(Consumer<? super Stream<E>> action) {
        action.accept(list.stream());
        return true;
    }

    @Override
    public Spliterator<Stream<E>> trySplit() {
        return new CyclingSpliterator<>(list);
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.ORDERED;
    }
}