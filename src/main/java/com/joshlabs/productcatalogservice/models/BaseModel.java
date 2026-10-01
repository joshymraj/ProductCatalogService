package com.joshlabs.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseModel {
    private Long id;
    private Date createdOn;
    private Date updatedOn;
    private State isActive;
}
