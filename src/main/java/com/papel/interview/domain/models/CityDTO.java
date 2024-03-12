package com.papel.interview.domain.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CityDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String name;

    private boolean isCapital;

}
