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
public class CategoryParent {

    @ApiModelProperty(value="Category Id")
    private Long id;

    @ApiModelProperty(value="Category name")
    private String name;

    @ApiModelProperty(value="Category url")
    private String url;

    CategoryParent categoryParent;

    public void assignParent(CategoryParent categoryParent){
        if(getCategoryParent() == null){
            setCategoryParent(categoryParent);
        }else{
            getCategoryParent().assignParent(categoryParent);
         }
    }

}
