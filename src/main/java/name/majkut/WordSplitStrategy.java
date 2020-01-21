package name.majkut;

import java.util.List;

public interface WordSplitStrategy {

    List<String> splitSentence(String sentence);
}
