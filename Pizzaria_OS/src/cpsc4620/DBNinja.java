package cpsc4620;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/*
 * This file is where most of your code changes will occur You will write the code to retrieve
 * information from the database, or save information to the database
 * 
 * The class has several hard coded static variables used for the connection, you will need to
 * change those to your connection information
 * 
 * This class also has static string variables for pickup, delivery and dine-in. If your database
 * stores the strings differently (i.e "pick-up" vs "pickup") changing these static variables will
 * ensure that the comparison is checking for the right string in other places in the program. You
 * will also need to use these strings if you store this as boolean fields or an integer.
 * 
 * 
 */

/**
 * A utility class to help add and retrieve information from the database
 */

public final class DBNinja {
	private static Connection conn;

	// Change these variables to however you record dine-in, pick-up and delivery,
	// and sizes and
	// crusts
	public final static String pickup = "pickup";
	public final static String delivery = "delivery";
	public final static String dine_in = "dine-in";

	public final static String size_s = "small";
	public final static String size_m = "medium";
	public final static String size_l = "large";
	public final static String size_xl = "x-large";

	public final static String crust_thin = "Thin";
	public final static String crust_orig = "Original";
	public final static String crust_pan = "Pan";
	public final static String crust_gf = "Gluten-Free";

	/**
	 * This function will handle the connection to the database
	 * 
	 * @return true if the connection was successfully made
	 * @throws SQLException
	 * @throws IOException
	 */
	private static boolean connect_to_db() throws SQLException, IOException {

		try {
			conn = DBConnector.make_connection();
			return true;
		} catch (SQLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

	}

	/**
	 *
	 * @param o order that needs to be saved to the database
	 * @throws SQLException
	 * @throws IOException
	 * @requires o is not NULL. o's ID is -1, as it has not been assigned yet. The
	 *           pizzas do not exist in the database yet, and the topping inventory
	 *           will allow for these pizzas to be made
	 * @ensures o will be assigned an id and added to the database, along with all
	 *          of it's pizzas. Inventory levels will be updated appropriately
	 */
	public static int addOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * add code to add the order to the DB. Remember that we're not just
		 * adding the order to the order DB table, but we're also recording
		 * the necessary data for the delivery, dinein, and pickup tables
		 */

		String insert =
				"INSERT INTO customer_order " +
				"(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber) " +
				"VALUES " +
				"(null, " + o.getOrderType() + ", '" + o.getDate() + "', 0, 0, 0, null);";

		Statement insertStatement = conn.createStatement();
		insertStatement.execute(insert);

		String update = "SELECT MAX(OrderID) as OrderID FROM customer_order;";
		PreparedStatement query = conn.prepareStatement(update,ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet result = query.executeQuery();
		int orderID = 0;
		result.beforeFirst();
		if(result.next()) {
			orderID = result.getInt("OrderID");
		}
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return orderID;
	}
	
	public static void addPizza(Pizza p) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Add the code needed to insert the pizza into into the database.
		 * Keep in mind adding pizza discounts to that bridge table and 
		 * instance of topping usage to that bridge table if you have't accounted
		 * for that somewhere else.
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static int getMaxPizzaID() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * A function I needed because I forgot to make my pizzas auto increment in my DB.
		 * It goes and fetches the largest PizzaID in the pizza table.
		 * You wont need this function if you didn't forget to do that
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return -1;
	}
	
