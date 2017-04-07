import java.io.*;
import java.util.*;

public class Solution {
    
    static class Interval implements Comparable<Interval> {
        final int start;
        final int end;
        final int range;
        
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
            this.range = this.end - this.start + 1;
        }
        
        @Override
        public int compareTo(Interval other) {

            int order = this.start - other.start;
            if(this.start == other.start) {
                order = this.end - other.end;
            }
            return order;
        }
        
        @Override
        public String toString() {
            
            return String.format("[%d, %d]", this.start, this.end);
        }
    }
    
    public static void resolve(int n, int m, Map<Integer, List<Interval>> intervals) {
        
        long result = (long) n * (long) m;
        for(Map.Entry<Integer, List<Interval>> entry : intervals.entrySet()) {
            Collections.sort(entry.getValue());
            int lastEnd = 0;
            for(Interval interval : entry.getValue()) {
                if(interval.start > lastEnd) {
                    result -= interval.range;
                } else {
                    result -= lastEnd < interval.end ? interval.end - lastEnd : 0;
                }
                lastEnd = Math.max(lastEnd, interval.end);
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        try(Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            int m = scan.nextInt();
            int k = scan.nextInt();
            int[][] limitsByRow = new int[n][2];
            //long result = (long) n * (long) m;
            Map<Integer, List<Interval>> intervals = new HashMap<>();
            for(int i = 0;i < k;i++) {
                int row = scan.nextInt();
                int colStart = scan.nextInt();
                int colEnd = scan.nextInt();
                List<Interval> foundIntervalList = intervals.getOrDefault(row - 1, new ArrayList<>());
                foundIntervalList.add(new Interval(colStart, colEnd));
                intervals.put(row - 1, foundIntervalList);
                /*if(limitsByRow[row - 1][0] == 0) {
                    limitsByRow[row - 1][0] = colStart;
                    limitsByRow[row - 1][1] = colEnd;
                    result -= (colEnd - colStart + 1);
                } else {
                    if(limitsByRow[row - 1][0] > colStart) {
                        result -= limitsByRow[row - 1][0] - colStart;
                        limitsByRow[row - 1][0] = colStart;
                    }
                    if(limitsByRow[row - 1][1] < colEnd) {
                        result -= colEnd - limitsByRow[row - 1][1];
                        limitsByRow[row - 1][1] = colEnd;
                    }
                }*/
            }
            resolve(n, m, intervals);
            //System.out.println(result);
        }
    }
}
