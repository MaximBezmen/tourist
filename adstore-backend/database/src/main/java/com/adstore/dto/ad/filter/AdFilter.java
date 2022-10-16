package com.adstore.dto.ad.filter;

public record AdFilter(
    String title,
    String description,
    Double priceFrom,
    Double priceTo
) {

}
