package Exceptions;

public class IncorrectSymbolsException extends Exception {

    private String incorrectSymbols;

    public IncorrectSymbolsException(char[] incorrectSymbols) {
        this.incorrectSymbols = symbolsToString(incorrectSymbols);
    }

    @Override
    public String getMessage() {
        return "Символы - { " + incorrectSymbols + "} - не корректны" ;
    }

    private String symbolsToString(char[] incorrectSymbols) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch :incorrectSymbols) {
            stringBuilder.append(ch);
            stringBuilder.append(' ');
        }

        return stringBuilder.toString();
    }
}
