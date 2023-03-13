package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.InstrumentedHelper;
import edu.neu.coe.info6205.sort.linearithmic.MergeSortBasic;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MergeBenchmark {
    public static void main(String[] args) {
        for(int n=10000;n<=256000;n=n*2)
        {
            System.out.println("=============MergeSort Time Test "+n+" elements"+"==========================");
            final Config config = Config.setupConfig("true", "0", "0", "", "");
            BaseHelper<Integer> helper = new InstrumentedHelper<>("MergeSortHelper", config);
            MergeSortBasic<Integer> merge = new MergeSortBasic<>(helper);
            Consumer<Integer[]> consumer = RanArr -> merge.sort(RanArr);
            Benchmark_Timer<Integer[]> BT = new Benchmark_Timer<>("MergeSort Time Test " + n + " elements", consumer);
            int finalN = n;
            Supplier<Integer[]> randomArrays = () -> {
                Integer[] randomArray = new Integer[finalN];
                for(int i = 0; i< finalN; i++)
                {
                    randomArray[i] = (int)(Math.random()*finalN+1);
                }
                return randomArray;
            };
            helper.postProcess(merge.sort(randomArrays.get()));
            PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");

            int compares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
            int hits = (int) statPack.getStatistics(InstrumentedHelper.HITS).mean();
            int fixes = (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean();
            int copies = (int) statPack.getStatistics(InstrumentedHelper.COPIES).mean();

            System.out.println("=============MergeSort instrumentation Test "+n+" elements"+"===============");
            System.out.println("Compares: " + compares);
            System.out.println("Hits : " + hits);
            System.out.println("fixes : " + fixes);
            System.out.println("copies : " + copies);
        }
        for(int n=10000;n<=256000;n=n*2)
        {
            System.out.println("=============MergeSort Time Test "+n+" elements"+"==========================");
            final Config config = Config.setupConfig("false", "0", "0", "", "");
            BaseHelper<Integer> helper = new InstrumentedHelper<>("MergeSortHelper", config);
            MergeSortBasic<Integer> merge = new MergeSortBasic<>(helper);
            Consumer<Integer[]> consumer = RanArr -> merge.sort(RanArr);
            Benchmark_Timer<Integer[]> BT = new Benchmark_Timer<>("MergeSort Time Test " + n + " elements", consumer);
            int finalN = n;
            Supplier<Integer[]> randomArrays = () -> {
                Integer[] randomArray = new Integer[finalN];
                for(int i = 0; i< finalN; i++)
                {
                    randomArray[i] = (int)(Math.random()*finalN+1);
                }
                return randomArray;
            };
            consumer.accept(randomArrays.get());
            double randTime = BT.run(randomArrays.get(), 1);
            System.out.println("MergeSort Time Test "+n+" elements : " + randTime);
        }
    }
}
