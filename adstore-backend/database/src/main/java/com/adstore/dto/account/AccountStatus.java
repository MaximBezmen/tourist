package com.adstore.dto.account;

import com.adstore.jooq.enums.AccountVerificationStatus;

public record AccountStatus(
    AccountVerificationStatus newStatus, long accountId
) {

}
