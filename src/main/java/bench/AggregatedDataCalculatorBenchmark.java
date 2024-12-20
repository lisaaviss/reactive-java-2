package bench;

import lab.AggregatedDataCalculator;
import lab.CollectionFill;
import lab.Commit;
import org.openjdk.jmh.annotations.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class AggregatedDataCalculatorBenchmark {

    private List<Commit> commitList5000;
    private List<Commit> commitList50000;
    private List<Commit> commitList250000;

    @Setup(Level.Trial)
    public void setUp() {
        commitList5000 = CollectionFill.collectionFill(5000);
        commitList50000 = CollectionFill.collectionFill(50000);
        commitList250000 = CollectionFill.collectionFill(250000);
    }

    //ParallelStreamNoDelay
    @Benchmark
    @Group("ParallelStreamNoDelay")
    public long benchmarkStreamAPI5000() {
        return AggregatedDataCalculator.calculateWithStreamAPI(commitList5000, 0);
    }
    @Group("ParallelStreamNoDelay")
    @Benchmark
    public long benchmarkStreamAPI50000() {
        return AggregatedDataCalculator.calculateWithStreamAPI(commitList50000, 0);
    }
    @Group("ParallelStreamNoDelay")
    @Benchmark
    public long benchmarkStreamAPI250000() {
        return AggregatedDataCalculator.calculateWithStreamAPI(commitList250000, 0);
    }
    //ParallelStreamWithDelay
    @Group("ParallelStreamWithDelay")
    @Benchmark
    public long benchmarkStreamAPI5000delay() {
        return AggregatedDataCalculator.calculateWithStreamAPI(commitList5000, 1);
    }

    @Group("ParallelStreamWithDelay")
    @Benchmark
    public long benchmarkStreamAPI50000delay() {
        return AggregatedDataCalculator.calculateWithStreamAPI(commitList50000, 1);
    }

    @Group("ParallelStreamWithDelay")
    @Benchmark
    public long benchmarkStreamAPI250000dealy() {
        return AggregatedDataCalculator.calculateWithStreamAPI(commitList250000, 1);
    }

    @Group("ConsistentStreamNoDelay")
    @Benchmark
    public long benchmarkStreamAPI5000consistent() {
        return AggregatedDataCalculator.calculateWithStreamAPIconsistent(commitList5000, 0);
    }

    //ConsistentStreamNoDelay
    @Group("ConsistentStreamNoDelay")
    @Benchmark
    public long benchmarkStreamAPI50000consistent() {
        return AggregatedDataCalculator.calculateWithStreamAPIconsistent(commitList50000, 0);
    }
    @Group("ConsistentStreamNoDelay")
    @Benchmark
    public long benchmarkStreamAPI250000consistent() {
        return AggregatedDataCalculator.calculateWithStreamAPIconsistent(commitList250000, 0);
    }

    //ConsistentStreamWithDelay
    @Group("ConsistentStreamWithDelay")
    @Benchmark
    public long benchmarkStreamAPI5000consistentDelay() {
        return AggregatedDataCalculator.calculateWithStreamAPIconsistent(commitList5000, 1);
    }
    @Group("ConsistentStreamWithDelay")
    @Benchmark
    public long benchmarkStreamAPI50000consistentDelay() {
        return AggregatedDataCalculator.calculateWithStreamAPIconsistent(commitList50000, 1);
    }
    @Group("ConsistentStreamWithDelay")
    @Benchmark
    public long benchmarkStreamAPI250000consistentDelay() {
        return AggregatedDataCalculator.calculateWithStreamAPIconsistent(commitList250000, 1);
    }


/*    @Benchmark
    public long benchmarkСalculateWithCustomSpliterator5000() {
        return AggregatedDataCalculator.calculateWithCustomSpliterator(commitList5000, 1);
    }
    @Benchmark
    public long benchmarkСalculateWithCustomSpliterator50000() {
        return AggregatedDataCalculator.calculateWithCustomSpliterator(commitList50000, 1);
    }
    @Benchmark
    public long benchmarkСalculateWithCustomSpliterator250000() {
        return AggregatedDataCalculator.calculateWithCustomSpliterator(commitList250000, 1);
    }*/


    //ForkJoinPool
    @Group("ForkJoinPool")
    @Benchmark
    public long benchmarkCalculateWithForkJoin5000() {
        return AggregatedDataCalculator.calculateWithForkJoin(commitList5000, 0);
    }
    @Group("ForkJoinPool")
    @Benchmark
    public long benchmarkCalculateWithForkJoin50000() {
        return AggregatedDataCalculator.calculateWithForkJoin(commitList50000, 0);
    }
    @Group("ForkJoinPool")
    @Benchmark
    public long benchmarkCalculateWithForkJoin250000() {
        return AggregatedDataCalculator.calculateWithForkJoin(commitList250000, 0);
    }

}


