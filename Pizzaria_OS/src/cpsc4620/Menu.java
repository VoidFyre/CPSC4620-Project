package cpsc4620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
<<<<<<< Updated upstream
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
=======
		int menuChoice;
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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
		System.out.println("9. Exit\n\n");
		System.out.println("Enter your option: ");
=======
		System.out.println("9. Exit");
		System.out.println("-------------------------------\n");
		int menuOption;
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
>>>>>>> Stashed changes
	}

	// allow for a new order to be placed
<<<<<<< Updated upstream
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
		Customer customer = null;
		boolean customer_found = false;
=======

	public static Customer getCustomer() throws SQLException, IOException{
		Customer customer = null;
		boolean customer_found = false;
		boolean switch_to_add = false;
>>>>>>> Stashed changes
		while (!customer_found) {
			System.out.println("Are You an Existing Customer or a New Customer?");
			System.out.println("1. Existing Customer");
			System.out.println("2. New Customer");
			System.out.println("Enter the Corresponding Number.");
			String newcustomer = reader.nextLine();
			ArrayList<Customer> customer_list;
<<<<<<< Updated upstream
			switch(newcustomer) {
				//Existing Customer
				case "1":
					boolean customer_number_found = false;
					customer_list = DBNinja.getCustomerList();
					while (!customer_number_found) {
						System.out.println("List of Customers:");
						viewCustomers();
						System.out.println("Enter Your Customer ID");
						String CustID = reader.nextLine();
						//check if customer exists
						for (Customer cus: customer_list) {
							if (cus.getCustID() == Integer.parseInt(CustID)) {
								customer = cus;
=======
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
								System.out.println("2. Enter customer ID");
								System.out.println("3. Return to Main Menu");
								int cnfChoice = getIntRange(1, 3, "Enter the Corresponding Number: ");
								switch (cnfChoice) {
									case 1:
										switch_to_add = true;
										newCustomer = 2;
										customer_number_found = true;
										System.out.println();
										break;
									case 2:
										System.out.println();
										break;
									case 3:
										 return null;

								}
>>>>>>> Stashed changes
							}
							customer_number_found = true;
							break;
						}
						if (!customer_number_found) {
							System.out.println("Invalid Selection. Try Again.");
						}
<<<<<<< Updated upstream
					}
					break;

				//New Customer
				case "2":
					customer = EnterCustomer();
					customer_list = DBNinja.getCustomerList();
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
=======
						break;

					//New Customer
					case 2:
						switch_to_add = false;
						customer = EnterCustomer();
						customer_found = true;
						break;
					default:
						System.out.println("Invalid Selection. Try Again.");
				}
			}while(switch_to_add);
		}
		return customer;
	}
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

		//Setting up an order

		Customer customer = null;
		System.out.println("\nWhat Type of Order is This?");
