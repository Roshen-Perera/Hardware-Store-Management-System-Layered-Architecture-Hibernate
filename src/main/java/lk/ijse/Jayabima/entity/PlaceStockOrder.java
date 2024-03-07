package lk.ijse.Jayabima.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class PlaceStockOrder {
    private String stockOrder_id;
    private String sup_id;
    private LocalDate supOrder_date;
    private List<StockCart> stockCartTmList = new ArrayList<>();
}
