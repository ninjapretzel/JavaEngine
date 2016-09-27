
public class Sphere {

    public Transform transform;

    public float radius;

    public Sphere() {
        transform = new Transform();
        radius = 1;
    }

    public boolean overlap(Sphere other) {
        float distance = other.position().to(position()).magnitude();
        return distance < radius + other.radius;
    }

    public void pushSelfFrom(Sphere other) {
        if (overlap(other)) {
            Vector3 off = other.position().to(position());
            off.plus(off.normalized().scaled(radius + other.radius));
            transform.position = other.position().plus(off);
        }

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

    public Vector3 position() {
        return transform.position;
    }

}
