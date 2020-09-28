package scoringmatrices;

import java.util.Objects;
/**
 * Hashable pair of chars compatible with MatrixMap
 * @param <Character>
 */
public class SeqPair<Character> {

    private final char[] pair;

    public SeqPair(char baseChar, char comparedChar) {
        char[] pair = new char[2];
        pair[0] = baseChar;
        pair[1] = comparedChar;
        this.pair = pair;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeqPair<?> seqPair = (SeqPair<?>) o;
        return ((seqPair.pair[0] == pair[0] && seqPair.pair[1] == pair[1]) ||
                (seqPair.pair[0] == pair[1] && seqPair.pair[1] == pair[0]));
    }

    @Override
    public int hashCode() {
        return Objects.hash(pair[0]) + Objects.hash(pair[1]);
    }
}
