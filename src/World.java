
import java.util.*;

public class World {

    public static List<GameObject> objects;
    public static List<GameObject> staticObjects;
    public static List<GameObject> movingObjects;
    public static List<GameObject> decoObjects;
    public static List<GameObject> decoBelows;
    public static List<GameObject> textObjects;

    public static List<GameObject> toAdd;
    public static List<GameObject> toRemove;

    public static float deltaTime = 1.0f / 60.0f;
    public static int maxDiscreteSteps = 20;

    public static Table stats;
    public static Table regen;
    public static Table caps;

    public static Map<String, String> strings;

    static {
        objects = new ArrayList<GameObject>();
        staticObjects = new ArrayList<GameObject>();
        movingObjects = new ArrayList<GameObject>();

        decoObjects = new ArrayList<GameObject>();
        decoBelows = new ArrayList<GameObject>();
        textObjects = new ArrayList<GameObject>();

        toAdd = new ArrayList<GameObject>();
        toRemove = new ArrayList<GameObject>();

        stats = new Table();
        regen = new Table();
        caps = new Table();

        strings = new HashMap<String, String>();

    }

    public static void clear() {
        objects.clear();
        staticObjects.clear();
        movingObjects.clear();
        decoObjects.clear();
        decoBelows.clear();
        textObjects.clear();
        toAdd.clear();
        toRemove.clear();
    }

    public static void tryLoad(String file) {
        clear();

    }

    public static void update() {
        update(deltaTime);
    }

    public static void update(float time) {

        checkAndAddNew();
        checkAndRemove();

        stats.add(Table.mul(regen, time));

        for (GameObject b : objects) {
            b.update();
        }
        for (GameObject b : decoObjects) {
            b.update();
        }
        for (GameObject b : decoBelows) {
            b.update();
        }
        for (GameObject b : textObjects) {
            b.update();
        }

        for (GameObject b : objects) {
            b.lateUpdate();
        }
        for (GameObject b : decoObjects) {
            b.lateUpdate();
        }
        for (GameObject b : decoBelows) {
            b.lateUpdate();
        }
        for (GameObject b : textObjects) {
            b.lateUpdate();
        }

        float discreteDelta = time / maxDiscreteSteps;
        for (int i = 0; i < maxDiscreteSteps; i++) {
            step(discreteDelta);

            for (GameObject b : movingObjects) {
                b.checkAgainst(objects);
            }

        }

    }

    public static void step(float time) {
        for (GameObject b : movingObjects) {
            b.move(time);
        }
    }

    public static void checkAndRemove() {
        while (toRemove.size() > 0) {

            objects.remove(toRemove.get(0));
            movingObjects.remove(toRemove.get(0));
            staticObjects.remove(toRemove.get(0));
            decoObjects.remove(toRemove.get(0));
            decoBelows.remove(toRemove.get(0));
            textObjects.remove(toRemove.get(0));

            toRemove.get(0).onDestroy();
            toRemove.remove(0);
        }
    }

    public static void checkAndAddNew() {
        //add new bodies :
        while (toAdd.size() > 0) {
            objects.add(toAdd.get(0));
            toAdd.remove(0);

        }
    }

    public static GameObject add(GameObject b) {
        toAdd.add(b);
        staticObjects.add(b);
        return b;
    }

    public static GameObject addMoving(GameObject b) {
        toAdd.add(b);
        movingObjects.add(b);
        return b;
    }

    public static GameObject addDeco(GameObject b) {
        return addDeco(b, true);
    }

    public static GameObject addDeco(GameObject b, boolean above) {
        if (above) {
            decoObjects.add(b);
        } else {
            decoBelows.add(b);
        }

        return b;
    }

    public static GameObject addText(GameObject b) {
        textObjects.add(b);

        return b;
    }

    public static void remove(GameObject b) {
        toRemove.add(b);

    }

    public static void handleInput() {
        for (GameObject b : objects) {
            b.handleInput();
        }
    }

    public static void draw() {
        for (GameObject b : staticObjects) {
            b.draw();
        }

        for (GameObject b : decoBelows) {
            b.draw();
        }

        for (GameObject b : movingObjects) {
            b.draw();
        }

        for (GameObject b : decoObjects) {
            b.draw();
        }

    }

    public static void drawText() {
        for (GameObject b : textObjects) {
            b.draw();
        }
    }

    public static GameObject find(String name) {

        return null;
    }

}
