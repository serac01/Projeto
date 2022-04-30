package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Teacher  implements Serializable {
    private String name;
    private final String email;

    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
}
