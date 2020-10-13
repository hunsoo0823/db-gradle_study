package exercise;

import java.util.*;

import org.junit.jupiter.api.*;                             // Test, BeforeAll, BeforeEach, AfterEach
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;           // assertEquals, assertTrue
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;                      // is(), lessThan(), empty()

public class HamcrestTest {
    @Test
    public void testNumber() {
        assertThat(1, is(1));
        assertThat(1, is(equalTo(1)));

        assertThat(1, is(lessThan(2)));
        assertThat(1, lessThan(2));

        assertThat(1, is(not(equalTo(2))));
        assertThat(1, not(equalTo(2)));
        assertThat(1, is(not(2)));
        assertThat(1, not(2));
    }

    @Test
    public void testString() {
        assertThat("one", is("one"));
        assertThat("one", is(equalTo("one")));

        assertThat("one", is(lessThan("two")));
        assertThat("one", lessThan("two"));

        assertThat("one", is(not(equalTo("two"))));
        assertThat("one", not(equalTo("two")));
        assertThat("one", is(not("two")));
        assertThat("one", not("two"));

    }

    @Test
    public void test_anyOf_allOf_instanceOf() {
        assertThat("test", allOf(is("test"), containsString("est")));

        assertThat("test", anyOf(is("testing"), containsString("est")));

        assertThat("test", instanceOf(String.class));
    }

    @Test
    public void testCollection() {
        List<Integer> numbers;

        numbers = new ArrayList<Integer>();
        assertThat(numbers, is(empty()));

        numbers = Arrays.asList(1, 2, 3, 4, 5);
        assertThat(numbers.size(), is(equalTo(5)));

        assertThat(numbers, hasSize(5));

        // ensure the order is correct
        assertThat(numbers, contains(1, 2, 3, 4, 5));

        assertThat(numbers, containsInAnyOrder(2, 3, 1, 4, 5));

        assertThat(numbers, everyItem(greaterThan(0)));
    }
}

