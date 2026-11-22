# Café Inventory & Order Management

A Java console application that brings café inventory, supplies, recipes, suppliers, and ordering into one menu-driven academic project.

**Java · Object-oriented programming · Console interface**

## Overview

The application provides separate administrator and staff workflows. Administrators manage products and supplies, while staff can view inventory and take orders through the console menus.

This repository is a team project forked from [Francisco-HM06/OOP_IM_Project_ver2](https://github.com/Francisco-HM06/OOP_IM_Project_ver2). The code reflects a learning project; it is not intended as a production café system.

## Features

| Module | Capabilities |
| :--- | :--- |
| User access | Separate administrator and staff login flows |
| Products | Food and beverage records; add, search, update, and delete operations |
| Supplies | Ingredient and packaging management |
| Recipes | Associate ingredients with food and beverage recipes |
| Suppliers | Supplier management and supply delivery records |
| Orders | Menu-driven customer order workflow |

## Object-oriented structure

- `User` is an abstract parent class for `Admin` and `Staff`.
- `Product` supports food and beverage specializations.
- Inventory, recipes, suppliers, and orders are organized into separate classes.
- Java collections maintain application data during a session.

## Project layout

```text
FinalOOPProject1/
  MainSystem.java          Application entry point and login portal
  Dashboard.java           Administrator and staff navigation
  InventoryDashboard.java  Product, supply, and recipe menus
  Product.java             Food and beverage models and operations
  Supply_01.java           Supply-related models and operations
  Recipe.java              Recipe management
  Suppliers.java           Supplier management
  SupplyDelivery.java      Supply delivery workflow
  Order.java               Ordering workflow
  User.java                Abstract user model
  Admin.java               Administrator access
  Staff.java               Staff access
```

## Run locally

Install a Java Development Kit (JDK) and use a terminal from the repository root:

```sh
git clone https://github.com/yenzclemente-blip/OOP_IM_Project_ver2.git
cd OOP_IM_Project_ver2
javac -d out FinalOOPProject1/*.java
java -cp out FinalOOPProject1.MainSystem
```

The application uses console input. Select administrator or staff access at the login portal, then follow the numbered menus. Sample accounts are initialized in `MainSystem.java` for local coursework demonstrations.

## Current scope

This version uses in-memory data and sample login credentials. It does not provide a persistent database or production authentication. Future improvements could include database persistence, stronger input validation, automated tests, and a graphical interface.

## Credits

Developed as an academic team project. See the [upstream repository](https://github.com/Francisco-HM06/OOP_IM_Project_ver2) and Git history for project provenance and contributions.
