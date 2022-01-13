package com.ww.pcms.repository;

import com.google.gson.Gson;
import com.ww.pcms.Exception.CatalogManagementException;
import com.ww.pcms.model.*;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository{
	Logger logger = LoggerFactory.getLogger(ProductRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SKU sku;

	public Product save(Product inProduct) {
		Long productId = generateProductId();
		logger.info( " productId ++++++++>"+productId);

		insertProduct(inProduct, productId);

		insertSKUs(inProduct.getSkus(),productId); //need to check

		inProduct.setId(productId.longValue());
		return inProduct;
	}

	private Long generateProductId() {
		return jdbcTemplate.queryForObject("select nextval('product_id_next_value')", Long.class);
	}

	private Long generateSkuId() {
		return jdbcTemplate.queryForObject("select nextval('sku_id_next_value')", Long.class);
	}

	private Long generateCategoryId() {
		return jdbcTemplate.queryForObject("select nextval('category_id_next_value')", Long.class);
	}


	private int insertCategory(Category inCategory){
		return jdbcTemplate.update("insert into category (" +
						"cat_id, cat_parent_id, cat_name, cat_active, cat_featured, cat_navigation_included, " +
						"cat_url, cat_url_key) values(?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] { generateCategoryId(), inCategory.getParentId(), inCategory.getName(), inCategory.getActive(),
						inCategory.getFeatured(), inCategory.getNavigationIncluded(),
						inCategory.getUrl(), inCategory.getUrlKey()});
	}

	private int[] insertSKUs(List<SKU> skus, Long productId) {
		Gson fulfillmentGson = new Gson();
		Gson skuMediaArayGson= new Gson();
		PGobject fulfillmentJsonbObj = new PGobject();
		PGobject skuMediaArayJsonbObj = new PGobject();
		fulfillmentJsonbObj.setType("json");
		skuMediaArayJsonbObj.setType("json");
		final List<Long> generatedSkus = new ArrayList<>();

		int[] batchResult;
		try{

			batchResult = jdbcTemplate.batchUpdate("insert into sku (" +
							"sku_id, prod_id, sku_name, sku_desc, sku_short_desc, sku_start_date, sku_retail_price, " +
							"sku_sale_price,sku_base_price_unit, sku_discount, sku_active, sku_available, sku_inventory_type, sku_ww, " +
							"sku_product_type, sku_qty_available, sku_subscription, sku_tax_code, sku_fulfillment_type, sku_medias)" +
							"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							int skuId = generateSkuId().intValue();
							generatedSkus.add(Long.valueOf(skuId));
							ps.setInt(1, skuId);
							ps.setInt(2, productId.intValue());
							ps.setString(3, skus.get(i).getName());
							ps.setString(4, skus.get(i).getDescription());
							ps.setString(5, skus.get(i).getShortDescription());
							ps.setTimestamp(6, skus.get(i).getStartDate());
							ps.setDouble(7, skus.get(i).getRetailPrice());
							ps.setDouble(8, skus.get(i).getSalePrice());
							ps.setString(9, skus.get(i).getBasePriceUnit());
							ps.setDouble(10, skus.get(i).getDiscount());
							ps.setBoolean(11, skus.get(i).isActive());
							ps.setBoolean(12, skus.get(i).isAvailable());
							ps.setString(13, skus.get(i).getInventoryType());
							ps.setString(14, skus.get(i).getWwSKU());
							ps.setString(15, skus.get(i).getProductType());
							ps.setLong(16, skus.get(i).getQuantityAvailable());
							ps.setBoolean(17, skus.get(i).isSubscription());
							ps.setString(18, skus.get(i).getTaxCode());

							fulfillmentJsonbObj.setValue(fulfillmentGson.toJson(skus.get(i).getFulfillmentType()));
							ps.setObject(19, fulfillmentJsonbObj);

							MediaListHolder mediaListHolder = new MediaListHolder();
							mediaListHolder.setMedias(skus.get(i).getMedia());
							skuMediaArayJsonbObj.setValue(skuMediaArayGson.toJson(mediaListHolder));
							ps.setObject(20, skuMediaArayJsonbObj);
						}

						@Override
						public int getBatchSize() {
							return skus.size();
						}
					});
			for(int i=0; i <= skus.size() -1 ; i++){
				skus.get(i).setId(generatedSkus.get(i));
			}

			return batchResult;

		}catch (Exception e){
			logger.info("Exception occurred while inserting the SKUs");
			throw new CatalogManagementException(1002L, e.getMessage());
		}
	}

	private int insertProduct(Product inProduct, Long productId){

		Gson currencyGson = new Gson();
		Gson mediaArayGson = new Gson();
		PGobject currencyJsonbObj = new PGobject();
		PGobject mediaArayJsonbObj = new PGobject();

		try{

			currencyJsonbObj.setType("json");
			currencyJsonbObj.setValue(currencyGson.toJson(inProduct.getCurrency()));

			MediaListHolder mediaListHolder = new MediaListHolder();
			mediaListHolder.setMedias(inProduct.getMedias());
			mediaArayJsonbObj.setType("json");
			mediaArayJsonbObj.setValue(mediaArayGson.toJson(mediaListHolder));

			Long catId = inProduct.getCategory().getId();

			System.out.println("catId during product insert ======>"+catId);

			return jdbcTemplate.update("insert into product (" +
							"prod_id, prod_name, prod_desc, prod_url, prod_active, prod_new," +
							"prod_retail_price, prod_sale_price, prod_discount, prod_currency, prod_medias, cat_id)"+
							"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					new Object[] { productId, inProduct.getName(), inProduct.getDescription(), inProduct.getUrl(),
							inProduct.getIsActive(), inProduct.getIsNew(), inProduct.getRetailPrice(), inProduct.getSalePrice(),
							inProduct.getDiscount(), currencyJsonbObj, mediaArayJsonbObj, catId},
					new int[]{Types.BIGINT, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,
							Types.BOOLEAN, Types.BOOLEAN, Types.DOUBLE, Types.DOUBLE,
							Types.DOUBLE, Types.OTHER, Types.OTHER, Types.BIGINT});

		}catch (SQLException e){
			logger.info("Exception occurred while inserting the Product");
			throw new CatalogManagementException(1002L, e.getMessage()+" SQL exception during product creation");

		}
	}

	public ProductListHolder findByCategory(String categoryName) {
		List<Product> productList = new ArrayList<>();
		ProductListHolder productListHolder = new ProductListHolder();
		String sql = "select p.prod_id, p.prod_name, p.prod_desc, p.prod_url, p.prod_active, p.prod_new, p.prod_retail_price,\n" +
				"\t\t\tp.prod_sale_price, p.prod_discount, p.prod_currency, p.prod_medias, p.cat_id, \n" +
				"\t   s.sku_id, s.sku_name, s.sku_desc, s.sku_short_desc, s.sku_start_date, s.sku_retail_price, s.sku_sale_price,\n" +
				"\t   \t\ts.sku_base_price_unit, s.sku_discount, s.sku_active, s.sku_available, s.sku_inventory_type, s.sku_ww,\n" +
				"\t\t\ts.sku_product_type, s.sku_qty_available, s.sku_subscription, s.sku_tax_code, s.sku_medias, s.sku_fulfillment_type\n" +
				"\t\tfrom product p, sku s  \n" +
				"\twhere p.cat_id in (select cat_id from category where category.cat_name = "+" '"+categoryName+"' "+") and s.prod_id = p.prod_id order by prod_id, sku_id";

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Gson gson = new Gson();
		MediaListHolder mediaListHolder = null;
		Map<Long, Product> pMap = new HashMap<>();

		try{

			for (Map row : rows) {
				Long productId = ((Long)row.get("prod_id")).longValue();

				List<SKU> skus = new ArrayList<>();
				SKU sku = new SKU();

				sku.setId(((Long)row.get("sku_id")).longValue());
				sku.setName((String) row.get("sku_name"));
				sku.setDescription((String) row.get("sku_desc"));
				sku.setShortDescription((String) row.get("sku_short_desc"));

				sku.setStartDate(((java.sql.Timestamp) row.get("sku_start_date")));

				sku.setRetailPrice(((Double) row.get("sku_retail_price")).doubleValue());
				sku.setSalePrice(((Double) row.get("sku_sale_price")).doubleValue());
				sku.setBasePriceUnit((String) row.get("sku_base_price_unit"));
				sku.setDiscount(((Double) row.get("sku_discount")).doubleValue());
				sku.setActive(((Boolean) row.get("sku_active")).booleanValue());
				sku.setAvailable(((Boolean) row.get("sku_available")).booleanValue());
				sku.setInventoryType((String) row.get("sku_inventory_type"));
				sku.setWwSKU((String) row.get("sku_ww"));
				sku.setProductType((String) row.get("sku_product_type"));
				sku.setQuantityAvailable(((Long)row.get("sku_qty_available")).longValue());
				sku.setSubscription(((Boolean) row.get("sku_subscription")).booleanValue());
				sku.setTaxCode((String)row.get("sku_tax_code"));

				mediaListHolder = gson.fromJson(String.valueOf(row.get("prod_medias")), MediaListHolder.class);
				sku.setMedia(mediaListHolder.getMedias());

				FulfillmentType fulfillmentType = gson.fromJson(String.valueOf(row.get("sku_fulfillment_type")), FulfillmentType.class);
				sku.setFulfillmentType(fulfillmentType);

				skus.add(sku);

				if(! pMap.containsKey(productId)){
					Product product = new Product();

					//setting the product details
					product.setId(productId);
					product.setName((String) row.get("prod_name"));
					product.setDescription((String) row.get("sku_name"));
					product.setUrl((String) row.get("sku_name"));
					product.setIsActive((Boolean) row.get("prod_active"));
					product.setIsNew((Boolean) row.get("prod_new"));
					product.setRetailPrice((Double) row.get("prod_retail_price"));
					product.setSalePrice((Double) row.get("prod_sale_price"));
					product.setDiscount((Double) row.get("prod_discount"));

					Currency currency = gson.fromJson(String.valueOf(row.get("prod_currency")), Currency.class);
					product.setCurrency(currency);

					mediaListHolder  = gson.fromJson(String.valueOf(row.get("prod_medias")), MediaListHolder.class);
					product.setMedias(mediaListHolder.getMedias());

					Long catId = (Long) row.get("cat_id");
					product.setCategoryId(catId);

					// setting the category in product
					product.setCategory(getCategoryHierarchy(catId));

					product.setSkus(skus);
					pMap.put(productId, product);
				}else{
					Product product = pMap.get(productId);
					List<SKU> existingSkus = product.getSkus();
					existingSkus.addAll(skus);
					product.setSkus(existingSkus);
					pMap.put(productId, product);
				}
			}

			pMap.forEach((k, v) -> productList.add(v));

			productListHolder.setProductList(productList);
			productListHolder.setTotalProductsCount(productList.size());
		}catch (Exception e){
			logger.info("Exception occurred while product category search");
			throw new CatalogManagementException(1002L, e.getMessage());
		}
		return productListHolder;
	}

	private Category getCategoryHierarchy(Long catId){
		String sql = "WITH RECURSIVE c1 as (select cat_id, cat_name, cat_parent_id, cat_url_key, cat_url, cat_active, " +
				"cat_navigation_included, cat_featured from category a where cat_id="+catId+"\n " +
				"\t\t\t\t\t union all \n" +
				"\t\t\t\t\t select in_cat.cat_id, in_cat.cat_name, in_cat.cat_parent_id, in_cat.cat_url_key, in_cat.cat_url, \n" +
				"\t\t\t\t\t  \t\tin_cat.cat_active, in_cat.cat_navigation_included, in_cat.cat_featured  \n" +
				"\t\t\t\t\t from c1 b join category in_cat on in_cat.cat_id = b.cat_parent_id\n" +
				"\t\t\t\t\t )                                                                                                                                                                                     \n" +
				"  SELECT *                                                                                                                                                                                 \n" +
				"  FROM   c1" ;

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Map<Long, Product> pMap = new HashMap<>();
		Category cat = null;
		for (Map row : rows) {

			if(cat == null){
				cat = new Category();
				cat.setId(((Long) row.get("cat_id")).longValue());
				cat.setName((String) row.get("cat_name"));
				//cat.setParentId(((Long) row.get("cat_parent_id")).longValue());
				cat.setUrlKey((String) row.get("cat_url_key"));
				cat.setUrl((String) row.get("cat_url"));
				cat.setActive((Boolean) row.get("cat_active"));
				cat.setNavigationIncluded((Boolean) row.get("cat_navigation_included"));
				cat.setFeatured((Boolean) row.get("cat_featured"));
			}else{
				CategoryParent parent = new CategoryParent();
				parent.setId(((Long) row.get("cat_id")).longValue());
				parent.setUrl((String) row.get("cat_url"));
				parent.setName((String) row.get("cat_name"));
				cat.assignParent(parent);	//parent level category assignment
			}
		}
		return cat;
	}

}
