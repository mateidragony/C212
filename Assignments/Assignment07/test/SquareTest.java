import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void testSquareMethods(){
        Square s1 = new Square(3.0);

        assertEquals(3.0, s1.getSide());
        assertEquals(3.0, s1.getLength());
        assertEquals(3.0, s1.getWidth());

        s1.setSide(5.0);
        assertEquals(5.0, s1.getSide());
        assertEquals(5.0, s1.getLength());
        assertEquals(5.0, s1.getWidth());

        s1.setLength(10.0);
        assertEquals(10.0, s1.getSide());
        assertEquals(10.0, s1.getLength());
        assertEquals(10.0, s1.getWidth());

        assertEquals("Square[Rectangle[Shape[color=red,filled=true],width=10.0,length=10.0]]",s1.toString());
    }

    @Test
    void testNegativeRectangle() {
        assertThrows(IllegalArgumentException.class, () -> {
            Square c1 = new Square(-2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Square c1 = new Square(-2,"red",false);
        });
    }

    @Test
    void testPolymorphism(){
        Shape s1 = new Square(2.0, "pink", true);
        assertEquals(2.0 * 2.0, s1.getArea());
        assertEquals(2 * 2.0 + 2 * 2.0, s1.getPerimeter());
        Square sq1 = (Square)s1;
        assertEquals(2.0 * 2.0, sq1.getArea());
        assertEquals(2 * 2.0 + 2 * 2.0, sq1.getPerimeter());



        Rectangle r1 = new Square(2.0, "pink", true);
        assertEquals(2.0 * 2.0, r1.getArea());
        assertEquals(2 * 2.0 + 2 * 2.0, r1.getPerimeter());
        Square sq2 = (Square)r1;
        assertEquals(2.0 * 2.0, sq2.getArea());
        assertEquals(2 * 2.0 + 2 * 2.0, sq2.getPerimeter());
    }

}