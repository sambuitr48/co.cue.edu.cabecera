package mapping.dto;

import lombok.Builder;
import models.Order;
import models.Toy;

@Builder
public record DetailDTO(int detail_id,
                        Order order,
                        Toy toy,
                        int quantity,
                        Double unit_price,
                        Double total_price) {
}
