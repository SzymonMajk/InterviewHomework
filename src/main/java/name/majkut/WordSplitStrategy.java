package name.majkut;

import java.util.List;

/**
 * Manage how worlds from sentence String should split into array of words.
 */
public interface WordSplitStrategy {

    /**
     * Implementation should split String with words into array of words somehow.
     * @param sentence String with words.
     * @return List with words.
     */
    List<String> splitSentence(String sentence);
}
