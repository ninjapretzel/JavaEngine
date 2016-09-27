
import org.lwjgl.input.Keyboard;

import org.lwjgl.opengl.Display;

public class SimplePlayer extends Box {

    public float moveSpeed;
    public float dashRatio;

    public int curGun;
    public Weapon[] guns;
    public Weapon gun;

    public boolean trigger = false;
    public boolean triggerDown = false;

    public static Skin skin;

    static {
        skin = new Skin("thug");
        skin.opaque = false;
    }

    public SimplePlayer() {
        super();
        guns = new Weapon[4];
        guns[0] = Weapon.M16();
        guns[1] = Weapon.USP();
        guns[2] = Weapon.AK0048();
        guns[3] = Weapon.Deagle();

        gun = guns[0];
        print("" + gun.fireTime);

        type = "Player";
        transform.scale.scale(2);
        transform.scale.z = .1f;
        color = Vector3.blue();
        moveSpeed = 25;
        dashRatio = 3;

    }

    public void collideWith(Box other) {
        if (other.type.equals("Wall")) {
            pushFrom(other, velocity(), velocity().magnitude() * World.deltaTime);
            print("pushing");
        }

    }

    public void update(float time) {
        Vector3 mousePos = Input.getMousePosition();
        Vector3 center = new Vector3(Display.getWidth(), Display.getHeight(), 0).scaled(.5f);
        Vector3 dir = center.to(mousePos);
        transform.rotation.z = Mathf.RAD2DEG * Mathf.atan2(dir.y, dir.x);

        if (gun.update(time) && gun.canFire() && trigger) {
            shoot();
        }

        if (gun.canFirePull() && triggerDown) {
            shoot();
        }
        gun.setStatsToWorld();
    }

    public void handleInput() {
        triggerDown = Input.getMouseButtonDown(0);
        trigger = Input.getMouseButton(0);

        Vector3 vel = Vector3.zero();

        if (Input.getButtonDown(Keyboard.KEY_R)) {
            gun.startReload();
        }

        if (Input.getButtonDown(Keyboard.KEY_1)) {
            curGun--;
            if (curGun < 0) {
                curGun += guns.length;
            }
        }
        if (Input.getButtonDown(Keyboard.KEY_2)) {
            curGun++;
            if (curGun >= guns.length) {
                curGun -= guns.length;
            }
        }
        gun = guns[curGun];

        if (Input.getButton(Keyboard.KEY_W)) {
            vel.add(Vector3.up());
        }

        if (Input.getButton(Keyboard.KEY_S)) {
            vel.add(Vector3.down());
        }

        if (Input.getButton(Keyboard.KEY_A)) {
            vel.add(Vector3.left());
        }

        if (Input.getButton(Keyboard.KEY_D)) {
            vel.add(Vector3.right());
        }

        setVelocity(vel.normalized().scaled(moveSpeed));

    }

    public void shoot() {
        if (!gun.hasAmmo()) {
            return;
        }
        Vector3 mousePos = Input.getMousePosition();
        Vector3 center = new Vector3(Display.getWidth(), Display.getHeight(), 0).scaled(.5f);
        Vector3 dir = center.to(mousePos);

        Shot shot = new Shot();
        shot.team = type;
        shot.transform.position = position().clone();
        shot.setVelocity(dir.normalized());
        shot.apply(gun);

        gun.fire();
        World.addMoving(shot);
        World.stats.sub("ammo", 1);

    }

    public boolean hasAmmo() {
        return World.stats.get("ammo") > 0;

    }

}
