package name.majkut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for indexing provided sentence in the way that every character
 * found in sentence points to all world containing it.
 */
public class WordIndexer {

    private Logger logger = Logger.getLogger("indexer");
    private WordSplitStrategy wordSplitter;
    private Map<Character, Set<String>> indexed = new HashMap<>();

    public WordIndexer(WordSplitStrategy splitter) {
        wordSplitter = splitter;
    }

    /**
     * Should be called, when we want further indexing to start from scratch.
     */
    public void clearIndexer() {
        indexed = new HashMap<>();
    }

    /**
     * Returns indexed sentence, using WordSplitStrategy passed in constructor. Before reusing, clearIndexer()
     * method should be run, to ensure no previous data left.
     * @param sentence String with sentence to index.
     * @return result structure is map, where key is each found character and value is set of words
     *      with this character.
     */
    public Map<Character, Set<String>> indexSentence(String sentence) {
        if (sentence != null) {
            wordSplitter.splitSentence(sentence).forEach(w -> indexWord(w.toLowerCase()));
        } else {
            logger.log(Level.WARNING, "Null sentence found!");
        }

        return indexed;
    }

    /**
     * Generates output, based on content of passed file.
     * @param args file with sentence should be the first argument.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Provide file with sentence as first argument.");
            System.exit(0);
        }

        String sentence = "";
        try {
            sentence = Files.readAllLines(Paths.get(args[0])).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WordSplitStrategy splitStrategy = new MatcherWordSplitter();
        Map<Character, Set<String>> result = new WordIndexer(splitStrategy).indexSentence(sentence);
        StringBuilder answer = new StringBuilder();

        for (Map.Entry<Character, Set<String>> entry : result.entrySet()) {
            answer.append(entry.getKey());
            answer.append(": ");
            answer.append(entry.getValue()
                            .toString()
                            .replace("[", "")
                            .replace("]", "")
                            .trim());
            answer.append("\r\n");
        }

        System.out.print(answer.toString());
    }

    private void indexWord(String word) {
        for (Character indexKey : word.toCharArray()) {
            Set<String> indexValue;

            if (indexed.containsKey(indexKey)) {
                indexValue = indexed.get(indexKey);
                indexValue.add(word);
            } else {
                indexValue = new TreeSet<>();
                indexValue.add(word);
                indexed.put(indexKey, indexValue);
            }
        }
    }
}
