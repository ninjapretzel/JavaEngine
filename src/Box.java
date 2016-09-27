
import static org.lwjgl.opengl.GL11.*;

import java.util.*;

public class Box extends GameObject {

    private static final float SPEEDCHANGE = 1.2f;
    private static final float ANGLECHANGE = 3.0f;

    public String type;
    public Vector3 color;
    public Vector3 flags;

    public float angle = 0;
    public float speed = 0;

    public void print(String s) {
        System.out.println(s);
    }

    public Box() {
        super();
        addComponent(new Wall());
        transform = new Transform();
        color = Vector3.white();
        type = "Wall";
    }

    public Box clone() {
        Box b = new Box();
        b.transform = transform.clone();
        b.color = color.clone();
        b.type = type;
        b.angle = angle;
        b.speed = speed;

        return b;
    }

    public String toString() {
        return "<Box Position: " + transform.position.toString()
                + " | Box Scale: " + transform.scale.toString()
                + " | Box Color: " + color.toString() + ">";
    }

    public void move(float time) {
        Vector3 vel = velocity();

        transform.position.x += time * vel.x;
        transform.position.y += time * vel.y;

    }

    public void move(Vector3 v) {
        transform.translate(v);
    }

	////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //OVERIDES
	////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //MY LAME ATTEMPT AT PHYSICS
    public void pushAwayFrom(List<Box> others) {
        for (Box b : others) {
            if (b.type == type) {
                continue;
            }
            if (axisAllignedCollide(b)) {
                pushFrom(b);
            }

        }

    }

    public void pushFrom(Box b) {
        Vector3 dir = position().to(b.position());
        pushFrom(b, dir);
    }

    public void pushFrom(Box b, Vector3 dir) {
        pushFrom(b, dir, .1f);
    }

    public void pushFrom(Box b, Vector3 dir, float maxDist) {
        while (axisAllignedCollide(b)) {
            transform.position.add(dir.normalized().neg().scaled(maxDist));
        }

    }

    public void bounceAgainst(List<Box> others) {
        for (Box b : others) {
            if (b.type == type) {
                continue;
            }
            if (axisAllignedCollide(b)) {
                pushFrom(b);
                float lr = left() - b.right();
                float rl = right() - b.left();
                float tb = top() - b.bottom();
                float bt = bottom() - b.top();

                print("LR:" + lr + "\nRL: " + rl + "\nTB:" + tb + "\nBT:" + bt);

                float lowest = Mathf.min(rl, lr, tb, bt);
                print("Lowest: " + lowest);
                if (lowest == lr || lowest == rl) {
                    setVelocity(Vector3.scale(velocity(), new Vector3(-1, 1, 1)));
                } else {
                    setVelocity(Vector3.scale(velocity(), new Vector3(1, -1, 1)));
                }
            }

        }
    }

    public boolean checkAgainstBoxes(List<Box> others) {
        //print("Checking against " + others.size() + " other boxes");
        boolean hit = false;
        for (Box b : others) {
            if (b.type.equals(type)) {
                continue;
            }
            if (axisAllignedCollide(b)) {
                print(type + " Collided with " + b.type);
                b.collideWith(this);
                collideWith(b);
                hit = true;
            }

        }
        return hit;

    }

    //Time at which this box will collide with another box
    public float collisionTime(Box other, float maxTime) {
        if (type == other.type) {
            return maxTime;
        }
        float smallVal = .00001f;
        Vector3 v = velocity();
        if (v.magnitude() < smallVal) {
            return maxTime;
        }

        Vector3 p = transform.position;
        Vector3 s = transform.scale;

        Vector3 op = other.transform.position;
        Vector3 os = other.transform.scale;
        Vector3 ov = other.velocity();

        float xv = ov.x + v.x;
        float yv = ov.y + v.y;
        float zv = ov.z + v.z;

        float[] t = new float[6];
        t[0] = ((op.x + os.x) - (p.x - s.x)) / (Mathf.abs(xv) < smallVal ? xv : Mathf.sign(xv) * smallVal);
        t[1] = ((p.x + s.x) - (op.x - os.x)) / (Mathf.abs(xv) < smallVal ? xv : Mathf.sign(xv) * smallVal);
        t[2] = ((op.y + os.y) - (p.y - s.y)) / (Mathf.abs(yv) < smallVal ? yv : Mathf.sign(yv) * smallVal);
        t[3] = ((p.y + s.y) - (op.y - os.y)) / (Mathf.abs(yv) < smallVal ? yv : Mathf.sign(yv) * smallVal);
        t[4] = ((op.z + os.z) - (p.z - s.z)) / (Mathf.abs(zv) < smallVal ? zv : Mathf.sign(zv) * smallVal);
        t[5] = ((p.z + s.z) - (op.z - os.z)) / (Mathf.abs(zv) < smallVal ? zv : Mathf.sign(zv) * smallVal);

        float min = maxTime;
        for (int i = 0; i < 6; i++) {
            if (t[i] <= 0 || t[i] >= maxTime) {
                continue;
            }
            if (t[i] < min) {
                min = t[i];
            }
        }
        if (smallVal > min) {
            return smallVal;
        }
        return min;
    }

