
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testRectangleMethods(){
        Rectangle r1 = new Rectangle(2.0,4.0);
        assertEquals(2.0, r1.getWidth());
        assertEquals(4.0, r1.getLength());
        assertEquals(2.0 * 4.0, r1.getArea());
        assertEquals(2.0 * 2 + 4.0 * 2, r1.getPerimeter());

        r1.setLength(10.0);
        assertEquals(10.0, r1.getLength());

        assertEquals("Rectangle[Shape[color=red,filled=true],width=2.0,length=10.0]", r1.toString());
    }

    @Test
    void testNegativeRectangle() {
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle c1 = new Rectangle(-2,-2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Rectangle c1 = new Rectangle(-2,67,"red",false);
        });
    }

    @Test
    void testManyRectangles() {
        Rectangle c1 = new Rectangle();
        Rectangle c2 = new Rectangle(2,4);
        Rectangle c3 = new Rectangle(4,8,"red",false);
        assertTrue(c1.getArea() < c2.getArea());
        assertTrue(c2.getArea() < c3.getArea());
        c3.setLength(2);
        assertEquals(c2.getArea(), c3.getArea());
    }

    @Test
    void testPolymorphism(){
        Shape s1 = new Rectangle(2.0, 4.0, "pink", true);
        assertEquals(2.0 * 4.0, s1.getArea());
        assertEquals(2 * 2.0 + 2 * 4.0, s1.getPerimeter());
        Rectangle r1 = (Rectangle)s1;
        assertEquals(2.0 * 4.0, r1.getArea());
        assertEquals(2 * 2.0 + 2 * 4.0, r1.getPerimeter());
    }

}