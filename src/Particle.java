
public class Particle {

    public Transform transform;

    public Vector3 velocity;
    public Vector3 force;
    public float lifeTime = 1;
    public float lifeMax = 1;

    public float lifePercent() {
        return lifeTime / lifeMax;
    }

    public float spin = 0;

    public float friction = 0;
    public float sizeGrow = 0;
    public Vector3 color = Vector3.white();

    public Vector3 position() {
        return transform.position;
    }

    public Vector3 rotation() {
        return transform.rotation;
    }

    public Vector3 scale() {
        return transform.scale;
    }

    public Particle() {
        init();
    }

    void init() {
        transform = new Transform();
        velocity = Vector3.zero();
        force = Vector3.zero();
    }

    //Returns true if the particle should be removed
    public boolean update(float time) {
        lifeTime -= time;
        if (lifeTime <= 0) {
            return true;
        }

        scale().add(Vector3.one(sizeGrow * time));
        rotation().add(Vector3.forward(spin * time));
        position().add(velocity.scaled(time));

        float lastMagnitude = velocity.magnitude();
        velocity.add(force.scaled(time));
        velocity.sub(velocity.normalized().scaled(time * friction));
        if (velocity.magnitude() > lastMagnitude) {
            velocity = Vector3.zero();
        }

        return false;
    }

}
