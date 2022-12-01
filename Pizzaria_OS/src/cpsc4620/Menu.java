package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	public static Scanner reader = new Scanner(System.in);
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("Welcome to Taylor's Pizzeria!\n");
		// present a menu of options and take their selection
		int menuChoice = 0;
		do{
			menuChoice = getMenuChoice();
			switch (menuChoice) {
				case 1:// enter order
					EnterOrder();
					break;
				case 2:// view customers
					viewCustomers();
					System.out.print("Press Enter to return");
					reader.nextLine();
					System.out.println();
					break;
				case 3:// enter customer
					EnterCustomer();
					break;
				case 4:// view order
					// open/closed/date
					ViewOrders();
					System.out.print("Press Enter to return");
					reader.nextLine();
					System.out.println();
					break;
				case 5:// mark order as complete
					MarkOrderAsComplete();
					break;
				case 6:// view inventory levels
					ViewInventoryLevels();
					System.out.print("Press Enter to return");
					reader.nextLine();
					System.out.println();
					break;
				case 7:// add to inventory
					AddInventory();
					break;
				case 8:// view reports
					PrintReports();
					System.out.print("Press Enter to return");
					reader.nextLine();
					System.out.println();
					break;
			}
		}while(menuChoice != 9);
		reader.close();
		System.out.println("Shutting down program ...");
	}

	public static int getMenuChoice() {
		System.out.println("Please enter a menu option:");
		System.out.println("-------------------------------");
		System.out.println("1. Enter a new order");
		System.out.println("2. View Customers ");
		System.out.println("3. Enter a new Customer ");
		System.out.println("4. View orders");
		System.out.println("5. Mark an order as completed");
		System.out.println("6. View Inventory Levels");
		System.out.println("7. Add Inventory");
		System.out.println("8. View Reports");
		System.out.println("9. Exit");
		System.out.println("-------------------------------\n");
		int menuOption = 0;
		menuOption = getIntRange(1,9,"Enter the Corresponding Number: ");
		System.out.println();
		return(menuOption);
	}

	public static int getInt(String prompt){
		int temp = 0;
		boolean repeat = true;
		do{
			try{
				System.out.print(prompt);
				temp = reader.nextInt();
				repeat = false;
			}catch(NumberFormatException e) {
				System.out.println("\tError: No input. Try again.");
			}catch(InputMismatchException e) {
				System.out.println("\tError: Invalid input. Try again.");
			}
			reader.nextLine();
		}while(repeat);
		return(temp);
	}

	public static int getIntRange(int min,int max,String prompt) {
		int temp = getInt(prompt);
		while((temp < min) || (temp > max)){
			System.out.println("\tError: Input not in range. Try again.");
			temp = getInt(prompt);
		}
		return(temp);
	}

	public static String getString(String prompt){
		String temp = null;
		boolean repeat = true;
		do{
			try{
				System.out.print(prompt);
				temp = reader.nextLine();
				repeat = false;
			}catch(NoSuchElementException e) {
				System.out.println("\tError: No input. Try again.");
			}

		}while(repeat);
		return(temp);
	}
	// allow for a new order to be placed
	public static void EnterOrder() throws SQLException, IOException 
	{

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
		Customer customer = null;
		boolean customer_found = false;
		boolean customer_info_found = false;
		boolean switch_to_add = false;
		boolean to_main_menu = false;
		while (!customer_found) {
			System.out.println("Are You an Existing Customer or a New Customer?");
			System.out.println("1. Existing Customer");
			System.out.println("2. New Customer");
			int newCustomer = getIntRange(1,2,"Enter the Corresponding Number: ");
			System.out.println();
			ArrayList<Customer> customer_list;
			do{
				switch(newCustomer) {
					//Existing Customer
					case 1:
						boolean customer_number_found = false;
						customer_list = DBNinja.getCustomerList();
						while (!customer_number_found) {
							viewCustomers();
							int CustID = getInt("Enter Your Customer ID: ");
							//check if customer exists
							for (Customer cus : customer_list) {
								if (cus.getCustID() == CustID) {
									customer = cus;
									customer_number_found = true;
									customer_found = true;
									break;
								}

							}
							if (!customer_number_found) {
								System.out.println("\tError: CustomerID not found");
								System.out.println();
								System.out.println("Do you wish to add a customer, go back to the main menu," +
										" or Try reentering the Customer ID?");
								System.out.println("1. Add a customer");
								System.out.println("2. Main menu");
								System.out.println("3. Reenter customer ID");
								int cnfChoice = getIntRange(1, 3, "Enter the Corresponding Number: ");
								switch (cnfChoice) {
									case 1:
										switch_to_add = true;
										newCustomer = 2;
										customer_number_found = true;
										System.out.println();
										break;
									case 2:
										to_main_menu = true;
										customer_number_found = true;
										customer_found = true;
										break;
									case 3:
										System.out.println();
										break;
								}
							} else {
								customer_info_found = true;
							}
						}
					break;

					//New Customer
					case 2:
						switch_to_add = false;
						customer = EnterCustomer();
						customer_list = DBNinja.getCustomerList();
						customer_found = true;
						break;
					default:
						System.out.println("Invalid Selection. Try Again.");
				}
			}while(switch_to_add);
		}
		if(to_main_menu){System.out.println();return;}

		//Setting up an order

		System.out.println("\nWhat Type of Order is This?");
		boolean got_order_type = false;
		Order order = null;
		while (!got_order_type) {
			System.out.println("1. Dine-in");
			System.out.println("2. Pickup");
			System.out.println("3. Delivery");
			int order_type = getIntRange(1,3,"Enter the Corresponding Number: ");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date date = new Date();
			String dateString = formatter.format(date);
			switch (order_type) {
				case 1:
					order = new DineinOrder(0, customer.getCustID(), dateString,  0, 0, 0, 0);
					got_order_type = true;
					break;
				case 2:
					order = new PickupOrder(0, customer.getCustID(), dateString, 0, 0, 0, 0);
					got_order_type = true;
					break;
				case 3:
					String address;
					System.out.println("What Is The Address for This Delivery?");
					while ((address = reader.nextLine()) == null) {
						System.out.println("What Is The Address for This Delivery?");
					}
					order = new DeliveryOrder(0, customer.getCustID(), dateString, 0, 0, 0, address);
					got_order_type = true;
					customer.setAddress(address);
			}
			order.setOrderID(DBNinja.addOrder(order));
		}
		
		//creating a pizza
		ArrayList<Pizza> pizza_list = new ArrayList<Pizza>();
		boolean all_pizza_added = false;
		while(!all_pizza_added) {
			pizza_list.add(buildPizza(order.getOrderID()));
			boolean add_another_pizza = false;
			while (!add_another_pizza) {
				System.out.println("Would You Like To Add Another Pizza?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				System.out.println("Enter the Corresponding Number.");
				int response = Integer.parseInt(reader.nextLine());
				switch (response) {
					case 1:
						add_another_pizza = true;
						break;
					case 2:
						add_another_pizza = true;
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
	
	
	public static void viewCustomers() throws SQLException, IOException {
		/*
		 * Simply print out all the customers from the database.
		 */
		ArrayList<Customer> customers = DBNinja.getCustomerList();
		
		System.out.println("List of Customers:\n----------------------------------------------");
		for(Customer C:customers) {
			System.out.println(C.toString());
		}		
		System.out.println("----------------------------------------------\n");
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


		String nameFirst = getString("Enter Your First Name: ");
		String nameLast = getString("Enter Your Last Name: ");
		boolean newPhone = false;
		String phone_number = null;
		while (!newPhone) {
			System.out.print("Enter Your Phone Number (###-###-####): ");
			while (!(reader.hasNext("\\d{3}-\\d{3}-\\d{4}"))) {
				System.out.println("\tError: Invalid Input. Check Your Formatting And Try Again.");
				System.out.print("Enter Your Phone Number (###-###-####): ");
				reader.nextLine();
			}
			boolean phone_found = false;
			phone_number = reader.nextLine();
			for (Customer cus: DBNinja.getCustomerList()) {
				if (cus.getPhone() == phone_number) {
					phone_found = true;
				}
			}
			if (phone_found) {
				System.out.println("Phone Number Already Exists in Registry. Try Again.");
			}
			else {
				newPhone = true;
			}	
		}
		
		//input customer
		Customer customer = new Customer(0, nameFirst, nameLast, phone_number);
		int CustID = DBNinja.addCustomer(customer);
		customer.setCustID(CustID);
		return customer;
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
		System.out.println("Would you like to display all orders or display all orders since a specific date?");
		System.out.println("1. Display all orders");
		System.out.println("2. Display all orders since a specific date");
		int choice = getIntRange(1,2,"Enter the Corresponding Number: ");
		if(choice == 1) {

		} else {
			System.out.println("test");
		}

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
			int response = Integer.parseInt(reader.readLine());
			switch (response) {
				case 1:
					ret.setSize("small");
					size_picked = true;
					break;
				case 2:
					ret.setSize("medium");
					size_picked = true;
					break;
				case 3:
					ret.setSize("large");
					size_picked = true;
					break;
				case 4:
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
			int response = Integer.parseInt(reader.readLine());
			switch (response) {
				case 1:
					ret.setCrustType("Original");
					crust_picked = true;
					break;
				case 2:
					ret.setCrustType("Thin");
					crust_picked = true;
					break;
				case 3:
					ret.setCrustType("Pan");
					crust_picked = true;
					break;
				case 4:
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
