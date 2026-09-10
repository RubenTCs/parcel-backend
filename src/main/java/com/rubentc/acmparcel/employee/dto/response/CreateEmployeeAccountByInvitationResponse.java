package com.rubentc.acmparcel.employee.dto.response;

import java.util.UUID;

public record CreateEmployeeAccountByInvitationResponse(
        UUID employeeId,
        String invitationToken
) {
}
