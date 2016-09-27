
import static org.lwjgl.opengl.GL11.*;

public class GUIBar {

    public Rect area = Rect.unit();
    public float value = .5f;
    public float max = 1;

    public float percentage() {
        return Mathf.clamp01(value / max);
    }

    public String name = "health";

    public float lerpSpeed = 5;
    public Vector3 color = Vector3.red();
    public Vector3 backColor = Vector3.red(.5f);

    public GUIBar() {
    }

    public GUIBar(Rect a) {
        area = a;
    }

    public void updateValues() {
        updateValues(name);
    }

    public void updateValues(float f) {
        updateValues(name, f);
    }

    public void updateValues(String n) {
        value = World.stats.get(n);
        max = World.caps.get(n);
    }

    public void updateValues(String n, float time) {
        float target = 0;
        if (World.stats.containsKey(n)) {
            target = World.stats.get(n);
        }
        value = Mathf.lerp(value, target, time * lerpSpeed);
        if (World.caps.containsKey(n)) {
            max = World.caps.get(n);
        }
    }

    public void setColor(Vector3 c) {
        color = c;
        backColor = c.scaled(.5f);
    }

    public void bindColor(Vector3 c) {
        glColor3f(c.x, c.y, c.z);
    }

    public void draw() {
        if (max == 0) {
            return;
        }
        bindColor(backColor);
        GUI.drawBox(area.upperRight(1f - percentage(), 1));
        bindColor(color);
        GUI.drawBox(area.upperLeft(percentage(), 1));

    }
}
