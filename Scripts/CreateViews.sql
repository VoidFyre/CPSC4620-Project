-- Written by Neil Kuehn, Christopher Doering
USE Pizzeria;

DROP VIEW IF EXISTS ToppingPopularity;

CREATE VIEW ToppingPopularity AS
SELECT
	topping.ToppingID,
    COUNT(pizza_topping.ToppingID) AS ToppingCount
FROM topping, pizza_topping
WHERE topping.ToppingID = pizza_topping.ToppingID
GROUP BY pizza_topping.ToppingID
ORDER BY ToppingCount DESC, topping.ToppingID ASC;

SELECT * FROM ToppingPopularity;
    
DROP VIEW IF EXISTS ProfitByPizza;

CREATE VIEW ProfitByPizza AS
SELECT
	profit.SizeID AS PizzaSize,
    profit.CrustID AS PizzaCrust,
    CAST(TypeProfit AS DECIMAL(6,2)) AS Profit,
    LastOrderDate
FROM
    (SELECT 
		SizeID,
        CrustID,
		ROUND(SUM(PizzaPrice-PizzaCost),2) AS TypeProfit,
        MAX(PizzaDate) AS LastOrderDate
	 FROM
		pizza
	 WHERE PizzaDate BETWEEN '2022-01-01' AND '2022-12-31'
	 GROUP BY SizeID,CrustID
	) AS profit
GROUP BY SizeID,CrustID
ORDER BY TypeProfit DESC;

SELECT * FROM ProfitByPizza;

DROP VIEW IF EXISTS ProfitByOrderType;
SELECT
    OrderType AS CustomerType,
    DATE_FORMAT(OrderTime,'%Y-%M') AS OrderDate,
    ROUND(SUM(OrderPrice),2) as TotalOrderPrice,
    ROUND(Sum(OrderCost),2) as TotalOrderCost,
    ROUND(Sum(OrderPrice-OrderCost),2) as Profit
FROM
    customer_order
GROUP BY 
    MONTH(OrderTime),
    OrderType
UNION all
select
    NULL,
    'Grand Total',
    round(sum(OrderPrice),2) as TotalOrderPrice,
    round(sum(OrderCost),2) as TotalOrderCost,
    round(sum(OrderPrice-OrderCost),2) as Profit
from
    customer_order
    
	
	

