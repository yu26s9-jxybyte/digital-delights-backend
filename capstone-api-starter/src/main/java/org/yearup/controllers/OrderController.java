package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Order;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@PreAuthorize("hasRole('ROLE_USER')")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<?> createOrder(Principal principal) {
        String username = principal.getName();
        int userId = userService.getIdByUsername(username);

        // ask the service to attempt order creation
        Order order = orderService.createOrder(userId);

        // if the service returns null, the cart was empty
        if (order == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot checkout an empty cart.");
        }
        // otherwise return 201 Created with the order
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }


}