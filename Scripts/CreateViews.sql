DROP VIEW IF EXISTS ToppingPopularity;

CREATE VIEW ToppingPopularity AS
SELECT
	topping.ToppingName,
    COUNT(pizza_topping.ToppingID) AS ToppingCount
    FROM topping, pizza_topping
    WHERE topping.ToppingID = pizza_topping.ToppingID
    GROUP BY pizza_topping.ToppingID;
    
SELECT * FROM ToppingPopularity