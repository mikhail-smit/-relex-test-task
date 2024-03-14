package mikhail.task.controllers;

import jakarta.validation.Valid;
import mikhail.task.dto.ProductDTO;
import mikhail.task.exceptions.IncorrectInputFieldException;
import mikhail.task.models.Product;
import mikhail.task.services.ProductService;
import mikhail.task.utils.ErrorMessageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductsController {
    private final ProductService productService;
    private final ModelMapper mapper;
    private final ErrorMessageUtils messageUtils;

    public ProductsController(ProductService productService, ModelMapper mapper, ErrorMessageUtils messageUtils) {
        this.productService = productService;
        this.mapper = mapper;
        this.messageUtils = messageUtils;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable(name = "id") int id) {
        return toDto(productService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductDTO product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(productService.save(fromDto(product)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") int id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable(name = "id") int id,
                             @Valid @RequestBody ProductDTO product,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(productService.update(fromDto(product), id));
    }

    private ProductDTO toDto(Product product) {
        return mapper.map(product, ProductDTO.class);
    }

    private Product fromDto(ProductDTO dto) {
        return mapper.map(dto, Product.class);
    }
}
