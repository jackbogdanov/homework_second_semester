package Decoder;

public class SymbolDecoder {

    private static String decodeString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static int base64decode(char symbol) {
        return decodeString.lastIndexOf(symbol);
    }

    public static int[] decode(String string) {
        String binCode;
        String buf;
        int i = 0;
        int contBit;
        int signBit;
        int decoderPointer = 0;
        int[] decodedSymbols = new int[5];

        char[] symbols = string.toCharArray();

        while (i < symbols.length) {
            binCode = Integer.toBinaryString(base64decode(symbols[i]));
            if (binCode.length() == 1) {
                decodedSymbols[decoderPointer] = 0;
                decoderPointer++;
            } else {

                signBit = Character.getNumericValue(binCode.charAt(binCode.length() - 1));
                if ( signBit == 1) {
                    signBit = -1;
                } else {
                    signBit = 1;
                }

                if (binCode.length() == 6) {
                    contBit = 1;
                    binCode = binCode.substring(1, binCode.length() - 1);

                    while (contBit == 1) {
                        i++;
                        buf = Integer.toBinaryString(base64decode(symbols[i]));
                        if (buf.length() < 6) {
                            contBit = 0;
                        }
                        binCode = buf + binCode;
                    }

                } else {
                    binCode = binCode.substring(0, binCode.length() - 1);
                }

                decodedSymbols[decoderPointer] = signBit * Integer.parseInt(binCode, 2);
                decoderPointer++;
            }


            i++;
        }


        return decodedSymbols;
    }

}
