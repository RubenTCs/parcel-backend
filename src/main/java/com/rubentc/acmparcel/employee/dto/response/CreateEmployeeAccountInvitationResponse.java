package com.rubentc.acmparcel.employee.dto.response;

import java.util.UUID;

public record CreateEmployeeAccountInvitationResponse(
        UUID employeeId,
        String invitationToken
) {
}
