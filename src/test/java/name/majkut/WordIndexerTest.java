package name.majkut;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

public class WordIndexerTest {

    private Map<Character, Set<String>> indexEntry(String givenEntry) {
        return new WordIndexer(new MatcherWordSplitter()).indexSentence(givenEntry);
    }

    @Test
    public void shouldIndexOneLetterString() {
        String givenEntry = "a";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(1, result.size());
        assertTrue(result.containsKey('a'));
        assertThat(result.get('a'), hasSize(1));
        assertThat(result.get('a'), contains("a"));
    }

    @Test
    public void shouldIndexStringWithTwoLetters() {
        String givenEntry = "ac";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(2, result.size());
        assertTrue(result.containsKey('a'));
        assertTrue(result.containsKey('c'));
        assertThat(result.get('a'), hasSize(1));
        assertThat(result.get('a'), contains("ac"));
        assertThat(result.get('c'), hasSize(1));
        assertThat(result.get('c'), contains("ac"));
    }

    @Test
    public void shouldNotCreateNewKeyWhenOneAlreadyExist() {
        String givenEntry = "aa";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(1, result.size());
        assertTrue(result.containsKey('a'));
        assertThat(result.get('a'), hasSize(1));
        assertThat(result.get('a'), contains("aa"));
    }

    @Test
    public void shouldAddTwoDifferentWordsInSentenceInRightOrder() {
        String givenEntry = "ba ab";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(2, result.size());
        assertTrue(result.containsKey('a'));
        assertTrue(result.containsKey('b'));
        assertThat(result.get('a'), hasSize(2));
        assertThat(result.get('a'), contains("ab", "ba"));
        assertThat(result.get('b'), hasSize(2));
        assertThat(result.get('b'), contains("ab", "ba"));
    }

    @Test
    public void shouldReturnEmptyMapWhenNoCharactersInSentence() {
        String givenEntry = "";
        String givenEntrySpaces = " ";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(0, result.size());

        result = indexEntry(givenEntrySpaces);

        assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnEmptyMapWhenNullSentence() {
        String givenEntry = null;

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(0, result.size());
    }

    @Test
    public void shouldProperlyClearIndexer() {
        String givenEntry = "a";
        String entryAfterCleaning = "b";

        WordIndexer indexer = new WordIndexer(new MatcherWordSplitter());
        indexer.indexSentence(givenEntry);
        indexer.clearIndexer();
        Map<Character, Set<String>> afterCleaning = indexer.indexSentence(entryAfterCleaning);

        assertEquals(1, afterCleaning.size());
        assertTrue(afterCleaning.containsKey('b'));
        assertThat(afterCleaning.get('b'), hasSize(1));
        assertThat(afterCleaning.get('b'), contains("b"));
    }

    @Test
    public void shouldLowerCaseResults() {
        String givenEntry = "aB bc CA";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(3, result.size());
        assertTrue(result.containsKey('a'));
        assertThat(result.get('a'), hasSize(2));
        assertThat(result.get('a'), contains("ab", "ca"));
        assertTrue(result.containsKey('b'));
        assertThat(result.get('b'), hasSize(2));
        assertThat(result.get('b'), contains("ab", "bc"));
        assertTrue(result.containsKey('c'));
        assertThat(result.get('c'), hasSize(2));
        assertThat(result.get('c'), contains("bc", "ca"));
    }

    @Test
    public void shouldOmitCommasAndDots() {
        String givenEntry = "aB, bc CA.";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(3, result.size());
        assertTrue(result.containsKey('a'));
        assertThat(result.get('a'), hasSize(2));
        assertThat(result.get('a'), contains("ab", "ca"));
        assertTrue(result.containsKey('b'));
        assertThat(result.get('b'), hasSize(2));
        assertThat(result.get('b'), contains("ab", "bc"));
        assertTrue(result.containsKey('c'));
        assertThat(result.get('c'), hasSize(2));
        assertThat(result.get('c'), contains("bc", "ca"));
    }

    @Test
    public void shouldProperlyProceedLongSentence() {
        String givenEntry = "ala ma kota, kot koduje w Javie Kota";

        Map<Character, Set<String>> result = indexEntry(givenEntry);

        assertEquals(13, result.size());
        assertTrue(result.containsKey('a'));
        assertThat(result.get('a'), hasSize(4));
        assertThat(result.get('a'), contains("ala", "javie", "kota", "ma"));
        assertTrue(result.containsKey('d'));
        assertThat(result.get('d'), hasSize(1));
        assertThat(result.get('d'), contains("koduje"));
        assertTrue(result.containsKey('e'));
        assertThat(result.get('e'), hasSize(2));
        assertThat(result.get('e'), contains("javie", "koduje"));
        assertTrue(result.containsKey('i'));
        assertThat(result.get('i'), hasSize(1));
        assertThat(result.get('i'), contains("javie"));
        assertTrue(result.containsKey('j'));
        assertThat(result.get('j'), hasSize(2));
        assertThat(result.get('j'), contains("javie", "koduje"));
        assertTrue(result.containsKey('k'));
        assertThat(result.get('k'), hasSize(3));
        assertThat(result.get('k'), contains("koduje", "kot", "kota"));
        assertTrue(result.containsKey('l'));
        assertThat(result.get('l'), hasSize(1));
        assertThat(result.get('l'), contains("ala"));
        assertTrue(result.containsKey('m'));
        assertThat(result.get('m'), hasSize(1));
        assertThat(result.get('m'), contains("ma"));
        assertTrue(result.containsKey('o'));
        assertThat(result.get('o'), hasSize(3));
        assertThat(result.get('o'), contains("koduje", "kot", "kota"));
        assertTrue(result.containsKey('t'));
        assertThat(result.get('t'), hasSize(2));
        assertThat(result.get('t'), contains("kot", "kota"));
        assertTrue(result.containsKey('u'));
        assertThat(result.get('u'), hasSize(1));
        assertThat(result.get('u'), contains("koduje"));
        assertTrue(result.containsKey('v'));
        assertThat(result.get('v'), hasSize(1));
        assertThat(result.get('v'), contains("javie"));
        assertTrue(result.containsKey('w'));
        assertThat(result.get('w'), hasSize(1));
        assertThat(result.get('w'), contains("w"));
    }
}