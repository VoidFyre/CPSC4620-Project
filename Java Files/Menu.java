package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * This file is where the front end magic happens.
 * 
 * You will have to write the functionality of each of these menu options' respective functions.
 * 
 * This file should need to access your DB at all, it should make calls to the DBNinja that will do all the connections.
 * 
 * You can add and remove functions as you see necessary. But you MUST have all 8 menu functions (9 including exit)
 * 
 * Simply removing menu functions because you don't know how to implement it will result in a major error penalty (akin to your program crashing)
 * 
 * Speaking of crashing. Your program shouldn't do it. Use exceptions, or if statements, or whatever it is you need to do to keep your program from breaking.
 * 
 * 
 */

public class Menu {
	public static void main(String[] args) throws SQLException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to Taylor's Pizzeria!");
		
		int menu_option = 0;

		// present a menu of options and take their selection
		PrintMenu();
		String option = reader.readLine();
		menu_option = Integer.parseInt(option);

		while (menu_option != 9) {
			switch (menu_option) {
			case 1:// enter order
				EnterOrder();
				break;
			case 2:// view customers
				viewCustomers();
				break;
			case 3:// enter customer
				EnterCustomer();
				break;
			case 4:// view order
				// open/closed/date
				ViewOrders();
				break;
			case 5:// mark order as complete
				MarkOrderAsComplete();
				break;
			case 6:// view inventory levels
				ViewInventoryLevels();
				break;
			case 7:// add to inventory
				AddInventory();
				break;
			case 8:// view reports
				PrintReports();
				break;
			}
			PrintMenu();
			option = reader.readLine();
			menu_option = Integer.parseInt(option);
		}

	}

	public static void PrintMenu() {
		System.out.println("\n\nPlease enter a menu option:");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
	}

	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException 
	{
		Scanner reader = new Scanner(System.in);
		/*
		 * EnterOrder should do the following:
		 * Ask if the order is for an existing customer -> If yes, select the customer. If no -> create the customer (as if the menu option 2 was selected).
		 * 
		 * Ask if the order is delivery, pickup, or dinein (ask for orderType specific information when needed)
		 * 
		 * Build the pizza (there's a function for this)
		 * 
		 * ask if more pizzas should be be created. if yes, go back to building your pizza. 
		 * 
		 * Apply order discounts as needed (including to the DB)
		 * 
		 * apply the pizza to the order (including to the DB)
		 * 
		 * return to menu
		 */
		
		 //Asking if existing customer
		Customer customer;
		boolean customer_found = false;
		while (!customer_found) {
			System.out.println("Are You an Existing Customer or a New Customer?");
			System.out.println("1. Existing Customer");
			System.out.println("2. New Customer");
			System.out.println("Enter the Corresponding Number.");
			String newcustomer = reader.nextLine();
			ArrayList<Customer> customer_list;
			switch(newcustomer) {
				//Existing Customer
				case "1":
					boolean customer_number_found = false;
					customer_list = getCustomerList();
					while (!customer_number_found) {
						System.out.println("List of Customers:");
						viewCustomers();
						System.out.println("Enter Your Customer ID");
						String CustID = reader.nextLine();
						//check if customer exists
						for (Customer cus: customer_list) {
							if (cus.getCustID() == CustID) {
								customer = cus;
							}
							customer_number_found = true;
							break;
						}
						if (!customer_number_found) {
							System.out.println("Invalid Selection. Try Again.");
						}
					}
					break;

				//New Customer
				case "2":
					customer = EnterCustomer();
					customer_list = getCustomerList();
					boolean customer_info_found = false;
					for(Customer cus: customer_list) {
						if (cus.getPhone() == customer.getPhone()) {
							customer = cus;
							customer_found = true;
						}
					}
					break;
				default:
					System.out.println("Invalid Selection. Try Again.");
			}
		}
		
		//Setting up an order

		System.out.println("What Type of Order is This?");
		boolean got_order_type = false;
		Order order;
		while (!got_order_type) {
			System.out.println("1. Dine-in");
			System.out.println("2. Pickup");
			System.out.println("3. Delivery");
			System.out.println("Enter the Corresponding Number.");
			String order_type = reader.nextLine();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
			Date date = new Date();  
			date = formatter.format(date); 
			switch (order_type) {
				case "1":
					order = new DineinOrder(null, customer.getCustID(), date,  null, null, 0, null);
					got_order_type = true;
					break;
				case "2":
					order = new PickupOrder(null, customer.getCustID(), date, null, null, 0, 0);
					got_order_type = true;
					break;
				case "3":
					String address;
					System.out.println("What Is The Address for This Delivery?");
					while ((address = reader.nextLine()) == null) {
						System.out.println("What Is The Address for This Delivery?");
					}
					order = new DeliveryOrder(null, customer.getCustID(), date, null, null, 0, address);
					got_order_type = true;
					customer.setAddress(address);
			}
		}
		

		//creating a pizza
		ArrayList<Pizza> pizza_list;
		boolean all_pizza_added = false;
		while(!all_pizza_added) {
			pizza_list.add(buildPizza())

			boolean add_another_answer = false;
			while (!add_another_pizza) {
				System.out.println("Would You Like To Add Another Pizza?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				System.out.println("Enter the Corresponding Number.");
				response = reader.nextLine();
				switch (response) {
					case "1":
						add_another_answer = true;
						break;
					case "2":
						add_another_answer = true;
						all_pizza_added = true;
						break;
					default: 
						System.out.println("Invalid Selection. Try Again.");
				}
			}
		}
		System.out.println("Finished adding order...Returning to menu...");
		reader.close();
	}
	
	
	public static void viewCustomers()
	{
		/*
		 * Simply print out all of the customers from the database. 
		 */
		ArrayList<Customer> customers = getCustomerList();
        
        System.out.println("Customers\n----------------------------------------------");
        for(Customer C:customers) {
            System.out.println(C.toString());
        }
	}
	

	// Enter a new customer in the database
	public static Customer EnterCustomer() throws SQLException, IOException 
	{
		/*
		 * Ask what the name of the customer is. YOU MUST TELL ME (the grader) HOW TO FORMAT THE FIRST NAME, LAST NAME, AND PHONE NUMBER.
		 * If you ask for first and last name one at a time, tell me to insert First name <enter> Last Name (or separate them by different print statements)
		 * If you want them in the same line, tell me (First Name <space> Last Name).
		 * 
		 * same with phone number. If there's hyphens, tell me XXX-XXX-XXXX. For spaces, XXX XXX XXXX. For nothing XXXXXXXXXXXX.
		 * 
		 * I don't care what the format is as long as you tell me what it is, but if I have to guess what your input is I will not be a happy grader
		 * 
		 * Once you get the name and phone number (and anything else your design might have) add it to the DB
		 */
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Your First Name");
		String name_first = reader.nextLine();
		System.out.println("Enter Your Last Name");
		String name_last = reader.nextLine();
		boolean newphone = false;
		String phone_number;
		while (!new_phone) {
			System.out.println("Enter Your Phone Number (###-###-####)");
			while (!(reader.hasNext("\\d{4}-\\d{2}-\\d{2}"))) {
				System.out.println("Invalid Input. Check Your Formatting And Try Again.");
				System.out.println("Enter Your Phone Number (###-###-####)");
				reader.nextLine();
			}
			boolean phone_found = false;
			phone_number = reader.nextLine();
			for (Customer cus: customer_list) {
				if (cus.getPhone() == phone_number) {
					customer = cus;
					phone_found = true;
				}
			}
			if (phone_found) {
				system.out.println("Phone Number Already Exists in Registry. Try Again.");
			}
			else {

				new_phone = true;
			}	
		}
		
		//input customer
		Customer customer = new Customer(null, name_first, name_last, phone_number);
		addCustomer(customer);
		return customer;
		reader.close();
	}

	// View any orders that are not marked as completed
	public static void ViewOrders() throws SQLException, IOException 
	{
	/*
	 * This should be subdivided into two options: print all orders (using simplified view) and print all orders (using simplified view) since a specific date.
	 * 
	 * Once you print the orders (using either sub option) you should then ask which order I want to see in detail
	 * 
	 * When I enter the order, print out all the information about that order, not just the simplified view.
	 * 
	 */
		
	}

	
	// When an order is completed, we need to make sure it is marked as complete
	public static void MarkOrderAsComplete() throws SQLException, IOException 
	{
		/*All orders that are created through java (part 3, not the 7 orders from part 2) should start as incomplete
		 * 
		 * When this function is called, you should print all of the orders marked as complete 
		 * and allow the user to choose which of the incomplete orders they wish to mark as complete
		 * 
		 */
		
		
		
		
		
		

	}

	// See the list of inventory and it's current level
	public static void ViewInventoryLevels() throws SQLException, IOException 
	{
		//print the inventory. I am really just concerned with the ID, the name, and the current inventory
		
		
		
		
		
		
		
	}

	// Select an inventory item and add more to the inventory level to re-stock the
	// inventory
	public static void AddInventory() throws SQLException, IOException 
	{
		/*
		 * This should print the current inventory and then ask the user which topping they want to add more to and how much to add
		 */
		
		
		
		
		
		
	}

	// A function that builds a pizza. Used in our add new order function
	public static Pizza buildPizza(int orderID) throws SQLException, IOException 
	{
		
		/*
		 * This is a helper function for first menu option.
		 * 
		 * It should ask which size pizza the user wants and the crustType.
		 * 
		 * Once the pizza is created, it should be added to the DB.
		 * 
		 * We also need to add toppings to the pizza. (Which means we not only need to add toppings here, but also our bridge table)
		 * 
		 * We then need to add pizza discounts (again, to here and to the database)
		 * 
		 * Once the discounts are added, we can return the pizza
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Pizza ret = null;

		//Picking size
		boolean size_picked = false;
		while (!size_picked) {
			System.out.println("What Size Pizza Would You Like?");
			System.out.println("1. Small");
			System.out.println("2. Medium");
			System.out.println("3. Large");
			System.out.println("4. Extra-Large");
			System.out.println("Enter the Corresponding Number.");
			response = reader.readLine();
			switch (response) {
				case "1":
					ret.setSize("small");
					size_picked = true;
					break;
				case "2":
					ret.setSize("medium");
					size_picked = true;
					break;
				case "3":
					ret.setSize("large");
					size_picked = true;
					break;
				case "4":
					ret.setSize("x-large");
					size_picked = true;
					break;
				default:
					System.out.println("Invalid Selection. Try Again.");
			}
		}

		//Picking crust
		boolean crust_picked = false;
		while (!crust_picked) {
			System.out.println("What Type of Crust Would You Like?");
			System.out.println("1. Original");
			System.out.println("2. Thin");
			System.out.println("3. Pan");
			System.out.println("4. Gluten-Free");
			System.out.println("Enter the Corresponding Number.");
			response = reader.readLine();
			switch (response) {
				case "1":
					ret.setCrustType("Original");
					crust_picked = true;
					break;
				case "2":
					ret.setCrustType("Thin");
					crust_picked = true;
					break;
				case "3":
					ret.setCrustType("Pan");
					crust_picked = true;
					break;
				case "4":
					ret.setCrustType("Gluten-Free");
					crust_picked = true;
					break;
				default:
					System.out.println("Invalid Selection. Try Again.");
			}
		}

		//Adding toppings
		
		
		
		
		
		
		
		return ret;
	}
	
	private static int getTopIndexFromList(int TopID, ArrayList<Topping> tops)
	{
		/*
		 * This is a helper function I used to get a topping index from a list of toppings
		 * It's very possible you never need a function like this
		 * 
		 */
		int ret = -1;
		
		
		
		return ret;
	}
	
	
	public static void PrintReports() throws SQLException, NumberFormatException, IOException
	{
		/*
		 * This function calls the DBNinja functions to print the three reports.
		 * 
		 * You should ask the user which report to print
		 */
	}

}
