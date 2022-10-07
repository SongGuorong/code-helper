package benchmark;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * LinkedList迭代性能测试
 * @BenchmarkMode: 可以注释在方法级别，也可以注释在类级别
 *      Throughput: 每段时间执行的次数，一般是秒
 *      AverageTime: 平均时间，每次操作的平均耗时
 *      SampleTime: 在测试中，随机进行采样执行的时间
 *      SingleShotTime: 在每次执行中计算耗时
 *      ALL: 所有模式
 * @Warmup: 预热
 * @Threads: 每个进程中的测试线程
 * @Fork: 进行fork的次数。如果fork数是3的话，则JMH会fork出3个进程来进行测试。
 * @Benchmark: 方法级注解，表示该方法是需要进行 benchmark 的对象
 * @Setup: 方法级注解，这个注解的作用就是我们需要在测试之前进行一些准备工作 ，比如对一些数据的初始化之类的。
 * @TearDown: 方法级注解，这个注解的作用就是我们需要在测试之后进行一些结束工作 ，比如关闭线程池，数据库连接等的，主要用于资源的回收等。
 * @State: 当使用@Setup参数的时候，必须在类上加这个参数，不然会提示无法运行。
 *      1. Thread: 该状态为每个线程独享。
 *      2. Group: 该状态为同一个组里面所有线程共享。
 *      3. Benchmark: 该状态在所有线程间共享。
 * (https://mp.weixin.qq.com/s/hJE4lneGppZ8M096_ALkxA)
 * 输出结果：
 * Benchmark                                      Mode  Cnt  Score   Error  Units
 * LinkedListIterationBenchMark.forEachIterate   thrpt    2  7.362          ops/s
 * LinkedListIterationBenchMark.forIndexIterate  thrpt    2  4.583          ops/s
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class LinkedListIterationBenchMark {
    private static final int SIZE = 10000;

    private List<String> list = new LinkedList<String>();

    @Setup
    public void setUp() {
        for (int i = 0; i < SIZE; i++) {
            list.add(String.valueOf(i));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forIndexIterate() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i);
            System.out.println("");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void forEachIterate() {
        for (String s : list) {
            System.out.println("");
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LinkedListIterationBenchMark.class.getSimpleName())
                .forks(1)  /*执行1轮测试，而一轮都是先预热，再正式计量*/
                .warmupIterations(2)       /*预热2轮*/
                .measurementIterations(2)
                .output("../benchmark.log")
                .build();
        new Runner(opt).run();
    }

}
