package Main;

import Decoder.FromSourceMapDecoder;
import Decoder.SourceMap;
import Decoder.SymbolDecoder;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        String name = "map.txt";

        try {

            FromSourceMapDecoder.decode(new SourceMap(name), "decoded.c");

        } catch (FileNotFoundException e) {
            System.out.println("Can't open file!!");
            e.printStackTrace();
        }
    }
}
