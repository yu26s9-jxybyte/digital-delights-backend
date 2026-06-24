package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    // get cart
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
    //add product (post)
    public ShoppingCart addProduct(int userId, int productId)
    {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing == null)
        {
            //creates new cart row
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            shoppingCartRepository.save(newItem);
        }
        else
        {
            //increment quantity
            existing.setQuantity(existing.getQuantity() + 1);
            shoppingCartRepository.save(existing);
        }
        return getByUserId(userId);
    }

    // add additional methods here
    public ShoppingCart updateProduct(int userId, int productId, int quantity)
    {
       CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
       if (existing != null)
       {
           existing.setQuantity(quantity);
           shoppingCartRepository.save(existing);
       }
       return getByUserId(userId);
    }

    @Transactional
    public ShoppingCart clearCart(int userId)
    {
        shoppingCartRepository.deleteByUserId(userId);
        return getByUserId(userId);
    }
}
