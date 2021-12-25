package com.ww.pcms.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Product {

	@ApiModelProperty(value="Id of the product")
	private Long id;

	@ApiModelProperty(value="Name of the product")
	private String name;

	@ApiModelProperty(value="Description of the product")
	private String description;

	@ApiModelProperty(value="URL of the product")
	private String url;

	@ApiModelProperty(value="Is active product")
	private Boolean isActive;

	@ApiModelProperty(value="Is new product")
	private Boolean isNew;

	@ApiModelProperty(value="List od SKUs")
	private List<SKU> skus;

	@ApiModelProperty(value="List od Medias")
	private List<Media> medias;

	@ApiModelProperty(value="Retail price of the product")
	private Double retailPrice;

	@ApiModelProperty(value="Sale price of the product")
	private Double salePrice;

	@ApiModelProperty(value="Discount")
	private Double discount;

	@ApiModelProperty(value="Currency")
	private Currency currency;

	@ApiModelProperty(value="Category of the product")
	private Category category;

	@ApiModelProperty(value="Category id")
	private Long categoryId;

}
