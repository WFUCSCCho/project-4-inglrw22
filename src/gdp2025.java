/**
 * @file gdp2025.java
 * @description Represents an entry from the GDP dataset.
 * @author Ravi Ingle
 * @date December 4, 2025
 */

public class gdp2025 implements Comparable<gdp2025> {
    private String country; // this is the name of the country
    private int gdp; // this is the country's GDP value

    // Default constructor
    public gdp2025() {
        this.country = "";
        this.gdp = 0;
    }

    // Parametrized constructor
    public gdp2025(String country, int gdp) {
        this.country = country;
        this.gdp = gdp;
    }

    // Copy constructor
    public gdp2025(gdp2025 other) {
        this.country = other.country;
        this.gdp = other.gdp;
    }

    // returns the country name
    public String getCountry() {
        return country;
    }

    // returns the gdp value
    public int getGdp() {
        return gdp;
    }

    // This method is used to return a string representation for printing
    @Override
    public String toString() {
        return country + ": $" + gdp;
    }

    // This method is crucial for 'search' and 'remove'. It tells the hash table
    // when two objects are considered equal based on their country name.
    @Override
    public boolean equals(Object obj) {
        // checks if the object is the exact same instance
        if (this == obj) return true;
        // checks if the object is null or not the same class
        if (obj == null || getClass() != obj.getClass()) return false;
        gdp2025 other = (gdp2025) obj;
        return country.equals(other.country); // returns true if the country names are the same
    }

    // This method is required when overriding equals(). It ensures that equal
    // objects have the same hash code, which is essential for hash table operations.
    @Override
    public int hashCode() {
        return country.hashCode(); // use the country name's hash code
    }

    // This method is the most important for sorting. It dictates how
    // objects are sorted alphabetically by country name.
    @Override
    public int compareTo(gdp2025 other) {
        return this.country.compareTo(other.country); // compares alphabetically by country name
    }
}