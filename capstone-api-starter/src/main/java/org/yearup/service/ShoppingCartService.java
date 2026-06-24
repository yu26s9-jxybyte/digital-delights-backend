package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    // getcart
    public ShoppingCart getCart(int userId)
    {
        ShoppingCart cart = new ShoppingCart();

        List<CartItem> items = shoppingCartRepository.findByUserId(userId);

        for (CartItem item : items)
        {
            Product product = productService.getById(item.getProductId());
            cart.add(new ShoppingCartItem(product, item.getQuantity()));
        }
        return cart;
    }
    //add product (post)
    public ShoppingCart addProduct(int userId, int productId)
    {
        CartItem existing = shoppingCartRepository.getCartItem(userId, productId);

        if (existing == null)
        {
            ShoppingCartRepository.addItem(userId, productId, 1);
        }
        else
        {
            int newQuantity = existing.getQuantity() + 1;
            shoppingCartRepository.updateItem(userId, productId, newQuantity);
        }
        return getCart(userId);
    }
    public ShoppingCart getByUserId(int userId)
    {
        ShoppingCart cart = new ShoppingCart();
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> items = shoppingCartRepository.findByUserId(userId);

        for (CartItem item : items)
        {
            Product product = productService.getById(item.getProductId());
            cart.add(new ShoppingCartItem(product, item.getQuantity()));
        }

        return cart;
    }

    // add additional methods here
    public ShoppingCart updateProduct(int userId, int productId, int quantity)
    {
       shoppingCartRepository.updateItem(userId, productId, quantity);
       return getCart(userId);
    }

    public ShoppingCart clearCart(int userId)
    {
        shoppingCartRepository.clearCart(userId);
        return getCart(userId);
    }
}
