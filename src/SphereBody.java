
public class SphereBody extends Sphere {

    public float angle = 0;
    public float speed = 0;

    public void move(float time) {
        transform.translate(velocity().scaled(time));
    }

    public Vector3 velocity() {
        return new Vector3(Mathf.dcos(angle), Mathf.dsin(angle), 0).scaled(speed);
    }

}
