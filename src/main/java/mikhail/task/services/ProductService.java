package mikhail.task.services;

import lombok.RequiredArgsConstructor;
import mikhail.task.exceptions.ProductNotFoundException;
import mikhail.task.models.Product;
import mikhail.task.repositories.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Cacheable(cacheNames = "product", key = "#id")
    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found, id: " + id));
    }

    @Cacheable(cacheNames = "product")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional
    // cache evict for update cache in get all method
    @CacheEvict(cacheNames = "product", allEntries = true)
    public Product save(Product product) {
        return productRepository.save(product);
    }


    @Transactional
    // cache evict for update cache in get all method
    @CacheEvict(cacheNames = "product", allEntries = true)
    public Product update(Product product, int id) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Transactional
    @CacheEvict(cacheNames = "product", key = "#id")
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
