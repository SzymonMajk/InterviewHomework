package name.majkut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordIndexer {

    public Map<Character, List<String>> indexSentence(String sentence) {
        List<String> forSentence = new ArrayList<>();
        Map<Character, List<String>> indexed = new HashMap<>();
        forSentence.add(sentence);
        indexed.put(sentence.charAt(0), forSentence);
        return indexed;
    }
}
