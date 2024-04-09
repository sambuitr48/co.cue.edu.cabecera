package mapping.mappers;

import mapping.dto.ClientDTO;
import models.Client;

public class ClientMapper {
    public static ClientDTO mapFromModel (Client client) {
        return new ClientDTO(client.getClient_cedula(), client.getClient_name(), client.getClient_age());
    }
    public static Client mapFromDTO (ClientDTO client) {
        return Client.builder()
                .client_cedula(client.client_cedula())
                .client_name(client.client_name())
                .client_age(client.client_age())
                .build();
    }
}
