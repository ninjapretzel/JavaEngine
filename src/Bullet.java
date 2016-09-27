
public class Bullet extends Mover {

    public float lifetime = 1f;
    public float distance = 200;

    public float damage = 1;
    public String team = "Player";
    public boolean hit = false;
    public boolean dead = false;
    public Vector3 hitPos;
    public LineRenderer lr;

    public void awake() {
        Vector3 color = Vector3.white();
        color = Vector3.lerp(color, Vector3.yellow(), Rand.value());
        lr = (LineRenderer) gameObject.addComponent(new LineRenderer(color, .8f));
        gameObject.name = "Bullet";
    }

    public void update(float time) {
        transform().rotation.z = Mathf.RAD2DEG * velocity().angle2d();
        lifetime -= time;
        if (lifetime < 0) {
            World.remove(gameObject);
        }

        distance -= velocity().magnitude() * time;
        if (distance < 0) {
            World.remove(gameObject);
        }

        lr.push(position());

    }

    public void postCollision() {
        Debug.log("Bullet post collision " + hit);
        if (hit) {
            die();
        }
    }

    public void collideWith(GameObject other) {
        Debug.log(gameObject.name + " - " + other.name);
        if (other.hasComponent("Wall")) {
            hit = true;
        }

    }

    public void apply(Weapon wep) {
        angle += wep.spread * Rand.gaussian();
        damage = wep.damage;
        speed = wep.bulletSpeed;
        transform().rotation.z = Mathf.RAD2DEG * velocity().angle2d();

    }

    public void die() {
        if (dead) {
            return;
        }
        ParticleSettings sets = ParticleFactories.hit();
        sets.baseVelocity = velocity().scaled(-.01f);
        ParticleSystem.spawn(position(), "spark", sets);

        speed = 0;
        Debug.log("Bullet has Died");
        World.remove(gameObject);
        renderer().enabled = false;
        dead = true;
        damage = 0;
        hitPos = position();
        lr.pos2 = hitPos.clone();

    }

}
