package com.engeto;

import com.engeto.entity.Dish;
import com.engeto.entity.Order;
import com.engeto.exceptions.DishException;
import com.engeto.service.DishService;
import com.engeto.service.OrderService;
import com.engeto.service.RestaurantManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DishService dishService = new DishService();
        OrderService orderService = new OrderService();
        RestaurantManager restaurantManager = new RestaurantManager();

        //TESTOVACI SCENAR
        //1.Stav evidence z disku:

//        try {
//           dishService.addLoadedDishes(dishService.loadDishesFromFile(Settings.fileDishesForLoad()));
//                } catch (DishException e) {
//            throw new RuntimeException(e);
//        }
//        dishService.printDishes();

        //2.Připrav testovací data:
        try {
            dishService.addDishObj(new Dish("Kureci rizek obalovany 150g", 6.50));
            dishService.addDishObj(new Dish("Hranolky 150g", 3.50, 15));
            dishService.addDishObj(new Dish("Pstruh na vine 200g", 12.50, 25));
            dishService.addDishObj(new Dish("Kofola 0,5l", 3.00, 5));
        } catch (DishException e) {
            throw new RuntimeException(e);
        }
        dishService.printDishes();
        orderService.addOrder(new Order(15, 1, 2, dishService));
        orderService.addOrder(new Order(15, 2, 2, dishService));
        orderService.addOrder(new Order(15, 4, 2, dishService));

        orderService.addOrder(new Order(2, 4, 3, dishService));
        orderService.addOrder(new Order(2, 2, 3, dishService));
        orderService.addOrder(new Order(2, 3, 3, dishService));


        //3.Celkova cena konzumace pro stul 15:
        System.out.println("Celkova cena konzumace pro stůl číslo  15: " + orderService.getTotalDishPriceForTable(15) + "€");


        //TODO //4.Informace pre Management:
        System.out.println("Počet aktualne rozpracovaných objednávek : " + restaurantManager.getUnpaidOrdersCount(orderService));
        restaurantManager.getSortedOrdersByOrderTime(orderService)
                .forEach(order -> System.out.println("Order ID: " + order.getOrderId() + ", Time: " + order.getOrderTime()));


        //TODO  //5.Uloz data na disk:


    }


}
