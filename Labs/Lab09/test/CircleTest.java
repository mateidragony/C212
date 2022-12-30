import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void testCircleMethods() {
        Circle c1 = new Circle(2);
        assertEquals(2, c1.getRadius());
        assertEquals(2 * 2 * Math.PI, c1.getArea());
        c1.setRadius(4);
        assertEquals(4, c1.getRadius());
    }
    @Test
    void testNegativeCircle() {
        assertThrows(IllegalArgumentException.class, () -> {
            Circle c1 = new Circle(-2);
        });
    }
    @Test
    void testManyCircles() {
        Circle c1 = new Circle(2);
        Circle c2 = new Circle(4);
        Circle c3 = new Circle(8);
        assertTrue(c1.getArea() < c2.getArea());
        assertTrue(c2.getArea() < c3.getArea());
        c3.setRadius(4);
        assertEquals(c2.getArea(), c3.getArea());
    }
}