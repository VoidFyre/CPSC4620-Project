INSERT INTO topping
	(ToppingID, ToppingPrice, ToppingCost, ToppingInventory, ToppingAmountPS, ToppingAmountMD, ToppingAmountLG, ToppingAmountXL)
    VALUES
		('Pepperoni', 1.25, 0.2, 100, 2, 2.75, 3.5, 4.5),
        ('Sausage', 1.25, .15, 100, 2.5, 3, 3.5, 4.25),
        ('Ham', 1.5, .15, 78, 2, 2.5, 3.25, 4),
        ('Chicken', 1.75, .25, 56, 1.5, 2, 2.25, 3),
        ('Green Pepper', .5, .02, 79, 1, 1.5, 2, 2.5),
        ('Onion', .5, .02, 85, 1, 1.5, 2, 2.75),
        ('Roma Tomato', .75, .03, 86, 2, 3, 3.5, 4.5),
        ('Mushrooms', .75, .1, 52, 1.5, 2, 2.5, 3),
        ('Black Olives', .6, .1, 39, .75, 1, 1.5, 2),
        ('Pineapple', 1, .25, 15, 1, 1.25, 1.75, 2),
        ('Jalapenos', .5, .05, 64, .5, .75, 1.25, 1.75),
        ('Banana Peppers', .5, .05, 36, .6, 1, 1.3, 1.75),
        ('Regular Cheese', 1.5, .12, 250, 2, 3.5, 5, 7),
        ('Four Cheese Blend', 2, .15, 150, 2, 3.5, 5, 7),
        ('Feta Cheese', 2, .18, 75, 1.75, 3, 4, 5.5),
        ('Goat Cheese', 2, .2, 54, 1.6, 2.75, 4, 5.5),
        ('Bacon', 1.5, .25, 89, 1, 1.5, 2, 3);
        
INSERT INTO base
	(SizeID,CrustID,BasePrice,BaseCost)
	VALUES 
		('small','Thin','3','0.5'),
		('small','Original','3','0.75'),
		('small','Pan','3.5','1'),
		('small','Gluten-Free','4','2'),
		('medium','Thin','5','1'),
		('medium','Original','5','1.5'),
		('medium','Pan','6','2.25'),
		('medium','Gluten-Free','6.25','3'),
		('large','Thin','8','1.25'),
		('large','Original','8','2'),
		('large','Pan','9','3'),
		('large','Gluten-Free','9.5','4'),
		('x-large','Thin','10','2'),
		('x-large','Original','10','3'),
		('x-large','Pan','11.5','4.5'),
		('x-large','Gluten-Free','12.5','6');
        
INSERT INTO discount
	(DiscountID,DiscountAmount,DiscountPercent)
	VALUES
		('Employee',15,1),
		('Lunch Special Medium',1,0),
		('Lunch Special Large',2,0),
		('Specialty Pizza',1.5,0),
		('Gameday Special',20,1);
        
-- INSERT INTO customer
-- 	(CustomerFirstName, CustomerLastName, CustomerPhone, CustomerAddress)
-- 	VALUES
-- 		('Andrew', 'Wilkes-Krier', '864-254-5861', '115 Party Blvd, Anderson SC 29621'),
-- 		('Matt', 'Engers', '864-474-9953', null),
-- 		('Frank', 'Turner', '864-232-8944', '6745 Wessex St, Anderson SC 29621'),
-- 		('Milo', 'Auckerman', '864-878-5679', '8879 Suburban Home, Anderson SC 29621');
        
        
-- ORDER 1
        
INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'dine-in', '2022-03-05 12:03:00', 0, 0, 1, 14);
        
CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Thin',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        1),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Sausage',
        0);
        
CALL UPDATEPIZZATOPPINGS();

UPDATE customer_order
SET OrderPrice = (
	SELECT SUM(PizzaPrice)
    FROM pizza
    WHERE OrderID = (SELECT MAX(OrderID) FROM pizza)), 
    OrderCost = (
    SELECT SUM(PizzaCost)
    FROM pizza
    WHERE OrderID = (SELECT MAX(OrderID) FROM pizza))
WHERE OrderID = (SELECT MAX(OrderID) FROM pizza);
    
    
-- ORDER 2
    
    
INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'dine-in', '2022-04-03 12:05:00', 0, 0, 1, 4);
        
CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Pan',
    'medium'
    );
        
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Feta Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Black Olives',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Roma Tomato',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Mushrooms',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Banana Peppers',
        0);

INSERT INTO discount_pizza
	(PizzaID, DiscountID)
	VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Lunch Special Medium'
	),
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Specialty Pizza'
	);
    
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'small'
    );

INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Chicken',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Banana Peppers',
        0);

CALL UPDATEPIZZATOPPINGS();

CALL UPDATEORDERIN();


-- ORDER 3

INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'dine-out', '2022-03-03 21:30:00', 0, 0, 1, null);
        
CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Regular Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDCUSTOMER('Andrew', 'Wilkes-Krier', '864-254-5861', null);

CALL UPDATEORDEROUT('864-254-5861');

-- ORDER 4

INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'delivery', '2022-04-20 19:11:00', 0, 0, 1, null);

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'x-large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Four Cheese Blend',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pepperoni',
        0), 
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Sausage',
        0);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'x-large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Four Cheese Blend',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Ham',
        1), 
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Pineapple',
        1);
        
INSERT INTO discount_pizza
	(PizzaID, DiscountID)
	VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Specialty Pizza'
	);
        
CALL UPDATEPIZZATOPPINGS();

CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Original',
    'x-large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Four Cheese Blend',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Jalapenos',
        0), 
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Bacon',
        0);

CALL UPDATEPIZZATOPPINGS();

INSERT INTO discount_order
	(OrderID, DiscountID)
    VALUES
		((SELECT MAX(OrderID)
		FROM customer_order),
        'Gameday Special'
	);
    
CALL UPDATECUSTOMERADDRESS('864-254-5861', '115 Party Blvd, Anderson SC 29621');
    
CALL UPDATEORDEROUT('864-254-5861');

-- ORDER 5

INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'dine-out', '2022-03-02 17:30:00', 0, 0, 1, null);
        
CALL ADDPIZZA(
	(SELECT MAX(OrderID) FROM customer_order),
    'Gluten-Free',
    'x-large'
    );
        
INSERT INTO pizza_topping
	(PizzaID, ToppingID, PizzaToppingExtra)
    VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Goat Cheese',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Green Pepper',
        0), 
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Onion',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Roma Tomato',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Mushrooms',
        0),
        ((SELECT MAX(PizzaID)
        FROM pizza),
        'Black Olives',
        0);
        
INSERT INTO discount_pizza
	(PizzaID, DiscountID)
	VALUES
		((SELECT MAX(PizzaID)
        FROM pizza),
        'Specialty Pizza'
	);

CALL UPDATEPIZZATOPPINGS();

CALL ADDCUSTOMER('Matt', 'Engers', '864-474-9953', null);
        
CALL UPDATEORDEROUT('864-474-9953');

-- ORDER 6

