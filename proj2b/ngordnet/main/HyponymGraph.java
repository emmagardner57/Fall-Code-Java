package ngordnet.main;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class HyponymGraph {

    public final Map<Integer, Node> adjList;
    public final Map<String, ArrayList<Integer>> findID;

    private int index;

    public HyponymGraph(String wordsID, String hypos) {
        adjList = new HashMap<>();
        findID = new HashMap<>();
        In code = new In(wordsID);
        In hierarchy = new In(hypos);
        createGraph(code);
        createHierarchy(hierarchy);
    }

    public class Node {
        //stores list with children IDs and parent Strings
        public ArrayList<Integer> childrenIDs;
        public final ArrayList<String> parentNames;
        public Node(ArrayList<String> synonyms) {
            childrenIDs = new ArrayList<>();
            parentNames = synonyms;
        }
    }

    //reads in the list of IDs and their corresponding synonyms. converts line to ID number and list of synonyms.
    //checks if word is already in map of words to IDs. If it is, add new ID to list of IDs. If not create new entry
    //creates new entry in adjList, in order (ID, Node). Node contains empty list of kids and String list of parent names.

    private void createGraph(In in){
        String[] allLines = in.readAllLines();
        index = allLines.length;
        if (allLines != null) {
            for (String line : allLines) {
                String[] elements = line.split(",");
                int num = Integer.parseInt(elements[0]);
                String[] words = elements[1].split(" ");
                ArrayList<String> synonyms = new ArrayList<>();

                if (words != null) {
                    for (String word : words) {
                        if (word != null) {
                            synonyms.add(word);
                            if (findID.containsKey(word)) {
                                findID.get(word).add(num);
                            } else {
                                ArrayList<Integer> wordID = new ArrayList<>();
                                wordID.add(num);
                                findID.put(word, wordID);
                            }
                        }
                    }
                    Node curr = new Node(synonyms);
                    adjList.put(num, curr);
                }
            }
        }
    }


    //takes in list of IDs. first ID is parent, following are children. IDs can repeat.
    //adds new children IDs to existing list of children IDs (can be null)
    private void createHierarchy(In in){
        String[] allLines = in.readAllLines();
        if (allLines != null) {
            for (String line : allLines) {
                String[] elements = line.split(",");
                int parent = Integer.parseInt(elements[0]);

                for (int i = 1; i < elements.length; i++) {
                    if (elements[i] != null) {
                        int kid = Integer.parseInt(elements[i]);
                        if (!adjList.get(parent).childrenIDs.contains(kid)) {
                            adjList.get(parent).childrenIDs.add(kid);
                        }
                    }
                }
            }
        }
    }


    public ArrayList<Integer> getID(String word) {
        return findID.get(word);

    }

    public ArrayList<String> getSyns(int num) {
        return adjList.get(num).parentNames;
    }


    //returns String of a words synonyms and children. Called by wordnet, which is called by hyponym handler
    public ArrayList<String> findChildren(String word) {
        ArrayList<Integer> IDList = getID(word);
        ArrayList<Integer> childrenList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (IDList != null) {
            for (int parentID : IDList) {
                if (DFS(parentID) != null) {
                    childrenList.addAll(DFS(parentID));
                }
            }
            if (childrenList != null) {
                for (int kidID : childrenList) {
                    if (adjList.get(kidID).parentNames != null) {
                        names.addAll(adjList.get(kidID).parentNames);
                    }
                }
            }
            return names;
        } else {
            return null;
        }
    }

    public ArrayList<Integer> DFS(int ID){
        ArrayList<Integer> IDArray = new ArrayList<>();
        boolean beenHere[] = new boolean[index];
        Stack<Integer> stack = new Stack<>();
        if (!beenHere[ID]) {
            stack.push(ID);
            beenHere[ID] = true;
            while (!stack.isEmpty()) {
                int parentID = stack.pop();
                IDArray.add(parentID);
                ArrayList<Integer> kidsIDs = adjList.get(parentID).childrenIDs;
                if (kidsIDs != null) {
                    for (int kidID : kidsIDs) {
                        if (!beenHere[kidID]) {
                            stack.push(kidID);
                            beenHere[kidID] = true;
                        }
                    }
                }

            }

        }
        return IDArray;
    }


}
