/*  draw a rotating cube with its faces
 texture mapped

 Note:  uses the slick-util package, which seems to have a problem
 with flipping PNG images (but not JPG?) 
 vertically (and the parameter that claims
 to fix this doesn't seem to work), so we should use a separate
 tool to flip the image vertically.

 */

import java.io.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TexCube extends Basic {

    public static void main(String[] args) {
        TexCube app = new TexCube();
        app.start();
    }

    // instance variables for TexCube:
    private double angleX, angleY, angleZ;
    private Texture texture1, texture2;

    public TexCube() {
        super("TexCube", 500, 500, 60);

        angleX = 0;
        angleY = 0;
        angleZ = 0;
    }

    public void init() {
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(-1, 1, -1, 1, 5, 1000);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // turn on texturing
        glEnable(GL_TEXTURE_2D);

        // load some textures
        texture1 = getTexture("JPG", "labs.jpg", true);
        texture2 = getTexture("PNG", "luckCat.png", true);

        // enable alpha blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }// init

    public void processInputs() {
        Keyboard.poll();

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            angleZ += 5;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            angleZ -= 5;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
            angleY += 5;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
            angleY -= 5;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            angleX += 5;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            angleX -= 5;
        }

    }

    public void update() {
    }

    public void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        Color.white.bind();
        glPushMatrix();
        glTranslated(0, 0, -10);
        glRotated(angleZ, 0, 0, 1);
        glRotated(angleY, 0, 1, 0);
        glRotated(angleX, 1, 0, 0);
        standardCube();
        glPopMatrix();

        // view a hard-coded polygon
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        texture2.bind();
        glTranslated(-3, 3, -20);
        Color.white.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(0, 0);
        glVertex3d(-1, -1, 0);
        glTexCoord2f(1, 0);
        glVertex3d(1, -1, 0);
        glTexCoord2f(1, 1);
        glVertex3d(1, 1, 0);
        glTexCoord2f(0, 1);
        glVertex3d(-1, 1, 0);
        glEnd();
    }

  // draw a 2 by 2 by 2 axis-aligned rectangular prism
    // centered at the origin
    // with hard-coded texture mapped faces
    private void standardCube() {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        Color.white.bind();

    // front face
        // glColor3f( 1.0f, 0.0f, 0.0f );
        texture1.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(0, 0);
        glVertex3d(-1, -1, -1);
        glTexCoord2f(1, 0);
        glVertex3d(1, -1, -1);
        glTexCoord2f(1, 1);
        glVertex3d(1, -1, 1);
        glTexCoord2f(0, 1);
        glVertex3d(-1, -1, 1);
        glEnd();

    // rear face
        //    glColor3f( 0.0f, 0.0f, 1.0f );
        texture2.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(0, 0);
        glVertex3d(1, 1, -1);
        glTexCoord2f(.5f, 0);
        glVertex3d(-1, 1, -1);
        glTexCoord2f(.5f, .5f);
        glVertex3d(-1, 1, 1);
        glTexCoord2f(0, .5f);
        glVertex3d(1, 1, 1);
        glEnd();

    // right face
        // glColor3f( 0.0f, 1.0f, 0.0f );
        texture2.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(0, 0);
        glVertex3d(1, -1, -1);
        glTexCoord2f(8, 0);
        glVertex3d(1, 1, -1);
        glTexCoord2f(8, 8);
        glVertex3d(1, 1, 1);
        glTexCoord2f(0, 8);
        glVertex3d(1, -1, 1);
        glEnd();

    // left face
        // glColor3f( 1.0f, 0.0f, 1.0f );
        texture1.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(0, 0);
        glVertex3d(-1, 1, -1);
        glTexCoord2f(300f / 512, 0);
        glVertex3d(-1, -1, -1);
        glTexCoord2f(300f / 512, 225f / 256);
        glVertex3d(-1, -1, 1);
        glTexCoord2f(0, 225f / 256);
        glVertex3d(-1, 1, 1);
        glEnd();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

        // top face
        glColor3f(1.0f, 1.0f, 0.0f);
        glBegin(GL_POLYGON);
        glVertex3d(-1, -1, 1);
        glVertex3d(1, -1, 1);
        glVertex3d(1, 1, 1);
        glVertex3d(-1, 1, 1);
        glEnd();

        // bottom face
        glColor3f(0.0f, 1.0f, 1.0f);
        glBegin(GL_POLYGON);
        glVertex3d(-1, -1, -1);
        glVertex3d(-1, 1, -1);
        glVertex3d(1, 1, -1);
        glVertex3d(1, -1, -1);
        glEnd();

    }

    private Texture getTexture(String type, String fileName, boolean showInfo) {
        Texture texture = null;

        try {
            texture = TextureLoader.getTexture(type,
                    ResourceLoader.getResourceAsStream(fileName), true);

            if (showInfo) {
                System.out.println("Texture loaded: " + texture);
                System.out.println(">> Image width: " + texture.getImageWidth());
                System.out.println(">> Image height: " + texture.getImageHeight());
                System.out.println(">> Texture width: " + texture.getTextureWidth());
                System.out.println(">> Texture height: " + texture.getTextureHeight());
                System.out.println(">> Texture ID: " + texture.getTextureID());
            }
        } catch (IOException e) {
            System.out.println("Loading of texture from file [" + fileName + "] failed");
            e.printStackTrace();
            System.exit(1);
        }

        return texture;

    }// getTexture

}// TexCube
