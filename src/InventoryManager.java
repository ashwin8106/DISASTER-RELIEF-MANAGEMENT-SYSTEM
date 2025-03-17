//Backend code to manage the inventory
package src;

import java.io.*;
class InventoryManager {
    private String[] keys = new String[100];
    private int[] values = new int[100];
    private int size = 0;
    private String cityName;

    public InventoryManager(String cityName) {
        this.cityName = cityName;
        loadInventory();
    }

    public void addItem(String item, int quantity) {
        int index = findItemIndex(item);
        if (index != -1) {
            values[index] += quantity;
        } else {
            keys[size] = item;
            values[size] = quantity;
            size++;
        }
        saveInventory();
    }

    public String viewInventory() {
        StringBuilder inventoryText = new StringBuilder("Current Inventory:\n==================\n");
        for (int i = 0; i < size; i++) {
            inventoryText.append(keys[i]).append(": ").append(values[i]).append(" units\n");
            if (values[i] < 10) {
                inventoryText.append("⚠ Low Supply Alert: ").append(keys[i]).append(" is below 10 units!\n");
            }
        }
        return inventoryText.toString();
    }

    public String allocateSupplies(String selectedItem, int personsCamp, int affected, String areaTypeSelected) {
        int index = findItemIndex(selectedItem);
        if (index == -1) return "Invalid item selected.\n";

        int available = values[index];
        if (personsCamp + affected <= 0) {
            return "Number of persons must be greater than zero.\n";
        }
        String hh = "";
        int allocated = 0;
        int campAllocation = 0, affectedAllocation = 0;

        switch (areaTypeSelected) {
            case "Low":
                affectedAllocation = Math.min(affected, available);
                campAllocation = Math.min(personsCamp, available - affectedAllocation);
                break;
            case "Mid":
                campAllocation = Math.min(personsCamp, available / 2);
                affectedAllocation = Math.min(affected, available - campAllocation);
                break;
            case "High":
                campAllocation = Math.min(personsCamp, available);
                affectedAllocation = Math.min(affected, available - campAllocation);
                break;
        }

        if (selectedItem.equals("Meals")) {
            affectedAllocation*=21;
            campAllocation*=21;
            allocated = 21* (campAllocation + affectedAllocation);
            hh = "Allocating 3 Meals per person per day for one week\n";
        } else if (selectedItem.equals("Water")) {
            affectedAllocation*=14;
            campAllocation*=14;
            allocated = 14 * (campAllocation + affectedAllocation);
            hh = "Allocating 2 units of water per person per day for one week\n";
        } else if (selectedItem.equals("Medicine")) {
            allocated = 2 * affectedAllocation;
            campAllocation*=0;
            hh = "Allocating 32 units of Medicine for heavily afftected persons only\n";
        } else if (selectedItem.equals("Canned Food")) {
            affectedAllocation*=7;
            campAllocation*=7;
            hh = "Allocating 1 unit of Canned food per person per day for one week Meals per person per day for one week\n";
            allocated = 7 * (campAllocation + affectedAllocation);
        } else {
            hh = "Allocating 1 Blanket per person\n";
            allocated = campAllocation + affectedAllocation; 
        }

        if (allocated > 0 && available >= allocated) {
            values[index] -= allocated;
            return String.format(
                hh+
                "Allocation Details:\n" + "-------------------\n" + "Allocated to Camp: %d units\n"
                + "Allocated to Affected People: %d units\n" + "Total Allocated: %d units of %s\n\n",
                campAllocation, affectedAllocation, allocated, selectedItem);
        } else {
            return "Not enough " + selectedItem + " in inventory for allocation.\nStill Require "+ (allocated-available) +" units of "+selectedItem;
        }
    }

    private int findItemIndex(String item) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    private void saveInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cityName + "_inventory.txt"))) {
            for (int i = 0; i < size; i++) {
                writer.write(keys[i] + "," + values[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(cityName + "_inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addItem(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            // File does not exist, initialize with default items
            String[] items = {"Meals", "Water", "Medicine", "Canned Food", "Blankets"};
            for (String item : items) {
                addItem(item, 0);
            }
        }
    }
}