>>>>>>> Stashed changes
		boolean got_order_type = false;
		Order order = null;
		int tableNum = 0;
		while (!got_order_type) {
			System.out.println("1. Dine-in");
			System.out.println("2. Pickup");
			System.out.println("3. Delivery");
<<<<<<< Updated upstream
			System.out.println("Enter the Corresponding Number.");
			String order_type = reader.nextLine();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date date = new Date();
			String dateString = formatter.format(date);
			switch (order_type) {
				case "1":
					order = new DineinOrder(0, customer.getCustID(), dateString,  0, 0, 0, 0);
					got_order_type = true;
					break;
				case "2":
					order = new PickupOrder(0, customer.getCustID(), dateString, 0, 0, 0, 0);
					got_order_type = true;
					break;
				case "3":
					String address;
					System.out.println("What Is The Address for This Delivery?");
					while ((address = reader.nextLine()) == null) {
						System.out.println("What Is The Address for This Delivery?");
=======
			int order_type = getIntRange(1,3,"Enter the Corresponding Number: ");
			System.out.println();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateString = formatter.format(date);
			switch (order_type) {
				case 1:
					while(tableNum == 0) {
						tableNum = getInt("Enter your table number: ");
						if (tableNum <= 0) {
							System.out.println("Your table number must be at least 1.");
							tableNum = 0;
						}
					}
					order = new DineinOrder(0, 0, dateString,  0, 0, 0, tableNum);
					got_order_type = true;
					break;
				case 2:
					if ((customer = getCustomer()) == null){return;}
					order = new PickupOrder(0, customer.getCustID(), dateString, 0, 0, 0, 0);
					got_order_type = true;
					break;
				case 3:
					if ((customer = getCustomer()) == null){return;}
					String address = customer.getAddress();
					int newAddr = 1;
					if (address != null) {
						System.out.println("This account has a registered address:\n" + address);
						System.out.println("Would you like to change the address?");
						System.out.println("1. Yes");
						System.out.println("2. No");
						newAddr = getIntRange(1, 2, "Enter the Corresponding Number: ");
					}
					if (newAddr == 1) {
						address = getString("Enter the address for this delivery: ");
						DBNinja.updateCustomerAddress(customer, address);
>>>>>>> Stashed changes
					}

					order = new DeliveryOrder(0, customer.getCustID(), dateString, 0, 0, 0, address);
					got_order_type = true;
					customer.setAddress(address);
			}
<<<<<<< Updated upstream
			order.setOrderID(DBNinja.updateOrder(order));
=======
			order.setOrderID(DBNinja.addOrder(order, tableNum));
>>>>>>> Stashed changes
		}
		
		//creating a pizza
		ArrayList<Pizza> pizzaList = new ArrayList<>();
		boolean all_pizza_added = false;
		while(!all_pizza_added) {
			pizzaList.add(buildPizza(order.getOrderID()));
			boolean add_another_pizza = false;
			while (!add_another_pizza) {
				System.out.println("Would You Like To Add Another Pizza?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				int response = getIntRange(1, 2, "Enter the Corresponding Number: ");
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

		for (Pizza p: pizzaList) {
			DBNinja.addPizza(p);
		}

		//Adding discounts
		ArrayList<Discount> discountList;

		System.out.println("Would you like to add any discounts to this order?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		int addDiscounts = getIntRange(1, 2, "Enter the Corresponding Number: ");
		if (addDiscounts == 1) {
			discountList = getDiscounts();
			order.setDiscountList(discountList);
		}

		if(customer == null) {
			DBNinja.updateOrder(order, "000-000-0000");
		} else {
			DBNinja.updateOrder(order,customer.getPhone());
		}


		System.out.println("Finished adding order...Returning to menu...");
	}
	
	
	public static void viewCustomers() throws SQLException, IOException {
		/*
		 * Simply print out all the customers from the database.
		 */
		ArrayList<Customer> customers = DBNinja.getCustomerList();
		
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
		String nameFirst = reader.nextLine();
		System.out.println("Enter Your Last Name");
		String nameLast = reader.nextLine();
		boolean newPhone = false;
		String phone_number = null;
		while (!newPhone) {
			System.out.println("Enter Your Phone Number (###-###-####)");
			while (!(reader.hasNext("\\d{4}-\\d{2}-\\d{2}"))) {
				System.out.println("Invalid Input. Check Your Formatting And Try Again.");
				System.out.println("Enter Your Phone Number (###-###-####)");
				reader.nextLine();
			}
			boolean phone_found = false;
			phone_number = reader.nextLine();
			for (Customer cus: DBNinja.getCustomerList()) {
				if (cus.getPhone().equals(phone_number)) {
					phone_found = true;
					break;
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
		DBNinja.addCustomer(customer);
		reader.close();
		return customer;
	}

	// View any orders that are not marked as completed
	public static void ViewOrders()
	{
	/*
	 * This should be subdivided into two options: print all orders (using simplified view) and print all orders (using simplified view) since a specific date.
	 * 
	 * Once you print the orders (using either sub option) you should then ask which order I want to see in detail
	 * 
	 * When I enter the order, print out all the information about that order, not just the simplified view.
	 * 
	 */
		Scanner reader = new Scanner(System.in);
		System.out.println("Would you like to:\n(a) display all orders\n(b) display all orders since a specific date" );
		
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
		System.out.println("Printing a list of incomplete orders...");

		
		
		
		
		

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

	public static Pizza addTopping(Pizza p) throws SQLException, IOException {
		ArrayList<Topping> topList = DBNinja.getToppingList();

		System.out.println("Printing the list of toppings...");

		System.out.println("====================================");
		System.out.println("ID\t| Name");
		for (Topping t: topList) {
			System.out.println(t.getTopID() + "\t| " + t.getTopName());
		}
		System.out.println("====================================");
		int topID = getIntRange(1, topList.size(), "Select a topping to add: ");
		Topping top = topList.get(topID - 1);
		switch (p.getSize()){
			case "small":
				if(top.getPerAMT() <= top.getCurINVT()) {
					System.out.println("Would you like to double this topping?");
					System.out.println("1. Yes");
					System.out.println("2. No");
					int isDouble = getIntRange(1,2,"Enter the Corresponding Number: ");
					switch (isDouble) {
						case 1:
							if((top.getPerAMT() * 2) <= top.getCurINVT()) {
								top.setDoubled(true);
								p.addToppings(top, true);
							} else {
								System.out.println("This topping cannot currently be doubled due to low inventory.");
								System.out.println("Would you like to add this topping anyway?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								int addAnyway = getIntRange(1, 2, "Enter the Corresponding Number: ");
								if (addAnyway == 1) {
									top.setDoubled(false);
									p.addToppings(top, false);
								}
							}
							break;

						case 2:
							top.setDoubled(false);
							p.addToppings(top, false);
							break;
					}
				} else {
					System.out.println("This topping cannot currently added due to low inventory.");
					System.out.println("Press Enter to return to the pizza.");
					reader.nextLine();
					System.out.println();
				}
				break;
			case "medium":
				if(top.getMedAMT() <= top.getCurINVT()) {
					System.out.println("Would you like to double this topping?");
					System.out.println("1. Yes");
					System.out.println("2. No");
					int isDouble = getIntRange(1,2,"Enter the Corresponding Number: ");
					switch (isDouble) {
						case 1:
							if((top.getMedAMT() * 2) <= top.getCurINVT()) {
								top.setDoubled(true);
								p.addToppings(top, true);
							} else {
								System.out.println("This topping cannot currently be doubled due to low inventory.");
								System.out.println("Would you like to add this topping anyway?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								int addAnyway = getIntRange(1, 2, "Enter the Corresponding Number: ");
								if (addAnyway == 1) {
									top.setDoubled(false);
									p.addToppings(top, false);
								}
							}
							break;

						case 2:
							top.setDoubled(false);
							p.addToppings(top, false);
							break;
					}
				} else {
					System.out.println("This topping cannot currently added due to low inventory.");
					System.out.println("Press Enter to return to the pizza.");
					reader.nextLine();
					System.out.println();
				}
				break;
			case "large":
				if(top.getLgAMT() <= top.getCurINVT()) {
					System.out.println("Would you like to double this topping?");
					System.out.println("1. Yes");
					System.out.println("2. No");
					int isDouble = getIntRange(1,2,"Enter the Corresponding Number: ");
					switch (isDouble) {
						case 1:
							if((top.getLgAMT() * 2) <= top.getCurINVT()) {
								top.setDoubled(true);
								p.addToppings(top, true);
							} else {
								System.out.println("This topping cannot currently be doubled due to low inventory.");
								System.out.println("Would you like to add this topping anyway?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								int addAnyway = getIntRange(1, 2, "Enter the Corresponding Number: ");
								if (addAnyway == 1) {
									top.setDoubled(false);
									p.addToppings(top, false);
								}
							}
							break;

						case 2:
							top.setDoubled(false);
							p.addToppings(top, false);
							break;
					}
				} else {
					System.out.println("This topping cannot currently added due to low inventory.");
					System.out.println("Press Enter to return to the pizza.");
					reader.nextLine();
					System.out.println();
				}
				break;
			case "x-large":
				if(top.getXLAMT() <= top.getCurINVT()) {
					System.out.println("Would you like to double this topping?");
					System.out.println("1. Yes");
					System.out.println("2. No");
					int isDouble = getIntRange(1,2,"Enter the Corresponding Number: ");
					switch (isDouble) {
						case 1:
							if((top.getXLAMT() * 2) <= top.getCurINVT()) {
								top.setDoubled(true);
								p.addToppings(top, true);

							} else {
								System.out.println("This topping cannot currently be doubled due to low inventory.");
								System.out.println("Would you like to add this topping anyway?");
								System.out.println("1. Yes");
								System.out.println("2. No");
								int addAnyway = getIntRange(1, 2, "Enter the Corresponding Number: ");
								if (addAnyway == 1) {
									top.setDoubled(false);
									p.addToppings(top, false);
								}
							}
							break;

						case 2:
							top.setDoubled(false);
							p.addToppings(top, false);
							break;
					}
				} else {
					System.out.println("This topping cannot currently added due to low inventory.");
					System.out.println("Press Enter to return to the pizza.");
					reader.nextLine();
					System.out.println();
				}
				break;
		}
		return p;
	}

	public static ArrayList<Discount> getDiscounts() throws SQLException, IOException{
		ArrayList<Discount> discounts = DBNinja.getDiscountList();
		ArrayList<Discount> appliedDiscounts = new ArrayList<>();
		boolean got_discounts = false;
		while (!got_discounts) {
			System.out.println("====================================");
			System.out.println("ID\t|\tName");
			for (Discount d: discounts) {
				System.out.println(d.getDiscountID() + "\t|\t" + d.getDiscountName());
			}
			System.out.println("====================================");
			int discID = getIntRange(1,discounts.size(), "Select a discount ID to apply: ");
			appliedDiscounts.add(discounts.get(discID - 1));
			System.out.println("Would you like to add another discount?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int choice = getIntRange(1, 2, "Enter the Corresponding Number: ");
			if (choice == 2) {
				got_discounts = true;
			}
		}
		return appliedDiscounts;
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
		Pizza ret = new Pizza(0,null,null,orderID,null,null,0,0);

		//Picking size
		boolean size_picked = false;
		while (!size_picked) {
			System.out.println("What Size Pizza Would You Like?");
			System.out.println("1. Small");
			System.out.println("2. Medium");
			System.out.println("3. Large");
			System.out.println("4. Extra-Large");
			int response = getIntRange(1,4,"Enter the Corresponding Number: ");
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
			int response = getIntRange(1,4,"Enter the Corresponding Number: ");
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
		boolean got_toppings = false;
		while (!got_toppings) {
			ret = addTopping(ret);
			System.out.println("Would you like to add more toppings?");
			System.out.println("1. Yes");
			System.out.println("2. No");
			int addMoreToppings = getIntRange(1, 2, "Enter the Corresponding Number: ");
			if (addMoreToppings == 2) {
				got_toppings = true;
			}
		}

		//Adding discounts
		ArrayList<Discount> discountList;

		System.out.println("Would you like to add any discounts to this pizza?");
		System.out.println("1. Yes");
		System.out.println("2. No");
		int addDiscounts = getIntRange(1, 2, "Enter the Corresponding Number: ");
		if (addDiscounts == 1) {
			discountList = getDiscounts();
			ret.setDiscounts(discountList);
		}

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
