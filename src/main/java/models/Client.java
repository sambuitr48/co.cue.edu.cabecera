package models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder //Patrón de diseños para crear objetos de forma más fácil.
public class Client {
    private int client_cedula;
    private String client_name;
    private Date client_age;
}
