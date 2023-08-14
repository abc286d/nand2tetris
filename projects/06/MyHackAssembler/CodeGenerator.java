import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

public class CodeGenerator {
    private static final Map<String, String> COMP_MAP;
    private static final Map<String, String> DEST_MAP;
    private static final Map<String, String> JUMP_MAP;

    static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("0", "0101010");
        tempMap.put("1", "0111111");
        tempMap.put("-1", "0111010");
        tempMap.put("D", "0001100");
        tempMap.put("A", "0110000");
        tempMap.put("!D", "0001101");
        tempMap.put("!A", "0110001");
        tempMap.put("-D", "0001111");
        tempMap.put("D+1", "0011111");
        tempMap.put("A+1", "0110111");
        tempMap.put("D-1", "0001110");
        tempMap.put("A-1", "0110010");
        tempMap.put("D+A", "0000010");
        tempMap.put("D-A", "0010011");
        tempMap.put("A-D", "0000111");
        tempMap.put("D&A", "0000000");
        tempMap.put("D|A", "0010101");
        tempMap.put("M", "1110000");
        tempMap.put("!M", "1110001");
        tempMap.put("-M", "1110011");
        tempMap.put("M+1", "1110111");
        tempMap.put("M-1", "1110010");
        tempMap.put("D+M", "1000010");
        tempMap.put("D-M", "1010011");
        tempMap.put("M-D", "1000111");
        tempMap.put("D&M", "1000000");
        tempMap.put("D|M", "1010101");
        COMP_MAP = Collections.unmodifiableMap(tempMap);

        tempMap = new HashMap<>();
        tempMap.put("null", "000");
        tempMap.put("M", "001");
        tempMap.put("D", "010");
        tempMap.put("MD", "011");
        tempMap.put("A", "100");
        tempMap.put("AM", "101");
        tempMap.put("AD", "110");
        tempMap.put("AMD", "111");
        DEST_MAP = Collections.unmodifiableMap(tempMap);

        tempMap = new HashMap<>();
        tempMap.put("null", "000");
        tempMap.put("JGT", "001");
        tempMap.put("JEQ", "010");
        tempMap.put("JGE", "011");
        tempMap.put("JLT", "100");
        tempMap.put("JNE", "101");
        tempMap.put("JLE", "110");
        tempMap.put("JMP", "111");
        JUMP_MAP = Collections.unmodifiableMap(tempMap);
    }

    public CodeGenerator() {
    }

    public String generateCompCode(final String comp) {
        return COMP_MAP.get(comp);
    }

    public String generateDestCode(final String dest) {
        return DEST_MAP.get(dest);
    }

    public String generateJumpCode(final String jump) {
        return JUMP_MAP.get(jump);
    }
}
