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
public class Currency {

    @ApiModelProperty(value="Currency name")
    private String name;

}
