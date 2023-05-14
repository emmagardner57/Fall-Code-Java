package ngordnet.proj2b_testing;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.main.HyponymGraph;
import ngordnet.main.WordNet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GraphTests {
    public static final String WORDS_FILE = "data/ngrams/top_14377_words.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    public static HyponymGraph graph;
    public static WordNet wn;


    @Test
    public void test1() {
        wn = new WordNet(SYNSET_FILE, HYPONYM_FILE);
        graph = wn.graph;
       NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SYNSET_FILE, HYPONYM_FILE);
        List<String> words = List.of("government");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 7);
        String actual = studentHandler.handle(nq);
        String expected = "[administration, authorities, court, government, legislation, passage, state]";
        assertEquals(expected, actual);
    }

}
