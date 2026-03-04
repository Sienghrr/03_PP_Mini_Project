#  Stock Management System

---

## Project Structure

```
StockManagementSystem_Mini_Project/
├── src/
│    ├── Main.java
│    ├── config/
│    │   ├── AppConfig.java
│    │   └── DatabaseConfig.java
│    ├── dao/
│    │   ├── impl/
│    │   │      └── ProductDAOImpl.java
│    │   └── ProductDAO.java 
│    ├── domain/
│    │   └── Product.java
│    ├── lib/
│    │   ├── postgresql-42.7.10.jar
│    │   └── text-table-formatter-1.1.2.jar
│    ├── mapper/
│    │   └── ProductMapper.java
│    ├── service/
│    │   └── StockService.java
│    ├── ui/
│    │    └── ConsoleUI.java
│    ├── utils/
│    │   ├── TableHelper.java
│    │   ├── Utils.java
│    │   └── Validate.java
├── config.properties
├── README.md   
├── out/                                
└── stock_management.sql                              
```

---

## ⚙️ Setup Steps

### Step 1 — Download Postgresql JDBC Driver

Download the jar from:
```
https://jdbc.postgresql.org/
```
Place it inside the `lib/` folder.

### Step 2 — Create the Database

Open PgAdmin or DataGrip and run:
```
source stock_management_db.sql
```
Or open the file and execute it.

### Step 3 — Configure DB Connection

Open `src/config/DatabaseConfig.java` and update:
```java
private static final String URL      = "jdbc:postgresql://localhost:{your_port}/{db_name}";
private static final String USER     = "your_user";
private static final String PASSWORD = "your_password_here";  
```
Or Load it from .env .
### Step 4 — Compile and Run

**Or open in IntelliJ IDEA:**
Run `Main.java`

---

## ✅ Features

| #  | Feature                    |
|----|----------------------------|
| 1  | Display Product            |
| 2  | Write (Add) Product        |
| 3  | Read Product by ID         |
| 4  | Update Product             |
| 5  | Delete Product             |
| 6  | Search by Product Name     |
| 7  | Set Number of Display Rows |
| 8  | Save to Database           |
| 9  | View Unsaved Changes       |
| 10 | Pagination (F/N/P/L/GoTo)  |


---

## 📋 How Save / Unsave Works

- Adding, updating products is **in-memory only** until you choose **Save (8)**.
- **View Unsaved Changes (9)** shows exactly what is pending.
- On exit, if there are unsaved changes you'll be asked whether to save or discard.
