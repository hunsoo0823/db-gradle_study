package exercise.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="employees")
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // By default,
    //    the JPA @ManyToOne and @OneToOne annotations are fetched EAGERly,
    //      FetchType.EAGER
    //    while the @OneToMany and @ManyToMany relationships are considered LAZY.
    //      FetchType.LAZY

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<Employee>();

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

}

