package ngordnet.main;

import java.util.*;
public class WordNet {
    public HyponymGraph graph;
    public WordNet(String syns, String hypos){
        graph = new HyponymGraph(syns, hypos);
    }


 //takes in list of words, creates list containing all parent names and children names. alphabetizes list and makes it into a returnable string.
    public ArrayList<String> childrenList(String word) {
        if (word.isEmpty()) {
            return null;
        }
        ArrayList<String> allChildren = graph.findChildren(word);
        return allChildren;
    }

    public ArrayList<String> combinedList(List<String> words) {
        ArrayList<String> allChildren = new ArrayList<>();
        HashMap<String, Boolean[]> wordsUse = new HashMap<>();
        ArrayList<String> singles = new ArrayList<>();

        int parentIndex = 0;
        if (words != null) {
            for (String word : words) {
                ArrayList<String> kidList = graph.findChildren(word);
                if (kidList != null) {
                    for (String kid : kidList) {
                        if (wordsUse.containsKey(kid)) {
                            Boolean[] occurrence = wordsUse.get(kid);
                            occurrence[parentIndex] = true;
                            wordsUse.put(kid, occurrence);
                        } else {
                            if (parentIndex == 0) {
                                allChildren.add(kid);
                                Boolean[] occurrence  = new Boolean[words.size()];
                                for (Boolean boo : occurrence) {
                                    boo = false;
                                }
                                occurrence[parentIndex] = true;
                                wordsUse.put(kid, occurrence);
                            }
                        }
                    }
                }
                parentIndex++;
            }
        }

        for (String word : allChildren) {
            int p = 0;
            while (p < parentIndex) {
                if (wordsUse.get(word)[p] != null && wordsUse.get(word)[p]) {
                    p++;
                } else {
                    p = parentIndex + 1;
                }
                if (p == parentIndex) {
                    singles.add(word);
                }
            }
        }
    return singles;
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> og) {
        ArrayList<String> singles = new ArrayList<>();
        if (og != null) {
            for (String word : og) {
                if (word != null && !singles.contains(word)) {
                    singles.add(word);
                }
            }
        }
        return singles;
    }

}
