package exercise.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude={"department", "phones"})
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    @OneToMany(mappedBy = "employee")
    private Set<Phone> phones = new HashSet<Phone>();

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

}

