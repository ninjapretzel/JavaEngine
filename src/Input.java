
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.*;

//Input
//Helper class that tracks the state of various keys
//provides getButtonDown and getButtonUp to let us know if a button was pressed or released on a given frame
public class Input {

    public static Map<Integer, Boolean> keyStates;
    public static boolean[] mouseStates;

    static {
        mouseStates = new boolean[3];
        mouseStates[0] = false;
        mouseStates[1] = false;
        mouseStates[2] = false;
    }

    public static final int[] trackedKeys = {
        Keyboard.KEY_1,
        Keyboard.KEY_2,
        Keyboard.KEY_3,
        Keyboard.KEY_4,
        Keyboard.KEY_5,
        Keyboard.KEY_6,
        Keyboard.KEY_7,
        Keyboard.KEY_8,
        Keyboard.KEY_9,
        Keyboard.KEY_0,
        Keyboard.KEY_A,
        Keyboard.KEY_B,
        Keyboard.KEY_C,
        Keyboard.KEY_D,
        Keyboard.KEY_E,
        Keyboard.KEY_F,
        Keyboard.KEY_G,
        Keyboard.KEY_H,
        Keyboard.KEY_I,
        Keyboard.KEY_J,
        Keyboard.KEY_K,
        Keyboard.KEY_L,
        Keyboard.KEY_M,
        Keyboard.KEY_N,
        Keyboard.KEY_O,
        Keyboard.KEY_P,
        Keyboard.KEY_Q,
        Keyboard.KEY_R,
        Keyboard.KEY_S,
        Keyboard.KEY_T,
        Keyboard.KEY_U,
        Keyboard.KEY_V,
        Keyboard.KEY_W,
        Keyboard.KEY_X,
        Keyboard.KEY_Y,
        Keyboard.KEY_Z,
        Keyboard.KEY_LEFT,
        Keyboard.KEY_RIGHT,
        Keyboard.KEY_UP,
        Keyboard.KEY_DOWN,
        Keyboard.KEY_SPACE,
        Keyboard.KEY_LCONTROL,
        Keyboard.KEY_LSHIFT,
        Keyboard.KEY_RCONTROL,
        Keyboard.KEY_RSHIFT

    };

    public static Vector3 getMousePosition() {
        return new Vector3(Mouse.getX(), Mouse.getY(), 0);
    }

    public static Vector3 getMouseDelta() {
        return new Vector3(Mouse.getDX(), Mouse.getDY(), 0);
    }

    public static Vector3 getMouseDeltaFlopped() {
        return new Vector3(Mouse.getDY(), Mouse.getDX(), 0);
    }

    //Store all the keys that are getting tracked in the map
    public static void init() {
        keyStates = new HashMap<Integer, Boolean>();
        for (int i = 0; i < trackedKeys.length; i++) {
            keyStates.put(trackedKeys[i], false);
        }
    }

    public static void poll() {
        Keyboard.poll();
        Mouse.poll();

    }

    /*
     public static void update() {
     while (Mouse.next()) {
			
			
     }
		
     while (Keyboard.next()) {
     int key = Keyboard.getEventKey();
     boolean state = Keyboard.getEventKeyState();
     keyStates.put(key, state);
     }
		
     }
     //*/
	//Update all the tracked values
    //*
    public static void update() {
        for (int i = 0; i < trackedKeys.length; i++) {
            keyStates.put(trackedKeys[i], Keyboard.isKeyDown(trackedKeys[i]));
        }

        for (int i = 0; i < mouseStates.length; i++) {
            mouseStates[i] = Mouse.isButtonDown(i);
        }

    }
	//*/

    public static boolean getMouseButtonDown(int button) {
        if (button < 0 || button > 2) {
            return false;
        }
        return !mouseStates[button] && Mouse.isButtonDown(button);

    }

    public static boolean getMouseButtonUp(int button) {
        if (button < 0 || button > 2) {
            return false;
        }
        return mouseStates[button] && !Mouse.isButtonDown(button);
    }

    public static boolean getMouseButton(int button) {
        if (button < 0 || button > 2) {
            return false;
        }
        return Mouse.isButtonDown(button);
    }

    //Was a key pressed?
    public static boolean getButtonDown(int button) {
        if (!keyStates.containsKey(button)) {
            return false;
        }
        return !keyStates.get(button) && Keyboard.isKeyDown(button);
    }

    //Was a key released?
    public static boolean getButtonUp(int button) {
        if (!keyStates.containsKey(button)) {
            return false;
        }
        return keyStates.get(button) && !Keyboard.isKeyDown(button);
    }

    //What is the current state of a key?
    public static boolean getButton(int button) {
        if (!keyStates.containsKey(button)) {
            return false;
        }
        return keyStates.get(button);
    }

}
