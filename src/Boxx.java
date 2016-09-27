
import static org.lwjgl.opengl.GL11.*;

import java.util.*;

//Boxx class
//Represents a simple axis aligned box for the purposes of just drawing the box on the screen.
public class Boxx {

    public Vector3 position;
    public Vector3 color;

    public void print(String s) {
        System.out.println(s);
    }

	//Constructors
    //Using clones because we just want to copy the values
    public Boxx() {
        position = Vector3.zero();
        color = Vector3.one();
    }

    public Boxx(Vector3 pos) {
        position = pos.clone();
        color = Vector3.one();
    }

    public Boxx(Vector3 pos, Vector3 col) {
        position = pos.clone();
        color = col.clone();
    }

    public Boxx clone() {
        return new Boxx(position, color);
    }

	//are we close to the position of another box?
    //we can also use this to short-circuit from collision detection if the two boxes are too far apart
    public boolean near(Boxx other) {
        return near(other, .1f);
    }

    public boolean near(Boxx other, float tolerance) {
        return other.position.to(position).magnitude() < tolerance;
    }

    //Debug toString
    public String toString() {
        String str = "<Boxx Position:" + position.toString() + " | Color: " + color.toString() + " >";
        return str;
    }

    //toString to get us a formatted string that we can load in later
    public String toLoadableString() {
        String str = "" + position.x + " " + position.y + " " + position.z;
        str += " " + color.x + " " + color.y + " " + color.z;

        return str;
    }

    //load values out of a single line
    public void loadFromString(String str) {
        Scanner in = new Scanner(str);

        position.x = in.nextFloat();
        position.y = in.nextFloat();
        position.z = in.nextFloat();

        color.x = in.nextFloat();
        color.y = in.nextFloat();
        color.z = in.nextFloat();

        in.close();
    }

	//Draw the box as a wireframe
    //Overloads for scale by uniform or non-uniform values (since scale has to happen before translation)
    public void drawWire(float x, float y, float z) {
        drawWire(new Vector3(x, y, z));
    }

    public void drawWire(float f) {
        drawWire(new Vector3(f, f, f));
    }

    public void drawWire() {
        drawWire(Vector3.ONE);
    }

    public void drawWire(Vector3 scale) {
        glPushMatrix();
        glTranslated(position.x, position.y, position.z);
        glScaled(scale.x, scale.y, scale.z);

        glColor3f(color.x, color.y, color.z);

        glBegin(GL_LINES);

        //Square 1
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, 1, 1);
        glVertex3f(-1, 1, 1);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, -1, -1);

        //Square 2
        glVertex3f(1, -1, -1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, -1, -1);

        //Connect the squares
        glVertex3f(-1, 1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(-1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(-1, -1, -1);
        glVertex3f(1, -1, -1);

        glEnd();
        glPopMatrix();
    }

    public void setColorScale(float f) {
        setColor(color.scaled(f));
    }

    public void setColor(Vector3 col) {
        glColor3f(col.x, col.y, col.z);
    }

	//Draws the cube as a series of planes
    //Overloaded for scale by uniform or non-uniform values
    public void draw(float x, float y, float z) {
        draw(new Vector3(x, y, z));
    }

    public void draw(float f) {
        draw(new Vector3(f, f, f));
    }

    public void draw() {
        draw(Vector3.one());
    }

    public void draw(Vector3 scale) {
        glPushMatrix();
        glTranslated(position.x, position.y, position.z);
        glScaled(scale.x, scale.y, scale.z);

		//glColor3f(color.x, color.y, color.z);
        setColorScale(.9f);
        //front face
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, -1);
        glVertex3f(1, -1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(-1, 1, -1);
        glEnd();

        //Back face
        setColorScale(.6f);
        glBegin(GL_POLYGON);
        glVertex3f(1, -1, 1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, 1, 1);
        glVertex3f(1, 1, 1);
        glEnd();

        //Right face
        setColorScale(.7f);
        glBegin(GL_POLYGON);
        glVertex3f(1, -1, -1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, 1, -1);
        glEnd();

        //Left face
        setColorScale(.8f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //Top face
        setColorScale(1f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //Bottom Face
        setColorScale(.4f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, -1, -1);
        glEnd();

        glPopMatrix();

    }

}
