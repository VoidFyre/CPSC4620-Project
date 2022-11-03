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
        
INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'dine-in', '2022-03-05 12:03:00', 0, 0, 1, 14);
        
INSERT INTO pizza
	(CrustID, SizeID, OrderID, PizzaPrice, PizzaCost, PizzaDate, PizzaComplete)
	VALUES(
		'Thin',
        'large',
        (SELECT MAX(OrderID)
        FROM customer_order),
        0,
        0,
        (SELECT CONVERT(OrderTime, DATE) as PizzaDate
        FROM customer_order
        WHERE OrderID = (SELECT MAX(OrderID) FROM customer_order)),
        1);
        
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
        
UPDATE pizza
SET PizzaPrice = (
	SELECT
	BasePrice + ToppingPriceTotal - DiscountTotal AS PizzaPrice
	FROM 
		(SELECT
			SUM((ToppingPrice * (1 + pizza_topping.PizzaToppingExtra))) AS ToppingPriceTotal,
			(SELECT
				BasePrice
				FROM
					base,
					pizza
				WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID
			) AS BasePrice
		FROM 
			topping,
			pizza_topping
		WHERE
			pizza_topping.ToppingID = topping.ToppingID AND PizzaID = (SELECT MAX(PizzaID) FROM pizza)
		) AS pizzaTemp,
		(SELECT
			COALESCE(SUM(DiscountAmount), 0) AS DiscountTotal
		FROM discount_pizza, pizza, discount
		WHERE discount.DiscountID = discount_pizza.DiscountID AND pizza.PizzaID = discount_pizza.PizzaID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
		) AS discountTemp
    ),
    PizzaCost = (SELECT
    BaseCost + ToppingCostTotal AS PizzaCost
FROM 
	(
		SELECT
			CASE pizza.SizeID 
				WHEN 'small' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountPS)),2) 
				WHEN 'medium' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountMD)),2)
				WHEN 'large' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountLG)),2) 
				WHEN 'x-large' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountXL)),2) 
			END AS ToppingCostTotal,
			(SELECT
				BaseCost
				FROM
					base,
					pizza
				WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
			) AS BaseCost
		FROM 
			pizza_topping
			JOIN topping ON topping.ToppingID = pizza_topping.ToppingID
			JOIN pizza ON pizza_topping.PizzaID = pizza.PizzaID
		WHERE
			pizza_topping.ToppingID = topping.ToppingID AND pizza.PizzaID = (SELECT MAX(PizzaID) FROM pizza)
	) AS temp
)
WHERE PizzaID = (SELECT MAX(PizzaID) FROM pizza_topping);

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
    
    
INSERT INTO customer_order
	(CustomerID, OrderType, OrderTime, OrderPrice, OrderCost, OrderComplete, OrderTableNumber)
    VALUES
		(null, 'dine-in', '2022-04-03 12:05:00', 0, 0, 1, 4);
        
INSERT INTO pizza
	(CrustID, SizeID, OrderID, PizzaPrice, PizzaCost, PizzaDate, PizzaComplete)
	VALUES(
		'Pan',
        'medium',
        (SELECT MAX(OrderID)
        FROM customer_order),
        0,
        0,
        (SELECT CONVERT(OrderTime, DATE) as PizzaDate
        FROM customer_order
        WHERE OrderID = (SELECT MAX(OrderID) FROM customer_order)),
        1);
        
        
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
    
