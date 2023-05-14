package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super(ts.subMap(startYear, endYear + 1));
    }

    /** Returns all years for this TimeSeries (in any order).
     * making a set that contains nodes of the tree*/
    public List<Integer> years() {
        Set<Map.Entry<Integer, Double>> setOfNodes = entrySet();
        List<Integer> listOfYears = new ArrayList<>(setOfNodes.size());
        for (Map.Entry<Integer, Double> node : setOfNodes) {
            listOfYears.add(node.getKey());
        }
        return listOfYears;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        Set<Map.Entry<Integer, Double>> setOfNodes = entrySet();
        List<Double> listOfData = new ArrayList<>(setOfNodes.size());
        for (Map.Entry<Integer, Double> node : setOfNodes) {
            listOfData.add(node.getValue());
        }
        return listOfData;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        Set<Map.Entry<Integer, Double>> originalSet = entrySet();
        Set<Map.Entry<Integer, Double>> newSet = ts.entrySet();
        TimeSeries temp = (TimeSeries) this.clone();
        for (Map.Entry<Integer, Double> originalNode : originalSet) {
            if (ts.containsKey(originalNode.getKey())) {
                double tsValue = ts.get(originalNode.getKey());
                double value = originalNode.getValue() + tsValue;
                temp.replace(originalNode.getKey(), value);
            }
        }
        for (Map.Entry<Integer, Double> newNode : newSet) {
            if (!temp.containsKey(newNode.getKey())) {
                temp.put(newNode.getKey(), newNode.getValue());
            }
        }
        return temp;
    }

    /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries dividedBy(TimeSeries ts) {

        Set<Map.Entry<Integer, Double>> originalSet = entrySet();
        TimeSeries temp = new TimeSeries();

        for (Map.Entry<Integer, Double> originalNode : originalSet) {
            if (!ts.containsKey(originalNode.getKey())) {
                throw new IllegalArgumentException();
            } else {
                double tsValue = ts.get(originalNode.getKey());
                double value = originalNode.getValue() / tsValue;
                temp.put(originalNode.getKey(), value);
            }
        }
        return temp;
    }
    private void printTimeSeries() {
        for (Map.Entry<Integer, Double> entry : this.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
        }
    }

}
