
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.*;
import org.lwjgl.util.glu.GLU;

import java.io.*;
import java.util.*;

import javax.swing.*;

enum PenMode {

    Move, Draw, Erase
}

public class Ex6 extends Basic {

    public float time = 0;

    public Camera camera;

    public boolean autoFocus;
    public boolean rainbowMode;

    public Boxx brush;
    public List<Boxx> boxes;

    public PenMode mode = PenMode.Move;

    private float speed = .3f;
    private float trn = 4;
    private float distance = 2;

    private String filename;
    private static final String ext = ".world";

    private Skybox skybox;

    public Vector3[] unitVectors = {
        Vector3.FORWARD,
        Vector3.BACKWARD,
        Vector3.LEFT,
        Vector3.RIGHT,
        Vector3.UP,
        Vector3.DOWN
    };

    public static void print(String s) {
        System.out.println(s);
    }

    public void tryLoad(String name) {
        File file = new File(name + ext);
        print("Loading from " + name + ext);
        boxes = new ArrayList<Boxx>();
        if (!file.exists()) {
            print("File doesn't exist, starting a new world.");
            return;
        }

        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String s = in.nextLine();
                if (s.length() <= 6) {
                    break;
                }
                Boxx b = new Boxx();
                b.loadFromString(s);

                boxes.add(b);
            }

