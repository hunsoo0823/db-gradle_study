package exercise.model;

@lombok.Data
public class DepartmentValue {

    private Long id;

    private String name;

    public DepartmentValue() {
    }

    public DepartmentValue(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}

