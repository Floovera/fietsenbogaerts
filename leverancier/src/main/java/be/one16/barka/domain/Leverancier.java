package be.one16.barka.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigInteger;
import java.util.UUID;

@Data
@Builder
public class Leverancier {
    private UUID uuid = UUID.randomUUID();
    private String name;
    private String streetName;
    private int houseNumber;
    private String box;
    private int zipCode;
    private String city;
    private String country;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String btw;
    private String remarks;
}
