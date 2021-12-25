package com.ww.pcms.Exception;

import lombok.*;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CatalogManagementException extends RuntimeException{

    private Long errorCode;
    private String errorMessage;

}
