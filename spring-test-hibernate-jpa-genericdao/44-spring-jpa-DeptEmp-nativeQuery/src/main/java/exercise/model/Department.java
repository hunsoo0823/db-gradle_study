package exercise.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "DepartmentMapping",
        entities = @EntityResult(
                entityClass = Department.class, // Entity Class
                fields = {
                    @FieldResult(name = "id", column = "id"),
                    @FieldResult(name = "name", column = "name")
                }
        )
)

@SqlResultSetMapping(
    name = "DepartmentValueMapping",
    classes = @ConstructorResult(
        targetClass = DepartmentValue.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name")
        }
    )
)

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="employees")
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<Employee>();

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

}

