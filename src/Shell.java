
import static org.lwjgl.opengl.GL11.*;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.*;
import org.lwjgl.util.glu.GLU;

public class Shell extends Basic {

    public float time = 0;
    public Camera camera;
    public Skybox skybox;

    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Shell world = new Shell("Gaem", 800, 800, 60);
        world.start();

    }

    public Shell(String appTitle, int pw, int ph, int fps) {
        super(appTitle, pw, ph, fps);

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

        camera = new Camera();

        skybox = new Skybox("DawnDusk");
        skybox.camera = camera;

    }

    public void onClose() {

    }

    public void processInputs() {
        Keyboard.poll();
        Mouse.poll();

        //update helper class with this frame's values
        Input.update();
    }

    public void update() {

        time += 1.0f / 60.0f;
    }

    public void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.setProjection();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        skybox.draw();

    }

}