            in.close();
        } catch (Exception e) {
            print(e.toString());
            e.printStackTrace();
        }

    }

    public void save(String name) {
        File file = new File(name + ext);
        print("Saving to " + name + ext);
        String content = "";
        if (file.exists()) {
            print("already exists, overwriting");
            file.delete();
        }

        for (int i = 0; i < boxes.size(); i++) {
            content += boxes.get(i).toLoadableString();
            if (i + 1 < boxes.size()) {
                content += "\n";
            }
        }

        //print("Content:\n" + content);
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            //print("wrote content");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Ex6 world = new Ex6("A 3d drawing program", 800, 600, 60);
        world.start();

    }

    public Ex6(String appTitle, int pw, int ph, int fps) {
        super(appTitle, pw, ph, fps);

        camera = new Camera();
        camera.transform.position = new Vector3(10, 10, 10);
        //camera.transform.rotation = new Vector3(-90, 90, 0);
        camera.transform.rotation = new Vector3(0, 0, 0);
        camera.updateAlignment();

        autoFocus = false;
        rainbowMode = false;
        brush = new Boxx(Vector3.ZERO, Vector3.ONE);
        boxes = new ArrayList<Boxx>();

        mode = PenMode.Move;

        filename = "";
        //skybox = new Skybox("DawnDusk");
    }

    public void init() {
        glClearColor(1, 1, 1, 1);
        Input.init();

        //Toggle this line and see what happens:
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glFrustum(-1, 1, -1, 1, .9, 1000);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        filename = JOptionPane.showInputDialog("What world do you want to load?");
        tryLoad(filename);

        skybox = new Skybox("DawnDusk");
        skybox.camera = camera;

        focusCameraToBrush();

    }

    //Save when we're closed
    public void onClose() {
        print("Saving");
        save(filename);

    }

    public void processInputs() {
        Keyboard.poll();
        Mouse.poll();

        userControls();

        //update helper class with this frame's values
        Input.update();
    }

    public void update() {

        time += 1.0f / 60.0f;
        //print("" + time);
        if (autoFocus) {
            focusCameraToBrush();

        }

        if (mode == PenMode.Draw) {
            addAtBrush();
        } else if (mode == PenMode.Erase) {
            removeAtBrush();
        }

        if (rainbowMode) {
            float r = .5f + .5f * Mathf.sin(time * 999);
            float g = .5f + .5f * Mathf.sin(time * 333);
            float b = .5f + .5f * Mathf.sin(time * 111);
            brush.color = new Vector3(r, g, b);
        }
    }

    public void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		//glMatrixMode(GL_PROJECTION);
        //glLoadIdentity();
        //glFrustum(-1, 1, -1, 1, .9, 1000);
        //glOrtho(-600, 600, -600, 600, -1000, 1000);
        //glViewport(0, 0, 500, 500);
        //glMatrixMode(GL_PROJECTION);
        //glLoadIdentity();
        //glFrustum(-1, 1, -1, 1, .8 + Mathf.sin(time) * .05, 1000);
		//View Transform
        camera.setProjection();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        skybox.draw();

        //Draw brush
        brush.drawWire();
        brush.drawWire(1.2f + Mathf.sin(time * 9) * .1f);
        if (mode == PenMode.Draw) {
            drawAdd();
        }
        if (mode == PenMode.Erase) {
            drawErase();
        }

        //Draw world
        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).draw();
        }

        Grid.xy.draw();
        Grid.xz.draw();
        Grid.yz.draw();
        Axis.x.draw();
        Axis.y.draw();
        Axis.z.draw();

    }

    //Small extra function to draw teal wireframes around the brush during erase mode
    public void drawAdd() {
        Boxx b = brush.clone();
        b.color = new Vector3(0, .5f, .5f);
        b.drawWire(1.3f + Mathf.sin(time * 18) * .1f);
        b.drawWire(1.4f + Mathf.sin(time * 18) * .1f);
        b.drawWire(1.5f + Mathf.sin(time * 18) * .1f);

    }

    //Small extra function to draw orange wireframes around the brush during erase mode
    public void drawErase() {
        Boxx b = brush.clone();
        b.color = new Vector3(1f, .5f, 0);
        b.drawWire(1.3f + Mathf.sin(time * 18) * .1f);
        b.drawWire(1.4f + Mathf.sin(time * 18) * .1f);
        b.drawWire(1.5f + Mathf.sin(time * 18) * .1f);

    }

    //Updates the camera's forward, right and up vectors
    public void updateCamera() {
        camera.updateAlignment();

    }

    //Place or replace a box at the current brush position
    public void addAtBrush() {
        //print("adding at " + brush.toString());
        for (int i = 0; i < boxes.size(); i++) {
            Boxx b = boxes.get(i);
            if (b.near(brush)) {
                //print("already exists, updating color");
                b.color = brush.color.scaled(.8f);
                return;
            }

        }

        //print("added.");
        Boxx b = brush.clone();
        b.color.scale(.8f);
        boxes.add(b);

    }

    //Look for a box at the current brush position to remove.
    public void removeAtBrush() {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).near(brush)) {
                boxes.remove(i);
                return;
            }
        }
    }

	//Was going to be used, decided against it for the sake of consistant controls...
    //used to make the controls of the brush be affected by the direction the camera is facing...
    public Vector3 closestUnit(Vector3 v) {
        int closest = -1;
        float closestAngle = 360;

        for (int i = 0; i < unitVectors.length; i++) {
            float angle = Vector3.angle(v, unitVectors[i]);
            if (angle < closestAngle) {
                closestAngle = angle;
                closest = i;
            }

        }
        return unitVectors[closest];

    }

    public void focusCameraToBrush() {
        camera.transform.position = brush.position.clone();
        camera.translate(0, 0, -20);

    }

    public void userControls() {
        //Turn
        if (Input.getButton(Keyboard.KEY_J)) {
            camera.rotate(0, trn, 0);
        }
        if (Input.getButton(Keyboard.KEY_L)) {
            camera.rotate(0, -trn, 0);
        }
        if (Input.getButton(Keyboard.KEY_I)) {
            camera.rotate(trn, 0, 0);
        }
        if (Input.getButton(Keyboard.KEY_K)) {
            camera.rotate(-trn, 0, 0);
        }

        float spd = speed;
        if (Input.getButton(Keyboard.KEY_LSHIFT)) {
            spd *= 9;
        }
        float dist = distance;
        if (Input.getButton(Keyboard.KEY_LCONTROL)) {
            dist *= 10;
        }

        if (Input.getMouseButtonDown(1)) {
            Mouse.setGrabbed(true);
        }
        if (Input.getMouseButtonUp(1)) {
            Mouse.setGrabbed(false);
        }
        if (Mouse.isGrabbed()) {
            Vector3 delta = Input.getMouseDelta();

            camera.rotate(delta.y * .5f, delta.x * -.5f, 0);
        }

        updateCamera();

        //Move camera
        if (Input.getButton(Keyboard.KEY_A)) {
            camera.translate(-spd, 0, 0);
        }
        if (Input.getButton(Keyboard.KEY_D)) {
            camera.translate(spd, 0, 0);
        }
        if (Input.getButton(Keyboard.KEY_W)) {
            camera.translate(0, 0, spd);
        }
        if (Input.getButton(Keyboard.KEY_S)) {
            camera.translate(0, 0, -spd);
        }
        if (Input.getButton(Keyboard.KEY_Q)) {
            camera.translate(0, -spd, 0);
        }
        if (Input.getButton(Keyboard.KEY_E)) {
            camera.translate(0, spd, 0);
        }

        //Color changing
        if (Input.getButton(Keyboard.KEY_1)) {
            brush.color = Vector3.ONE;
        }
        if (Input.getButton(Keyboard.KEY_2)) {
            brush.color = new Vector3(1, 0, 0);
        }
        if (Input.getButton(Keyboard.KEY_3)) {
            brush.color = new Vector3(0, 1, 0);
        }
        if (Input.getButton(Keyboard.KEY_4)) {
            brush.color = new Vector3(0, 0, 1);
        }
        if (Input.getButton(Keyboard.KEY_5)) {
            brush.color = new Vector3(1, 1, 0);
        }
        if (Input.getButton(Keyboard.KEY_6)) {
            brush.color = new Vector3(1, 0, 1);
        }
        if (Input.getButton(Keyboard.KEY_7)) {
            brush.color = new Vector3(0, 1, 1);
        }

        //Brush movement
        if (Input.getButtonDown(Keyboard.KEY_LEFT)) {
            brush.position.x -= dist;
        }
        if (Input.getButtonDown(Keyboard.KEY_RIGHT)) {
            brush.position.x += dist;
        }
        if (Input.getButtonDown(Keyboard.KEY_UP)) {
            brush.position.z -= dist;
        }
        if (Input.getButtonDown(Keyboard.KEY_DOWN)) {
            brush.position.z += dist;
        }
        if (Input.getButtonDown(Keyboard.KEY_RSHIFT)) {
            brush.position.y += dist;
        }
        if (Input.getButtonDown(Keyboard.KEY_RCONTROL)) {
            brush.position.y -= dist;
        }

        //Change Pen Mode
        if (Input.getButtonDown(Keyboard.KEY_M)) {
            mode = PenMode.Move;
        }
        if (Input.getButtonDown(Keyboard.KEY_N)) {
            mode = PenMode.Draw;
        }
        if (Input.getButtonDown(Keyboard.KEY_B)) {
            mode = PenMode.Erase;
        }

        //Brush draw/remove
        if (Input.getButtonDown(Keyboard.KEY_SPACE)) {
            addAtBrush();
        }
        if (Input.getButtonDown(Keyboard.KEY_V)) {
            removeAtBrush();
        }

        if (Input.getButtonDown(Keyboard.KEY_F)) {
            focusCameraToBrush();
        }
        if (Input.getButtonDown(Keyboard.KEY_G)) {
            autoFocus = !autoFocus;
        }
        if (Input.getButtonDown(Keyboard.KEY_R)) {
            rainbowMode = !rainbowMode;
        }
        if (Input.getButtonDown(Keyboard.KEY_T)) {
            camera.transform.rotation.y = 90;
            camera.transform.rotation.x = -90;
            updateCamera();
            focusCameraToBrush();
        }

    }

}
