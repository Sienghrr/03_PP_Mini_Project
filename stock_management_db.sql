-- ============================================================
-- Stock Management System
-- ============================================================

-- Create Database
CREATE DATABASE stock_management_db;

-- ============================================================
-- Products Table
-- ============================================================
CREATE TABLE IF NOT EXISTS products (
    product_id   SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    unit_price   DECIMAL(10,2) NOT NULL,
    quantity     INT  NOT NULL  DEFAULT 0,
    imported_date DATE  NOT NULL,
    is_deleted  BOOLEAN  NOT NULL  DEFAULT FALSE
);

-- ============================================================
-- Seed Sample Data
-- ============================================================

INSERT INTO products (product_name, unit_price, quantity, imported_date) VALUES
 ('Coca-Cola 330ml',        0.75,   200, '2025-12-01'),
 ('Pepsi 500ml',            0.85,   150, '2025-12-03'),
 ('Mineral Water 1.5L',     0.50,   300, '2025-12-05'),
 ('Lay''s Chips 100g',      1.20,    80, '2025-12-07'),
 ('Oreo Cookies 150g',      1.50,   120, '2025-12-08'),
 ('Nescafe 3-in-1',         0.60,   250, '2025-12-10'),
 ('Milo Chocolate Drink',   1.00,   100, '2025-12-12'),
 ('Uncle Tobys Oats 1kg',   3.50,    60, '2025-12-15'),
 ('Maggi Instant Noodles',  0.40,   400, '2026-01-02'),
 ('Pringles Original 165g', 2.50,    90, '2026-01-05'),
 ('Red Bull 250ml',         1.80,   110, '2026-01-07'),
 ('Green Tea 500ml',        0.90,   180, '2026-01-09'),
 ('Yakult 5-pack',          2.20,    70, '2026-01-11'),
 ('Kit Kat 4-finger',       1.10,   130, '2026-01-14'),
 ('Snickers 50g',           0.95,   160, '2026-01-16'),
 ('M&M Peanut 200g',        3.00,    50, '2026-01-18'),
 ('Haribo Gummy Bears',     2.00,    85, '2026-01-20'),
 ('Tiger Beer 330ml',       2.50,   200, '2026-01-22'),
 ('Heineken 330ml',         2.80,   150, '2026-01-25'),
 ('Fresh Milk 1L',          1.40,   220, '2026-01-28'),
 ('Greek Yogurt 200g',      1.60,    95, '2026-01-30'),
 ('Butter 250g',            3.20,    45, '2026-02-01'),
 ('Cheddar Cheese 200g',    4.50,    40, '2026-02-03'),
 ('Egg Tray 30pcs',         5.00,    60, '2026-02-05'),
 ('Bread Loaf 400g',        2.10,   100, '2026-02-07');
