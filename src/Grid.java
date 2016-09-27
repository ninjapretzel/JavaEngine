
import static org.lwjgl.opengl.GL11.*;

public class Grid {

    public static float DEFAULTSCALE = 10;
    public static int DEFAULTSECTIONS = 20;
    public Vector3 position;
    public float scaleS;
    public float scaleT;
    public int sections;
    public Vector3 directionS;
    public Vector3 directionT;

    public Vector3 color;

    public static Grid xy = GridXY();
    public static Grid xz = GridXZ();
    public static Grid yz = GridYZ();

    public Grid() {
        position = Vector3.zero();

        directionS = Vector3.right();
        directionT = Vector3.forward();
        scaleS = DEFAULTSCALE;
        scaleT = DEFAULTSCALE;

        color = Vector3.green(2);
        sections = DEFAULTSECTIONS;
    }

    public Grid(Vector3 s, Vector3 t) {
        position = Vector3.zero();
        directionS = s;
        directionT = t;

        scaleS = DEFAULTSCALE;
        scaleT = DEFAULTSCALE;
        color = Vector3.white();
        sections = DEFAULTSECTIONS;
    }

    public static Grid GridXZ() {
        return new Grid();
    }

    public static Grid GridXY() {
        Grid g = new Grid();

        g.directionT = Vector3.up();
        g.color = Vector3.blue(2);

        return g;
    }

    public static Grid GridYZ() {
        Grid g = new Grid();

        g.directionS = Vector3.up();
        g.color = Vector3.red(2);

        return g;
    }

    public void draw() {
        glPushMatrix();

        Vector3 start = position.clone();
        Vector3 end = position.plus(directionT.scaled(scaleT));
        glColor3f(color.x, color.y, color.z);

        glBegin(GL_LINES);
        //Drawing all lines from t0 to t1
        for (int s = 0; s < sections + 1; s++) {
            start = position.plus(directionS.scaled(s * scaleS));
            end = start.plus(directionT.scaled(scaleT * sections));

            glVertex3f(start.x, start.y, start.z);
            glVertex3f(end.x, end.y, end.z);
        }

        for (int t = 0; t < sections + 1; t++) {
            start = position.plus(directionT.scaled(t * scaleT));
            end = start.plus(directionS.scaled(scaleS * sections));

            glVertex3f(start.x, start.y, start.z);
            glVertex3f(end.x, end.y, end.z);
        }

        glEnd();
        glPopMatrix();
    }

}
