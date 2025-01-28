package br.com.fiap.techchallenge.order.infra.entrypoint.controller;

import br.com.fiap.techchallenge.order.application.usecase.product.FindProductsByCategoryUseCase;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.ProductPageResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.openapi.ProductControllerOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductsController  implements ProductControllerOpenApi {

    private final FindProductsByCategoryUseCase findProductsByCategoryUseCase;

    public ProductsController(FindProductsByCategoryUseCase findProductsByCategoryUseCase) {
        this.findProductsByCategoryUseCase = findProductsByCategoryUseCase;
    }

    @Override
    @GetMapping
    public ResponseEntity<ProductPageResponseDTO> findProductsByCategory(@RequestParam ProductCategoryEnum category,
                                                                         @RequestParam(required = false, defaultValue = "0") int page,
                                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        var pageableProduct = findProductsByCategoryUseCase.findByCategory(category, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(new ProductPageResponseDTO(pageableProduct));
    }
}
