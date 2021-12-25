package com.ww.pcms.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProductListHolder {
    List<Product> productList;
    Integer totalProductsCount;
    Long errorCode;
    String errorMessage;
}
