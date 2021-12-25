package com.ww.pcms.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SKU {

    @ApiModelProperty(value="Sku Id of the product")
    private Long id;

    @ApiModelProperty(value="SKU name of the product")
    private String name;

    @ApiModelProperty(value="SKU Description")
    private String description;

    @ApiModelProperty(value="SKU short description")
    private String shortDescription;

    @ApiModelProperty(value="SKU start date")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-ddThh:mm:ss.s+zzzzzz")
    private Timestamp startDate;

    @ApiModelProperty(value="Retail price of the product")
    private Double retailPrice;

    @ApiModelProperty(value="Sale price of the product")
    private Double salePrice;

    @ApiModelProperty(value="SKU base price unit")
    private String basePriceUnit;

    @ApiModelProperty(value="SKU discount")
    private Double discount;

    @ApiModelProperty(value="is SKU active")
    private boolean isActive;

    @ApiModelProperty(value="is SKU available")
    private boolean isAvailable;

    @ApiModelProperty(value="Inventory type of the product")
    private String inventoryType;

    @ApiModelProperty(value=" WW SKU of the product")
    private String wwSKU;

    @ApiModelProperty(value="Product Type")
    private String productType;

    @ApiModelProperty(value="SAvailable quantity of the product")
    private Long quantityAvailable;

    @ApiModelProperty(value="Subscription")
    private boolean isSubscription;

    @ApiModelProperty(value="Tax code")
    private String taxCode;

    /*@ApiModelProperty(value="id of the product")
    private long productId;*/

    @ApiModelProperty(value="Fulfillment Type")
    private FulfillmentType fulfillmentType;

    @ApiModelProperty(value="Media details")
    private List<Media> media;
}
