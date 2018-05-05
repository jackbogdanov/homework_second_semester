package Main;

import Decoder.FromSourceMapDecoder;
import Decoder.SourceMap;
import Decoder.SymbolDecoder;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        String name = "/home/jack/HW/SourceMap/map.txt";

//        int[] mas = SymbolDecoder.decode("KAUCF");
//
//
//        for (int i = 0; i < mas.length; i++) {
//            System.out.print(mas[i] + " ");
//        }

        try {
            SourceMap map = new SourceMap(name);
            FromSourceMapDecoder.decodeProgram(map, "decoded.c");
            FromSourceMapDecoder.decodeSourceMap(map, "decoded.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Can't open file!!");
            e.printStackTrace();
        }
    }
}
