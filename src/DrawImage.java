/*  read a text file containing raw format image
 and draw it in the window
 */

import java.io.*;
import java.util.Scanner;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class DrawImage extends Basic {

    public static void main(String[] args) {
        DrawImage app = new DrawImage(args[0]);
        app.start();
    }

    // instance variables for DrawImage:
    private int numRows, numCols;  // image size
    private ByteBuffer pixels;  // stored image

    public DrawImage(String fileName) {
        super("DrawImage", 512, 512, 60);

        try {
            Scanner input = new Scanner(new File(fileName));
            numRows = input.nextInt();
            numCols = input.nextInt();

            byte[] pix = new byte[numRows * numCols * 4];

            int r, g, b, a;
            int index;

            for (int k = 0; k < numRows; ++k) {
                for (int j = 0; j < numCols; ++j) {
                    r = input.nextInt();
                    g = input.nextInt();
                    b = input.nextInt();
                    a = input.nextInt();

                    index = getIndex(numCols, numRows - k - 1, j);

                    pix[index + 0] = convertToByte(r);
                    pix[index + 1] = convertToByte(g);
                    pix[index + 2] = convertToByte(b);
                    pix[index + 3] = convertToByte(a);

                }
            }

            input.close();

            // store pix in pixels:
            pixels = BufferUtils.createByteBuffer(pix.length);
            pixels.put(pix);
            pixels.rewind();

        } catch (Exception e) {
            System.out.println("Something went wrong loading image from ["
                    + fileName + "]");
            System.exit(1);
        }

    }

    private byte convertToByte(int x) {
        return (byte) x;
    }

    private int getIndex(int colSize, int row, int col) {
        return row * colSize * 4 + col * 4;
    }

    public void draw(double x, double y, double z) {
        glRasterPos3d(x, y, z);
        pixels.rewind();
        glDrawPixels(numCols, numRows,
                GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    }

    public void init() {
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 512, 0, 512, 1, 3);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

    }// init

    public void processInputs() {
    }

    public void update() {
    }

    public void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        draw(0, 0, -2);

    }

}// DrawImage
