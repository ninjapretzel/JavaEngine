
import static org.lwjgl.opengl.GL11.*;

public class GLUtils {

    static void glColorV3(Vector3 v) {
        glColor3f(v.x, v.y, v.z);
    }
}
