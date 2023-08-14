import java.util.Map;
import java.util.HashMap;

public class SymbolTable {
    private Map<String, Integer> symbolMap;
    private int currVarAddr;

    public SymbolTable() {
        currVarAddr = 16;
        symbolMap = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            symbolMap.put("R" + i, i);
        }
        symbolMap.put("SCREEN", 16384);
        symbolMap.put("KBD", 24576);
        symbolMap.put("SP", 0);
        symbolMap.put("LCL", 1);
        symbolMap.put("ARG", 2);
        symbolMap.put("THIS", 3);
        symbolMap.put("THAT", 4);
    }

    public void addEntry(final String varSymbol) {
        if (contains(varSymbol)) {
            throw new IllegalStateException("Variable symbol should not be inserted if it's already in the table");
        }
        symbolMap.put(varSymbol, currVarAddr);
        currVarAddr += 1;
    }

    public void addEntry(final String labelSymbol, final int address) {
        symbolMap.put(labelSymbol, address);
    }

    public boolean contains(final String symbol) {
        return symbolMap.containsKey(symbol);
    }

    public int getAddress(final String symbol) {
        return symbolMap.get(symbol);
    }

}
