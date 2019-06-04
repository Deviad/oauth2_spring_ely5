package com.simplicity.resourceserver.api.v1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public aspect UserinfoDTO {

    private String name;

    private String surname;

    private String telephone;
}
