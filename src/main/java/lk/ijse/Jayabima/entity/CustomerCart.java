package lk.ijse.Jayabima.entity;

import javafx.scene.control.Button;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerCart {
    private String code;
    private String name;
    private String description;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button btn;
}
