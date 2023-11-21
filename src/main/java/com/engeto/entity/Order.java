package com.engeto.entity;

import com.engeto.service.DishService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private int orderId;
    private int tableNumber;

    private LocalDate orderTime;
    private LocalTime fulfilmentTime;
    private boolean isPaid;
    private int countDish = 1;
    private static int nextBillId = 1;
    private static int nextOrderId = 1;
    private Dish dish;


//region constructors

    public Order(int tableNumber, int dishId, int countDish, DishService dishService) {
        this.orderId = nextOrderId++;
        this.tableNumber = tableNumber;
        this.dish = dishService.getDishById(dishId);
        this.orderTime = LocalDate.from(LocalTime.now());
        this.isPaid = false;
        this.countDish = countDish;
    }

    public Order(int tableNumber, int dishId,DishService dishService) {
        this.orderId = nextOrderId++;
        this.tableNumber = tableNumber;
        this.dish = dishService.getDishById(dishId);
        this.orderTime = LocalDate.from(LocalTime.now());
        this.isPaid = false;
        this.countDish = 1;
    }

    public Order() {
    }

//endregion construktors

    //region Getter Setter
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = LocalDate.from(orderTime);
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }


    public static int getNextBillId() {
        return nextBillId++;
    }

    public void setNextBillId() {
        this.nextBillId = 1;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getDishId() {
        return dish.getIdDish();
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public LocalTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(String time) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public int getCountDish() {
        return countDish;
    }

    public void setCountDish(int countDish) {
        this.countDish = countDish;
    }

    //endregion Getter Setter


    public String getDescription() {
        StringBuilder description = new StringBuilder();
        description.append(Order.getNextBillId()).append(".").append(" ").append(dish.getTitle())
                .append(" ").append(countDish).append(" ").append("(")
                .append(totalDishPrice()).append(" €").append(")").append(":").append("\t")
                .append(orderTime.format(DateTimeFormatter.ofPattern("HH:mm"))).append("-");
        if (fulfilmentTime != null) {
            description.append(fulfilmentTime.format(DateTimeFormatter.ofPattern("HH:mm"))).append("\t");
        }
        description.append(isPaid ? "Zaplaceno" : "");
        return description.toString();
    }

    public double totalDishPrice() {
        double price = getCountDish() * dish.getPrice();
        return Math.round(price * 100.0) / 100.0;
    }


}
