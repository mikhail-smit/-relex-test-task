package mikhail.task.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "roles")
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
