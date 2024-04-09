package mapping.mappers;

import mapping.dto.DetailDTO;
import models.Detail;

public class DetailMapper {
    public static DetailDTO mapFromModel(Detail detail){
        return new DetailDTO(detail.getDetail_id(), detail.getOrder(), detail.getToy(), detail.getQuantity(), detail.getUnit_price(), detail.getTotal_Price());
    }
    public static Detail mapFromDTO(DetailDTO detail){
        return Detail.builder()
                .detail_id(detail.detail_id())
                .order(detail.order())
                .toy(detail.toy())
                .quantity(detail.quantity())
                .unit_price(detail.unit_price())
                .total_Price(detail.total_price())
                .build();
    }
}
