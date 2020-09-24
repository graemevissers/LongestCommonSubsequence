package scoringmatrices;

public interface ScoringMatrix {
    /**
     * Uses characters from two sequences to retrieve their alignment scores.
     * @param baseChar - Char from base string
     * @param comparedChar - Char from compared string
     * @return Alignment score of that scoring matrix
     */
    double getScore(char baseChar, char comparedChar);
}
