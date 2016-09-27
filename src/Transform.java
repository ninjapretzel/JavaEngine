
public class Transform {

    public Vector3 position;
    public Vector3 rotation;
    public Vector3 scale;

    public Vector3 forward;
    public Vector3 up;
    public Vector3 right;

    public Vector3 back;
    public Vector3 down;
    public Vector3 left;

    public GameObject gameObject = null;

    public Transform() {
        position = Vector3.zero();
        rotation = Vector3.zero();
        scale = Vector3.one();
    }

    public Transform(GameObject gob) {
        position = Vector3.zero();
        rotation = Vector3.zero();
        scale = Vector3.one();

    }

    public Transform(Vector3 pos) {
        position = pos.clone();
        rotation = Vector3.zero();
        scale = Vector3.one();
        updateAlignment();
    }

    public Transform(Vector3 pos, Vector3 rot) {
        position = pos.clone();
        rotation = rot.clone();
        scale = Vector3.one();
        updateAlignment();
    }

    public Transform(Vector3 pos, Vector3 rot, Vector3 sc) {
        position = pos.clone();
        rotation = rot.clone();
        scale = sc.clone();
        updateAlignment();
    }

    public Transform clone() {
        Transform t = new Transform(position, rotation, scale);
        return t;
    }

    public void lookAt2dY(Transform other) {
        Vector3 dir = position.to(other.position);
        rotation.z = 0;
        rotation.y = Mathf.RAD2DEG * Mathf.atan2(dir.y, dir.x);
        rotation.x = 0;
        updateAlignment();
    }

    public void lookAt2dZ(Transform other) {
        Vector3 dir = position.to(other.position);
        rotation.z = Mathf.RAD2DEG * Mathf.atan2(dir.y, dir.x);
        rotation.y = 0;
        rotation.x = 0;
        updateAlignment();
    }

    public void setForward2dY(Vector3 dir) {
        rotation.z = 0;
        rotation.y = Mathf.RAD2DEG * Mathf.atan2(dir.y, dir.x);
        rotation.x = 0;
        updateAlignment();
    }

    public void setForward2dZ(Vector3 dir) {
        rotation.z = Mathf.RAD2DEG * Mathf.atan2(dir.y, dir.x);
        rotation.y = 0;
        rotation.x = 0;
        updateAlignment();
    }

    //Works for 3d stuff only.
    public void lookAt(Transform other) {
        lookAt(other, Vector3.up());
    }

    ;
	public void lookAt(Transform other, Vector3 upVect) {
        Vector3 dir = position.to(other.position).normalized();
        forward = dir;
        right = Vector3.cross(dir, upVect.normalized());
        up = Vector3.cross(right, dir);
        float xz = new Vector3(dir.x, dir.z, 0).magnitude();
        rotation.x = Mathf.atan2(dir.y, xz);
        rotation.y = Mathf.RAD2DEG * Mathf.atan2(Mathf.asin(dir.z), Mathf.asin(dir.x));

        calcNegs();
    }

    public void translate(Vector3 direction) {
        translate(direction.x, direction.y, direction.z);
    }

    public void translate(float x, float y, float z) {
        updateAlignment();
        position.add(forward.scaled(z));
        position.add(up.scaled(y));
        position.add(right.scaled(x));
    }

    public void rotate(Vector3 rot) {
        rotate(rot.x, rot.y, rot.z);
    }

    public void rotate(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
        updateAlignment();
    }

    public void updateAlignment() {
        /*
         forward = new Vector3(Mathf.dcos(rotation.y), 0, Mathf.dsin(rotation.y));
         forward.scale(Mathf.dcos(rotation.x));
         forward.z = Mathf.dsin(rotation.x);
		
         right = new Vector3(Mathf.dcos(rotation.y-90), 0, Mathf.dsin(rotation.y-90));
         up = Vector3.cross(right, forward);
         //*/

        forward = new Vector3(Mathf.dsin(rotation.y), 0, Mathf.dcos(rotation.y));
        forward.scale(Mathf.dcos(rotation.x));
        forward.y = Mathf.dsin(rotation.x);

        right = new Vector3(Mathf.dsin(rotation.y - 90), 0, Mathf.dcos(rotation.y - 90));
        up = Vector3.cross(right, forward);

        calcNegs();

    }

    public void calcNegs() {
        back = forward.neg();
        down = up.neg();
        left = right.neg();
    }

}
