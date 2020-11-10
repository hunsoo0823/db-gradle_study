package exercise.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

}

