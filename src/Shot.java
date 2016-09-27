
public class Shot extends Box {

    public float lifetime;
    public float damage;
    public String team;
    public boolean hit = false;

    public static Skin skin;

    static {
        skin = new Skin("bullet");
        skin.opaque = false;
    }

    public Shot() {
        super();
        type = "Shot";
        lifetime = 1;
        damage = 1;
    }

    public void apply(Weapon wep) {
        angle += wep.spread * Rand.gaussian();
        damage = wep.damage;
        speed = wep.bulletSpeed;
    }

    public void update(float time) {
        lifetime -= time;
        if (lifetime < 0) {
            World.remove(this);
        }
    }

    public void draw() {
        if (hit) {
            return;
        }

        transform.rotation.z = Mathf.RAD2DEG * velocity().angle2d();
        skin.draw(transform);
    }

    public void collideWith(GameObject other) {
        if (other instanceof Box) {
            collideWith((Box) other);
        }
    }

    public void collideWith(Box other) {

        if (other.type.equals("Wall")) {
            World.remove(this);
            hit = true;
        }

        if (other.type.equals("Enemy")) {
            hit = true;
            World.remove(this);
        }

    }

}
