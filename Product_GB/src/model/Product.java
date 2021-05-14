package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Product {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gadgetbadget", "root", "");

			// For testing
			System.out.print("Successfully connected");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String readProduct() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Product Date</th>" + "<th>Product Type</th><th>Description</th>"
					+ "<th>Product Number</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from products";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String ProductID = Integer.toString(rs.getInt("ProductID"));
				String ProductDate = rs.getString("ProductDate");
				String ProductType = rs.getString("ProductType");
				String Description = rs.getString("Description");
				String ProductNumber = Integer.toString(rs.getInt("ProductNumber"));

				// Add into the html table

				output += "<tr><td><input id='hidProductIDUpdate' name='hidProductIDUpdate' type='hidden' value='"
						+ ProductID + "'>" + ProductDate + "</td>";

				output += "<td>" + ProductType + "</td>";
				output += "<td>" + Description + "</td>";
				output += "<td>" + ProductNumber + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-ProductID='"
						+ ProductID + "'>" + "</td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Payment Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Insert Product
	public String insertProduct(String ProductDate, String ProductType, String Description, String ProductNumber) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into products (`ProductID`,`ProductDate`,`ProductType`,`Description`,`ProductNumber`)"
					+ " values (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ProductDate);
			preparedStmt.setString(3, ProductType);
			preparedStmt.setString(4, Description);
			preparedStmt.setString(5, ProductNumber);

			// execute the statement
			preparedStmt.execute();
			con.close();

			// Create JSON Object to show successful msg.
			String newProduct = readProduct();
			output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}";
		} catch (Exception e) {
			// Create JSON Object to show Error msg.
			output = "{\"status\":\"error\", \"data\": \"Error while Inserting Product.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Update Product Details
	public String updateProduct(String ProductID, String ProductDate, String ProductType, String Description,
			String ProductNumber) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE products SET ProductDate=?,ProductType=?,Description=?,ProductNumber=? WHERE ProductID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, ProductDate);
			preparedStmt.setString(2, ProductType);
			preparedStmt.setString(3, Description);
			preparedStmt.setInt(4, Integer.parseInt(ProductNumber));
			preparedStmt.setInt(5, Integer.parseInt(ProductID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			// create JSON object to show successful msg
			String newProduct = readProduct();
			output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while Updating Product Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteProduct(String ProductID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "DELETE FROM products WHERE ProductID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ProductID));
			// execute the statement
			preparedStmt.execute();
			con.close();

			// create JSON Object
			String newProduct = readProduct();
			output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}";
		} catch (Exception e) {
			// Create JSON object
			output = "{\"status\":\"error\", \"data\": \"Error while Deleting Product.\"}";
			System.err.println(e.getMessage());

		}

		return output;
	}

}
