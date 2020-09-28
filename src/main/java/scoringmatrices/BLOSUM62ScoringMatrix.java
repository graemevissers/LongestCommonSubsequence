package scoringmatrices;

import sequencealign.SequenceAlign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Blosum62 Scoring Matrix developed by Hinkoff & Heinkoff to provide substitution penalties
 */
public class BLOSUM62ScoringMatrix implements ScoringMatrix {

    private final String aminoAcids;
    private final double[][] scores;
    private final HashMap<SeqPair<Character>, Double> scoringMatrix;

    public BLOSUM62ScoringMatrix() {
        this.aminoAcids = "ARNDCQEGHILKMFPSTWYVBZX*";
        ScoringMatrixFileParser fileParser = new ScoringMatrixFileParser("./src/main/java/scoringmatrices/data/BLOSUM62");
        // Scores from https://www.ncbi.nlm.nih.gov/Class/FieldGuide/BLOSUM62.txt
        this.scores = fileParser.getFullScoreMatrix();
        this.scoringMatrix = createScoringMatrix();
    }

    public double getScore(char baseChar, char comparedChar) {
        SeqPair<Character> pairQry = new SeqPair<>(baseChar, comparedChar);
        return scoringMatrix.get(pairQry);
    }

    private HashMap<SeqPair<Character>, Double> createScoringMatrix() {
        HashMap<SeqPair<Character>, Double> scoringMatrix = new HashMap<>();
        for (int i = 0; i < aminoAcids.length(); i++) {
            char baseAA = aminoAcids.charAt(i);
            for (int j = 0; j < aminoAcids.length() - i; j++) {
                char comparedAA = aminoAcids.charAt(j + i);
                SeqPair<Character> aaPair = new SeqPair<>(baseAA, comparedAA);
                scoringMatrix.put(aaPair, scores[i][j + i]);
            }
        }
        return scoringMatrix;
    }
}
