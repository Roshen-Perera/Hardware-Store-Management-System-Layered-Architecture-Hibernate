package lk.ijse.Jayabima.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlaceCustomerOrder {
    private String orderId;
    private String customerId;
    private String customerName;
    private String totalPrice;
    private LocalDate date;
    private List<CustomerCart> customerCartList = new ArrayList<>();
}