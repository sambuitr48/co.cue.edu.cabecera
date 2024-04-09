package mapping.dto;

import java.util.Date;

public record   ClientDTO(int client_cedula,
                        String client_name,
                        Date client_age) {
}
