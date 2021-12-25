package com.ww.pcms.service;

import com.ww.pcms.Exception.EmptyInputException;
import com.ww.pcms.model.Product;
import com.ww.pcms.model.ProductListHolder;
import com.ww.pcms.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	Logger logger = LoggerFactory.getLogger(ProductRepository.class);

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	public ResponseEntity<Product> addProduct(Product inProduct) {
		//try {
			Product product = productRepository
					.save(inProduct);
			return new ResponseEntity<>(product, HttpStatus.CREATED);
		/*} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}*/
	}

	public ResponseEntity<ProductListHolder> fetchAllProductsByCategory(String category){
		ProductListHolder plh;
		//try {

			if (category == null || category.trim().length()==0) {
				logger.info("++++++++++++ Category is empty +++++++++++"+category);
				throw new EmptyInputException(100L, "Category can't be empty");
			}
			else {
				plh = productRepository.findByCategory(category);
			}
			if (plh.getTotalProductsCount() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(plh, HttpStatus.OK);
		/*} catch (EmptyInputException emptyEror) {
			ProductListHolder productListHolder = new ProductListHolder();
			productListHolder.setErrorCode(emptyEror.getErrorCode());
			productListHolder.setErrorMessage(emptyEror.getErrorMessage());
			return new ResponseEntity<>(productListHolder, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ProductListHolder productListHolder = new ProductListHolder();
			productListHolder.setErrorCode(500L);
			productListHolder.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}*/
	}

}
