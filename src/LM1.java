
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

public class LM1 extends Basic {

    public static void main(String[] args) {
        LM1 app = new LM1();
        app.start();
    }

    // instance variables for LM1:
    private boolean useLighting;
    private Light light;

    public LM1() {
        super("L&M Example 1", 500, 500, 60);

        useLighting = true;

        light = new Light(0, // light0
                1, 1, 1, // white ambient
                1, 1, 1, // white diffuse
                1, 1, 1, // white specular
                0, 0, 1, 1, // position of local light
                0, 0, -1, // spotlight direction
                10, 45, // spotlight exponent, cutoff
                1, 0, 0);  // constant attentuation
    }

    public void init() {
        glEnable(GL_DEPTH_TEST);

        // set up projection once and for all:
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(-1, 1, -1, 1, 1, 1000);

        // set the background color:
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        if (useLighting) {
            Light.activateLighting();
            light.turnOn();
            light.place();
        }

    }

    public void processInputs() {
        Keyboard.poll();
    }

    public void update() {
        //System.out.println( getFrameNumber() );

    }

    public void display() {
        // set viewport:
        glViewport(0, 0, 500, 500);

        // clear the screen to background color and clear the depth buffer:
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // switch to model view from this point on:
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        if (useLighting) {
            Material.brass.use(GL_FRONT_AND_BACK);
        }

        wall(-5, 5, 10, -5, 5, 10, -10);

    }

    // draw a simple tiled wall
    private void wall(double left, double right, int numHoriz,
            double bottom, double top, int numVert,
            double z) {
        double w = (right - left) / numHoriz;
        double x = left;
        double h = (top - bottom) / numVert;
        double y = bottom;

        glNormal3d(0, 0, -1);

        for (int row = 0; row < numVert; row++) {// draw row
            for (int col = 0; col < numHoriz; col++) {// draw a rectangle
                if (!useLighting) {
                    if ((row + col) % 2 == 0) {
                        glColor3f(1, 0, 0);
                    } else {
                        glColor3f(0, 1, 0);
                    }
                }

                glBegin(GL_POLYGON);
                glVertex3d(x, y, z);
                glVertex3d(x + w, y, z);
                glVertex3d(x + w, y + h, z);
                glVertex3d(x, y + h, z);
                glEnd();

                x += w;  // move on to next column
            }

            y += h;    // move on to next row
            x = left;
        }
    }

}