UPDATE pizza
SET PizzaPrice = (
	SELECT
		BasePrice + ToppingPriceTotal - DiscountTotal AS PizzaPrice
	FROM 
		(SELECT
			ROUND(SUM((ToppingPrice * (1 + pizza_topping.PizzaToppingExtra))),2) AS ToppingPriceTotal,
			(SELECT
				BasePrice
				FROM
					base,
					pizza
				WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
			) AS BasePrice
		FROM 
			topping,
			pizza_topping
		WHERE
			pizza_topping.ToppingID = topping.ToppingID AND PizzaID = (SELECT MAX(PizzaID) FROM pizza)
		) AS pizzaTemp,
		(SELECT
			COALESCE(SUM(DiscountAmount), 0) AS DiscountTotal
		FROM discount_pizza, pizza, discount
		WHERE discount.DiscountID = discount_pizza.DiscountID AND pizza.PizzaID = discount_pizza.PizzaID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
		) AS discountTemp
    ),
    PizzaCost = (
		SELECT
			BaseCost + ToppingCostTotal AS PizzaCost
		FROM 
			(
				SELECT
					CASE pizza.SizeID 
						WHEN 'small' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountPS)),2) 
						WHEN 'medium' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountMD)),2)
						WHEN 'large' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountLG)),2) 
						WHEN 'x-large' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountXL)),2) 
					END AS ToppingCostTotal,
					(SELECT
						BaseCost
						FROM
							base,
							pizza
						WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
					) AS BaseCost
				FROM 
					pizza_topping
					JOIN topping ON topping.ToppingID = pizza_topping.ToppingID
					JOIN pizza ON pizza_topping.PizzaID = pizza.PizzaID
				WHERE
					pizza_topping.ToppingID = topping.ToppingID AND pizza.PizzaID = (SELECT MAX(PizzaID) FROM pizza)
			) AS temp
		)
WHERE PizzaID = (SELECT MAX(PizzaID) FROM pizza_topping);

INSERT INTO pizza
	(CrustID, SizeID, OrderID, PizzaPrice, PizzaCost, PizzaDate, PizzaComplete)
	VALUES(
		'Original',
        'small',
        (SELECT MAX(OrderID)
        FROM customer_order),
        0,
        0,
        (SELECT CONVERT(OrderTime, DATE) as PizzaDate
        FROM customer_order
        WHERE OrderID = (SELECT MAX(OrderID) FROM customer_order)),
        1);

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

UPDATE pizza
SET PizzaPrice = (
	SELECT
		BasePrice + ToppingPriceTotal - DiscountTotal AS PizzaPrice
	FROM 
		(SELECT
			ROUND(SUM((ToppingPrice * (1 + pizza_topping.PizzaToppingExtra))),2) AS ToppingPriceTotal,
			(SELECT
				BasePrice
				FROM
					base,
					pizza
				WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
			) AS BasePrice
		FROM 
			topping,
			pizza_topping
		WHERE
			pizza_topping.ToppingID = topping.ToppingID AND PizzaID = (SELECT MAX(PizzaID) FROM pizza)
		) AS pizzaTemp,
		(SELECT
			COALESCE(SUM(DiscountAmount), 0) AS DiscountTotal
		FROM discount_pizza, pizza, discount
		WHERE discount.DiscountID = discount_pizza.DiscountID AND pizza.PizzaID = discount_pizza.PizzaID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
		) AS discountTemp
    ),
    PizzaCost = (
		SELECT
			BaseCost + ToppingCostTotal AS PizzaCost
		FROM 
			(
				SELECT
					CASE pizza.SizeID 
						WHEN 'small' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountPS)),2) 
						WHEN 'medium' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountMD)),2)
						WHEN 'large' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountLG)),2) 
						WHEN 'x-large' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountXL)),2) 
					END AS ToppingCostTotal,
					(SELECT
						BaseCost
						FROM
							base,
							pizza
						WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
					) AS BaseCost
				FROM 
					pizza_topping
					JOIN topping ON topping.ToppingID = pizza_topping.ToppingID
					JOIN pizza ON pizza_topping.PizzaID = pizza.PizzaID
				WHERE
					pizza_topping.ToppingID = topping.ToppingID AND pizza.PizzaID = (SELECT MAX(PizzaID) FROM pizza)
			) AS temp
		)
WHERE PizzaID = (SELECT MAX(PizzaID) FROM pizza_topping);

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