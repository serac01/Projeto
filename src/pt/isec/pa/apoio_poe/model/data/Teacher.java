package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Teacher  implements Serializable {
    public static final long serialVersionUID=2020129026;
    private String name;
    private final String email;

    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Teacher name: %-40s Teacher email: %-50s",name,email);
    }
}
