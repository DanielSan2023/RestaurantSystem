package com.engeto.service;

import com.engeto.entity.Dish;
import com.engeto.entity.Order;
import com.engeto.exceptions.DishException;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class OrderService {

    private  Map<Integer, Order> orders = new HashMap<>();

    public void addOrder(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }


    public List<Order> getOrdersForTable(int tableNumber) {
        return orders.values()
                .stream()
                .filter(order -> order.getTableNumber() == tableNumber)
                .collect(Collectors.toList());
    }

    public void printOrdersForTable(int tableNumber) {
        List<Order> ordersForTable = getOrdersForTable(tableNumber);

        if (ordersForTable.isEmpty()) {
            System.out.println("Pre stôl číslo " + tableNumber + " nie sú žiadne objednávky.");
        } else {
            System.out.println("Objednávky pre stôl číslo " + tableNumber + ":");
            for (Order order : ordersForTable) {
                System.out.println(order.getDescription());
            }
        }
    }

    public double getTotalDishPriceForTable(int tableNumber) {
        List<Order> orders = getOrdersForTable(tableNumber);
        return orders.stream()
                .mapToDouble(Order::totalDishPrice)
                .sum();
    }

    public void printTotalDishPriceForTable(int tableNumber) {
        List<Order> orders = getOrdersForTable(tableNumber);
        double total = 0.0;
        for (Order order : orders) {
            total += order.totalDishPrice();
        }
        System.out.println("Celkova cena konzumace pro stůl číslo "
                + tableNumber + " je :" + Math.round(total * 100.0) / 100.0 + "€");
    }


    public static String formatTableNumber(int tableNumber) {
        if (tableNumber >= 1 && tableNumber <= 9) {
            return " " + tableNumber;
        } else {
            return String.valueOf(tableNumber);
        }
    }

    public void setPaidForOrder(int orderId) throws DishException {
        Order order = getOrderById(orderId);
        try {
            if (order != null) {
                if (order.getFulfilmentTime() != null) {
                    order.markAsPaid();
                } else {
                    throw new DishException("Objednávka s ID " + orderId + " nebola vybavená, a preto nemôže byť označená ako zaplatená.");
                }
            } else {
                throw new DishException("Objednávka s ID " + orderId + " neexistuje.");
            }
        } catch (Exception e) {
            throw new DishException("Chyba pri platení objednávky: " + e.getMessage());
        }
    }

//    public void setFulfilmentTimeForOrder(int orderId) {
//        Order order = getOrderById(orderId);
//        if (order != null) {
//            order.settingFulfilmentTime();
//        }
//    }

    public Order getOrderById(int orderId) {
        return orders.values()
                .stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

//    public void settingFulfilmentTime() {
//        if (orderTime != null && dish != null) {
//            int preparationTimeMinutes = dish.getPreparationTime();
//            if (preparationTimeMinutes > 0) {
//                fulfilmentTime = orderTime.plusMinutes(preparationTimeMinutes);
//            }
//        }
//    }



}
