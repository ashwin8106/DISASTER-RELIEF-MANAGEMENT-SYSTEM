# Disaster Relief Supply Chain and Inventory Management System

## 📌 Project Overview
The **Disaster Relief Supply Chain and Inventory Management System** is a Java-based desktop application designed to help organizations manage disaster relief supplies efficiently. It enables tracking, updating, and managing inventory to ensure effective distribution during emergency situations.

## 🏗️ Technologies Used
- **Java** (Core application)
- **File Handling** (For data storage)
- **Swing/JavaFX** (For user interface, if applicable)

## 🛠️ Features
- **Inventory Management**: Add, update, delete, and search for supplies.
- **Supply Tracking**: Monitor stock levels and categories.
- **File-Based Storage**: Saves and retrieves data from files.
- **User-Friendly Interface**: Simple desktop-based system.

## 🚀 Setup & Installation

### **1. Clone the Repository**
  [git clone https://github.com/ashwin8106/DISASTER-RELIEF-MANAGEMENT-SYSTEM]

### **2. Compile the Project**
  javac -d bin -cp src Main.java src/inventory/*.java

### **3. Run the Project**
  java -cp bin Main

## 🛠️ Usage

### **1. Home Page**:
Collects information about the type of disaster and the affected area.

### **2. Inventory Management**:
Tracks essential items like meals, water, medicine, canned foods, and blankets.
Alerts users if any item is below 10% of its total capacity.

### **3. Resource Allocation Strategies**:
First-Come-First-Serve: Distributes items to people in the camp efficiently for at least a week.
Priority-Based: Prioritizes heavily affected individuals before allocating to others.
Location-Based: Allocates resources based on the area's sea level (low, mid, high) and population density.

### **4. User Interaction**:
Allows users to add items to the inventory as needed.
Asks for critical details like the number of people in the camp, heavily affected individuals, and the type of area (low/mid/high sea level).

### **5. Strategy Recommendation**:
Analyzes the situation and recommends the fastest and most effective allocation strategy.

## 📌 REQUIREMENTS:
- Java 8
- GIT (for cloning)
