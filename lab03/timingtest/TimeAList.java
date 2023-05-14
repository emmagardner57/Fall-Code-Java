package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> N = new AList<Integer>();
        AList<Double> timing = new AList<Double>();
        for(int x = 1000; x<=64000; x *= 2) {
            AList<Integer> useless = new AList<Integer>();
            Stopwatch sw = new Stopwatch();
            for (int i = 0; i <= x; i++) {
                useless.addLast(1);
            }
            N.addLast(x);
            timing.addLast(sw.elapsedTime());
        }
        printTimingTable(N, timing, N);
    }
}
