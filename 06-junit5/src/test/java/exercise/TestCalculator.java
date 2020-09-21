package exercise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCalculator {

    // JUnit 4:
    //   assertEquals([message,] expected, actual)
    // JUnit 5:
    //   assertEquals(expected, actual[, message])
    // Hamcrest:
    //   assertThat([message,] T actual, Matcher<T> matcher)
    // AssertJ:
    //   assertThat(T actual).as("check %s's age", frodo.getName()).isEquals(expected)

    @Test
    public void testAdd() {
        Calculator calculator = new Calculator();
        assertEquals(3, calculator.add(1, 2), "1 + 2 should equal 3");
    }

    @Test
    public void testNumber() {
        assertEquals(1, 1);

        assertNotEquals(1, 2);

        assertTrue(true);
        // assertTrue(false);

        assertFalse(1 == 2);
    }

}