    public boolean axisAllignedCollide(Box other) {
        Vector3 op = other.transform.position;
        Vector3 os = other.transform.scale.abs();

        Vector3 p = transform.position;
        Vector3 s = transform.scale.abs();

        float dx = Mathf.abs(p.x - op.x);
        float dy = Mathf.abs(p.y - op.y);
        float dz = Mathf.abs(p.z - op.z);

        float cw = s.x + os.x;
        float ch = s.y + os.y;
        float cl = s.z + os.z;

        boolean collided = dx <= cw && dy <= ch && dz <= cl;
        if (collided) {
            flags = new Vector3(dx, dy, dz);
        }

        return collided;
    }

    public Vector3 overlap(Box other) {
        float dx = other.x() - x();
        float dy = other.y() - y();
        float dz = other.z() - z();

        float cw = other.scale().x + scale().x;
        float ch = other.scale().y + scale().y;
        float cl = other.scale().z + scale().z;

        if (dx > cw) {
            return Vector3.zero();
        }
        if (dy > ch) {
            return Vector3.zero();
        }
        if (dz > cl) {
            return Vector3.zero();
        }

        return new Vector3(Mathf.abs(dx - cw), Mathf.abs(dy - ch), Mathf.abs(dz - cl));

    }

    public void changeSpeed(int amt) {
        speed += SPEEDCHANGE * amt;
    }

    public void turn(int amt) {
        angle += ANGLECHANGE * amt;
    }

	////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //UTILITY FUNCTIONS
    public void setVelocity(Vector3 newVelocity) {
        changeAngle(newVelocity);
        speed = newVelocity.magnitude();
    }

    public Vector3 velocity() {
        return new Vector3(Mathf.dcos(angle), Mathf.dsin(angle), 0).scaled(speed);
    }

    public void changeAngle(Vector3 newAngle) {
        angle = Mathf.RAD2DEG * Mathf.atan2(newAngle.y, newAngle.x);
    }

    public void scale(float amt) {
        transform.scale.scale(amt);
    }

    public Vector3 scale() {
        return transform.scale;
    }

    public Vector3 position() {
        return transform.position;
    }

    public float x() {
        return transform.position.x;
    }

    public float y() {
        return transform.position.y;
    }

    public float z() {
        return transform.position.z;
    }

    public AABB bounds() {
        return new AABB(left(), right(), top(), bottom(), front(), back());
    }

    public float left() {
        return transform.position.x - Mathf.abs(transform.scale.x);
    }

    public float right() {
        return transform.position.x + Mathf.abs(transform.scale.x);
    }

    public float bottom() {
        return transform.position.y - Mathf.abs(transform.scale.y);
    }

    public float top() {
        return transform.position.y + Mathf.abs(transform.scale.y);
    }

    public float back() {
        return transform.position.z - Mathf.abs(transform.scale.z);
    }

    public float front() {
        return transform.position.z + Mathf.abs(transform.scale.z);
    }

	////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //DRAWING FUNCTIONS
    public void drawDirection() {
        drawDirection(speed, color);
    }

    public void drawDirection(float sc) {
        drawDirection(sc, color);
    }

    public void drawDirection(Vector3 col) {
        drawDirection(speed, col);
    }

    public void drawDirection(float sc, Vector3 col) {
        glPushMatrix();

        float c = Mathf.dcos(angle);
        float s = Mathf.dsin(angle);

        glTranslated(transform.position.x, transform.position.y, transform.position.z);
        glScaled(sc, sc, sc);
        glColor3f(col.x, col.y, col.z);

        glBegin(GL_LINES);
        glVertex3f(0, 0, 0);
        glVertex3f(c, s, 0);
        glEnd();
        glPopMatrix();

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
        drawWire(transform.scale);
    }

    public void drawWire(Vector3 scale) {
        Skin.clear();
        glPushMatrix();
        glTranslated(transform.position.x, transform.position.y, transform.position.z);
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

    public void draww() {
        draw(transform.scale);
    }

    public void draw(Vector3 scale) {
        Skin.clear();
        glPushMatrix();
        glTranslated(transform.position.x, transform.position.y, transform.position.z);
        glScaled(scale.x, scale.y, scale.z);

		//glColor3f(color.x, color.y, color.z);
        setColorScale(.9f);
        //front face
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, -1);
        glVertex3f(1, -1, -1);
        glVertex3f(1, -1, 1);
        glVertex3f(-1, -1, 1);
        glEnd();

        //Back face
        setColorScale(.6f);
        glBegin(GL_POLYGON);
        glVertex3f(1, 1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, 1, 1);
        glVertex3f(1, 1, 1);
        glEnd();

        //Right face
        setColorScale(.7f);
        glBegin(GL_POLYGON);
        glVertex3f(1, -1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, -1, 1);
        glEnd();

        //Left face
        setColorScale(.8f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //Top face
        setColorScale(1f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //Bottom Face
        setColorScale(.4f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, -1, -1);
        glEnd();

        glPopMatrix();

    }

}
