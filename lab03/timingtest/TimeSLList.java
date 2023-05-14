package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> N = new AList<Integer>();
        AList<Double> timing = new AList<Double>();
        AList<Integer> ops = new AList<Integer>();
        for(int x = 1000; x<=64000; x *= 2) {

            SLList<Integer> useless = new SLList<Integer>();

            for(int i = 0; i < x; i++) {
                useless.addLast(8);
            }

            Stopwatch sw = new Stopwatch();

            for (int k = 0; k < x; k++) {
                useless.getLast();
            }

            timing.addLast(sw.elapsedTime());
            N.addLast(x);
            ops.addLast(10000);

        }
        printTimingTable(N, timing, ops);
    }

}
