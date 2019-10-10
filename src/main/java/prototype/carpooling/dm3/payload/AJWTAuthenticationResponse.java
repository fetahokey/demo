package prototype.carpooling.dm3.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AJWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public AJWTAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
