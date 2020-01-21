package name.majkut;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordIndexer {

    private Logger logger = Logger.getLogger("indexer");
    private WordSplitStrategy wordSplitter;
    private Map<Character, Set<String>> indexed = new HashMap<>();

    public WordIndexer(MatcherWordSplitter splitter) {
        wordSplitter = splitter;
    }

    public void clearIndexer() {
        indexed = new HashMap<>();
    }

    public Map<Character, Set<String>> indexSentence(String sentence) {
        if (sentence != null) {
            wordSplitter.splitSentence(sentence).forEach(w -> {
                logger.log(Level.INFO, "Word found: " + w);
                indexWord(w);
            });
        } else {
            logger.log(Level.WARNING, "Null sentence entered!");
        }

        return indexed;
    }

    private void indexWord(String word) {
        for (Character indexKey : word.toCharArray()) {
            Set<String> indexValue;

            if (indexed.containsKey(indexKey)) {
                indexValue = indexed.get(indexKey);
            } else {
                indexValue = new TreeSet<>();
            }

            indexValue.add(word);
            indexed.put(indexKey, indexValue);
        }
    }
}
