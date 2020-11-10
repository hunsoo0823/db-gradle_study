package exercise.model;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "EmployeeMapping",
        entities = @EntityResult(
                entityClass = Employee.class, // Entity Class
                fields = {
                    @FieldResult(name = "id", column = "id"),
                    @FieldResult(name = "name", column = "name"),
                    @FieldResult(name = "department_id", column = "department_id")
                }
        )
)

@SqlResultSetMapping(
    name = "EmployeeValueMapping",
    classes = @ConstructorResult(
        targetClass = EmployeeValue.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name"),
            @ColumnResult(name = "department_id", type = Long.class)
        }
    )
)

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="department")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
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

