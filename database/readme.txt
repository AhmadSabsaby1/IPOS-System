Local MySQL database set up:
1. Install MySQL
2. Open MySQL Command Client
3. Log in with your password (if you have forgotten it, reinstall MySQL)
4. Set up database (bottom of file)

Link MySQL and Intellij:
1. (Intellij, right side) Database -> New -> Data Source -> MySQL
2. Set up with URL:
    2a. Fill in user (default is root), password and Database (choose iposca)
    2b. Apply (If errors occur, try refreshing)
3. Put the correct URL, username and password in DBParent class (Keep
the ?useSSL=false at the end, it did not work on my end without it)

Global Library set up:
1. File -> Project Structure -> Global Libraries
2. Press the leftmost +
3. From Maven
4. Enter mysql:mysql-connector-java:8.0.33 (or whichever version works with your local mysql installation)
5. Apply

The class DBParent is simply the setup. When you want to interact with a table, use the class that has its name.
Note that the joining tables (AccountHolders_Transactions and LocalStock_Transactions) do not have their own classes, as
their functionality is accessed via the DBTransactions class.

Note: These classes will NOT check for null values and other such errors. This is the job of the model class
you are using to interact with the database classes.

Any errors or lacking functionality found please send a message in the discord or through whatsapp directly

Database set up commands: (Paste these on your command line client to create the database; you will only need to do this once)

CREATE DATABASE IPOSCA;

USE IPOSCA

