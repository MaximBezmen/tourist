package com.adstore.dto.store;

import javax.validation.constraints.NotNull;

public record RecommendStore(
    @NotNull String url, @NotNull String storeLogo, @NotNull String name
) {

}
