package exercise;

import java.util.*;

import org.junit.jupiter.api.*;                             // Test, BeforeAll, BeforeEach, AfterEach
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;           // assertEquals, assertTrue
import static org.assertj.core.api.Assertions.assertThat;   // assertThat
import static org.assertj.core.api.Assertions.withPrecision;

public class AssertJTest {
    @Test
    public void testInteger() {
        assertThat(1+2).isEqualTo(3);
        assertThat(1+2).isGreaterThan(1);
        assertThat(1+2).isGreaterThanOrEqualTo(3);
        assertThat(1+2).isBetween(1, 3);
        assertThat(1+2).isNotNegative();
    }

    @Test
    public void testFloat() {
        assertThat(5.1).isEqualTo(5, withPrecision(1d));
    }


    @Test
    public void testString() {
        assertThat("John").isEqualTo("John");
        assertThat("John").isGreaterThan("Abc");
        assertThat("John").isLessThan("Xyz");

        assertThat("".isEmpty()).isTrue();
    }

    @Test
    public void testCollection() {

        // List<Integer>

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        assertThat(numbers).contains(1);

        assertThat(numbers).isNotEmpty();

        assertThat(numbers).startsWith(1);

        assertThat(numbers.size()).isEqualTo(5);

        assertThat(numbers).contains(3);

        assertThat(numbers).containsAnyOf(2,3,4);

        assertThat(numbers).endsWith(5);

        // List<String>

        List<String> list = Arrays.asList("one", "two", "three");

        assertThat(list).contains("one");

        assertThat(list).isNotEmpty();

        assertThat(list).startsWith("one");

        assertThat(list)
                .isNotEmpty()
                .contains("one")
                .doesNotContainNull()
                .containsSequence("two", "three");

    }
}

