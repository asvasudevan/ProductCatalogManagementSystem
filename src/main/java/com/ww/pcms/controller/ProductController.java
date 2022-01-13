package com.ww.pcms.controller;

import com.ww.pcms.model.Product;
import com.ww.pcms.model.ProductListHolder;
import com.ww.pcms.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("ww/api/pcms")
@Api(value="Product API", description="WW Product Catalog Management System")
public class ProductController {

	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService){
		this.productService = productService;
	}

	@ApiOperation(value="New Product Creation")
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product inProduct) {
		return productService.addProduct(inProduct);
	}

	@ApiOperation(value="Retrieve products using catalog name")
	@GetMapping("/products")
	public ResponseEntity<ProductListHolder> getAllProductsByCategory(@RequestParam String category){
		return productService.fetchAllProductsByCategory(category);
	}

}
