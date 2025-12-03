/**************************************************************************
 * @file: Proj4.java
 * @description: This program tests hash table performance by timing insert,
 *               search, and delete operations on sorted, shuffled, and reversed
 *               datasets. Results are output to screen and appended to analysis.txt.
 * @author: Ravi Ingle
 * @date: December 4, 2025
 **************************************************************************/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java Proj4 <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line (header)
        inputFileNameScanner.nextLine();

        // Read the dataset into an ArrayList
        ArrayList<gdp2025> dataList = new ArrayList<>();
        int linesRead = 0;

        while (inputFileNameScanner.hasNextLine() && linesRead < numLines) {
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length == 2) {
                String country = parts[0].trim();
                String gdpStr = parts[1].trim();

                // Skip entries with empty GDP values
                if (!gdpStr.isEmpty()) {
                    try {
                        int gdp = Integer.parseInt(gdpStr);
                        dataList.add(new gdp2025(country, gdp));
                        linesRead++;
                    } catch (NumberFormatException e) {
                        // Skip invalid entries
                    }
                }
            }
        }
        inputFileNameScanner.close();

        // Print header
        System.out.println("\n========================================");
        System.out.println("Hash Table Performance Analysis");
        System.out.println("Dataset: " + inputFileName);
        System.out.println("Number of entries: " + linesRead);
        System.out.println("========================================\n");

        // Create one hash table to use for all tests
        SeparateChainingHashTable<gdp2025> hashTable = new SeparateChainingHashTable<>();

        // Test 1: Already Sorted List
        ArrayList<gdp2025> sortedList = new ArrayList<>(dataList);
        Collections.sort(sortedList);

        long[] sortedTimes = testHashTable(hashTable, sortedList, "Sorted");

        // Test 2: Shuffled List
        ArrayList<gdp2025> shuffledList = new ArrayList<>(dataList);
        Collections.shuffle(shuffledList);

        long[] shuffledTimes = testHashTable(hashTable, shuffledList, "Shuffled");

        // Test 3: Reversed List
        ArrayList<gdp2025> reversedList = new ArrayList<>(dataList);
        Collections.sort(reversedList, Collections.reverseOrder());

        long[] reversedTimes = testHashTable(hashTable, reversedList, "Reversed");

        // Append results to analysis.txt in CSV format
        FileOutputStream outputStream = new FileOutputStream("analysis.txt", true);
        PrintWriter outputWriter = new PrintWriter(outputStream);

        // Convert nanoseconds to seconds
        outputWriter.printf("%d,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f%n",
                linesRead,
                sortedTimes[0] / 1e9, sortedTimes[1] / 1e9, sortedTimes[2] / 1e9,
                shuffledTimes[0] / 1e9, shuffledTimes[1] / 1e9, shuffledTimes[2] / 1e9,
                reversedTimes[0] / 1e9, reversedTimes[1] / 1e9, reversedTimes[2] / 1e9);

        outputWriter.close();

        System.out.println("\nResults appended to analysis.txt");
        System.out.println("========================================\n");
    }

    /**
     * Tests hash table operations (insert, search, delete) on a given list.
     *
     * @param hashTable the hash table to test
     * @param list the list of data to test with
     * @param listType description of the list type (for output)
     * @return array of times [insertTime, searchTime, deleteTime] in nanoseconds
     */
    private static long[] testHashTable(SeparateChainingHashTable<gdp2025> hashTable,
                                        ArrayList<gdp2025> list, String listType) {
        long[] times = new long[3];

        // Time INSERT operation
        long startTime = System.nanoTime();
        for (gdp2025 item : list) {
            hashTable.insert(item);
        }
        long endTime = System.nanoTime();
        times[0] = endTime - startTime;

        // Time SEARCH operation
        startTime = System.nanoTime();
        for (gdp2025 item : list) {
            hashTable.contains(item);
        }
        endTime = System.nanoTime();
        times[1] = endTime - startTime;

        // Time DELETE operation
        startTime = System.nanoTime();
        for (gdp2025 item : list) {
            hashTable.remove(item);
        }
        endTime = System.nanoTime();
        times[2] = endTime - startTime;

        // Print results to screen
        System.out.printf("%-10s - Insert: %.6f s, Search: %.6f s, Delete: %.6f s%n",
                listType, times[0] / 1e9, times[1] / 1e9, times[2] / 1e9);

        return times;
    }
}