package name.majkut;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WordSplitStrategy based on Matcher and Patterns from java.util package.
 */
public class MatcherWordSplitter implements WordSplitStrategy {

    /**
     * Split String with words into array of words with usage of regex finding all words.
     * @param sentence String with words.
     * @return List with words.
     */
    public List<String> splitSentence(String sentence) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(sentence);
        List<String> words = new ArrayList<>();

        while (matcher.find()) {
            words.add(matcher.group());
        }

        return words;
    }
}
