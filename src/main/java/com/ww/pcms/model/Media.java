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
public class Media {

    @ApiModelProperty(value="Media id")
    private Long mediaId;

    @ApiModelProperty(value="Alternate text")
    private String altText;

    @ApiModelProperty(value="Media url")
    private String url;

}
