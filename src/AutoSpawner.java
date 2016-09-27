
public abstract class AutoSpawner extends Component {

    public float baseSpawnTime = 5;
    public float randSpawnTime = 10;

    public float spawnTime() {
        return baseSpawnTime + randSpawnTime * Rand.value();
    }
    public float timeout;

    public GameObject factory() {
        print("No overide for spawn factory");
        return null;
    }
    public Vector3 area;

    public AutoSpawner() {
        area = Vector3.zero();
    }

    public AutoSpawner(Vector3 a) {
        area = a;
    }

    public AutoSpawner(float t) {
        area = Vector3.zero();
        baseSpawnTime = t;
    }

    public AutoSpawner(float t, Vector3 a) {
        area = a;
        baseSpawnTime = t;
    }

    public AutoSpawner(float t, float r) {
        area = Vector3.zero();
        baseSpawnTime = t;
        randSpawnTime = r;
    }

    public AutoSpawner(float t, float r, Vector3 a) {
        area = a;
        baseSpawnTime = t;
        randSpawnTime = r;
    }

    public AutoSpawner setArea(Vector3 a) {
        area = a;
        return this;
    }

    public AutoSpawner setTimes(float t, float r) {
        baseSpawnTime = t;
        randSpawnTime = r;
        return this;
    }

    public void start() {
        timeout = spawnTime();
    }

    public void update(float time) {
        timeout -= time;
        if (timeout <= 0) {
            spawn(factory());
            timeout += spawnTime();
        }

    }

    public void spawn(GameObject o) {
        if (o == null) {
            print("No object set to spawn...");
            return;
        }

        Vector3 pos = position().plus(Rand.insideUnitSphere().scaled(area));
        o.transform.position = pos;
        World.addMoving(o);

    }

}
