package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.repository.CategoryRepository;
import org.yearup.repository.ProductRepository;


import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository)
    {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int categoryId)
    {
        // get category by id
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return category;
    }

    public List<Product> getProductsByCategoryId(int categoryId)
    {
        if (!categoryRepository.existsById(categoryId))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return productRepository.findByCategoryId(categoryId);
    }

    public Category create(Category category)
    {
        // create a new category
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category)
    {
        // update category and return the updated category
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
        if (existingCategory == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

        category.setCategoryId(categoryId);
        return categoryRepository.save(category);
    }

    public void delete(int categoryId)
    {
        // delete category
        Category existing = categoryRepository.findById(categoryId).orElse(null);
        if (existing == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(categoryId);
    }
}
