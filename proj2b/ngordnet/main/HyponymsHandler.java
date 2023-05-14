package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet net;
    private NGramMap ngm;
    private final int EARLIEST_YEAR = 1900;
    private final int LATEST_YEAR = 2020;
    public HyponymsHandler(WordNet wn, NGramMap nGram) {
        net = wn;
        ngm = nGram;
    }

    public ArrayList<String> calculateListofWords(NgordnetQuery q) {
        List<String> words = q.words();
        if (words == null || words.isEmpty()) {
            return null;
        } else if (words.size() > 1) {
            return net.combinedList(words);
        } else {
            return net.childrenList(words.get(0));
        }
    }
    @Override
    public String handle(NgordnetQuery q) {
        int k = q.k();
        int start = q.startYear();
        int end = q.endYear();
        ArrayList<String> kidsList = calculateListofWords(q);
        if (start == 0) {
            start = EARLIEST_YEAR;
        }
        if (end == 0) {
            end = LATEST_YEAR;
        }
        if (k == 0 && kidsList != null) {
            return tidyUp(kidsList);
        } else if (k > 0 && kidsList != null) {
            return popularityCalculator(kidsList, k, start, end);
        } else {
            String empty = "[]";
            return empty;
        }
    }

    private String popularityCalculator(ArrayList<String> wordList, int k, int startYear, int endYear) {
        HashMap<Double, ArrayList<String>> wordPopularity = new HashMap<>();
        ArrayList<String> finalList = new ArrayList<>();
        Stack<Double> stack = new Stack<>();
        if (wordList != null) {
            for (String word : wordList) {
                List<Double> timesUsed = ngm.countHistory(word, startYear, endYear).data();
                double amount = 0;
                for (double eachYear : timesUsed) {
                    amount += eachYear;
                }
                ArrayList<String> amountToWords = new ArrayList<>();
                if (amount > 0) {
                    if (wordPopularity.containsKey(amount)) {
                        amountToWords.addAll(wordPopularity.get(amount));
                    }
                    amountToWords.add(word);
                    wordPopularity.put(amount, amountToWords);
                    if (!stack.contains(amount)) {
                        stack.push(amount);
                    }
                }
            }
        }
        if (stack.isEmpty()) {
            String empty = "[]";
            return empty;
        }
        Double[] decIndex = new Double[stack.size()];
        decIndex[0] = stack.pop();
        while (!stack.isEmpty()) {
            double newbie = stack.pop();
            int p = 0;
            while (p < decIndex.length) {
                if (decIndex[p] != null) {
                    if (newbie > decIndex[p]) {
                        double moveRight = decIndex[p];
                        decIndex[p] = newbie;
                        newbie = moveRight;
                    }
                } else {
                    decIndex[p] = newbie;
                    p = decIndex.length;
                }
                p++;
            }
        }
        int count = 0;
        int index = 0;
        while (count < k && index < wordPopularity.size()) {
            if (count >= decIndex.length) {
                return tidyUp(finalList);
            }
            ArrayList<String> wordsWithSameAmount = wordPopularity.get(decIndex[count]);
            ArrayList<String> keptWords = new ArrayList<>();
            if (wordsWithSameAmount != null) {
                if (wordsWithSameAmount.size() + count >= k) {
                    int h = 0;
                    for (String abcWord : wordsWithSameAmount) {
                        if (h <= k - count && !keptWords.contains(abcWord)) {
                            keptWords.add(abcWord);
                            h++;
                        }
                    }
                } else {
                    for (String possibleDups : wordsWithSameAmount) {
                        if (!keptWords.contains(possibleDups)) {
                            keptWords.add(possibleDups);
                        }
                    }
                }
                finalList.addAll(keptWords);
                count += keptWords.size();
            }
            index++;
        }
        return tidyUp(finalList);
    }
    private String tidyUp(ArrayList<String> yucky) {
        if (yucky != null && !yucky.isEmpty()) {
            yucky = net.removeDuplicates(yucky);
            Object[] sortedList = yucky.toArray();
            Arrays.sort(sortedList);
            StringBuilder sortedString = new StringBuilder((String) sortedList[0]);
            for (int i = 1; i < sortedList.length; i++) {
                sortedString.append(", ").append(sortedList[i]);
            }
            sortedString = new StringBuilder("[" + sortedString + "]");
            return sortedString.toString();
        }
        String empty = "[]";
        return empty;
    }
}

