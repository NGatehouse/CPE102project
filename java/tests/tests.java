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
    @Test
    public void test_getName()
    {
        String n = "myName";
        Entity e = new Entity(n);
        assertTrue(e.get_name().equals(n));
    }
    @Test
    public void test_getRate()
    {
        String n = "myName";
        int r = 10;
        Point p = new Point(3,4);
        Actor a = new Actor(n,p,r);
        assertTrue(a.get_rate() == r);
    }
    @Test
     public void test_getPosition()
    {
        String n = "myName";
        Point p = new Point(3,4);
        On_Grid grid = new On_Grid(n,p);
        assertTrue(grid.get_position().get_x() == p.get_x()); //testing get_position & get_x
        assertTrue(grid.get_position().get_y() == p.get_y()); //testing get_position & get_y
    }
    @Test
      public void test_ObstacleString()
    {
        String n = "myName";
        Point p = new Point(3,4);
        Obstacle o = new Obstacle(n,p);
        String s = "obstacle myName 3 4";
        assertTrue(o.entity_string().equals(s));
    }
    @Test
    public void test_OreString()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        Ore o = new Ore(n,p,r);
        String s = "ore myName 3 4 5000";
        assertTrue(o.entity_string().equals(s));
    }
    @Test
    public void test_VeinString()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        Vein v = new Vein(n,r,p,resource);
        String s = "vein myName 3 4 5000 1";
        assertTrue(v.entity_string().equals(s));
    }
    @Test
    public void test_VeinGet_r_Dist()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        Vein v = new Vein(n,r,p,resource);
        assertTrue(v.get_resource_distance() == resource);
    }
    @Test
    public void test_BlacksmithString()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        Blacksmith b = new Blacksmith(n,p,resource,r);
        String s = "blacksmith myName 3 4 1 5000";
        assertTrue(b.entity_string().equals(s));
    }
    @Test
      public void test_MiningGetResourceLimit()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        Mining m = new Mining(n,resource,p,r);
        assertTrue(m.get_resource_limit() == resource );
    }
    @Test
    public void test_MiningGetResourceCount()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        Mining m = new Mining(n,resource,p,r);
        assertTrue(m.get_resource_count() == 0);
    }
    @Test
    public void test_MiningSetResourceCount()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        int replacement = 10;
        Mining m = new Mining(n,resource,p,r);
        m.set_resource_count(replacement); //what we are testing
        assertEquals(m.get_resource_count(), replacement, DELTA);
    }
    @Test
    public void test_MinerString()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        Miner m = new Miner(n,resource,p,r);
        String s = "miner myName 3 4 1 5000";
        assertTrue(m.entity_string().equals(s));
    }
    @Test
    public void test_MinerNotFull_try_transform()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 1;
        MinerNotFull m = new MinerNotFull(n,resource,p,r);
        MinerFull new_m = new MinerFull(n,resource,p,r);
        WorldModel world = new WorldModel(1,2);
        assertTrue(m.try_transform(world).equals(new_m)); // check both pathways..resourcecount
    }
    @Test
    public void test_MinerNotFull_doesnt_transform()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 0;
        MinerNotFull m = new MinerNotFull(n,resource,p,r);
        WorldModel world = new WorldModel(1,2);
        assertTrue(m.try_transform(world).equals(m)); // check both pathways..resourcecount
    }
    @Test
    public void test_MinerFull_try_transform()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 0;
        MinerNotFull new_m = new MinerNotFull(n,resource,p,r);
        MinerFull m = new MinerFull(n,resource,p,r);
        WorldModel world = new WorldModel(1,2);
        assertTrue(m.try_transform(world).equals(new_m));
    }
    
    @Test
    public void test_MinerNotFull_to_other()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 0;
        MinerNotFull m = new MinerNotFull(n,resource,p,r);
        WorldModel world = new WorldModel(1,2);
        Ore ore = new Ore(n,p,r);
        assertTrue(m._to_other(world,ore)); //
    }
    @Test
    public void test_MinerNotFull_to_other_fail()
    {
        String n = "myName";
        Point p = new Point(3,4);
        int r = 5000;
        int resource = 0;
        MinerNotFull m = new MinerNotFull(n,resource,p,r);
        WorldModel world = new WorldModel(1,2);
        Ore ore = new Ore(n,p,r);
        assertTrue(!(m._to_other(world,ore))); // check both pathways..resourcecount
    }



}