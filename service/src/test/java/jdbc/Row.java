package jdbc;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
public class Row {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
