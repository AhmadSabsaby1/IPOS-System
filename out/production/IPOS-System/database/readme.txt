1. Set up local mysql database
2. (Intellij, right side) Database -> New -> Data Source -> MySQL
3. Set up with URL
4. Put the correct URL, username and password in DBParent class (Keep
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
