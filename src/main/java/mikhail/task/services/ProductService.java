package mikhail.task.services;

import mikhail.task.exceptions.ProductNotFoundException;
import mikhail.task.models.Product;
import mikhail.task.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found, id: " + id));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Product product, int id) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
