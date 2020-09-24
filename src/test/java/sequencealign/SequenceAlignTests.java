package sequencealign;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SequenceAlignTests {

    /**
     * Tests using different short sequences
     * baseSeq = "AAAAA", comparedSeq = "AAAAB"
     */
    private String baseSeq_short = "AAAAA";
    private String comparedSeq_short = "AAAAB";
    private SequenceAlign sequenceAlign_diffSeq_long = new SequenceAlign(baseSeq_short, comparedSeq_short, new DijkstraLCSFinder());

    @Test
    public void baseSequence_returnsCorrectSequence() {
        String baseSeq = sequenceAlign_diffSeq_long.baseSequence();
        assertEquals(baseSeq_short, baseSeq);
    }

    @Test
    public void comparedSequence_returnsCorrectSequence() {
        String comparedSeq = sequenceAlign_diffSeq_long.comparedSequence();
        assertEquals(comparedSeq_short, comparedSeq);
    }

    @Test
    public void getMatches_withDifferentSeq_returnsCorrectMatrix() {
        double[] col = new double[] {1, 1, 1, 1, 0};
        double[][] matchesExpected = new double[][] {col, col, col, col, col};
        double[][] matches = sequenceAlign_diffSeq_long.computeMatches();
        assertArrayEquals(matchesExpected, matches);
    }

    @Test
    public void getLCS_withDifferentSeq_returnsCorrectArray() {
        String[] output = new String[] {"AAAAA", "AAAAB"};
        String[] lcs = sequenceAlign_diffSeq_long.getLCS();
        assertArrayEquals(output, lcs);
    }

    /**
     * Tests using the same short sequence
     * baseSeq = "AAAAA", comparedSeq = "AAAAA"
     */
    private SequenceAlign sequenceAlign_sameSeq_short = new SequenceAlign(baseSeq_short, baseSeq_short, new DijkstraLCSFinder());

    @Test
    public void getMatches_withSameSeq_returnsCorrectMatrix() {
        double [] col_allOnes = new double[] {1, 1, 1, 1, 1};
        double [][] matches_allOnes = new double[][] {col_allOnes, col_allOnes, col_allOnes, col_allOnes, col_allOnes};
        double[][] matches = sequenceAlign_sameSeq_short.computeMatches();
        assertArrayEquals(matches_allOnes, matches);
    }

    @Test
    public void getLCS_withSameSeq_returnsCorrectArray() {
        String[] lcsExpected = new String[] {"AAAAA", "AAAAA"};
        String[] lcs = sequenceAlign_sameSeq_short.getLCS();
        assertArrayEquals(lcsExpected, lcs);
    }
}