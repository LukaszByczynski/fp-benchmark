# REMEMBER:

The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

| Benchmark                 | Mode                       | Cnt            | Score       | Error         | Units |
| MtlBenchmark.imperative   | thrpt                      | 3              | 3149150.786 | ±  521129.197 | ops/s |
| MtlBenchmark.io           | thrpt                      | 3              | 1061941.750 | ± 1101644.065 | ops/s |
| MtlBenchmark.io_zio       | thrpt                      | 3              | 217763.685  | ±   81488.224 | ops/s |
| MtlBenchmark.monxIo       | thrpt                      | 3              | 978416.058  | ±  415729.010 | ops/s |
| MtlBenchmark.mtlMeow      | thrpt                      | 3              | 640451.011  | ±  303729.911 | ops/s |
| MtlBenchmark.mtlStack     | thrpt                      | 3              | 112897.210  | ±   26473.125 | ops/s |
| MtlBenchmark.mtlStackOpt | thrpt | 3   | 398611.416 | ±  181754.108 | ops/s |
