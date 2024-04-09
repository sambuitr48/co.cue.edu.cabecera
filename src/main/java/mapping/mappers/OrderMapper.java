package mapping.mappers;

import mapping.dto.OrderDTO;
import models.Order;

public class OrderMapper {
    public static OrderDTO mapFromModel(Order order) {
        return new OrderDTO(order.getOrder_id(), order.getClient(), order.getEmployee(), order.getPurchase_date());
    }
    public static Order mapFromDto(OrderDTO order){
        return Order.builder()
                .order_id(order.order_id())
                .client(order.client())
                .employee(order.employee())
                .purchase_date(order.purchase_date())
                .build();
    }
}
