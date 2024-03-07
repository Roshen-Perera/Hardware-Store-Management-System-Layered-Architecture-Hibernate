package lk.ijse.Jayabima.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Supplier {
    private String supId;
    private String supName;
    private String supDesc;
    private String supMobile;
}