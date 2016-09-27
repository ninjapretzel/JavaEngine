
public abstract class Component {

    public GameObject gameObject;
    public String name = "";
    public boolean enabled = true;
    public boolean started = false;

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

    public Transform transform() {
        return gameObject.transform;
    }

    public Renderer renderer() {
        return gameObject.renderer;
    }

    public Vector3 position() {
        return transform().position;
    }

    public float x() {
        return position().x;
    }

    public float y() {
        return position().y;
    }

    public float z() {
        return position().z;
    }

    public AABB bounds() {
        return new AABB(left(), right(), top(), bottom(), front(), back());
    }

    public float left() {
        return transform().position.x - Mathf.abs(transform().scale.x);
    }

    public float right() {
        return transform().position.x + Mathf.abs(transform().scale.x);
    }

    public float bottom() {
        return transform().position.y - Mathf.abs(transform().scale.y);
    }

    public float top() {
        return transform().position.y + Mathf.abs(transform().scale.y);
    }

    public float back() {
        return transform().position.z - Mathf.abs(transform().scale.z);
    }

    public float front() {
        return transform().position.z + Mathf.abs(transform().scale.z);
    }

    public Component getComponent(String s) {
        return gameObject.getComponent(s);
    }

    public boolean hasComponent(String s) {
        return gameObject.hasComponent(s);
    }

    public Component addComponent(Component c) {
        return gameObject.addComponent(c);
    }

    public void pushFrom(GameObject other) {
        gameObject.pushFrom(other);
    }

    public void pushFrom(Component other) {
        gameObject.pushFrom(other.gameObject);
    }

    public void onUpdate() {
        onUpdate(World.deltaTime);
    }

    public void onUpdate(float time) {
        if (!started) {
            start();
            started = true;
        }
        update();
    }

    //overridable Functions 
    public void awake() {
    }

    public void start() {
    }

    public void update() {
        update(World.deltaTime);
    }

    public void update(float time) {
    }

    public void lateUpdate() {
        lateUpdate(World.deltaTime);
    }

    public void lateUpdate(float time) {
    }

    public void draw() {
    }

    public void move() {
        move(World.deltaTime);
    }

    public void move(float time) {
    }

    public void collideWith(GameObject other) {
    }

    public void postCollision() {
    }

    public void onDestroy() {
    }

    public void handleInput() {
    }

    public StringDisplay makeText() {
        return makeText("Text");
    }

    public StringDisplay makeText(String str) {
        return new StringDisplay(str, Camera.main.worldToScreen(position()), Vector3.down(100), Vector3.up(90));
    }

    public StringDisplay showMessage(String str) {
        StringDisplay s = makeText(str);
        World.addText(s);
        return s;
    }

}
