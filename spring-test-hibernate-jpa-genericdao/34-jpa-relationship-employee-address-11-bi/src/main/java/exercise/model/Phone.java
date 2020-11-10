package exercise.model;

import javax.persistence.*;

@Entity

@lombok.Data
@lombok.EqualsAndHashCode(of="id")
@lombok.ToString(exclude="employee")
public class Phone {

    @Id
    @GeneratedValue
    private Long id;

    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    private Employee employee;

    public Phone() {}

    public Phone(String model, Employee employee) {
        this.model = model;
        this.employee = employee;
    }

    public Phone(Long id, String model, Employee employee) {
        this.id = id;
        this.model = model;
        this.employee = employee;
    }

}

