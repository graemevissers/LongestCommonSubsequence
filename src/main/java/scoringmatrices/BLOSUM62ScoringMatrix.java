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
        this.aminoAcids = "ARNDCQEGHILKMFPSTWYV";
        ScoringMatrixFileParser fileParser = new ScoringMatrixFileParser("./src/main/java/scoringmatrices/data/BLOSUM62");
        fileParser.getFullScoreMatrix();
        // Scores from https://www.ncbi.nlm.nih.gov/Class/FieldGuide/BLOSUM62.txt
        this.scores = new double[][] {
                      //  A   R   N   D   C   Q   E   G   H   I   L   K   M   F   P   S   T   W   Y   V
            new double[] {4, -1, -2, -2,  0, -1, -1,  0, -2, -1, -1, -1, -1, -2, -1,  1,  0, -3, -2,  0},
                new double[] {5,  0, -2, -3,  1,  0, -2,  0, -3, -2,  2, -1, -3, -2, -1, -1, -3, -2, -3},
                    new double[] {6,  1, -3,  0,  0,  0,  1, -3, -3,  0, -2, -3, -2,  1,  0, -4, -2, -3},
                        new double[] {6, -3,  0,  2, -1, -1, -3, -4, -1, -3, -3, -1,  0, -1, -4, -3, -3},
                            new double[] {9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1},
                                new double[] {5,  2, -2,  0, -3, -2,  1,  0, -3, -1,  0, -1, -2, -1, -2},
                                    new double[] {5, -2,  0, -3, -3,  1, -2, -3, -1,  0, -1, -3, -2, -2},
                                        new double[] {6, -2, -4, -4, -2, -3, -3, -2,  0, -2, -2, -3, -3},
                                            new double[] {8, -3, -3, -1, -2, -1, -2, -1, -2, -2,  2, -3},
                                                new double[] {4,  2, -3,  1,  0, -3, -2, -1, -3, -1,  3},
                                                    new double[] {4, -2,  2,  0, -3, -2, -1, -2, -1,  1},
                                                        new double[] {5, -1, -3, -1,  0, -1, -3, -2, -2},
                                                            new double[] {5,  0, -2, -1, -1, -1, -1,  1},
                                                                new double[] {6, -4, -2, -2,  1,  3, -1},
                                                                    new double[] {7, -1, -1, -4, -3, -2},
                                                                        new double[] {4,  1, -3, -2, -2},
                                                                            new double[] { 5, -2, -2, 0},
                                                                                new double[] {11, 2, -3},
                                                                                    new double[] {7, -1},
                                                                                        new double[] {4}
        };
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
                scoringMatrix.put(aaPair, scores[i][j]);
            }
        }
        return scoringMatrix;
    }
}
