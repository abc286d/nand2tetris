import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser implements Closeable {

    private Scanner scanner;
    private boolean hasAdvanced;
    private String currLine;
    private String stagingLine;
    private int currLineNum;

    public Parser(final String filePath) throws FileNotFoundException {
        scanner = new Scanner(new File(filePath));
        hasAdvanced = true;
        currLine = null;
        stagingLine = null;
        currLineNum = -1;
    }

    public boolean hasMoreLines() {
        if (hasAdvanced == true) {
            while (scanner.hasNextLine()) {
                final String rawLine = scanner.nextLine();
                final String cleanLine = removeAdditionalChars(rawLine);
                if (!cleanLine.isEmpty()) {
                    stagingLine = cleanLine;
                    hasAdvanced = false;
                    return true;
                }
            }
            stagingLine = null;
            return false;
        } else {
            return stagingLine != null;
        }
    }

    public void advance() {
        if (hasAdvanced) {
            if (!hasMoreLines()) {
                throw new IllegalAccessError("Reaching EOF. No more lines available.");
            }
        }

        hasAdvanced = true;
        currLine = stagingLine;
        if (getInstructionType() != InstructionType.L_INSTRUCTION)
            currLineNum += 1;
    }

    public InstructionType getInstructionType() {
        if (currLine.contains("(")) {
            return InstructionType.L_INSTRUCTION;
        } else if (currLine.contains("@")) {
            return InstructionType.A_INSTRUCTION;
        } else {
            return InstructionType.C_INSTRUCTION;
        }
    }

    public String getSymbol() {
        InstructionType instrType = getInstructionType();
        if (instrType == InstructionType.L_INSTRUCTION) {
            return currLine.substring(1, currLine.length() - 1);
        } else if (instrType == InstructionType.A_INSTRUCTION) {
            return currLine.substring(1);
        } else {
            throw new IllegalStateException("Only L and A instruction contains symbol");
        }
    }

    public String getDestField() {
        if (getInstructionType() != InstructionType.C_INSTRUCTION) {
            throw new IllegalStateException("Only C instruction contains dest");
        }
        final int idx = currLine.indexOf("=");
        if (idx != -1) {
            return currLine.substring(0, idx);
        } else {
            return "null";
        }
    }

    public String getCompField() {
        if (getInstructionType() != InstructionType.C_INSTRUCTION) {
            throw new IllegalStateException("Only C instruction contains comp");
        }
        final int idx1 = currLine.indexOf("=");
        final int idx2 = currLine.indexOf(";");
        if (idx1 != -1 && idx2 != -1) {
            return currLine.substring(idx1 + 1, idx2);
        } else if (idx1 == -1 && idx2 != -1) {
            return currLine.substring(0, idx2);
        } else if (idx1 != -1 && idx2 == -1) {
            return currLine.substring(idx1 + 1);
        } else {
            return currLine;
        }
    }

    public String getJumpField() {
        if (getInstructionType() != InstructionType.C_INSTRUCTION) {
            throw new IllegalStateException("Only C instruction contains jump");
        }
        final int idx = currLine.indexOf(";");
        if (idx != -1) {
            return currLine.substring(idx + 1);
        } else {
            return "null";
        }
    }

    public int getLineNum() {
        return currLineNum;
    }

    public void close() {
        scanner.close();
    }

    private String removeAdditionalChars(final String rawLine) {
        return rawLine.replace(" ", "")
                .replace("\t", "")
                .replaceFirst("//.*", "");
    }
}
