package scoringmatrices;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ScoringMatrixFileParser {

    private File file;

    public ScoringMatrixFileParser(String fileName) {
        this.file = new File(fileName);
    }

    public double[][] getFullScoreMatrix() {
        String allowedChars = getAllowedChars();
        double[][] scoreMatrix = new double[allowedChars.length()][allowedChars.length()];
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < getFirstLine(); i++) {
                sc.nextLine();
            }
            for (int i = 0; i < scoreMatrix.length; i++) {
                for (int j = 0; j < scoreMatrix.length; j++) {
                    if (sc.hasNext()) {
                        String charOrDouble = sc.next();
                        if (isDouble(charOrDouble)) {
                            Double score = Double.parseDouble(charOrDouble);
                            scoreMatrix[i][j] = score;
                        } else {
                            j--;
                        }
                    }
                }
            }
        } catch (IOException err) {
            err.printStackTrace();
        }

        for (int i = 0; i < scoreMatrix.length; i++) {
            for (int j = 0; j < scoreMatrix[i].length; j++) {
                System.out.print(scoreMatrix[i][j] + " ");
            }
            System.out.println();
        }
        return scoreMatrix;
    }

    /**
     * Helper method to use letters in matrix to get usable chars
     * @return All allowed chars in a String
     */
    private String getAllowedChars() {
        String allowedChars = "";
        int firstLineNum = getFirstLine();
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < firstLineNum - 1; i++) {
                sc.nextLine();
            }
            allowedChars = sc.nextLine().replaceAll("\\s", "");
        } catch (IOException err) {
            err.printStackTrace();
        }
        return allowedChars;
    }

    /**
     * Helper method to return the line at which the matrix begins in the text file.
     * Only matrices that begin with "([a-zA-Z]) ([ -[0-9]])([0-9])" will be read
     * properly.
     * @return line number in text file where the matrix begins, as an int
     */
    private int getFirstLine() {
        int lineNum = 0;
        try {
            Scanner sc = new Scanner(file);
            if (!sc.hasNext()) {
                throw new IllegalArgumentException("Cannot pass empty file.");
            }
            String line = sc.nextLine();
            while (!line.substring(0, 4).matches("([a-zA-Z]) ([ -[0-9]])([0-9])") && sc.hasNext()) {
                lineNum++;
                line = sc.nextLine();
            }
        } catch (IOException err){
            err.printStackTrace();
        }
        return lineNum;
    }

    /**
     * Helper method to determine if String can be parsed as a double when scanning a file.
     * @param charOrDouble String to determine if can be parsed
     * @return true if String can be parsed as Double
     */
    private boolean isDouble(String charOrDouble) {
        if (charOrDouble == null) {
            return false;
        }
        try {
            Double.parseDouble(charOrDouble);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}
