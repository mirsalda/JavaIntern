package com.example.Project.Service;

import com.example.Project.Repository.ProductRepository;
import com.example.Project.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
@Autowired
    private ProductRepository productRepository;

//Per Create
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    //Per leximin e te gjitha produkteve
public List<Product> getAllProducts(){
    return productRepository.findAll();
}
// per leximin e nje produkti te vetem

    public Optional<Product>getProductById(Long id){
        return productRepository.findById(id);
    }
    //per update
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setAvailable(updatedProduct.isAvailable());
            return productRepository.save(product);
        }).orElse(null);
    }
    //per delete
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
