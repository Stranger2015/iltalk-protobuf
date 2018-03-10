package org.ltc.iltalk.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 */
interface DatabaseAPI {

    static void main(String[] args) {
        try {
// Make sure the DriverManager knows about the driver
            Class.forName("COM.cloudscape.core.JDBCDriver");
// Create a connection to the database
            Connection conn = DriverManager.getConnection(
                    "jdbc:cloudscape:j2eebook");
// Create a statement
            Statement stmt = conn.createStatement();
// Execute the query
            ResultSet results = stmt.executeQuery(
                    "select * from person");
// Loop through all the results
            while (results.next()) {
// Get the values from the result set
                String firstName = results.getString("first_name");
                String middleName = results.getString("middle_name");
                String lastName = results.getString("last_name");
                int age = results.getInt("age");
// Print out the values
                System.out.println(firstName + " " + middleName + " " + lastName +
                        "  " + age);
            }
            conn.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
