
import java.util.*;
import static org.lwjgl.opengl.GL11.*;

public class Body {

    public String kind;
    public Transform transform;
    public Vector3[] verts;

    public float angle;
    public float speed;

    public Vector3 velocity;

    public float spinRate;

    public float maxRadius;
    public Vector3 color;
    public AABB sweptVolume;

    public void print(String s) {
        System.out.println(s);
    }

    public Body() {
        kind = "Thing";
        transform = new Transform();
        verts = new Vector3[3];

        angle = 0;
        speed = 0;

        velocity = Vector3.zero();

        spinRate = 0;
    }

    public Body(Scanner input) {
        transform = new Transform();
        kind = input.nextLine();
        print("Loading: " + kind);
        if (kind.equals("Player")) {
            loadPlayer(input);
        } else if (kind.equals("Wall")) {
            loadWall(input);
        }

    }

    public float maxRadius() {
        float rad = 0;
        int max = -1;
        for (int i = 0; i < verts.length; i++) {
            if (verts[i].sqrMagnitude() > rad) {
                rad = verts[i].sqrMagnitude();

                max = i;
            }
        }

        return verts[max].magnitude();
    }

    public void move(float amt) {
        transform.translate(velocity.scaled(amt));
        transform.rotation.z += amt * spinRate;
    }

    public void setSpeedAngle(float spd, float ang) {
        speed = spd;
        angle = ang;
        updateVelocity();
    }

    public void accelerate(float amt) {
        setSpeed(speed + amt);
    }

    public void setSpeed(float amt) {
        speed = amt;
        updateVelocity();
    }

    public void turn(float amt) {
        setAngle(angle + amt);
    }

    public void setAngle(float amt) {
        angle = amt;
        updateVelocity();
    }

    public void updateVelocity() {
        velocity = new Vector3(Mathf.dcos(angle), Mathf.dsin(angle), 0).scaled(speed);
    }

    public void draw() {

        glColor3f(color.x, color.y, color.z);

        glPushMatrix();
        glTranslated(x(), y(), z());
        glRotated(transform.rotation.z, 0f, 0f, 1f);
        glBegin(GL_POLYGON);
        for (int i = 0; i < verts.length; i++) {
            glVertex3f(verts[i].x, verts[i].y, 0f);
        }

        glEnd();
        glPopMatrix();

    }

    public void updateSweptVolume(float time) {
        sweptVolume = getSweptVolume(time);
    }

    public AABB getSweptVolume(float time) {
        AABB box = new AABB();
        Vector3 vel = velocity.scaled(time);

        box.left = Mathf.min(position().x - maxRadius, position().x + vel.x - maxRadius);
        box.right = Mathf.max(position().x + maxRadius, position().x + vel.x + maxRadius);
        box.top = Mathf.max(position().y + maxRadius, position().y + vel.y + maxRadius);
        box.bottom = Mathf.min(position().x - maxRadius, position().y + vel.y - maxRadius);
        box.back = Mathf.min(position().z - maxRadius, position().z + vel.z - maxRadius);
        box.front = Mathf.max(position().z + maxRadius, position().z - vel.z + maxRadius);

        return box;
    }

    public void loadWall(Scanner input) {
        transform.position = new Vector3(input.nextFloat(), input.nextFloat(), 0);

        input.nextLine();
        angle = 0;
        speed = 0;
        updateVelocity();
        color = Vector3.blue();

        float w = input.nextFloat();
        float h = input.nextFloat();
        input.nextLine();

        verts = new Vector3[4];
        verts[0] = new Vector3(x() - w, y() - h, 0);
        verts[1] = new Vector3(x() + w, y() - h, 0);
        verts[2] = new Vector3(x() + w, y() + h, 0);
        verts[3] = new Vector3(x() - w, y() + h, 0);

    }

    public void loadPlayer(Scanner input) {
        //Load number of verts and each vert
        int number = input.nextInt();
        input.nextLine();

        verts = new Vector3[number];
        for (int k = 0; k < number; k++) {
            verts[k] = new Vector3(input.nextFloat(), input.nextFloat(), 0);
            input.nextLine();
        }

        transform.position = new Vector3(input.nextFloat(), input.nextFloat(), 0);
        input.nextLine();

        transform.rotation = new Vector3(0, 0, input.nextFloat());
        input.nextLine();

        angle = input.nextFloat();
        input.nextLine();

        speed = input.nextFloat();
        input.nextLine();

        updateVelocity();
        spinRate = input.nextFloat();
        input.nextLine();

        color = new Vector3(input.nextFloat(), input.nextFloat(), input.nextFloat());
        input.nextLine();

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

}
