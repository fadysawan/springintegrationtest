package dl.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

    @Id
    public int id;

    public String name;

    public int age;

    public Person() {

    }

}
