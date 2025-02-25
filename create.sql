-- Create Database
CREATE DATABASE Energizer_Database;
GO
USE Energizer_Database;
GO

-- Customers Table
CREATE TABLE Customers (
    CustomerID INT IDENTITY(1,1) PRIMARY KEY,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    Email NVARCHAR(100),
    Phone NVARCHAR(20),
    Address NVARCHAR(255)
);

-- Employees Table
CREATE TABLE Employees (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    Position NVARCHAR(100),
    Email NVARCHAR(100),
    Phone NVARCHAR(20)
);

-- Products (Batteries) Table
CREATE TABLE Products (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(100),
    Category NVARCHAR(50),
    Price DECIMAL(10,2),
    StockQuantity INT,
	[images] NVARCHAR(50),
	Description NVARCHAR(255)	
);

-- Orders Table
CREATE TABLE Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerID INT,
    OrderDate DATETIME DEFAULT GETDATE(),
    TotalAmount DECIMAL(10,2),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

-- Order_Items Table
CREATE TABLE Order_Items (
    OrderItemID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT,
    Subtotal DECIMAL(10,2),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Suppliers Table
CREATE TABLE Suppliers (
    SupplierID INT IDENTITY(1,1) PRIMARY KEY,
    SupplierName NVARCHAR(100),
    ContactName NVARCHAR(100),
    Phone NVARCHAR(20),
    Email NVARCHAR(100)
);

-- Inventory Table
CREATE TABLE Inventory (
    InventoryID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT,
    SupplierID INT,
    StockLevel INT,
    LastUpdated DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID)
);
USE Energizer_Database;
GO

-- Insert into Customers
INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES
('John', 'Doe', 'johndoe@email.com', '123-456-7890', '123 Main St, NY'),
('Jane', 'Smith', 'janesmith@email.com', '987-654-3210', '456 Oak St, CA'),
('Michael', 'Brown', 'michaelbrown@email.com', '555-666-7777', '789 Pine St, TX');

-- Insert into Employees
INSERT INTO Employees (FirstName, LastName, Position, Email, Phone) VALUES
('Alice', 'Johnson', 'Manager', 'alice.johnson@email.com', '111-222-3333'),
('Bob', 'Williams', 'Sales Associate', 'bob.williams@email.com', '444-555-6666'),
('Charlie', 'Miller', 'Technician', 'charlie.miller@email.com', '777-888-9999');

-- Insert into Products (Batteries)
INSERT INTO Products (ProductName, Category, Price, StockQuantity, Images, Description) VALUES
-- 🔋 Alkaline Batteries
('Energizer AA Battery', 'Alkaline', 5.99, 100, 'asset/images/AA_Battery.jpg', 'Reliable and long-lasting AA alkaline battery for everyday devices.'),
('Energizer AAA Battery', 'Alkaline', 4.99, 120, 'asset/images/AAA_Battery.jpg', 'Compact and powerful AAA alkaline battery for small electronics.'),
('Energizer 9V Battery', 'Alkaline', 8.99, 80, 'asset/images/9V_Battery.jpg', 'Ideal for smoke detectors and alarms, providing dependable power.'),
('Energizer C Battery', 'Alkaline', 6.99, 70, 'asset/images/C_Battery.jpg', 'Mid-size alkaline battery commonly used in flashlights and toys.'),
('Energizer D Battery', 'Alkaline', 7.99, 60, 'asset/images/D_Battery.jpg', 'Large alkaline battery, great for high-power devices like radios.'),

-- 🔋 Lithium Batteries
('Energizer Ultimate Lithium AA', 'Lithium', 10.99, 50, 'asset/images/Lithium_AA.jpg', 'Longest-lasting AA lithium battery, great for high-drain devices.'),
('Energizer Ultimate Lithium AAA', 'Lithium', 9.99, 60, 'asset/images/Lithium_AAA.jpg', 'Best-in-class AAA lithium battery for digital cameras and gaming accessories.'),
('Energizer CR2032 Coin Battery', 'Lithium', 3.49, 150, 'asset/images/CR2032_Battery.jpg', 'Common coin cell battery used in watches and key fobs.'),
('Energizer CR2025 Coin Battery', 'Lithium', 3.29, 130, 'asset/images/CR2025_Battery.jpg', 'Thin coin battery ideal for calculators and medical devices.'),
('Energizer CR2016 Coin Battery', 'Lithium', 2.99, 140, 'asset/images/CR2016_Battery.jpg', 'Small and reliable coin cell battery for remotes and wearables.'),
('Energizer 123A Lithium Battery', 'Lithium', 9.99, 90, 'asset/images/123A_Battery.jpg', 'High-power lithium battery used in cameras and security devices.'),

