USE Project;

CREATE TABLE IF NOT EXISTS base (
    SizeID VARCHAR(20) NOT NULL,
    CrustID VARCHAR(20) NOT NULL,
    BasePrice FLOAT NOT NULL,
    BaseCost FLOAT NOT NULL,
    PRIMARY KEY (SizeID, CrustID)
); 

CREATE TABLE IF NOT EXISTS customer (
	CustomerID INT NOT NULL AUTO_INCREMENT,
	CustomerFirstName VARCHAR(20) NOT NULL,
    CustomerLastName VARCHAR(20) NOT NULL,
    CustomerPhone VARCHAR(20) NOT NULL,
    CustomerAddress VARCHAR(100),
    PRIMARY KEY (CustomerID)
);

CREATE TABLE IF NOT EXISTS customer_order (
	OrderID INT NOT NULL AUTO_INCREMENT,
    CustomerID INT,
    OrderType VARCHAR(20) NOT NULL,
    OrderTime DATETIME NOT NULL,
    OrderPrice FLOAT NOT NULL,
    OrderCost FLOAT NOT NULL,
    OrderComplete INT NOT NULL,
    OrderTableNumber INT,
    PRIMARY KEY (OrderID),
    FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID)
);

CREATE TABLE IF NOT EXISTS pizza (
    PizzaID INT NOT NULL AUTO_INCREMENT,
    CrustID VARCHAR(20) NOT NULL,
    SizeID VARCHAR(20) NOT NULL,
    OrderID INT NOT NULL,
    PizzaCost FLOAT NOT NULL,
    PizzaPrice FLOAT NOT NULL,
    PizzaDate DATE NOT NULL,
    PizzaComplete BOOL NOT NULL,
    PRIMARY KEY (PizzaID),
    FOREIGN KEY (SizeID, CrustID) REFERENCES base(SizeID, CrustID),
    FOREIGN KEY (OrderID) REFERENCES customer_order(OrderID)
);

CREATE TABLE IF NOT EXISTS discount (
    DiscountID VARCHAR(20) NOT NULL,
    DiscountAmount FLOAT NOT NULL,
    DiscountPercent BOOL NOT NULL,
    PRIMARY KEY (DiscountID)
);

CREATE TABLE IF NOT EXISTS discount_order (
	OrderID INT NOT NULL,
    DiscountID VARCHAR(20) NOT NULL,
    PRIMARY KEY (OrderID, DiscountID),
    FOREIGN KEY (OrderID) REFERENCES customer_order(OrderID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID)
);

CREATE TABLE IF NOT EXISTS discount_pizza (
	PizzaID INT NOT NULL,
    DiscountID VARCHAR(20) NOT NULL,
    PRIMARY KEY (PizzaID, DiscountID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID)
);

CREATE TABLE IF NOT EXISTS topping (
    ToppingID VARCHAR(20) NOT NULL,
    ToppingPrice FLOAT NOT NULL,
    ToppingAmountPS FLOAT NOT NULL,
    ToppingAmountMD FLOAT NOT NULL,
    ToppingAmountLG FLOAT NOT NULL,
    ToppingAmountXL FLOAT NOT NULL,
    ToppingCost FLOAT NOT NULL,
    ToppingInventory INT NOT NULL,
    PRIMARY KEY (ToppingID)
);

CREATE TABLE IF NOT EXISTS pizza_topping (
	PizzaID INT NOT NULL,
    ToppingID VARCHAR(20) NOT NULL,
    PizzaToppingExtra BOOL NOT NULL,
    PRIMARY KEY (PizzaID, ToppingID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (ToppingID) REFERENCES topping(ToppingID)
);

