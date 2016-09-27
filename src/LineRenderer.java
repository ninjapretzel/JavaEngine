
import static org.lwjgl.opengl.GL11.*;

public class LineRenderer extends Renderer {

    public Vector3 pos1;
    public Vector3 pos2;
    public Vector3 color = Vector3.white();
    public float alpha = 1;
    public float endAlpha = 0;

    public LineRenderer() {
    }

    public LineRenderer(Vector3 c) {
        color = c;
    }

    public LineRenderer(Vector3 c, float a) {
        color = c;
        alpha = a;
    }

    public void awake() {
        pos1 = position().clone();
        pos2 = position().clone();
    }

    public void draw() {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glColor4f(color.x, color.y, color.z, endAlpha);
        glBegin(GL_LINES);
        sendPoint(pos1);
        glColor4f(color.x, color.y, color.z, alpha);
        sendPoint(pos2);
        glEnd();

    }

    public void push(Vector3 v) {
        pos1 = pos2;
        pos2 = v.clone();
    }

    void sendPoint(Vector3 v) {
        glVertex3f(v.x, v.y, v.z);
        Debug.log(v);
    }

}
