package com.adstore.dto.account;

import com.adstore.types.RoleType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record AccountStoreDto(
    Long id, @NotNull @NotEmpty String firstName, @NotNull @NotEmpty String userName,
    @NotNull @NotEmpty String lastName, @NotNull @NotEmpty String email, RoleType role,
    boolean hasStore
) {

}
