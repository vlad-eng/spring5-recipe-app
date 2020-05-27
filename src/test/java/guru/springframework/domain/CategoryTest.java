package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {

        Long idValue = 4l;
        category.setId(idValue);

        assertThat(4l, equalTo(category.getId()));
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}
