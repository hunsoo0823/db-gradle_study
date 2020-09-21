package exercise;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


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
        assertThat(calculator.add(1, 2)).isEqualTo(3);
    }

    @Test
    public void testNumber() {
        assertThat(1).isEqualTo(1);

        assertThat(1).isNotEqualTo(2);

        assertThat(true).isTrue();

        assertThat(1 == 2).isFalse();
    }

    @Test
    public void testString() {
        assertThat("abc").contains("bc");

        assertThat("Frodo")
            .startsWith("Fro")
            .endsWith("do")
            .isEqualToIgnoringCase("frodo");

        assertThat("".isEmpty()).isTrue();
    }

    @Test
    public void testCollection() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        assertThat(numbers.size()).isEqualTo(5);

        assertThat(numbers).hasSize(5);

        assertThat(numbers).isNotEmpty();

        assertThat(numbers)
            .contains(1)
            .contains(3, 2, 5)
            .doesNotContain(7, 8)
            .containsSequence(2, 3);

        assertThat(3).isIn(numbers);

        assertThat(numbers).containsExactlyInAnyOrder(2, 3, 1, 4, 5);
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5);

        assertThat(numbers)
            .startsWith(1)
            .endsWith(5);

        List<Integer> emptyList = new ArrayList<Integer>();
        assertThat(emptyList).isEmpty();
    }

    @Test
    public void testDescribeingAssertion() {
        assertThat(45)
          .as("%s's age should be not equal to 100", "Steve")
          .isNotEqualTo(100);
    }

}