	public static void useTopping(Pizza p, Topping t, boolean isDoubled) throws SQLException, IOException //this function will update toppings inventory in SQL and add entities to the Pizzatops table. Pass in the p pizza that is using t topping
	{
		connect_to_db();
		/*
		 * This function should 2 two things.
		 * We need to update the topping inventory every time we use t topping (accounting for extra toppings as well)
		 * and we need to add that instance of topping usage to the pizza-topping bridge if we haven't done that elsewhere
		 * Ideally, you should't let toppings go negative. If someone tries to use toppings that you don't have, just print
		 * that you've run out of that topping.
		 */
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	
	public static void usePizzaDiscount(Pizza p, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Helper function I used to update the pizza-discount bridge table. 
		 * You might use this, you might not depending on where / how to want to update
		 * this table
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void useOrderDiscount(Order o, Discount d) throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Helper function I used to update the pizza-discount bridge table. 
		 * You might use this, you might not depending on where / how to want to update
		 * this table
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	


	
	public static int addCustomer(Customer c) throws SQLException, IOException {
		connect_to_db();
		/*
		 * This should add a customer to the database
		 */

		String fName = c.getFName();
		String lName = c.getLName();
		String phone = c.getPhone();
		String insert = "CALL ADDCUSTOMER('" + fName + "','" + lName + "','" + phone + "',null);";

		Statement insertStatement = conn.createStatement();
		insertStatement.execute(insert);


		String update = "SELECT CustomerID FROM customer WHERE CustomerPhone = '" + phone + "';";
		PreparedStatement query = conn.prepareStatement(update,ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		ResultSet result = query.executeQuery();
		int CustID = 0;
		result.beforeFirst();
		if(result.next()) {
			CustID = result.getInt("CustomerID");
		}


		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return CustID;
	}


	
	public static void CompleteOrder(Order o) throws SQLException, IOException {
		connect_to_db();
		/*
		 * add code to mark an order as complete in the DB. You may have a boolean field
		 * for this, or maybe a completed time timestamp. However you have it.
		 */
		


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}


	
	
	public static void AddToInventory(Topping t, double toAdd) throws SQLException, IOException {
		connect_to_db();
		/*
		 * Adds toAdd amount of topping to topping t.
		 */


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}

	

	public static void printInventory() throws SQLException, IOException {
		connect_to_db();
		
		/*
		 * I used this function to PRINT (not return) the inventory list.
		 * When you print the inventory (either here or somewhere else)
		 * be sure that you print it in a way that is readable.
		 * 
		 * 
		 * 
		 * The topping list should also print in alphabetical order
		 */
		
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION		
	}
	
	
	public static ArrayList<Topping> getInventory() throws SQLException, IOException {
		connect_to_db();
		/*
		 * This function actually returns the toppings. The toppings
		 * should be returned in alphabetical order if you don't
		 * plan on using a printInventory function
		 */

		

		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
	}


	public static ArrayList<Order> getCurrentOrders() throws SQLException, IOException {
		connect_to_db();
		ArrayList<Order> orders = new ArrayList<Order>();
		/*
		 * This function should return an arraylist of all of the orders.
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 * 
		 * Also, like toppings, whenever we print out the orders using menu function 4 and 5
		 * these orders should print in order from newest to oldest.
		 */

		String query = 	"SELECT " +
				"customer_order.*, " +
				"customer.CustomerAddress " +
				"FROM " +
				"customer_order " +
				"LEFT JOIN  " +
				"customer ON customer_order.CustomerID = customer.CustomerID " +
				"ORDER BY " +
				"customer_order.OrderTime ASC;";
		Statement queryStatement = conn.createStatement();
		ResultSet resultSet = queryStatement.executeQuery(query);
		
		while(resultSet.next()) {
			switch (resultSet.getString("OrderType")) {
				case "pickup" :
					orders.add(new PickupOrder(
						resultSet.getInt("OrderID"),
						resultSet.getInt("CustomerID"),
						resultSet.getString("OrderTime"),
							resultSet.getDouble("OrderPrice"),
							resultSet.getDouble("OrderCost"),
							0,
							resultSet.getInt("OrderComplete")
					));
					break;
				case "delivery" :
					orders.add(new DeliveryOrder(
							resultSet.getInt("OrderID"),
							resultSet.getInt("CustomerID"),
							resultSet.getString("OrderTime"),
							resultSet.getDouble("OrderPrice"),
							resultSet.getDouble("OrderCost"),
							resultSet.getInt("OrderComplete"),
							resultSet.getString("CustomerAddress")
					));
					break;
				case "dine-in" :
					orders.add(new DineinOrder(
							resultSet.getInt("OrderID"),
							0,
							resultSet.getString("OrderTime"),
							resultSet.getDouble("OrderPrice"),
							resultSet.getDouble("OrderCost"),
							resultSet.getInt("OrderComplete"),
							resultSet.getInt("OrderTableNumber")
					));
					break;
			}
		}			
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return(orders);
	}

	public static ArrayList<Order> getCurrentOrdersRange(String olddate,String newdate) throws SQLException, IOException {
		connect_to_db();
		ArrayList<Order> orders = new ArrayList<Order>();
		/*
		 * This function should return an arraylist of all of the orders.
		 * Remember that in Java, we account for supertypes and subtypes
		 * which means that when we create an arrayList of orders, that really
		 * means we have an arrayList of dineinOrders, deliveryOrders, and pickupOrders.
		 *
		 * Also, like toppings, whenever we print out the orders using menu function 4 and 5
		 * these orders should print in order from newest to oldest.
		 */

		String query = 	"SELECT " +
				"customer_order.*, " +
				"customer.CustomerAddress " +
				"FROM " +
				"customer_order " +
				"LEFT JOIN  " +
				"customer ON customer_order.CustomerID = customer.CustomerID " +
				"WHERE CAST(customer_order.OrderTime AS DATE) BETWEEN '" + olddate + "' AND '" + newdate + "'" +
				" ORDER BY " +
				"customer_order.OrderTime ASC;";
		Statement queryStatement = conn.createStatement();
		ResultSet resultSet = queryStatement.executeQuery(query);

		while(resultSet.next()) {
			switch (resultSet.getString("OrderType")) {
				case "pickup" :
					orders.add(new PickupOrder(
							resultSet.getInt("OrderID"),
							resultSet.getInt("CustomerID"),
							resultSet.getString("OrderTime"),
							resultSet.getDouble("OrderPrice"),
							resultSet.getDouble("OrderCost"),
							0,
							resultSet.getInt("OrderComplete")
					));
					break;
				case "delivery" :
					orders.add(new DeliveryOrder(
							resultSet.getInt("OrderID"),
							resultSet.getInt("CustomerID"),
							resultSet.getString("OrderTime"),
							resultSet.getDouble("OrderPrice"),
							resultSet.getDouble("OrderCost"),
							resultSet.getInt("OrderComplete"),
							resultSet.getString("CustomerAddress")
					));
					break;
				case "dine-in" :
					orders.add(new DineinOrder(
							resultSet.getInt("OrderID"),
							0,
							resultSet.getString("OrderTime"),
							resultSet.getDouble("OrderPrice"),
							resultSet.getDouble("OrderCost"),
							resultSet.getInt("OrderComplete"),
							resultSet.getInt("OrderTableNumber")
					));
					break;
			}
		}

		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return(orders);
	}

	public static ArrayList<Order> sortOrders(ArrayList<Order> list)
	{
		/*
		 * This was a function that I used to sort my arraylist based on date.
		 * You may or may not need this function depending on how you fetch
		 * your orders from the DB in the getCurrentOrders function.
		 */
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return null;
		
	}
	
	public static boolean checkDate(String dateOfOrder)
	{
		//Helper function I used to help sort my dates. You likely wont need these

		int year = getYear(dateOfOrder);
		int month = getMonth(dateOfOrder);
		int day = getDay(dateOfOrder);
		boolean returnVal = true;
		boolean goodDay = true;
		if(year <= 0) {
			System.out.println("\tError: Invalid year. Try Again");
			returnVal = false;
		}

		if(((month <= 0) || (month > 13))) {
			returnVal = false;
			System.out.println("\tError: Invalid month. Try Again");
		}
		switch(month) {
			case 2:
				if((year % 4) == 0 ) {
					if((year % 100) == 0) {
						if((year % 400) == 0) {
							if(day > 29) {
								 goodDay = false;
							}
						}
						if(day > 28 && ((year % 400) != 0))  goodDay = false;
					}
				} else if(day > 28)  goodDay = false;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if(day > 30)  goodDay = false;
				break;
			default:
				if((day > 31)||(day <= 0))  goodDay = false;
		}
		if(goodDay == false) {
			returnVal = false;
			System.out.println("\tError: Invalid day. Try Again");
		}

		return returnVal;
	}
	
	
	/*
	 * The next 3 private functions help get the individual components of a SQL datetime object. 
	 * You're welcome to keep them or remove them.
	 */
	private static int getYear(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(1,4));
	}
	private static int getMonth(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(5, 7));
	}
	private static int getDay(String date)// assumes date format 'YYYY-MM-DD HH:mm:ss'
	{
		return Integer.parseInt(date.substring(8, 10));
	}

	public static boolean compDates(String date1, String date2){
		int year1 = getYear(date1),month1 = getMonth(date1),day1 = getDay(date1);
		int year2 = getYear(date1),month2 = getMonth(date1),day2 = getDay(date1);

		if(year1>year2){
			return false;
		} else if(month1>month2){
			return false;
		} els
	}

	
	
	
	public static double getBaseCustPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		// add code to get the base price (for the customer) for that size and crust pizza Depending on how
		// you store size & crust in your database, you may have to do a conversion
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return bp;
	}
	
