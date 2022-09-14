SHOW DATABASES;
DROP DATABASE IF EXISTS Wholesale;
CREATE DATABASE IF NOT EXISTS Wholesale;
SHOW DATABASES;
USE Wholesale;

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    CustomerID VARCHAR(15),
    CustomerTitle VARCHAR(15),
    CustomerName VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    CustomerAddress VARCHAR(45),
    City VARCHAR(30),
    Province VARCHAR(45),
    PostalCode VARCHAR(15),
    CONSTRAINT PRIMARY KEY (CustomerID)
    );
SHOW TABLES;
DESCRIBE Customer;
#==============================================================
DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
    OrderID VARCHAR(15),
    OrderDate DATE,
    CustomerID VARCHAR(15)default 0,
    CONSTRAINT PRIMARY KEY (OrderID),
    CONSTRAINT FOREIGN KEY (CustomerID) REFERENCES Customer (CustomerID) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE Orders;
#==============================================================
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    ItemCode VARCHAR(15),
    Description VARCHAR(50),
    PackSize VARCHAR(15),
    UnitPrice DOUBLE DEFAULT 0.00,
    QtyOnHand INT(5),
    Discount DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (ItemCode)
    );
SHOW TABLES;
DESCRIBE Item;
#==============================================================
DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
    OrderID VARCHAR(15),
    ItemCode VARCHAR(15),
    UnitPrice DECIMAL(6,2),
    OrderQty INT(11),
    Discount DECIMAL(6,2),
    CONSTRAINT PRIMARY KEY (OrderID, ItemCode),
    CONSTRAINT FOREIGN KEY (OrderID) REFERENCES Orders (OrderID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES Item (ItemCode) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES;
DESCRIBE OrderDetail;