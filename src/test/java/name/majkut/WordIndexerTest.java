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
}