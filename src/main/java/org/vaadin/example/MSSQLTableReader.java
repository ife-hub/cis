package org.vaadin.example;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MSSQLTableReader {

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://demo.integrabanking.ng:2866;databaseName=Kabimyauat;trustServerCertificate=true";
    private static final String USERNAME = "devops";
    private static final String PASSWORD = "I$$lng#2";

    public MSSQLTableReader() {

    }

    public List<Map<String, Object>> getAll(){
        MSSQLTableReader reader = new MSSQLTableReader();

        List<Map<String, Object>> results = new ArrayList<>();

        try {
            // Example: Read all contents from a table
            results = reader.getAllTableContents("ServiceRequestMap");

            // Display results
            //reader.displayResults(results);
            return results;

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Retrieves all contents from a specified table
     * @param tableName The name of the table to query
     * @return List of Maps containing row data
     * @throws SQLException if database error occurs
     */
    public List<Map<String, Object>> getAllTableContents(String tableName) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName(DRIVER);

            // Establish connection
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Create statement
            stmt = conn.createStatement();

            // Execute query - using parameterized approach for table name
            String query = "SELECT * FROM " + sanitizeTableName(tableName);
            rs = stmt.executeQuery(query);

            // Get column metadata
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Process each row
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }

                results.add(row);
            }

        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        } finally {
            // Close resources in reverse order
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { /* ignore */ }
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { /* ignore */ }
            }
        }

        return results;
    }

    /**
     * Alternative method using try-with-resources for automatic resource management
     */
    public List<Map<String, Object>> getAllTableContentsWithTryWithResources(String tableName) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }

        String query = "SELECT * FROM " + sanitizeTableName(tableName);

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }

                results.add(row);
            }
        }

        return results;
    }

    /**
     * Basic sanitization for table name to prevent SQL injection
     * In production, use proper validation/whitelisting
     */
    private String sanitizeTableName(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty");
        }

        // Remove potentially dangerous characters
        // In production, use a whitelist approach instead
        return tableName.replaceAll("[^a-zA-Z0-9_]", "");
    }

    /**
     * Display results in a formatted way
     */
    public void displayResults(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            System.out.println("No data found in the table.");
            return;
        }

        System.out.println("Total rows: " + results.size());
        System.out.println("==========================================");

        // Get column names from first row
        Map<String, Object> firstRow = results.get(0);
        System.out.println("Columns: " + firstRow.keySet());
        System.out.println("==========================================");

        // Display each row
        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> row = results.get(i);
            System.out.println("Row " + (i + 1) + ":");

            for (Map.Entry<String, Object> entry : row.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("------------------------------------------");
        }
    }

    /**
     * Get table contents with pagination
     */
    public List<Map<String, Object>> getTableContentsWithPagination(String tableName, int offset, int limit) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found", e);
        }

        String query = "SELECT * FROM " + sanitizeTableName(tableName) +
                " ORDER BY (SELECT NULL) OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, offset);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(columnName, value);
                    }

                    results.add(row);
                }
            }
        }

        return results;
    }
}