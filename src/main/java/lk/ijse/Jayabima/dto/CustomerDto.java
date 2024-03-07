package lk.ijse.Jayabima.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {
    private String Id;
    private String Name;
    private String Address;
    private String Mobile;
}
