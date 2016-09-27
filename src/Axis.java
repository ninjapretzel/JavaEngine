
import static org.lwjgl.opengl.GL11.*;

public class Axis {

    public Vector3 position;
    public Vector3 direction;
    public Vector3 color;
    public float length;
    public float dot;

    public static Axis x = AxisX();
    public static Axis y = AxisY();
    public static Axis z = AxisZ();

    public Axis() {
        position = Vector3.zero();
        direction = Vector3.right();
        color = Vector3.red();
        length = 600;
        dot = .1f;
    }

    public static Axis AxisX() {
        return new Axis();
    }

    public static Axis AxisY() {
        Axis a = new Axis();
        a.direction = Vector3.up();
        a.color = Vector3.green();
        return a;
    }

    public static Axis AxisZ() {
        Axis a = new Axis();
        a.direction = Vector3.forward();
        a.color = Vector3.blue();
        return a;
    }

    public void draw() {
        glPushMatrix();
        glColor3f(color.x, color.y, color.z);
        Vector3 end = position.plus(direction.scaled(length));

        glBegin(GL_LINES);
        point(position);
        point(end);

        point(position.plus(Vector3.right(dot)));
        point(end);

        point(position.plus(Vector3.left(dot)));
        point(end);

        point(position.plus(Vector3.forward(dot)));
        point(end);

        point(position.plus(Vector3.backward(dot)));
        point(end);

        point(position.plus(Vector3.up(dot)));
        point(end);

        point(position.plus(Vector3.down(dot)));
        point(end);

        glEnd();
        glPopMatrix();
    }

    public void point(Vector3 in) {
        glVertex3f(in.x, in.y, in.z);
    }

}
