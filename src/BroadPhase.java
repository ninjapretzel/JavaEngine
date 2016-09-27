
import static org.lwjgl.opengl.GL11.*;

import javax.swing.JOptionPane;

import java.util.*;
import java.io.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.*;
import org.lwjgl.util.glu.GLU;

public class BroadPhase extends Basic {

    public float time = 0;
    public Camera camera;

    //all boxes, including player
    public ArrayList<Body> bodies;

    //pointer to player
    public Body player;
    public CellTable table;

    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        BroadPhase world = new BroadPhase("Gaem", 600, 600, 60);
        world.start();

    }

    public BroadPhase(String appTitle, int pw, int ph, int fps) {
        super(appTitle, pw, ph, fps);

        bodies = new ArrayList<Body>();
        try {
            Scanner input = new Scanner(new File("worldx.dat"));
            int num = input.nextInt();
            input.nextLine();
            for (int i = 0; i < num; i++) {
                bodies.add(new Body(input));
            }

        } catch (Exception e) {
            System.out.println("Failed to load");
            e.printStackTrace();
        }

        player = bodies.get(0);
        table = new CellTable();
        //Register all static bodies
        for (Body b : bodies) {
            if (b.kind.equals("Wall")) {
                b.updateSweptVolume(1);
                table.register(b);
            }
        }

    }

    public void init() {
        Input.init();

        //Toggle this line and see what happens:
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        camera = new Camera();

        camera.transform.position = new Vector3(100, 100, 0);
        camera.ortho = true;
        camera.clipNear = -10;
        camera.clipFar = 10;
        camera.orthoSize = 100;

    }

    public void onClose() {

    }

    public void processInputs() {
        Input.poll();
        userInput();

        //update helper class with this frame's values
        Input.update();
    }

    public void update() {
        float deltaTime = 1.0f / 60.0f;
        time += deltaTime;

		//Game loop
        //remove bodies marked for removal:
        //add new bodies :
        //
        //behave:
        //
        //
        //*
        float elapsedTime = 0;
        while (elapsedTime < 1) {
            //update broad phase info
            //register all bodies
            for (Body b : bodies) {
                b.updateSweptVolume(1 - elapsedTime);

            }
        }
    }

    public void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.setProjection();

        for (int i = 0; i < bodies.size(); i++) {
            bodies.get(i).draw();

        }

    }

    public void userInput() {
        while (Keyboard.next()) {

        }
        if (Input.getButton(Keyboard.KEY_W)) {
            player.accelerate(.01f);
        }
        if (Input.getButton(Keyboard.KEY_S)) {
            player.accelerate(-.01f);
        }
        if (Input.getButton(Keyboard.KEY_A)) {
            player.turn(-4);
        }
        if (Input.getButton(Keyboard.KEY_D)) {
            player.turn(4);
        }

    }

}
