import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void testCircleMethods(){
        Circle c1 = new Circle(2);
        assertEquals(2, c1.getRadius());
        assertEquals(2 * 2 * Math.PI, c1.getArea());
        assertEquals(4 * 2 * Math.PI, c1.getPerimeter());
        c1.setRadius(4);
        assertEquals(4, c1.getRadius());
        assertEquals("Circle[Shape[color=red,filled=true],radius=4.0]", c1.toString());
    }

    @Test
    void testNegativeCircle() {
        assertThrows(IllegalArgumentException.class, () -> {
            Circle c1 = new Circle(-2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Circle c1 = new Circle(-2,"red",false);
        });

    }

    @Test
    void testManyCircles(){
        Circle c1 = new Circle();
        Circle c2 = new Circle(2);
        Circle c3 = new Circle(4,"red",false);
        assertTrue(c1.getArea() < c2.getArea());
        assertTrue(c2.getArea() < c3.getArea());
        c3.setRadius(2);
        assertEquals(c2.getArea(), c3.getArea());
    }

    @Test
    void testPolymorphism(){
        Shape s1 = new Circle(2.0, "pink", true);
        assertEquals(2 * 2 * Math.PI, s1.getArea());
        assertEquals(4 * 2 * Math.PI, s1.getPerimeter());
        Circle c1 = (Circle)s1;
        assertEquals(2 * 2 * Math.PI, c1.getArea());
        assertEquals(4 * 2 * Math.PI, c1.getPerimeter());
    }

}