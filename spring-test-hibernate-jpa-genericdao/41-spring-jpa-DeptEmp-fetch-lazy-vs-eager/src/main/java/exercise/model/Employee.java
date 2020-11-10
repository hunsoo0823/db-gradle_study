package exercise.model;

import javax.persistence.*;

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="department")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // By default,
    //    the JPA @ManyToOne and @OneToOne annotations are fetched EAGERly,
    //      FetchType.EAGER
    //    while the @OneToMany and @ManyToMany relationships are considered LAZY.
    //      FetchType.LAZY

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

}

