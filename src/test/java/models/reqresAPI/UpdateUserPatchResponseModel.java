package models.reqresAPI;

import lombok.Data;

@Data
public class UpdateUserPatchResponseModel {
    String name, job, updatedAt;
}
