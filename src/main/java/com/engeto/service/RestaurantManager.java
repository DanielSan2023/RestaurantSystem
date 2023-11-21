package com.engeto.service;

import com.engeto.entity.Order;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantManager {
    private OrderService orderService = new OrderService();

    public int getUnpaidOrdersCount(OrderService orderService) {
        return (int) orderService.getOrders().values()
                .stream()
                .filter(order -> !order.isPaid() && order.getFulfilmentTime() == null)
                .count();
    }

    public List<Order> getSortedOrdersByOrderTime(OrderService orderService) {
        return orderService.getOrders().values()
                .stream()
                .sorted(Comparator.comparing(Order::getOrderTime))
                .collect(Collectors.toList());
    }

    //    public double averageProcessTimeforOrders(OrderService orderService) {
//        return orderService.getOrders().values().stream()
//                .map(this::getOrderProcessingTime)
//                .filter(processingTime -> !processingTime.isZero())
//                .collect(Collectors.collectingAndThen(
//                        Collectors.averagingDouble(Duration::getSeconds),
//                        averageTimeInSeconds -> averageTimeInSeconds.isNaN() ? 0 : averageTimeInSeconds
//                ));
//    }
    public List<Order> getTodayOrderedDishes(OrderService orderService) {
        LocalDate today = LocalDate.now();

        return orderService.getOrders().values().stream()
                .filter(order -> order.getOrderTime().equals(today))
                .collect(Collectors.toList());
    }


}
