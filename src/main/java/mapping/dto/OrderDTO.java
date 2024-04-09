package mapping.dto;

import models.Client;
import models.Employee;

import java.time.LocalDateTime;

public record OrderDTO(int order_id,
                       Client client,
                       Employee employee,
                       LocalDateTime purchase_date
                       ) {
}
