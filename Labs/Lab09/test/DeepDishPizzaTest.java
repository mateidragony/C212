import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeepDishPizzaTest {

    @Test
    void testPizzaMethods() {
        DeepDishPizza p1 = new DeepDishPizza(2, "pep");

        assertEquals(2*2*Math.PI * 0.25, p1.getPrice());
        p1.setRadius(4);
        assertEquals(4*4*Math.PI * 0.25, p1.getPrice());
    }
    @Test
    void testManyPizzas() {
        DeepDishPizza c1 = new DeepDishPizza(2, "pep");
        DeepDishPizza c2 = new DeepDishPizza(4, "Pop");
        DeepDishPizza c3 = new DeepDishPizza(8, "peep");
        assertTrue(c1.getPrice() < c2.getPrice());
        assertTrue(c2.getPrice() < c3.getPrice());
        c3.setRadius(4);
        assertEquals(c2.getPrice(), c3.getPrice());
    }

}