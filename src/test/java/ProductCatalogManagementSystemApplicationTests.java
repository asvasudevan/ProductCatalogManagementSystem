import com.ww.pcms.model.*;
import com.ww.pcms.repository.ProductRepository;
import com.ww.pcms.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ProductCatalogManagementSystemApplicationTests  {
	Logger logger = LoggerFactory.getLogger(ProductCatalogManagementSystemApplicationTests.class);
	private ProductService service = new ProductService();
	private ProductRepository repository;

	@BeforeEach
	public void testSetUp() {
		repository = Mockito.mock(ProductRepository.class);
		service.setProductRepository(repository);
	}

	@Test
	public void addProductTest() {
		ProductListHolder productListHolder = getTestProduct();
		when(repository.save(Mockito.any())).thenReturn(productListHolder.getProductList().get(0));
		ResponseEntity<Product> response = service.addProduct(productListHolder.getProductList().get(0));
		Product body = response.getBody();
		Assert.assertEquals(productListHolder.getProductList().get(0), body);
	}

	@Test
	public void fetchAllProductsByCategoryTest(){
		String category = "WW by BUILT(r) 30 oz Tumbler";
		when(repository.findByCategory(category))
				.thenReturn(getTestProduct());
		Assert.assertEquals(Integer.valueOf(1), service.fetchAllProductsByCategory(category).getBody().getTotalProductsCount());
	}

	private ProductListHolder getTestProduct(){
		ProductListHolder productListHolder = new ProductListHolder();
		Product product = new Product();
		product.setId(1L);
		product.setName("PName");
		product.setDescription("PDesc");
		product.setUrl("PUrl");
		product.setIsActive(true);
		product.setIsNew(true);
		product.setRetailPrice(100.20);
		product.setSalePrice(120.00);
		product.setDiscount(20.00);
		Currency currency = new Currency("USD");
		product.setCurrency(currency);

		Category category = new Category();
		category.setFeatured(true);
		category.setUrlKey("www-ur-key");
		category.setName("TestCatName");
		category.setActive(true);
		category.setUrl("www.url");
		category.setParentId(Long.valueOf(4));
		category.setNavigationIncluded(true);

		CategoryParent parentCategory = new CategoryParent();
		parentCategory.setId(Long.valueOf(3));
		parentCategory.setName("parent cat");
		parentCategory.setUrl("www.paret-url");

		CategoryParent parentCategory1 = new CategoryParent();
		parentCategory1.setId(Long.valueOf(4));
		parentCategory1.setName("parent cat 1");
		parentCategory1.setUrl("www.paret-url 1");

		parentCategory.assignParent(parentCategory1);
		category.setParent(parentCategory);

		category.setParent(parentCategory);

		product.setCategory(category);

		List<SKU> skus = new ArrayList<SKU>();
		SKU sku1 = new SKU();

		sku1.setId(Long.valueOf(1));
		sku1.setName("sku1-name");
		sku1.setDescription("sku1-desc");
		sku1.setShortDescription("sku1-short-desc");
		sku1.setStartDate(new Timestamp(Long.valueOf(342424242)));
		sku1.setRetailPrice(100.10);
		sku1.setSalePrice(120.10);
		sku1.setBasePriceUnit("base");
		sku1.setDiscount(20.10);
		sku1.setActive(true);
		sku1.setAvailable(true);
		sku1.setInventoryType("sku1-inv-type");
		sku1.setWwSKU("sku1-ww-sku");
		sku1.setProductType("sku1-product-type");
		sku1.setQuantityAvailable(Long.valueOf(130));
		sku1.setSubscription(true);
		sku1.setTaxCode("sku1-Tax");

		FulfillmentType fulfillment = new FulfillmentType();
		fulfillment.setType("F-Type");
		fulfillment.setFriendlyName("F-Name");
		sku1.setFulfillmentType(fulfillment);

		List<Media> medias = new ArrayList<Media>();
		Media media = new Media();

		media.setAltText("alt-text");
		media.setUrl("/abc/xyz.giff");
		medias.add(media);
		sku1.setMedia(medias);

		skus.add(sku1);
		product.setSkus(skus);
		List<Product> products = new ArrayList<>();
		products.add(product);
		productListHolder.setProductList(products);
		productListHolder.setTotalProductsCount(products.size());
		return productListHolder;
	}
}