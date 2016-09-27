
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.GLU;

public class City extends Basic {

    public float time = 0;
    public Vector3 cam;
    public Vector3 camLook;
    public Vector3 camRight;
    public Vector3 camUp;
    public float camYaw;
    public float camPitch;

    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        City world = new City("An Open GL City", 1000, 500, 60);
        world.start();

    }

    public City(String appTitle, int pw, int ph, int fps) {
        super(appTitle, pw, ph, fps);

        cam = new Vector3(900, 900, 6);
        camUp = new Vector3(0, 0, 1);
        camYaw = 135;
        camPitch = 0;

    }

    public void init() {
        glClearColor(1, 1, 1, 1);

        //Toggle this line and see what happens:
        glEnable(GL_DEPTH_TEST);

        glFrustum(-1, 1, -1, 1, .9, 1000);
        glOrtho(-600, 600, -600, 600, -1000, 1000);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void processInputs() {
        Keyboard.poll();
        float speed = 3;
        float trn = 12f;

        float spd = speed;
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            spd *= 3;
        }

        camLook = new Vector3(Mathf.dcos(camYaw), Mathf.dsin(camYaw), 0);
        camRight = new Vector3(Mathf.dcos(camYaw - 90), Mathf.dsin(camYaw - 90), 0);
        camUp = Vector3.cross(camRight, camLook);

        //Move
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            cam = cam.plus(camRight.scaled(-spd));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            cam = cam.plus(camRight.scaled(spd));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            cam = cam.plus(camLook.scaled(spd));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            cam = cam.plus(camLook.scaled(-spd));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            cam = cam.plus(camUp.scaled(-spd));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            cam = cam.plus(camUp.scaled(spd));
        }

        //Turn
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            camYaw += trn;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            camYaw -= trn;
        }

    }

    public void update() {
        time += 1.0f / 60.0f;

    }

    public void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        //glFrustum(-1, 1, -1, 1, .9, 1000);
        glOrtho(-600, 600, -600, 600, -1000, 1000);
        glViewport(0, 0, 500, 500);
		//glMatrixMode(GL_PROJECTION);
        //glLoadIdentity();
        //glFrustum(-1, 1, -1, 1, .8 + Mathf.sin(time) * .05, 1000);

        //View Transform
        glTranslated(-500.0f, -500.0f, -500.0f);

        DrawCity();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(-1, 1, -1, 1, .9, 1000);
        glViewport(500, 0, 500, 500);

		//view Transform
        //Eye point, focus point, and an up vector
        //camLook.x *=
        GLU.gluLookAt(cam.x, cam.y, cam.z,
                cam.x + camLook.x, cam.y + camLook.y, cam.z + camLook.z,
                camUp.x, camUp.y, camUp.z);
		//glRotated(Mathf.sin(.1f * time) * 10, 0.0f, 0.0f, 1.0f);
        //glTranslated(Mathf.sin(time) * 20, Mathf.cos(time) * 20, 0);

        DrawCity();

    }

    public void DrawCity() {
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glPushMatrix();

		//model Transform
        //Drawing the ground
        glTranslated(500, 500, -1);
        glRotated(180, 0, 1, 0);
        glScaled(500, 500, 1);
//			glRotated(180, 0, 1, 0);
        StandardBox();
        glPopMatrix();

        //buildings
        Building(40, 40, 60, 500, 400, 0);
        Building(40, 40, 60, 500, 600, 0);
        Building(320, 80, 100, 800, 300, 45);
        Building(30, 40, 50, 30, 920, -30);

    }

    public void Building(float w, float l, float h, float x, float y) {
        glPushMatrix();
        glTranslated(x, y, h / 2);
        glScaled(w / 2, l / 2, h / 2);
        StandardBox();
        glPopMatrix();
    }

    public void Building(float w, float l, float h, float x, float y, float angle) {
        glPushMatrix();
        glTranslated(x, y, h / 2);
        glRotated(angle, 0.0f, 0.0f, 1.0f);
        glScaled(w / 2, l / 2, h / 2);
        //glRotated(angle, 0.0f, 0.0f, 1.0f);
        StandardBox();
        glPopMatrix();
    }

    //Draws a 2x2x2 axis-aligned rectangular prism centered at the origin
    public void StandardBox() {
        //Front face
        glColor3f(1.0f, 0.0f, 0.0f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, -1);
        glVertex3f(1, -1, -1);
        glVertex3f(1, -1, 1);
        glVertex3f(-1, -1, 1);
        glEnd();

        //Back face
        glColor3f(0.0f, 0.0f, 1.0f);
        glBegin(GL_POLYGON);
        glVertex3f(1, 1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, 1, 1);
        glVertex3f(1, 1, 1);
        glEnd();

        //Right face
        glColor3f(0.0f, 1.0f, 0.0f);
        glBegin(GL_POLYGON);
        glVertex3f(1, -1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, 1, 1);
        glVertex3f(1, -1, 1);
        glEnd();

        //Left face
        glColor3f(1.0f, 0.0f, 1.0f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, 1, -1);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, -1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //Top face
        glColor3f(1.0f, 1.0f, 0.0f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, 1);
        glVertex3f(1, -1, 1);
        glVertex3f(1, 1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //Bottom Face
        glColor3f(0.0f, 1.0f, 1.0f);
        glBegin(GL_POLYGON);
        glVertex3f(-1, -1, -1);
        glVertex3f(-1, 1, -1);
        glVertex3f(1, 1, -1);
        glVertex3f(1, -1, -1);
        glEnd();

    }

}
