package scoringmatrices;

import java.util.HashMap;

/**
 * Blosum62 Scoring Matrix developed by Hinkoff & Heinkoff to provide substitution penalties
 */
public class ScoringMatrix {

    private final String allowedChars;
    private final double[][] scores;
    private final HashMap<SeqPair<Character>, Double> scoreMap;

    public ScoringMatrix(String file) {
        ScoringMatrixFileParser fileParser = new ScoringMatrixFileParser("./src/main/java/scoringmatrices/data/" + file);
        // Scores from https://www.ncbi.nlm.nih.gov/Class/FieldGuide/BLOSUM62.txt
        this.allowedChars = fileParser.allowedChars();
        this.scores = fileParser.getFullScoringMatrix();
        this.scoreMap = createScoreMap();
    }

    public double getScore(char baseChar, char comparedChar) {
        SeqPair<Character> pairQry = new SeqPair<>(baseChar, comparedChar);
        return scoreMap.get(pairQry);
    }

    private HashMap<SeqPair<Character>, Double> createScoreMap() {
        HashMap<SeqPair<Character>, Double> scoringMatrix = new HashMap<>();
        for (int i = 0; i < allowedChars.length(); i++) {
            char baseAA = allowedChars.charAt(i);
            for (int j = 0; j < allowedChars.length() - i; j++) {
                char comparedAA = allowedChars.charAt(j + i);
                SeqPair<Character> aaPair = new SeqPair<>(baseAA, comparedAA);
                scoringMatrix.put(aaPair, scores[i][j + i]);
            }
        }
        return scoringMatrix;
    }
}
