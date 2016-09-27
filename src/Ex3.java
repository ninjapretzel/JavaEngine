
import static org.lwjgl.opengl.GL11.*;
import javax.swing.JOptionPane;
import java.util.*;

public class Ex3 extends Basic {

    ArrayList<Vector3> points;
    ArrayList<Vector3> colors;
    boolean test = true;

    private Vertex vert = new Vertex(25, 25, 0, .4f, .6f, 1);
    private Vector3 eye, a, b, c;

    private Vector3 viewPlaneOrigin = new Vector3(-1, -1, 1);
    private Vector3 viewPlaneX = new Vector3(1, -1, 0);
    private Vector3 viewPlaneY = new Vector3(-1, 1, 0);

    public static void main(String[] args) {
        System.out.println("FUCK");
        Ex3 world = new Ex3("H", 400, 400, 60);
        world.start();
    }

    public Ex3(String appTitle, int pw, int ph, int fps) {
        super(appTitle, pw, ph, fps);

        points = new ArrayList<Vector3>();
        colors = new ArrayList<Vector3>();

        points.add(new Vector3(1, 1, 0));
        colors.add(new Vector3(1, 0, 0));
        points.add(new Vector3(1, 9, 0));
        colors.add(new Vector3(1, 0, 0));
        points.add(new Vector3(9, 1, 0));
        colors.add(new Vector3(1, 0, 0));
        points.add(new Vector3(9, 9, 0));
        colors.add(new Vector3(1, 0, 0));
        points.add(new Vector3(5, 6, 0));
        colors.add(new Vector3(0, 0, 1));
        points.add(new Vector3(5, 4, 0));
        colors.add(new Vector3(0, 0, 1));
        points.add(new Vector3(4, 5, 0));
        colors.add(new Vector3(0, 0, 1));
        points.add(new Vector3(6, 5, 0));
        colors.add(new Vector3(0, 0, 1));

    }

    public void init() {
        // glMatrixMode(GL_PROJECTION);
        if (test) {

            eye = new Vector3(0, 0, 0);
            a = new Vector3(-1, -1, 1);
            b = new Vector3(1, -1, 1);
            c = new Vector3(1, 1, 1);
        } else {
            eye = getInputVector3("Eye");

            a = getInputVector3("LowerLeft");
            b = getInputVector3("LowerRight");
            c = getInputVector3("UpperRight");
        }

        glOrtho(0, 10, 0, 10, -1, 1);
        glClearColor(0f, 0f, 0.0f, 0.0f);

    }

    private Vector3 getInputVector3(String name) {
        float x, y, z;
        try {
            x = Float.parseFloat(JOptionPane.showInputDialog("Give " + name + " X cord"));
            y = Float.parseFloat(JOptionPane.showInputDialog("Give " + name + " Y cord"));
            z = Float.parseFloat(JOptionPane.showInputDialog("Give " + name + " Z cord"));
        } catch (Exception e) {
            x = 0;
            y = 0;
            z = 0;
        }
        return new Vector3(x, y, z);
    }

    public void processInputs() {

    }

    public void update() {

    }

    //render? 

    public void display() {
        Vector3 point = new Vector3(0, -1, 1);
        Vector3 norm = Vector3.normal(b, c, a);
        Vector3 q = Vector3.intersect(eye, eye.minus(point), c, norm);
        System.out.println(q.toString());
		//System.out.println(norm.toString());

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glColor3f(1.0f, 0.5f, 0.0f);
        glPointSize(12);
        // draw a square and transform it:
        glPushMatrix();
        //glRotatef( 1, 1, 1, 0 );
        glBegin(GL_POINTS);
        drawPoints();
        glEnd();
        glPopMatrix();
    }

    public void drawPoints() {
        for (int i = 0; i < points.size(); i++) {
            Vector3 pt = points.get(i);

            vertexColor(points.get(i), colors.get(i));
        }
    }

    public void vertexColor(Vector3 v, Vector3 c) {
        glColor3f(c.x, c.y, c.z);
        glVertex3f(v.x, v.y, v.z);
    }

}
