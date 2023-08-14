import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class MyHackAssembler {
    public static void main(String[] args) throws FileNotFoundException {
        final CodeGenerator codeGenerator = new CodeGenerator();
        final SymbolTable symbolTable = new SymbolTable();

        final String outputFileName = getOutputFileName(args[0]);
        File file = new File(outputFileName);

        try (final Parser parser = new Parser(args[0])) {
            while (parser.hasMoreLines()) {
                parser.advance();
                if (parser.getInstructionType() == InstructionType.L_INSTRUCTION) {
                    final String symbol = parser.getSymbol();
                    if (!symbolTable.contains(symbol)) {
                        symbolTable.addEntry(symbol, parser.getLineNum() + 1);
                    }
                }
            }
        }

        try (
                final Parser parser = new Parser(args[0]);
                final PrintWriter writer = new PrintWriter(file);) {
            while (parser.hasMoreLines()) {
                parser.advance();
                switch (parser.getInstructionType()) {
                    case L_INSTRUCTION:
                        break;
                    case A_INSTRUCTION:
                        final String symbol = parser.getSymbol();
                        if (symbol.matches("^[0-9]+$")) {
                            writer.println(convertDecToBin(Integer.parseInt(symbol)));
                        } else {
                            if (!symbolTable.contains(symbol)) {
                                symbolTable.addEntry(symbol);
                            }
                            writer.println(convertDecToBin(symbolTable.getAddress(symbol)));
                        }
                        break;
                    case C_INSTRUCTION:
                        final String combinedCode = "111"
                                + codeGenerator.generateCompCode(parser.getCompField())
                                + codeGenerator.generateDestCode(parser.getDestField())
                                + codeGenerator.generateJumpCode(parser.getJumpField());
                        writer.println(combinedCode);
                }
            }
        }
    }

    private static final String convertDecToBin(int decNum) {
        return String.format("%16s", Integer.toBinaryString(decNum)).replace(" ", "0");
    }

    private static final String getOutputFileName(final String inputFilePath) {
        final String inputFileName = Paths.get(inputFilePath).getFileName().toString();
        if (inputFileName.contains(".")) {
            return inputFileName.split("\\.")[0] + ".hack";
        } else {
            return inputFileName + ".hack";
        }
    }
}