-- 🔋 Rechargeable Batteries
('Energizer Recharge AA', 'Rechargeable', 14.99, 50, 'asset/images/Recharge_AA.jpg', 'Rechargeable AA battery, eco-friendly and long-lasting.'),
('Energizer Recharge AAA', 'Rechargeable', 12.99, 60, 'asset/images/Recharge_AAA.jpg', 'AAA rechargeable battery, perfect for wireless gaming controllers.'),
('Energizer Recharge 9V', 'Rechargeable', 21.99, 30, 'asset/images/9V_Recharge_Battery.jpg', 'Larger rechargeable battery for high-performance electronics.'),

-- ⚡ Battery Chargers
('Energizer Recharge Pro Charger', 'Charger', 19.99, 40, 'asset/images/Recharge_Pro_Charger.jpg', 'Fast charging station for AA and AAA rechargeable batteries.'),
('Energizer Universal Charger', 'Charger', 29.99, 35, 'asset/images/Universal_Charger.jpg', 'Charges multiple battery sizes: AA, AAA, C, D, and 9V.'),
('Energizer Rapid Charger', 'Charger', 24.99, 50, 'asset/images/Rapid_Charger.jpg', 'Ultra-fast charger for quick power on the go.'),

-- 🔦 Flashlights
('Energizer LED Tactical Flashlight', 'Flashlight', 34.99, 25, 'asset/images/Tactical_Flashlight.jpg', 'High-performance tactical flashlight with durable construction.'),
('Energizer Compact LED Flashlight', 'Flashlight', 12.99, 60, 'asset/images/Compact_Flashlight.jpg', 'Small and lightweight LED flashlight for everyday carry.'),
('Energizer Rechargeable Headlamp', 'Flashlight', 22.99, 40, 'asset/images/Headlamp.jpg', 'Adjustable LED headlamp for hands-free lighting.'),
('Energizer Waterproof Lantern', 'Flashlight', 27.99, 20, 'asset/images/Waterproof_Lantern.jpg', 'Bright LED lantern with waterproof design for camping.'),

-- 🔋 Power Banks
('Energizer 10,000mAh Power Bank', 'Power Bank', 39.99, 30, 'asset/images/10000mAh_PowerBank.jpg', 'Compact and high-capacity power bank for mobile devices.'),
('Energizer 20,000mAh Power Bank', 'Power Bank', 59.99, 25, 'asset/images/20000mAh_PowerBank.jpg', 'Long-lasting power bank with fast charging support.'),
('Energizer Wireless Charging Power Bank', 'Power Bank', 49.99, 20, 'asset/images/Wireless_PowerBank.jpg', 'Wireless charging power bank with multiple USB ports.')
;

DELETE FROM Products 
WHERE ProductName = 'D Battery'


-- Insert into Orders
INSERT INTO Orders (CustomerID, OrderDate, TotalAmount) VALUES
(1, GETDATE(), 15.98),
(2, GETDATE(), 25.99),
(3, GETDATE(), 8.99);

-- Insert into Order_Items
INSERT INTO Order_Items (OrderID, ProductID, Quantity, Subtotal) VALUES
(1, 1, 2, 11.98),
(1, 2, 1, 4.99),
(2, 3, 1, 8.99),
(3, 4, 1, 12.99);

-- Insert into Suppliers
INSERT INTO Suppliers (SupplierName, ContactName, Phone, Email) VALUES
('Battery Co.', 'Mark Spencer', '123-111-2222', 'contact@batteryco.com'),
('Power Supply Ltd.', 'Susan Green', '456-333-4444', 'info@powersupply.com'),
('ElectroTech Inc.', 'David White', '789-555-6666', 'support@electrotech.com');

-- Insert into Inventory
INSERT INTO Inventory (ProductID, SupplierID, StockLevel, LastUpdated) VALUES
(1, 1, 100, GETDATE()),
(2, 2, 200, GETDATE()),
(3, 3, 50, GETDATE()),
(4, 1, 30, GETDATE());



