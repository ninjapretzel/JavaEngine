
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.*;
import org.lwjgl.util.glu.GLU;

public class Camera {

    public Transform transform;

    public float clipNear = 1;
    public float clipFar = 1000;

    public boolean ortho = false;
    public float orthoSize = 10;

    public float width() {
        return Display.getWidth();
    }

    public float height() {
        return Display.getHeight();
    }

    public static Camera main;

    public Camera() {
        if (main == null) {
            main = this;
        }
        transform = new Transform();
        transform.position = Vector3.ZERO.clone();
        transform.rotation = Vector3.ZERO.clone();
        transform.updateAlignment();
    }

    public Vector3 screenToWorld(Vector3 screenPoint) {
        Vector3 pos = Vector3.zero();
        if (ortho) {
            pos.x = screenPoint.x / width() * orthoSize;
            pos.y = screenPoint.y / height() * orthoSize;
            pos.x += transform.position.x;
            pos.y += transform.position.y;
        } else {

        }

        return pos;
    }

    public Vector3 worldToScreen(Vector3 worldPoint) {
        Vector3 pos = Vector3.zero();
        Debug.log("" + worldPoint);
        Debug.log("" + transform.position);
        if (ortho) {
            pos.x = worldPoint.x - transform.position.x;
            pos.y = worldPoint.y - transform.position.y;
            Debug.log("" + pos + " " + orthoSize);
            pos.x *= .5f * width() / orthoSize;
            pos.y *= -.5f * height() / orthoSize;

            pos.x += width() / 2;
            pos.y += height() / 2;

            pos.z = 0;
        } else {

        }

        Debug.log("" + pos);

        return pos;
    }

    public void translate(Vector3 direction) {
        transform.translate(direction);
    }

    public void translate(float x, float y, float z) {
        transform.translate(x, y, z);
    }

    public void rotate(Vector3 rot) {
        transform.rotate(rot);
    }

    public void rotate(float x, float y, float z) {
        transform.rotate(x, y, z);
    }

    public void updateAlignment() {
        transform.updateAlignment();
    }

    public void setProjection() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        if (!ortho) {
            glFrustum(-1, 1, -1, 1, clipNear, clipFar);
            GLU.gluLookAt(transform.position.x, transform.position.y, transform.position.z,
                    transform.position.x + transform.forward.x, transform.position.y + transform.forward.y, transform.position.z + transform.forward.z,
                    transform.up.x, transform.up.y, transform.up.z);
            glMatrixMode(GL_MODELVIEW);
        } else {
            glOrtho(-orthoSize, orthoSize, -orthoSize, orthoSize, clipNear, clipFar);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glTranslated(-transform.position.x, -transform.position.y, -transform.position.z);
        }
    }

}
