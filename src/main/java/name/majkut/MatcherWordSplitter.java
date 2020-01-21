package name.majkut;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWordSplitter implements WordSplitStrategy {

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
