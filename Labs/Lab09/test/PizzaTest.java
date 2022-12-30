import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    @Test
    void testPizzaMethods() {
        Pizza p1 = new Pizza(2, "pep");
        assertEquals(2, p1.getRadius());
        assertEquals(2 * 2 * Math.PI, p1.getArea());
        p1.setRadius(4);
        assertEquals(4, p1.getRadius());

        assertEquals("pep", p1.getTopping());
        p1.setTopping("Pop");
        assertEquals("Pop", p1.getTopping());

        assertEquals(4*4*Math.PI * 0.15, p1.getPrice());
    }
    @Test
    void testNegativePizza() {
        assertThrows(IllegalArgumentException.class, () -> {
            Pizza c1 = new Pizza(-2,"pep");
        });
    }
    @Test
    void testManyPizza() {
        Pizza c1 = new Pizza(2, "pep");
        Pizza c2 = new Pizza(4, "Pop");
        Pizza c3 = new Pizza(8, "peep");
        assertTrue(c1.getPrice() < c2.getPrice());
        assertTrue(c2.getPrice() < c3.getPrice());
        c3.setRadius(4);
        assertEquals(c2.getPrice(), c3.getPrice());
    }
}