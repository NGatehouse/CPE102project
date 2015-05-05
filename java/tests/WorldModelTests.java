import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class WorldModelTests
{
    private static final double DELTA = 0.00001;

    @Test
    public void test_1() {
        assertEquals(1.0, 1.0, DELTA);
    }

    @Test
    public void test_2() {
        assertEquals(1.0, 1.00000000000001, DELTA);
    }

    @Test
    public void test_find_open_around()
    {
        Point pt = new Point(3,4);
        int distance = 5;
        WorldModel world = new WorldModel(5,5);
        assertTrue(pt.equals(world.find_open_around(pt, distance)));
    }

    @Test
    public void test_find_open_around_ifnot()
    {
        Point pt = new Point(20,50);
        int distance = 5;
        WorldModel world = new WorldModel(5,5);
        assertTrue(!pt.equals(world.find_open_around(pt, distance)));
    }

    @Test
    public void test_blob_next_pos()
    {
        Point entpt = new Point(1,1);
        Point destpt = new Point(2,2);
        Point newpt = new Point(0,-1);
        WorldModel world = new WorldModel(5,5);
        assertTrue(newpt.get_x() == world.blob_next_position(entpt, destpt).get_x());
    }

    @Test
    public void test_next_position()
    {
        Point entpt = new Point(1,1);
        Point destpt = new Point(2,2);
        Point newpt = new Point(0,-1);
        WorldModel world = new WorldModel(5,5);
        assertTrue(newpt.get_x() == world.next_position(entpt, destpt).get_x());
    }

    @Test
    public void test_within_boundsT()
    {
        Point pt = new Point(1,1);
        WorldModel world = new WorldModel(5,5);
        assertTrue(world.within_bounds(pt));
    }

    @Test
    public void test_within_boundsF()
    {
        Point pt = new Point(50,555);
        WorldModel world = new WorldModel(5,5);
        assertTrue(world.within_bounds(pt));
    }





}
