
import java.lang.reflect.Type;
import java.util.*;

public class GameObject {

    public String name = "Game Object";
    public Transform transform;
    public Renderer renderer;

    public int id = 100;
    private static int nextId = 1000000;
    public boolean enabled = true;

    public Map<String, Component> components;
    public List<String> toRemove;

    public boolean removed = false;

    public void init() {
        transform = new Transform(this);
        components = new HashMap<String, Component>();
        toRemove = new ArrayList<String>();
        id = nextId++;
    }

    public GameObject() {
        init();
    }

    public GameObject(String s) {
        init();
        name = s;
    }

    public GameObject(Transform t) {
        init();
        transform = t.clone();
    }

    public GameObject(Component... cs) {
        init();
        for (int i = 0; i < cs.length; i++) {
            addComponent(cs[i]);
        }
    }

    public boolean equals(Object other) {
        if (!(other instanceof GameObject)) {
            return false;
        }
        return id == ((GameObject) other).id;
    }

    public boolean hasComponent(String name) {
        return components.containsKey(name);
    }

    public Component getComponent(String name) {
        return components.containsKey(name) ? components.get(name) : null;
    }

    public Component addComponent(Component c) {
        c.gameObject = this;
        components.put(c.getClass().getSimpleName(), c);
        c.awake();

        if (Renderer.class.isAssignableFrom(c.getClass())) {
            if (renderer == null) {
                renderer = (Renderer) c;
            }
        }

        return c;
    }

    public void print(String s) {
        System.out.println(s);
    }

    public Vector3 vectorTo(Component other) {
        return position().to(other.position());
    }

    public Vector3 vectorTo(GameObject other) {
        return position().to(other.position());
    }

    public float distanceTo(Component other) {
        return vectorTo(other).magnitude();
    }

    public float distanceTo(GameObject other) {
        return vectorTo(other).magnitude();
    }

    public void scale(float amt) {
        transform.scale.scale(amt);
    }

    public Vector3 position() {
        return transform.position;
    }

    public Vector3 scale() {
        return transform.scale;
    }

    public Vector3 rotation() {
        return transform.rotation;
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

    public void update() {
        update(World.deltaTime);
    }

    public void update(float time) {
        if (!enabled) {
            return;
        }
        while (toRemove.size() > 0) {
            String key = toRemove.get(0);
            if (components.containsKey(key)) {
                components.remove(key);
            }
            toRemove.remove(0);
        }

        for (String s : components.keySet()) {
            Component c = components.get(s);
            if (c.enabled) {
                c.onUpdate(time);
            }
        }
    }

    public void lateUpdate() {
        lateUpdate(World.deltaTime);
    }

    public void lateUpdate(float time) {
        if (!enabled) {
            return;
        }
        for (String s : components.keySet()) {
            Component c = components.get(s);
            if (c.enabled) {
                c.lateUpdate(time);
            }
        }
    }

    public void handleInput() {
        if (!enabled) {
            return;
        }
        for (String s : components.keySet()) {
            Component c = components.get(s);
            if (c.enabled) {
                c.handleInput();
            }
        }
    }

    public void draw() {
        if (!enabled) {
            return;
        }
        for (String s : components.keySet()) {
            Component c = components.get(s);
            if (c.enabled) {
                c.draw();
            }
        }
    }

    public void collideWith(GameObject other) {
        if (other.equals(this)) {
            return;
        }
        for (String s : components.keySet()) {
            components.get(s).collideWith(other);
        }
    }

    public void onDestroy() {
        removed = true;
        for (String s : components.keySet()) {
            components.get(s).onDestroy();
        }
    }

    public void postCollision() {
        for (String s : components.keySet()) {
            components.get(s).postCollision();
        }
    }

    public void checkAgainst(List<GameObject> objects) {
        if (!enabled) {
            return;
        }
        for (GameObject b : objects) {
            if (!b.enabled) {
                continue;
            }
            if (bounds().collidesWith(b.bounds())) {
                collideWith(b);
                b.collideWith(this);

                postCollision();
                b.postCollision();
            }
        }

    }

    public void move(float time) {
        if (!enabled) {
            return;
        }
        for (String s : components.keySet()) {
            components.get(s).move(time);
        }
    }

    public void pushFrom(GameObject b) {
        Vector3 dir = position().to(b.position());
        pushFrom(b, dir);
    }

    public void pushFrom(GameObject b, Vector3 dir) {
        pushFrom(b, dir, .1f);
    }

    public void pushFrom(GameObject b, Vector3 dir, float maxDist) {
        while (bounds().collidesWith(b.bounds())) {
            transform.position.add(dir.normalized().neg().scaled(maxDist));
        }

    }

}
