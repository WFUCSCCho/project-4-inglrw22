/**************************************************************************
 * @file: SeparateChainingHashTable.java
 * @description: This program implements a hash table using separate chaining
 *               for collision resolution. It supports generic types and
 *               provides O(1) average case operations for insert, search, delete.
 * @author: Ravi Ingle
 * @date: December 4, 2025
 **************************************************************************/

import java.util.LinkedList;
import java.util.List;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

public class SeparateChainingHashTable<AnyType> {
    /**
     * Construct the hash table.
     */
    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     *
     * @param size approximate table size.
     */
    public SeparateChainingHashTable(int size) {
        theLists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theLists.length; i++)
            theLists[i] = new LinkedList<>();
        currentSize = 0;
    }

    /**
     * Insert into the hash table. If the item is
     * already present, then do nothing. Rehash if
     * the insertion exceeds the table size.
     *
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        // Get the list at the hashed index
        List<AnyType> whichList = theLists[myhash(x)];

        // Only insert if the item is not already present
        if (!whichList.contains(x)) {
            whichList.add(x);
            currentSize++;

            // Rehash if load factor exceeds 1.0
            if (currentSize > theLists.length)
                rehash();
        }
    }

    /**
     * Remove from the hash table.
     *
     * @param x the item to remove.
     */
    public void remove(AnyType x) {
        // Get the list at the hashed index
        List<AnyType> whichList = theLists[myhash(x)];

        // Remove the item if it exists
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    /**
     * Find an item in the hash table.
     *
     * @param x the item to search for.
     * @return true if x is found, false otherwise.
     */
    public boolean contains(AnyType x) {
        // Get the list at the hashed index
        List<AnyType> whichList = theLists[myhash(x)];

        // Check if the item exists in the list
        return whichList.contains(x);
    }

    /**
     * Make the hash table logically empty by clearing all lists.
     */
    public void makeEmpty() {
        // Clear each linked list in the array
        for (int i = 0; i < theLists.length; i++) {
            theLists[i].clear();
        }
        currentSize = 0;
    }

    /**
     * A hash routine for String objects.
     *
     * @param key       the String to hash.
     * @param tableSize the size of the hash table.
     * @return the hash value.
     */
    public static int hash(String key, int tableSize) {
        int hashVal = 0;

        for (int i = 0; i < key.length(); i++)
            hashVal = 37 * hashVal + key.charAt(i);

        hashVal %= tableSize;
        if (hashVal < 0)
            hashVal += tableSize;

        return hashVal;
    }

    /**
     * Rehash the table by creating a new table twice the size
     * and reinserting all elements from the old table.
     */
    private void rehash() {
        // Save the old table
        List<AnyType>[] oldLists = theLists;

        // Create a new, larger table
        theLists = new LinkedList[nextPrime(2 * theLists.length)];
        for (int i = 0; i < theLists.length; i++)
            theLists[i] = new LinkedList<>();

        // Reset current size
        currentSize = 0;

        // Copy elements from old table to new table
        for (int i = 0; i < oldLists.length; i++) {
            for (AnyType item : oldLists[i]) {
                insert(item);
            }
        }
    }

    /**
     * Hash function for generic types using their hashCode method.
     *
     * @param x the item to hash.
     * @return the hash index.
     */
    private int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= theLists.length;
        if (hashVal < 0)
            hashVal += theLists.length;

        return hashVal;
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    /**
     * The array of Lists.
     */
    private List<AnyType>[] theLists;
    private int currentSize;

    /**
     * Internal method to find a prime number at least as large as n.
     *
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;

        for (; !isPrime(n); n += 2)
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     *
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;

        if (n == 1 || n % 2 == 0)
            return false;

        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;

        return true;
    }

}