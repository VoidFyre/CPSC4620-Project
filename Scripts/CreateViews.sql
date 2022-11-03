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
    CAST(TypeProfit AS DECIMAL(5,2)) AS Profit,
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

Select * from ProfitByPizza;

DROP VIEW IF EXISTS ProfitByOrderType;
Create VIEW ProfitByOrderType AS 
SELECT
	OrderType AS CustomerType,
	if(grouping(OrderType),'Grand Total',(DATE_FORMAT(OrderTime,'%Y-%M'))) AS OrderDate,
    ROUND(SUM(OrderPrice),2) as TotalOrderPrice,
	ROUND(Sum(OrderCost),2) as TotalOrderCost,
	ROUND(Sum(OrderPrice-OrderCost),2) as Profit
FROM
    customer_order
GROUP BY OrderType WITH ROLLUP
ORDER BY OrderType DESC;

SELECT * FROM ProfitByOrderType;
    
	
	

