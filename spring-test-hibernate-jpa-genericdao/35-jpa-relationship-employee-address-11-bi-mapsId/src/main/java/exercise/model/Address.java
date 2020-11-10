package exercise.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude={"employee"})
public class Address {

    @Id
    private Long id;

    private String street;

    private String city;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Employee employee;

    public Address() {
    }

    public Address(String city) {
        this.city = city;
    }

    public Address(Long id, String city) {
        this.id = id;
        this.city = city;
    }
}
