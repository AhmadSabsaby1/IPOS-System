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
    balance INT unsigned,
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
    PRIMARY KEY (accountID)
);

CREATE TABLE LocalStock
(
    itemID VARCHAR(20) NOT NULL,
    description VARCHAR(45) NOT NULL,
    packageType VARCHAR(12),
    unit VARCHAR(8),
    unitsInAPack INT unsigned,
    packageCost INT unsigned,
    availability INT unsigned,
    stockLimit INT unsigned,
    retailMarkUpRate INT unsigned,
    PRIMARY KEY (itemID)
);

CREATE TABLE Transactions
(
    orderID VARCHAR(8) NOT NULL,
    paymentType VARCHAR(4),
    amountReceived INT unsigned,
    cardType VARCHAR(10),
    firstFour INT unsigned,
    LastFour INT unsigned,
    expiryDate INT unsigned,
    shippingAddress VARCHAR(45),
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
