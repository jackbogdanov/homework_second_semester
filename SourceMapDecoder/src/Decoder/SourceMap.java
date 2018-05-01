package Decoder;

import Exceptions.ParseErrorException;

import java.io.*;
import java.util.HashMap;

public class SourceMap {

    private int version;
    private String fileOutName;

    private String[] sources;
    private String[] names;
    private HashMap<Integer, String[]> mappings;
    private int linesCount;


    public SourceMap(String fileName) throws FileNotFoundException {
        try {
            readSourceMap(fileName);
        } catch (ParseErrorException e) {
            e.printStackTrace();
        }
    }

    private void readSourceMap(String fileName) throws FileNotFoundException, ParseErrorException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String filter = "[\":,\\[\\]]";
        try {
            reader.readLine();
            initVersion(reader.readLine(), filter);
            initfileOutName(reader.readLine(), filter);
            initSources(reader.readLine(), filter);
            initNames(reader.readLine());
            initMappings(reader.readLine());

            printMap();
            reader.close();
        } catch (IOException e) {
            System.out.println("Reading error!");
            e.printStackTrace();
        }
    }

    private void initVersion(String line, String filter) throws ParseErrorException {

        line = line.replaceAll(filter, "").trim();
        String[] buf = line.split(" ");

        if (buf.length == 2) {
            version = Integer.parseInt(buf[1]);
        } else {
            throw new ParseErrorException();
        }

    }

    private void initfileOutName(String line, String filter) throws ParseErrorException {

        line = line.replaceAll(filter, "").trim();
        String[] buf = line.split(" ");

        if (buf.length == 2) {
            fileOutName = buf[1];
        } else {
            throw new ParseErrorException();
        }
    }

    private void initSources(String line, String filter)  {
        line = line.replaceAll(filter, "");
        sources = line.replaceAll("sources", "").trim().split(" ");
    }

    private void initNames(String line) {
        names = line.replaceAll("\"names\":", "").trim().split("\",");
        int len  = names.length;


        for (int i = 0; i < len; i++) {
            names[i] = names[i].substring(2);
        }

        names[len - 1] = names[len - 1].substring(names[len - 1].length() - 4, names[len - 1].length() - 3);
    }

    private void initMappings(String line) {
        line = line.replaceAll("\"mappings\":|\"", "").trim();
        mappings = new HashMap<>();
        int i = 0;

        for (String l: line.split(";")) {
            mappings.put(i, l.split(","));
            linesCount++;
            i++;
        }
    }

    public String getNameByIndex(int index) {

        if (index >= names.length) {
            System.out.println("Index is too large!");
            throw new ArrayIndexOutOfBoundsException();
        }

        return names[index];
    }

    public String getOneMapping(int lineIndex, int index) {
        if (linesCount <= lineIndex) {
            System.out.println("LineIndex is too large!!");
            throw new ArrayIndexOutOfBoundsException();
        }

        String[] line = mappings.get(lineIndex);

        if (line.length <= index) {
            System.out.println("Index is too large!!");
            throw new ArrayIndexOutOfBoundsException();
        }

        return line[index];
    }

    public String[] getLineMapping(int lineIndex) {
        if (linesCount <= lineIndex) {
            System.out.println("LineIndex is too large!!");
            throw new ArrayIndexOutOfBoundsException();
        }

        return  mappings.get(lineIndex);
    }

    public int getLinesCount(){
        return linesCount;
    }

    public String getSource(int i) {
        return sources[i];
    }

    public void printMap() {
        System.out.println("version:" + version);
        System.out.println("file out name: " + fileOutName);
        System.out.print("sources used: ");
        for (String s: sources) {
            System.out.print(s + " ");
        }
        System.out.println();

        System.out.print("names: ");
        for (String s: names) {
            System.out.print(s + " ");
        }
        System.out.println();

        String[] buf;
        System.out.println("mappings:");
        for (HashMap.Entry<Integer, String[]> entry: mappings.entrySet()) {
            System.out.print("    " + entry.getKey() + ": ");
            buf = entry.getValue();
            for (String aBuf : buf) {
                System.out.print(aBuf + " ");
            }
            System.out.println();
        }


    }

    public String getFileOutName() {
        return fileOutName;
    }
}
