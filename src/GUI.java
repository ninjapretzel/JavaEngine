
import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GUI {

    public static TrueTypeFont mainFont;

    static {
        Font font = new Font("Helvetica", Font.BOLD, 14);
        mainFont = new TrueTypeFont(font, false);
    }

    public static void drawText(float x, float y, String text, Vector3 color) {
        mainFont.drawString(x, y, text, new Color(color.x, color.y, color.z));
    }

    public static void drawText(float x, float y, String text, Vector3 color, float alpha) {
        mainFont.drawString(x, y, text, new Color(color.x, color.y, color.z, alpha));
    }

    public static void drawReticle(float recoil) {
        Vector3 pos = Input.getMousePosition();
        pos.x /= Display.getWidth();
        pos.y /= Display.getHeight();
        float size = recoil * .01f;

        Rect area = new Rect(pos.x, pos.y, size, size);
        area.shift(-.5f, -.5f);

        float longside = .2f;
        float shortside = .01f;
        float padding = .001f;

        drawBox(area.middleLeft(longside, shortside).padded(padding));
        drawBox(area.middleRight(longside, shortside).padded(padding));
        drawBox(area.upperCenter(shortside, longside).padded(padding));
        drawBox(area.lowerCenter(shortside, longside).padded(padding));

    }

    public static void drawAtCursor() {
        drawAtCursor(.01f);
    }

    public static void drawAtCursor(float size) {
        Vector3 pos = Input.getMousePosition();
        pos.x /= Display.getWidth();
        pos.y /= Display.getHeight();

        Rect box = new Rect(pos.x - size / 2f, pos.y - size / 2f, size, size);
        bindColor(Vector3.green());
        drawBox(box);
    }

    public static void bindColor(Vector3 c) {
        glColor3f(c.x, c.y, c.z);
    }

    public static void drawBox(Rect area) {
        glPushMatrix();
        glBegin(GL_POLYGON);
        glVertex3f(area.left(), area.top(), 0);
        glVertex3f(area.right(), area.top(), 0);
        glVertex3f(area.right(), area.bottom(), 0);
        glVertex3f(area.left(), area.bottom(), 0);
        glEnd();
        glPopMatrix();
    }

    public static void drawBox(float left, float top, float right, float bottom) {
        glPushMatrix();
        glBegin(GL_POLYGON);
        glVertex3f(left, top, 0);
        glVertex3f(right, top, 0);
        glVertex3f(right, bottom, 0);
        glVertex3f(left, bottom, 0);
        glEnd();
        glPopMatrix();
    }

}
