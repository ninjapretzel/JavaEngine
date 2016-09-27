
import static org.lwjgl.opengl.GL11.*;

import javax.swing.JOptionPane;

import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.*;
import org.lwjgl.util.glu.GLU;

public class Ex8 extends Basic {

    public float time = 0;
    public Camera camera;

	//all boxes, including player
    //pointer to player
    public GameObject player;
    public PlayerControl playerControl;
    public List<GUIBar> bars;

    public StringDisplayCounter ammoText;
    public StringDisplayCounter ammoText2;
    public StringDisplayCounter healthText;

    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Ex8 world = new Ex8("Gaem", 600, 600, 60);
        world.start();

    }

    public Ex8(String appTitle, int pw, int ph, int fps) {
        super(appTitle, pw, ph, fps);
    }

    public void init() {
        Input.init();

        player = new GameObject("Player");
        player.scale(2);
        player.addComponent(new PlaneRenderer("thug", false));
        playerControl = (PlayerControl) player.addComponent(new PlayerControl());
        player.addComponent(new Mortal("Player", 100, .3333f));

        World.addMoving(player);

        /*
         GameObject enemy = new GameObject();
         enemy.transform.position = new Vector3(0, -40, 1);
         enemy.addComponent(new PlaneRenderer("thug", false));
         enemy.addComponent(new Mortal(50));
         enemy.addComponent(new Enemy());
         enemy.scale(3);
         World.addMoving(enemy);
         //*/
        World.add(Make.ThugSpawner(new Vector3(0, -50, 1)));
        World.add(Make.ThugSpawner(new Vector3(0, 50, 1)));
        World.add(Make.ThugSpawner(new Vector3(-25, -50, 1)));
        World.add(Make.ThugSpawner(new Vector3(-25, 50, 1)));
        World.add(Make.ThugSpawner(new Vector3(-50, 50, 1)));
        World.add(Make.ThugSpawner(new Vector3(-50, -50, 1)));
        World.add(Make.ThugSpawner(new Vector3(100, 0, 1)));
        World.add(Make.ThugSpawner(new Vector3(150, -50, 1)));
        World.add(Make.ThugSpawner(new Vector3(150, 50, 1)));

        buildWorld();

		//GameObject ammobox = World.add(new GameObject(new ItemPickup("ammo", 20), new PlaneRenderer("ammo", false)));
        //ItemPickup p = (ItemPickup)ammobox.getComponent("ItemPickup");
        //p.msg = "Got the food";
        //ammobox.transform.position.plus(Vector3.up(30));
        World.add(new FoodBox(new Vector3(0, 20, 0)));
        World.add(new FoodBox(new Vector3(150, 0, 0)));
        World.add(new FoodBox(new Vector3(0, -20, 0)));
        World.add(new FoodBox(new Vector3(-150, 10, 0)));
        World.add(new FoodBox(new Vector3(-150, -10, 0)));

        World.stats.put("health", 50f);
        World.stats.put("ammo", 100f);
        World.stats.put("heldAmmo", 100f);

        World.regen.put("health", 1f);

        World.caps.put("health", 100f);
        World.caps.put("ammo", 300f);
        World.caps.put("heldAmmo", 100f);

        StringDisplay text = new StringDisplay("Survive...", new Vector3(100, 100, 0));
        World.addText(text);

        camera = new Camera();
        camera.ortho = true;
        camera.clipNear = -1000;
        camera.clipFar = 1000;
        camera.orthoSize = 100;

        bars = new ArrayList<GUIBar>();

        GUIBar hp = new GUIBar(Rect.unit().lowerLeft(1f, .025f).shifted(0, -2));
        hp.setColor(Vector3.red());
        hp.name = "health";
        bars.add(hp);

        GUIBar ammo = new GUIBar(Rect.unit().lowerLeft(1f, .025f).shifted(0, -1));
        ammo.setColor(Vector3.yellow());
        ammo.name = "ammo";
        ammo.lerpSpeed = 20;
        bars.add(ammo);

        GUIBar heldAmmo = new GUIBar(Rect.unit().lowerLeft(1f, .025f));
        heldAmmo.setColor(new Vector3(.7f, .3f, 0));
        heldAmmo.name = "heldAmmo";
        heldAmmo.lerpSpeed = 5;
        bars.add(heldAmmo);

        GUIBar reload = new GUIBar(Rect.unit().middleCenter(.2f, 0.005f).moved(0, .05f));
        reload.setColor(new Vector3(.9f, .6f, 0));
        reload.name = "reload";
        reload.lerpSpeed = 100;
        bars.add(reload);

        healthText = new StringDisplayCounter("", new Vector3(0, Display.getHeight() * .925f, 0));
        healthText.perm = true;
        healthText.stat = "health";
        healthText.baseMessage = "HP: ";
        healthText.color = Vector3.red(.3f);
        World.addText(healthText);

        ammoText = new StringDisplayCounter("", new Vector3(0, Display.getHeight() * .95f, 0));
        ammoText.perm = true;
        ammoText.stat = "ammo";
        ammoText.baseMessage = "Ammo: ";
        ammoText.color = Vector3.yellow(.3f);
        World.addText(ammoText);

        ammoText2 = new StringDisplayCounter("", new Vector3(0, Display.getHeight() * .975f, 0));
        ammoText2.perm = true;
        ammoText2.stat = "heldAmmo";
        ammoText2.baseMessage = "Extra: ";
        ammoText2.color = Vector3.yellow(.1f);
        World.addText(ammoText2);

        StringDisplayCounter scoreText = new StringDisplayCounter("", new Vector3(0, 0, 0));
        scoreText.perm = true;
        scoreText.stat = "score";
        scoreText.baseMessage = "Score: ";
        scoreText.color = Vector3.white();
        World.addText(scoreText);

    }

    public void buildWorld() {
        //Edges of world:
        String wallTexture = "Textures/Sea Green Marble";
        String groundTexture = "Textures/Rusted Iron Grip";

        World.add(new TexBox(wallTexture, new Vector3(220, 0, 0), new Vector3(20, 200, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(-220, 0, 0), new Vector3(20, 200, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(0, 220, 0), new Vector3(240, 20, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(0, -220, 0), new Vector3(240, 20, 1), Vector3.gray(.5f)));

        World.add(new TexBox(wallTexture, new Vector3(0, -110, 0), new Vector3(120, 10, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(0, 110, 0), new Vector3(120, 10, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(-110, 0, 0), new Vector3(10, 120, 1), Vector3.gray(.5f)));

        World.add(new TexBox(wallTexture, new Vector3(10, 0, 0), new Vector3(5, 80, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(-80, 0, 0), new Vector3(.2f, 40, 1), Vector3.gray(.5f)));

        World.add(new TexBox(wallTexture, new Vector3(60, 0, 0), new Vector3(.2f, 40, 1), Vector3.gray(.5f)));
        World.add(new TexBox(wallTexture, new Vector3(80, 0, 0), new Vector3(.05f, 40, 1), Vector3.gray(.5f)));

        GameObject ground = World.add(new TexBox(groundTexture, new Vector3(0, 0, -800), new Vector3(2000, 2000, .1f), Vector3.gray(.5f)));
        ground.renderer.skin.repeat.scale(.5f);
        ground.renderer.skin.repeat.x *= 2;

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
        ammoText.baseMessage = playerControl.gun.name + " Ammo: ";
        if (player.removed) {
            //Game Over
            World.regen.remove("health");
            World.stats.put("health", 0f);

        }

        World.update(deltaTime);
        for (GUIBar b : bars) {
            b.updateValues(deltaTime);
        }

        camera.transform.position.x = player.position().x;
        camera.transform.position.y = player.position().y;

		//
        //behave:
        //
        //
        //*
    }

    public void display() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        camera.setProjection();

        World.draw();
		//skybox.draw();

        Skin.clear();
        setGUIProjection();
        for (GUIBar b : bars) {
            b.draw();
        }

        GUI.bindColor(Vector3.green());
        GUI.drawReticle(5.0f + World.stats.get("spread"));

        setTextProjection();
        World.drawText();

    }

    public void setColor(Vector3 col) {
        glColor3f(col.x, col.y, col.z);
    }

    public void setGUIProjection() {
        glClear(GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1, 0, 1, -100, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

    }

    public void setTextProjection() {
        glClear(GL_DEPTH_BUFFER_BIT);
        glDisable(GL_DEPTH_TEST);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -100, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

    }

    public void userInput() {
        while (Keyboard.next()) {

        }
        World.handleInput();
    }

}
