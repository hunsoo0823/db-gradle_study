package exercise.model;

@lombok.Data
public class EmployeeValue {

    private Long id;

    private String name;

    private Long department_id;

    public EmployeeValue() {
    }

    public EmployeeValue(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmployeeValue(Long id, String name, Long department_id) {
        this.id = id;
        this.name = name;
        this.department_id = department_id;
    }
}

