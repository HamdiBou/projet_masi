# projet_masi

## Description

A JavaFX application to draw geometric shapes (rectangle, circle, line, triangle), create and visualize graphs, and compute the shortest path between nodes. The application uses design patterns (Factory, Strategy, DAO) for modularity and extensibility.

## Features
- Select and draw geometric shapes (rectangle, circle, line, triangle)
- Draw and connect nodes/edges to create a graph
- Compute and highlight the shortest path between two nodes (Dijkstra)
- User action logging (console, file, or MySQL database)
- Modern, delimited drawing area

## Folder Structure
- `src/` : Java source files
- `lib/` : External dependencies (e.g., MySQL JDBC driver)
- `bin/` : Compiled `.class` files

## Database Setup (MySQL)
1. Install [Laragon](https://laragon.org/) and start MySQL.
2. Download the MySQL JDBC driver and place the JAR in `lib/` (e.g., `mysql-connector-j-9.3.0.jar`).
3. Create the database and table:
   ```sql
   CREATE DATABASE projet_masi;
   USE projet_masi;
   CREATE TABLE logs (
       id INT AUTO_INCREMENT PRIMARY KEY,
       message TEXT,
       timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
   );
   ```
4. The application uses user `root` and password `root` by default.

## Build & Run
Open a terminal in the project root and run:
```powershell
javac -cp ".;lib/mysql-connector-j-9.3.0.jar" -d bin src/*.java
java -cp ".;bin;lib/mysql-connector-j-9.3.0.jar" HelloFX
```

## Design Patterns Used
- **Factory**: For shape creation (RectangleFactory, CircleFactory, etc.)
- **Strategy**: For logging (ConsoleLogger, FileLogger, DatabaseLogger)
- **DAO**: For saving/loading drawings and logging to the database

## Authors
- [Hamdi Boussarsar]
- [Zahrane Rabhi]

---

> For dependency management in VS Code, see [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
