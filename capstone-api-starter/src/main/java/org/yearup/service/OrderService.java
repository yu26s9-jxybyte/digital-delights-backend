package org.yearup.service;


import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.Profile;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProfileService profileService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, ProfileService profileService, ProductService productService, OrderLineItemRepository orderLineItemRepository){
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.profileService = profileService;
        this.productService = productService;
        this.orderLineItemRepository = orderLineItemRepository;
    }

    public Order createOrder(int userId){
        List<CartItem> cartItemList = shoppingCartRepository.findByUserId(userId);
        if (cartItemList.isEmpty()) {
            return null;
        }

        Profile profile = profileService.getByUserId(userId);
        Order order = new Order();

        order.setUserId(userId);
        order.setDate(LocalDateTime.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        order.setShippingAmount(BigDecimal.valueOf(0.0));;
        orderRepository.save(order);

        for (CartItem cartItem : cartItemList){
            OrderLineItem orderLineItem = new OrderLineItem();
            //order line item id is generated
            orderLineItem.setOrderId(order.getOrderId());
            orderLineItem.setProduct(productService.getById(cartItem.getProductId()));;
            orderLineItem.setSalesPrice(BigDecimal.valueOf(productService.getById(cartItem.getProductId()).getPrice())
            );
            orderLineItem.setQuantity(cartItem.getQuantity());
            orderLineItem.setDiscount(BigDecimal.ZERO);
            orderLineItemRepository.save(orderLineItem);
        }
        shoppingCartRepository.deleteAll();
        return order;
    }
}