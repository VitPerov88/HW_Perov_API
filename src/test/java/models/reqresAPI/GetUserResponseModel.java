package models.reqresAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetUserResponseModel {

    private Data data;
    private Support support;

    @lombok.Data
    public class Data {
        String id, email, avatar;
        @JsonProperty("first_name")
        String firstName;
        @JsonProperty("last_name")
        String lastName;
    }

    @lombok.Data
    public class Support {
        String url, text;
    }
}