	public static String getCustomerName(int CustID) throws SQLException, IOException
	{
		/*
		 *This is a helper function I used to fetch the name of a customer
		 *based on a customer ID. It actually gets called in the Order class
		 *so I'll keep the implementation here. You're welcome to change
		 *how the order print statements work so that you don't need this function.
		 */
		if(CustID == 0){
			return ("Not recorded because of order type");
		}else{
			connect_to_db();
			String ret = "";
			String query = "SELECT CustomerFirstName, CustomerLastName FROM customer WHERE CustomerID=" + CustID + ";";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			while(rset.next())
			{
				ret = rset.getString(1) + " " + rset.getString(2);
			}
			conn.close();
			return ret;
		}
	}
	
	public static double getBaseBusPrice(String size, String crust) throws SQLException, IOException {
		connect_to_db();
		double bp = 0.0;
		// add code to get the base cost (for the business) for that size and crust pizza Depending on how
		// you store size and crust in your database, you may have to do a conversion
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return bp;
	}

	
	public static ArrayList<Discount> getDiscountList() throws SQLException, IOException {
		ArrayList<Discount> discs = new ArrayList<Discount>();
		connect_to_db();
		//returns a list of all the discounts.
		
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return discs;
	}


	public static ArrayList<Customer> getCustomerList() throws SQLException, IOException {
		ArrayList<Customer> custs = new ArrayList<Customer>();
		connect_to_db();
		/*
		 * return an arrayList of all the customers. These customers should
		 *print in alphabetical order, so account for that as you see fit.
		*/
		String query = "SELECT * FROM customer ORDER BY CustomerLastName, CustomerFirstName, CustomerPhone;";
		Statement queryStatement = conn.createStatement();
		ResultSet resultSet = queryStatement.executeQuery(query);
		
		while(resultSet.next()) {
			Customer temp = new Customer(
					resultSet.getInt("CustomerID"),
					resultSet.getString("CustomerFirstName"),
					resultSet.getString("CustomerLastName"),
					resultSet.getString("CustomerPhone")
			);
			custs.add(temp);
		}			
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		conn.close();
		return custs;
	}
	
	public static int getNextOrderID() throws SQLException, IOException
	{
		/*
		 * A helper function I had to use because I forgot to make
		 * my OrderID auto increment...You can remove it if you
		 * did not forget to auto increment your orderID.
		 */
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
		return -1;
	}
	
	public static void printToppingPopReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ToppingPopularity view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print (other than that it should
		 * be in alphabetical order by name), just make sure it's readable.
		 */


		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void printProfitByPizzaReport() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByPizza view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print, just make sure it's readable.
		 */
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION
	}
	
	public static void printProfitByOrderType() throws SQLException, IOException
	{
		connect_to_db();
		/*
		 * Prints the ProfitByOrderType view. Remember that these views
		 * need to exist in your DB, so be sure you've run your createViews.sql
		 * files on your testing DB if you haven't already.
		 * 
		 * I'm not picky about how they print, just make sure it's readable.
		 */
		
		
		
		
		
		
		
		//DO NOT FORGET TO CLOSE YOUR CONNECTION	
	}
	
	

}
