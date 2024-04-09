package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Detail {
    private int detail_id;
    private Order order;
    private Toy toy;
    private int quantity;
    private Double unit_price;
    private Double total_Price;
}
