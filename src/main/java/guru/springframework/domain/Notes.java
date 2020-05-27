package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = ("recipe"))
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Not cascaded, if a Notes entity is
    //deleted, the corresponding Recipe
    //entity won't be deleted.
    @OneToOne
    private Recipe recipe;

    @Lob //annotation for Large Object Storage
    private String recipeNotes;

    public Notes() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Notes;
    }

}

