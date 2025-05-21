package com.example.Project.Controller;

import com.example.Project.Service.ProductService;
import com.example.Project.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    //Per create
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    //per leximin e te gjithave produkteve
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    //per leximin e nje produkti te vetem
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    //per update
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        return productService.updateProduct(id, updatedProduct);
    }
    // per delete
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {}

}

