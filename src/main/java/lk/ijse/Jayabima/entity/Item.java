package lk.ijse.Jayabima.entity;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {
    private String itemCode;
    private String itemName;
    private String itemDesc;
    private int itemQty;
    private double itemUnitPrice;
    private String supplierId;

}


