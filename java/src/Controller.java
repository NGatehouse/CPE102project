/*
import worldview
import worldmodel
import point

KEY_DELAY = 400
KEY_INTERVAL = 100
TIMER_FREQUENCY = 100
MOUSE_HOVER_ALPHA = 120
MOUSE_HOVER_EMPTY_COLOR = (0, 255, 0)
MOUSE_HOVER_OCC_COLOR = (255, 0, 0)
 */
import processing.core.*;

public class Controller extends PApplet {

    public void setup() {
        //do something
    }

    public void draw() {
        //do something
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT) {
                //do something
            } else if (keyCode == RIGHT) {
                //do something
            } else if (keyCode == UP) {
                //do something
            } else if (keyCode == DOWN) {
                //do something
            }
        }
    }


    /* Not sure what this does, but think maybe part of draw()
    def handle_timer_event(world, view):
         rects = world.update_on_time(pygame.time.get_ticks())
         view.update_view_tiles(rects)
     */

    /*  Methods in Controller.py
        that may not be implemented
    mouse_to_tile
    handle_mouse_motion
    handle_keydown
    activity_loop
    */


}