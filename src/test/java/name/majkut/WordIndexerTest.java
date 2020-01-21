package name.majkut;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

public class WordIndexerTest {

    @Test
    public void shouldIndexOneLetterString() {
        String givenEntry = "a";
        WordIndexer indexer = new WordIndexer();

        Map<Character, List<String>> result = indexer.indexSentence(givenEntry);

        assertEquals(1, result.size());
        assertTrue(result.containsKey('a'));
        assertThat(result.get('a'), hasSize(1));
        assertThat(result.get('a'), contains("a"));
    }
}