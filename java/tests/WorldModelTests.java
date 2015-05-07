import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    public void test_find_open_around() //TODO given pt and distance, should return new_pt
    {
        Point pt = new Point(14,22);
        int distance = 1;
        Point new_pt = new Point(13,21);

        WorldModel world = new WorldModel(50,50);
        assertTrue(new_pt.equals(world.find_open_around(pt, distance)));
    }

    @Test
    public void test_find_open_around_ifnot()  //TODO, make return null
    {
        Point pt = new Point(14,22);
        int distance = 1;
        WorldModel world = new WorldModel(50,50);
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

    @Test
    public  void test_is_occupiedF() {
        WorldModel world = new WorldModel(5, 5);
        Point pt = new Point(1, 1);
        On_Grid ent = new On_Grid("Ore", pt);
        assertTrue(!world.is_occupied(pt));
    }
    @Test
    public  void test_is_occupiedT()
    {
        WorldModel world = new WorldModel(5,5);
        Point pt = new Point(1,1);
        assertTrue(world.is_occupied(pt));
    }
    @Test
    public  void test_move_entity()
    {
        WorldModel world = new WorldModel(5,5);
        Point pt = new Point(1,1);
        On_Grid ent = new On_Grid("Ore", pt);
        List<Point> tilesComp = new ArrayList<Point>();
        tilesComp.add(pt);
        tilesComp.add(pt);
        assertTrue(tilesComp.equals(world.move_entity(ent, pt)));
    }

    @Test
    public  void test_remove_entity()  //void
    {
        Point pt = new Point(1,1);
        On_Grid ent = new On_Grid("Ore", pt);
        WorldModel world = new WorldModel(5,5);
    }

    @Test
    public  void test_get_tile_occupantT()
    {
        Point pt = new Point(1,1);
        On_Grid ent = new On_Grid("Ore", pt);
        WorldModel world = new WorldModel(5,5);
        assertTrue(ent.equals(world.get_tile_occupant(pt)));
    }

    @Test
    public  void test_get_tile_occupantF()
    {
        Point pt = new Point(1,1);
        On_Grid ent = new On_Grid("Ore", new Point(2,3));
        WorldModel world = new WorldModel(5,5);
        assertTrue(!ent.equals(world.get_tile_occupant(pt)));
    }

    @Test
    public  void test_get_entities()
    {
        List<On_Grid> ents = new ArrayList<On_Grid>();
        On_Grid ent = new On_Grid("Ore", new Point(2,3));
        ents.add(ent);
        WorldModel world = new WorldModel(5,5);
        assertTrue(ents.equals(world.get_entities()));
    }

    @Test
    public  void test_find_nearest()
    {
        Point pt = new Point(1,2);
        List<On_Grid> entList = new LinkedList<On_Grid>();
        List<Double> distsList = new LinkedList<Double>();
        On_Grid ent1 = new On_Grid("Ore", new Point(2,3));
        On_Grid ent2 = new On_Grid("Vein", new Point(3,3));
        entList.add(ent1);
        entList.add(ent2);
        distsList.add(3.0);
        distsList.add(4.0);

        WorldModel world = new WorldModel(5,5);

        assertTrue( Utility.nearest_entity(entList, distsList).equals(null) );
    }

}
