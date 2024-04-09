package mapping.dto;

public record ToyDTO(int toy_id,
                     String toy_name,
                     String toy_category,
                     Double toy_price,
                     int toy_stock) {
}
