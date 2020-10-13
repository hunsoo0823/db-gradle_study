package exercise;

import java.util.*;

import org.junit.jupiter.api.*;                             // Test, BeforeAll, BeforeEach, AfterEach
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;           // assertEquals, assertTrue

public class JUnitTest {
    @BeforeAll
    public static void setUpClass() throws Exception {
        // Code executed before the first test method
        System.out.println("@BeforeAll setUpClass()");
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
        // Code executed after the last test method
        System.out.println("@AfterAll tearDownClass()");
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Code executed before each test
        System.out.println("@BeforeEach setUp()");
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Code executed after each test
        System.out.println("@AfterEach tearDown()");
    }

    @Test
    public void testOne() {
        System.out.println("@Test tesetOne()");

        assertEquals(3, 1 + 2);
    }

    @Test
    public void testTwo() {
        System.out.println("@Test testTwo()");

        List<Integer> numbers = List.of(1,2,3);
        assertNotNull(numbers);
    }
}
