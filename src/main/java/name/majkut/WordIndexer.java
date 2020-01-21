package name.majkut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordIndexer {

    public Map<Character, List<String>> indexSentence(String sentence) {
        Map<Character, List<String>> indexed = new HashMap<>();

        for (Character indexKey : sentence.toCharArray()) {
            List<String> indexValue = new ArrayList<>();
            indexValue.add(sentence);
            indexed.put(indexKey, indexValue);
        }

        return indexed;
    }
}
