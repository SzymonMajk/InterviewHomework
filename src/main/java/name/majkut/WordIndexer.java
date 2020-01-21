package name.majkut;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordIndexer {

    private Logger logger = Logger.getLogger("indexer");

    public Map<Character, Set<String>> indexSentence(String sentence) {
        Map<Character, Set<String>> indexed = new HashMap<>();
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            String word = matcher.group();
            logger.log(Level.INFO, "Word found: " + word);
            indexWord(word, indexed);
        }

        return indexed;
    }

    private void indexWord(String word, Map<Character, Set<String>> indexed) {
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
