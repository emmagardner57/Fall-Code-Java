package ngordnet.ngrams;


import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.algs4.In;


/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    private HashMap<String, TimeSeries> map;
    private TimeSeries ts;
    private int buckets = 0;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        map = new HashMap<>();
        ts = new TimeSeries();
        In in = new In(wordsFilename);
        createMap(in);
        In secondIn = new In(countsFilename);
        createTS(secondIn);
    }


    private void createMap(In in) {
        while (!in.isEmpty()) {
            String word = in.readString();
            int year = in.readInt();
            double amount = in.readInt();
            int useless = in.readInt();
            TimeSeries temp = new TimeSeries();
            temp.put(year, amount);
            if (checkExists(word)) {
                TimeSeries original = map.get(word);
                map.replace(word, original.plus(temp));
            } else {
                map.put(word, temp);
                buckets++;
            }
        }
    }

    private boolean checkExists(String word) {
        if (map.containsKey(word)) {
            return true;
        }
        return false;
    }

    private void createTS(In data) {
        String line = data.readLine();
        String[] list = line.split(",");
        if (list == null) {
            return;
        }
        int year = Integer.parseInt(list[0]);
        double numWords = Double.parseDouble(list[1]);
        ts.put(year, numWords);
        if (data.hasNextLine()) {
            createTS(data);
        }
    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        TimeSeries copy = (TimeSeries) map.get(word).clone();
        return copy;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries copy = new TimeSeries(map.get(word), startYear, endYear);
        return copy;
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries copy = (TimeSeries) ts.clone();
        return copy;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordList = map.get(word);
        TimeSeries relFreq = wordList.dividedBy(ts);
        return relFreq;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries copy = countHistory(word, startYear, endYear);
        copy = copy.dividedBy(ts);
        return copy;
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sumFreq = new TimeSeries();
        for (String entry : words) {
            sumFreq = sumFreq.plus(countHistory(entry));
        }
        sumFreq = sumFreq.dividedBy(ts);
        return sumFreq;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries sumFreq = new TimeSeries();
        for (String entry : words) {
            sumFreq = sumFreq.plus(countHistory(entry, startYear, endYear));
        }
        sumFreq = sumFreq.dividedBy(ts);
        return sumFreq;
    }
}
