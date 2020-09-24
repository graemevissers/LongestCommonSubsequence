package scoringmatrices;

import java.util.Set;
import java.util.TreeSet;

public class SeqPair<Character> {

    private final Set<java.lang.Character> pair;

    public SeqPair(char baseChar, char comparedChar) {
        Set<java.lang.Character> pair = new TreeSet<>();
        pair.add(baseChar);
        pair.add(comparedChar);
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
        return seqPair.pair.equals(pair);
    }

    @Override
    public int hashCode() {
        return pair.hashCode();
    }
}
