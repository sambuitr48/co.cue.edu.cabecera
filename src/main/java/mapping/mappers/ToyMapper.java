package mapping.mappers;

import mapping.dto.ToyDTO;
import models.Toy;

public class ToyMapper {
    public static ToyDTO mapFromModel(Toy toy){
        return new ToyDTO(toy.getToy_id(), toy.getToy_name(), toy.getToy_category(), toy.getToy_price(), toy.getToy_stock());
    }
    public static Toy mapFromDto(ToyDTO toy){
        return Toy.builder()
                .toy_id(toy.toy_id())
                .toy_name(toy.toy_name())
                .toy_category(toy.toy_category())
                .toy_price(toy.toy_price())
                .toy_stock(toy.toy_stock())
                .build();
    }
}
