CREATE TABLE customer_order (
	OrderID INT NOT NULL AUTO_INCREMENT,
    CustomerID INT NOT NULL,
    OrderType INT NOT NULL,
    OrderTime INT NOT NULL,
    OrderCost INT NOT NULL,
    OrderComplete INT NOT NULL,
    PRIMARY KEY (OrderID),
    FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID)
);

CREATE TABLE customer (
	CustomerID INT NOT NULL AUTO_INCREMENT,
	CustomerFirstName VARCHAR(20) NOT NULL,
    CustomerLastName VARCHAR(20) NOT NULL,
    CustomerPhone VARCHAR(20) NOT NULL,
    CustomerAddress VARCHAR(20),
    PRIMARY KEY (CustomerID)
);

CREATE TABLE discount (
	DiscountID INT NOT NULL AUTO_INCREMENT,
    DiscountName VARCHAR(20) NOT NULL,
    DiscountType VARCHAR(20) NOT NULL,
    DiscountAmount FLOAT NOT NULL,
    DiscountPercent BOOL NOT NULL,
    PRIMARY KEY (DiscountID)
);

CREATE TABLE discount_order (
	OrderID INT NOT NULL,
    DiscountID INT NOT NULL,
    PRIMARY KEY (OrderID, DiscountID),
    FOREIGN KEY (OrderID) REFERENCES customer_order(OrderID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID)
);

CREATE TABLE discount_pizza (
	PizzaID INT NOT NULL,
    DiscountID INT NOT NULL,
    PRIMARY KEY (PizzaID, DiscountID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (DiscountID) REFERENCES discount(DiscountID)
);

CREATE TABLE topping (
	ToppingID INT NOT NULL AUTO_INCREMENT,
    ToppingName VARCHAR(20) NOT NULL,
    ToppingPrice INT NOT NULL,
    ToppingAmountPS INT NOT NULL,
    ToppingAmountMD INT NOT NULL,
    ToppingAmountLG INT NOT NULL,
    ToppingAmountXL INT NOT NULL,
    ToppingCost FLOAT NOT NULL,
    ToppingInventoryCurrent INT NOT NULL,
    ToppingInventoryMinimum INT NOT NULL,
    PRIMARY KEY (ToppingID)
);

CREATE TABLE pizza_topping (
	PizzaID INT NOT NULL,
    ToppingID INT NOT NULL,
    PRIMARY KEY (PizzaID, ToppingID),
    FOREIGN KEY (PizzaID) REFERENCES pizza(PizzaID),
    FOREIGN KEY (ToppingID) REFERENCES topping(ToppingID)
);

CREATE TABLE pizza (
    PizzaID INT NOT NULL AUTO_INCREMENT,
    CrustID VARCHAR(20),
    SizeID VARCHAR(20),
    OrderID INT,
    PizzaCost INT,
    PizzaPrice INT,
    PizzaDate DATE,
    PizzaComplete BOOL,
    PRIMARY KEY (PizzaID),
    FOREIGN KEY (CrustID) REFERENCES base(CrustID),
    FOREIGN KEY (SizeID) REFERENCES base(SizeID),
    FOREIGN KEY (OrderID) REFERENCES customer_order(OrderID)
);

CREATE TABLE base (
    SizeID VARCHAR(20),
    CrustID VARCHAR(20),
    BasePrice INT,
    BaseCost INT,
    PRIMARY KEY (SizeID),
    PRIMARY KEY (CrustID)
); 