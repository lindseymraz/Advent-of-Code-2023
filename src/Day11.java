import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
    static String galaxyRegex = "\\#";
    private static final String realPath = "Day11.txt";
    private static final String testPath1 = "src/tests/Day11Part1Test.txt";
    private static final String testPath2 = "src/tests/Day11Part1Test2.txt";
    private static String Path = realPath;

    private static int charsPerLine = 0;

    static long day11part1() throws IOException {
        long distance = 0;
        int[][] galaxyArray = makeGalaxyArray();
        for(int i = 0; i < galaxyArray.length - 1; i++) {
            for(int j = i + 1; j < galaxyArray.length; j++) {
                distance += distance(galaxyArray[i][0], galaxyArray[i][1], galaxyArray[j][0], galaxyArray[j][1]);
            }
        }
        return distance;
    }

    static int charsPerLine() throws IOException {
        Scanner scanner = new Scanner(Paths.get(Path));
        String line = scanner.nextLine();
        scanner.close();
        return line.length();
    }

    static int linesPerFile() throws IOException {
        Scanner scanner = new Scanner(Paths.get(Path));
        int lines = 0;
        while(scanner.hasNextLine()) {
            scanner.nextLine();
            lines++;
        }
        scanner.close();
        return lines;
    }

    static boolean[] makeColumnHasGalaxy() throws IOException {
        Scanner scanner = new Scanner(Paths.get(Path));
        setCharsPerLine();
        boolean[] columnHasGalaxy = new boolean[charsPerLine];
        String line;
        Pattern galaxy = Pattern.compile(galaxyRegex);
        Matcher matcher;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            matcher = galaxy.matcher(line);
            while(matcher.find()) {
                columnHasGalaxy[matcher.start()] = true;
            }
        }
        scanner.close();
        return columnHasGalaxy;
    }

    static int[] makeXOffsets() throws IOException {
        boolean[] columnHasGalaxy = makeColumnHasGalaxy();
        int[] xOffsets = new int[charsPerLine];
        for(int i = 0; i < charsPerLine; i++) {
            if(!columnHasGalaxy[i]) {
                for(int j = i + 1; j < charsPerLine; j++) {
                    xOffsets[j] += 1;
                }
            }
        }
        return xOffsets;
    }

    static int[] makeYOffsets() throws IOException {
        int linesPerFile = linesPerFile();
        int[] yOffsets = new int[linesPerFile];
        Scanner scanner = new Scanner(Paths.get(Path));
        String emptyLineRegex = "\\.{" + charsPerLine + "}";
        String line;
        Pattern galaxy = Pattern.compile(emptyLineRegex);
        Matcher matcher;
        int i = 0;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            matcher = galaxy.matcher(line);
            if(line.matches(emptyLineRegex)) {
                for(int j = i + 1; j < linesPerFile; j++) {
                    yOffsets[j] += 1;
                }
            }
            i++;
        }
        scanner.close();
        return yOffsets;
    }

    static int countGalaxies() throws IOException {
        Scanner scanner = new Scanner(Paths.get(Path));
        int galaxies = 0;
        String line;
        Pattern galaxy = Pattern.compile(galaxyRegex);
        Matcher matcher;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            matcher = galaxy.matcher(line);
            while(matcher.find()) {
                galaxies++;
            }
        }
        scanner.close();
        return galaxies;
    }

    static int[][] makeGalaxyArray() throws IOException {
        int[] xOffsets = makeXOffsets();
        int[] yOffsets = makeYOffsets();
        int[][] galaxyArray = new int[countGalaxies()][2];
        Scanner scanner = new Scanner(Paths.get(Path));
        String line;
        Pattern galaxy = Pattern.compile(galaxyRegex);
        Matcher matcher;
        int yCoord = 0; //same as current line index
        int galaxyNum = 0;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            matcher = galaxy.matcher(line);
            while(matcher.find()) {
                int xCoord = matcher.start();
                galaxyArray[galaxyNum] = new int[]{xCoord + xOffsets[xCoord], yCoord + yOffsets[yCoord]};
                galaxyNum++;
            }
            yCoord++;
        }
        scanner.close();
        return galaxyArray;
    }
    static int distance(int xCoord1, int yCoord1, int xCoord2, int yCoord2) {
        return Math.abs(yCoord2 - yCoord1) + Math.abs(xCoord2 - xCoord1);
    }

    /**
     * for testing
     */
    static void setPathToTestPath1() throws IOException {
        Path = testPath1;
        setCharsPerLine();
    }

    static void setPathToTestPath2() throws IOException {
        Path = testPath2;
        setCharsPerLine();
    }

    static void setPathToRealPath() throws IOException {
        Path = realPath;
        setCharsPerLine();
    }

    static void setCharsPerLine() throws IOException {
        charsPerLine = charsPerLine();
    }
}
