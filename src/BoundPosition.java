
public class BoundPosition extends Component {

    public AABB bounds;

    public BoundPosition(Vector3 a) {
        bounds = new AABB(a.x, -a.x, a.y, -a.y, a.z, -a.z);
    }

    public BoundPosition(Vector3 a, Vector3 b) {
        bounds = new AABB(a.x, b.x, a.y, b.y, a.z, b.z);
    }

    public BoundPosition(AABB b) {
        bounds = b.clone();
    }

    public void update(float time) {
        transform().position = bounds.bound(position());

    }

}
