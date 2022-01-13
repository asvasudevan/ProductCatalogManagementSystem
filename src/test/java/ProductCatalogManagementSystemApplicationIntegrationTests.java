import com.ww.pcms.ProductCatalogManagementSystemApplication;
import com.ww.pcms.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ProductCatalogManagementSystemApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCatalogManagementSystemApplicationIntegrationTests {

    Logger logger = LoggerFactory.getLogger(ProductCatalogManagementSystemApplicationIntegrationTests.class);
    private int port = 8080;

    @Test
    public void fetchProductTest(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
      //  headers.set("x-api-key", "vasu");
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        try{
            ResponseEntity<ProductListHolder> response = restTemplate.exchange("http://localhost:+"+port+"/ww/api/pcms/products?category=full grain leather sofa",
                    HttpMethod.GET, entity, ProductListHolder.class);

            //Assert.assertEquals(response.getBody().getProductList().size(), 6);
        }catch(Exception e){
            logger.info("Integration test failed to tech the products by category name");
        }

    }


    @Test
    public void addProductTest(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        //headers.set("x-api-key", "vasu");
        try{
            final HttpEntity<ProductListHolder> entity = new HttpEntity(getTestProduct(), headers);
            ResponseEntity<Product> response = restTemplate.postForEntity(new URI("http://localhost:"+port+"/ww/api/pcms/products"),entity, Product.class);
            Assert.assertEquals(201, response.getStatusCodeValue());

        }catch (Exception e){
            logger.info("Integration test failed for product creation");
        }

    }

    private Product getTestProduct(){
        Product product = new Product();
        product.setId(999999999L);
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

        sku1.setId(Long.valueOf(999999999));
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

        return product;
    }

}
