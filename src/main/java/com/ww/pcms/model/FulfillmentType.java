package com.ww.pcms.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FulfillmentType {

    @ApiModelProperty(value="Fulfillment Friendly Name")
    private String friendlyName;

    @ApiModelProperty(value="Fulfillment name")
    private String type;

}
