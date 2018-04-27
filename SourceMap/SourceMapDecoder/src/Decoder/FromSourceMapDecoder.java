package Decoder;

import java.io.*;

public class FromSourceMapDecoder {

    private static final int NAME_INDEX = 4;
    private static final int COLUMN_INDEX = 0;

    public static void decode(SourceMap map, String fileOutName) {

        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(map.getFileOutName()));
            RandomAccessFile writer = new RandomAccessFile(fileOutName, "rw");

            int namePointer = 0;
            int symbolsWritten = 0;

            for (int i = 0; i < map.getLinesCount(); i++) {
                String[] line = map.getLineMapping(i);

                for (int j = 0; j < line.length; j++) {
                    int[] decodedNums = SymbolDecoder.decode(line[j]);

                    namePointer += decodedNums[NAME_INDEX];
                    symbolsWritten += decodedNums[COLUMN_INDEX];

                    if (writer.getFilePointer() != symbolsWritten){
                        addSpaces(writer, symbolsWritten - writer.getFilePointer() + 1);
                    }

                    writer.write(map.getNameByIndex(namePointer).getBytes());

                    if (j != line.length - 1) {
                        writer.writeByte(' ');
                    }
                }

                writer.writeByte('\n');
                symbolsWritten++;

            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Can't write to file!");
            e.printStackTrace();
        }

    }

    private static void addSpaces(RandomAccessFile writer, long num) throws IOException {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < num; i++) {
            builder.append(' ');
        }

        writer.write(builder.toString().getBytes());
    }

}
