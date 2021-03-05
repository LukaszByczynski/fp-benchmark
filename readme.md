# Simple benchmark to ilustrate the cost of FP

# REMEMBER:

The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

```
 Benchmark                  Mode  Cnt        Score         Error  Units
 MtlBenchmark.imperative   thrpt    5  6184042.466 ± 2747933.859  ops/s
 MtlBenchmark.io           thrpt    5  2242371.481 ±  550261.882  ops/s
 MtlBenchmark.io_zio       thrpt    5   691043.552 ±   87659.235  ops/s
 MtlBenchmark.monxIo       thrpt    5  1842797.694 ±  334572.983  ops/s
 MtlBenchmark.mtlMeow      thrpt    5  1755027.936 ±  104711.308  ops/s
 MtlBenchmark.mtlStack     thrpt    5   252575.529 ±   15548.080  ops/s
 MtlBenchmark.mtlStackOpt  thrpt    5   801246.467 ±   61912.341  ops/s
```
