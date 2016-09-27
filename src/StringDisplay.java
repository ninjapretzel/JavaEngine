
public class StringDisplay extends Box {

    public Vector3 position = Vector3.zero();
    public Vector3 velocity = Vector3.zero();
    public Vector3 acceleration = Vector3.zero();
    public Vector3 color = Vector3.white();

    public float lifeTime = 3;
    public String message = "dicks";
    public boolean perm = false;

    public StringDisplay() {
    }

    public StringDisplay(String m) {
        message = m;
    }

    public StringDisplay(String m, Vector3 pos) {
        message = m;
        position = pos;
    }

    public StringDisplay(String m, Vector3 pos, Vector3 vel) {
        message = m;
        position = pos;
        velocity = vel;
    }

    public StringDisplay(String m, Vector3 pos, Vector3 vel, Vector3 acc) {
        message = m;
        position = pos;
        velocity = vel;
        acceleration = acc;
    }

    public void update(float time) {
        position.add(velocity.scaled(time));
        velocity.add(acceleration.scaled(time));

        if (!perm) {
            lifeTime -= time;
            if (lifeTime < 0) {
                World.remove(this);
            }
        }

    }

    public void draw() {
        Skin.text.bind();
        GUI.drawText(position.x, position.y, message, color);
    }
}
