package com.engeto.service;

import com.engeto.Settings;
import com.engeto.entity.Dish;
import com.engeto.exceptions.DishException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class DishService {

    private Map<Integer, Dish> dishes;


    public DishService() {
        dishes = new HashMap<>();
    }

    public void addLoadedDishes(Map<Integer, Dish> loadedDishes) {
        if (dishes == null) {
            dishes = new HashMap<>();
        }
        dishes.putAll(loadedDishes);
    }

    public void addDishObj(Dish newDish) {
        int id = generateNewId();
        newDish.setIdDish(id);
        dishes.put(id, newDish);
    }

    public void addDish(Dish newDish) {
        int id = generateNewId();
        newDish.setIdDish(id);
        dishes.put(id, newDish);
    }


    private int generateNewId() {
        int maxId = dishes.keySet().stream().max(Integer::compare).orElse(0);
        return maxId + 1;
    }

    public Dish getDishById(int dishId) {
        for (Dish dish : dishes.values()) {
            if (dish.getIdDish() == dishId) {
                return dish;
            }
        }

        return null;
    }

    public Dish editDish(int dishId, Dish updatedDish) throws DishException {
        if (dishes.containsKey(dishId)) {
            Dish dish = dishes.get(dishId);
            dish.setTitle(updatedDish.getTitle());
            dish.setPrice(updatedDish.getPrice());
            dish.setPreparationTime(updatedDish.getPreparationTime());
            dish.setImage(updatedDish.getImage());
            System.out.println("Dish with id: " + dishId + " was updated.");
            return dish;
        } else {
            System.out.println("\u001B[31m Dish with id: " + dishId + " is not found." + "\u001B[0m");
        }
        return updatedDish;
    }

    public void removeDish(int dishId) {
        if (dishes.containsKey(dishId)) {
            dishes.remove(dishId);
        } else {
            System.out.println(dishId + " not found");
        }
    }

    public List<Dish> getDishes() {
        return new ArrayList<>(dishes.values());
    }

    public void printDishes() {
        for (Map.Entry<Integer, Dish> entry : dishes.entrySet()) {
            int dishId = entry.getKey();
            Dish dish = entry.getValue();
            System.out.print("ID: " + dishId);
            System.out.print("Jedlo: " + dish.getTitle());
            System.out.print("cena: " + dish.getPrice() + " € ");
            System.out.print("doba pripravy: " + dish.getPreparationTime() + " minut");
            System.out.println(" ");
        }
    }

    public static void saveToFile(String filename, DishService dishService) throws DishException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Dish dish : dishService.getDishes()) {
                writer.println(dish.toFile());
            }
            System.out.println("Dishes saved successfully in the file: " + filename + ".");
        } catch (IOException e) {
            throw new DishException("\u001B[31m Dishes not saved. " + filename + ": " + e.getLocalizedMessage() + "\u001B[0m");
        }
    }

    public Map<Integer, Dish> loadDishesFromFile(String filename) throws DishException {
        Map<Integer, Dish> loadedDishes = new HashMap<>();
        try {
            if (Files.size(Paths.get(filename)) == 0) {
                throw new DishException("\u001B[31m File " + filename + " is empty." + "\u001B[0m");
            }
            try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Dish dish = parseDishLine(line);
                    if (dish != null) {
                        loadedDishes.put(dish.getIdDish(), dish);
                    }
                }
            }
        } catch (IOException e) {
            throw new DishException("\u001B[31m Problem with loading dishes from " + filename + ": " + e.getLocalizedMessage() + "\u001B[0m");
        }
        return loadedDishes;
    }

    private Dish parseDishLine(String line) throws DishException {
        String[] blocks = line.split(Settings.fileItemDelimiter());
        int numOfBlocks = blocks.length;
        if (numOfBlocks != 4) {
            System.err.println("\u001B[31m Incorrect number of items in the line: " + line + "! Number of items: " + numOfBlocks + "." + "\u001B[0m");
            return null;
        }
        int idDish = Integer.parseInt(blocks[0].trim());
        String title = blocks[1].trim();
        double price = Double.parseDouble(blocks[2].trim());
        int preparationTime = Integer.parseInt(blocks[3].trim());

        return new Dish(title, price, preparationTime, idDish);
    }
}


