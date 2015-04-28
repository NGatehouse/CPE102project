import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class tests {
    private static final double DELTA = 0.00001;

    @Test
    public void test_1() {
        assertEquals(1.0, 1.0, DELTA);
    }

    @Test
    public void test_2() {
        assertEquals(1.0, 1.00000000000001, DELTA);
    }

} //meeeeeeeeeeeeeeeeeen! YAY!dfdasfdsaasfadsf22