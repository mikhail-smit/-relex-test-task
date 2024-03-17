package mikhail.task.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Tag(name = "Products", description = "Methods for products")
public class ProductsController {
    private final ProductService productService;
    private final ModelMapper mapper;
    private final ErrorMessageUtils messageUtils;

    @GetMapping
    @Operation(summary = "Returns all products")
    public List<ProductDTO> getAll() {
        return productService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns product by Id")
    public ProductDTO getProduct(@PathVariable(name = "id") int id) {
        return toDto(productService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds new product")
    public ProductDTO create(@Valid @RequestBody ProductDTO product,
                             @Parameter(hidden = true) BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IncorrectInputFieldException(messageUtils.createMessage(bindingResult));
        }
        return toDto(productService.save(fromDto(product)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes product by Id")
    public void delete(@PathVariable(name = "id") int id) {
        productService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates product data")
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