CREATE TABLE Users
(
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE AccountHolders
(
    accountID VARCHAR(7) NOT NULL,
    name VARCHAR(45) NOT NULL,
    address VARCHAR(45) NOT NULL,
    balance DOUBLE unsigned,
    balanceLimit INT unsigned,
    discountType VARCHAR(10),
    discount DOUBLE unsigned,
    tier1Discount DOUBLE unsigned,
    tier1Threshold INT unsigned,
    tier2Discount DOUBLE unsigned,
    tier2Threshold INT unsigned,
    tier3Discount DOUBLE unsigned,
    status VARCHAR(10),
    status1stReminder VARCHAR(7),
    status2ndReminder VARCHAR(7),
    phoneNum VARCHAR(20),
    email VARCHAR(45),
    PRIMARY KEY (accountID)
);

CREATE TABLE LocalStock
(
    itemID VARCHAR(20) NOT NULL,
    description VARCHAR(45) NOT NULL,
    packageType VARCHAR(12),
    unit VARCHAR(8),
    unitsInAPack INT unsigned,
    packageCost DOUBLE unsigned,
    availability INT unsigned,
    stockLimit INT unsigned,
    retailMarkUpRate INT unsigned,
    PRIMARY KEY (itemID)
);

CREATE TABLE Transactions
(
    orderID VARCHAR(8) NOT NULL,
    paymentType VARCHAR(4),
    amountReceived DOUBLE unsigned,
    cardType VARCHAR(10),
    firstFour INT unsigned,
    LastFour INT unsigned,
    expiryDate VARCHAR(10),
    shippingAddress VARCHAR(45),
    orderDate VARCHAR(10),
    totalCost DOUBLE unsigned,
    PRIMARY KEY (orderID)
);

CREATE TABLE AccountHolders_Transactions
(
    orderID VARCHAR(8) NOT NULL,
    accountID VARCHAR(7) NOT NULL,
    FOREIGN KEY (orderID) REFERENCES Transactions(orderID),
    FOREIGN KEY (accountID) REFERENCES AccountHolders(accountID),
    PRIMARY KEY (orderID, accountID)
);

CREATE TABLE LocalStock_Transactions
(
    itemID VARCHAR(20) NOT NULL,
    orderID VARCHAR(8) NOT NULL,
    quantity INT unsigned NOT NULL,
    FOREIGN KEY (itemID) REFERENCES LocalStock(itemID),
    FOREIGN KEY (orderID) REFERENCES Transactions(orderID),
    PRIMARY KEY (itemID, orderID)
);

SAMPLE DATA INSERT: (This can be done any time after the database is created as long as no primary keys are duplicated)

INSERT INTO Users VALUES
('sysdba', 'masterkey', 'ADMIN'),
('manager', 'Get_it_done', 'MANAGER'),
('accountant', 'Count_money', 'PHARMACIST'),
('clerk', 'Paperwork', 'PHARMACIST');

INSERT INTO AccountHolders VALUES
('ACC0001', 'Ms Eva Bauyer', '1, Liverpool street, London EC2V 8NS', 79.74, 500, 'fixed', 0.03, 0, 0, 0, 0, 0, 'suspended', 'sent', 'no_need', '02073218001', 'evabauyer@gmail.com'),
('ACC0002', 'Ms Glynne Morisson', '1, Liverpool street, London EC2V 8NS', 0, 500, 'flexible', 0, 0, 100, 0.01, 300, 0.02, 'normal', 'no_need', 'no_need', '02073218001', 'morrisonglynne@gmail.com');

INSERT INTO LocalStock VALUES
('10000001', 'Paracetamol', 'Box', 'Caps', 20, 0.10, 131, 10, 100),
('10000002', 'Aspirin', 'Box', 'Caps', 20, 0.50, 197, 15, 100),
('10000003', 'Analgin', 'Box', 'Caps', 10, 1.20, 36, 10, 100),
('10000004', 'Celebrex, caps 100 mg', 'Box', 'Caps', 10, 10.00, 37, 10, 100),
('10000005', 'Celebrex, caps 200 mg', 'Box', 'Caps', 10, 18.50, 34, 5, 100),
('10000006', 'Retin-A Tretin, 30 g', 'Box', 'Caps', 20, 25.00, 24, 10, 100),
('10000007', 'Lipitor TB, 20 mg', 'Box', 'Caps', 30, 15.50, 9, 10, 100),
('10000008', 'Claritin CR, 60g', 'Box', 'Caps', 20, 19.50, 20, 10, 100),
('20000004', 'Iodine tincture', 'Bottle', 'Ml', 100, 0.30, 33, 10, 100),
('20000005', 'Rhynol', 'Bottle', 'Ml', 200, 2.50, 32, 15, 100),
('30000001', 'Ospen', 'Box', 'Caps', 20, 10.50, 84, 10, 100),
('30000002', 'Amopen', 'Box', 'Caps', 30, 15.00, 125, 15, 100),
('40000001', 'Vitamin C', 'Box', 'Caps', 30, 1.20, 18, 15, 100),
('40000002', 'Vitamin B12', 'Box', 'Caps', 30, 1.30, 57, 15, 100);

INSERT INTO Transactions VALUES
('IP0001', 'Card', 0, '', 0, 0, '', '1, Liverpool street, London EC2V 8NS', '2026-03-01', 44.14),

('IP0002', 'Cash', 4.60, '', 0, 0, '', '', '2026-03-03', 4.60),
('IP0003', 'Card', 70.00, 'Credit', 1648, 9874, '2030/02/01', '', '2026-03-03', 70.00),
('IP0004', 'Cash', 35.00, '', 0, 0, '', '', '2026-03-03', 35.00),
('IP0005', 'Cash', 24.10, '', 0, 0, '', '', '2026-03-03', 24.10),
('IP0006', 'Card', 23.40, 'Debit', 6255, 9909, '2029/11/20', '', '2026-03-03', 23.40),
('IP0007', 'Cash', 47.60, '', 0, 0, '', '', '2026-03-03', 47.60),

('IP0008', 'Card', 74.60, 'Credit', 8265, 2234, '2030/01/30', '1, Liverpool street, London EC2V 8NS', '2026-03-05', 74.60),

('IP0009', 'Card', 0, '', 0, 0, '', '1, Liverpool street, London EC2V 8NS', '2026-04-01', 35.60);

INSERT INTO LocalStock_Transactions VALUES
('30000001', 'IP0001', 1),
('30000002', 'IP0001', 2),
('40000001', 'IP0001', 2),
('40000002', 'IP0001', 2),

('10000002', 'IP0002', 2),
('10000003', 'IP0002', 3),

('10000004', 'IP0003', 2),
('10000006', 'IP0003', 2),

('10000007', 'IP0004', 1),
('10000008', 'IP0004', 1),

('10000005', 'IP0005', 1),
('20000004', 'IP0005', 2),
('20000005', 'IP0005', 2),

('30000001', 'IP0006', 2),
('40000001', 'IP0006', 2),

('30000002', 'IP0007', 3),
('40000002', 'IP0007', 2),

('30000001', 'IP0009', 1),
('10000003', 'IP0009', 3),
('10000004', 'IP0009', 2),
('40000002', 'IP0009', 2),

('10000002', 'IP0008', 2),
('10000003', 'IP0008', 3),
('10000004', 'IP0008', 2),
('10000006', 'IP0008', 2);

INSERT INTO AccountHolders_Transactions VALUES
('IP0001', 'ACC0001'),
('IP0008', 'ACC0002'),
('IP0009', 'ACC0001');
