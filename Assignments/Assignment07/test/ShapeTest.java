import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    @Test
    void testShapeMethods(){
        Shape s1 = new Shape() {
            @Override
            public double getArea() {
                return 0;
            }

            @Override
            public double getPerimeter() {
                return 0;
            }
        };
        Shape s2 = new Shape("pink", false) {
            @Override
            public double getArea() {
                return 0;
            }

            @Override
            public double getPerimeter() {
                return 0;
            }
        };

        assertEquals("red", s1.getColor());
        assertEquals("pink", s2.getColor());
        assertEquals("Shape[color=red,filled=true]", s1.toString());
        assertEquals("Shape[color=pink,filled=false]", s2.toString());

        s2.setColor("blue");
        assertEquals("blue", s2.getColor());
        assertEquals("Shape[color=blue,filled=false]", s2.toString());

        assertTrue(s1.isFilled());
        assertFalse(s2.isFilled());

        s2.setFilled(true);
        assertTrue(s2.isFilled());
        assertEquals("Shape[color=blue,filled=true]", s2.toString());


    }

}