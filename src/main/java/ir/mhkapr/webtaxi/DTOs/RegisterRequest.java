package ir.mhkapr.webtaxi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String phoneNumber;
    String password;
    String firstname;
    String lastname;
}