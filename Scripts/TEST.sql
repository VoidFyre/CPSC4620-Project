-- SELECT
-- 	BasePrice + ToppingPriceTotal - DiscountTotal AS PizzaPrice
-- FROM 
-- 	(SELECT
-- 		ROUND(SUM((ToppingPrice * (1 + pizza_topping.PizzaToppingExtra))),2) AS ToppingPriceTotal,
-- 		(SELECT
-- 			BasePrice
-- 			FROM
-- 				base,
-- 				pizza
-- 			WHERE pizza.CrustID = base.CrustID AND pizza.SizeID = base.SizeID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
-- 		) AS BasePrice
-- 	FROM 
-- 		topping,
-- 		pizza_topping
-- 	WHERE
-- 		pizza_topping.ToppingID = topping.ToppingID AND PizzaID = (SELECT MAX(PizzaID) FROM pizza)
-- 	) AS pizzaTemp,
-- 	(SELECT
-- 		COALESCE(SUM(DiscountAmount), 0) AS DiscountTotal
-- 	FROM discount_pizza, pizza, discount
-- 	WHERE discount.DiscountID = discount_pizza.DiscountID AND pizza.PizzaID = discount_pizza.PizzaID AND pizza.PizzaID = (SELECT MAX(pizza.PizzaID) FROM pizza)
-- 	) AS discountTemp

SELECT
	CASE pizza.SizeID 
		WHEN 'personal' THEN ROUND(SUM(((ToppingCost * (1 + pizza_topping.PizzaToppingExtra)) * topping.ToppingAmountPS)),2) 
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