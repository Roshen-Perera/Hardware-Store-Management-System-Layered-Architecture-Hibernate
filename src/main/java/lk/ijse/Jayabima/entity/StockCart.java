package lk.ijse.Jayabima.entity;

import javafx.scene.control.Button;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StockCart {
    private String code;
    private String name;
    private String description;
    private int qty;
    private String supName;
    private Button btn;

}
