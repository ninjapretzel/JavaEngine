
public class Mover extends Component {

    public float angle = 0;
    public float speed = 0;

    public Vector3 velocity() {
        return new Vector3(Mathf.dcos(angle), Mathf.dsin(angle), 0).scaled(speed);
    }

    public void setVelocity(Vector3 newVelocity) {
        changeAngle(newVelocity);
        speed = newVelocity.magnitude();
    }

    public void changeAngle(Vector3 newAngle) {
        angle = Mathf.RAD2DEG * Mathf.atan2(newAngle.y, newAngle.x);
    }

    public void move(float time) {
        transform().position.add(velocity().scaled(time));
    }

}
