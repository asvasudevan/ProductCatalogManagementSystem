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
public class Category {

    @ApiModelProperty(value="Category Id")
    private Long id;

    @ApiModelProperty(value="Parent category Id")
    private Long parentId;

    @ApiModelProperty(value="Category name")
    private String name;

    @ApiModelProperty(value="Category Active")
    private Boolean active;

    @ApiModelProperty(value="Category Featured")
    private Boolean featured;

    @ApiModelProperty(value="NavigationIncluded")
    private Boolean navigationIncluded;

    @ApiModelProperty(value="Category url")
    private String url;

    @ApiModelProperty(value="Category url key")
    private String urlKey;

    CategoryParent parent;

    public void assignParent(CategoryParent categoryParent){
        if(getParent() == null){
          setParent(categoryParent);
        }else{
            getParent().assignParent(categoryParent);
        }
    }

}
