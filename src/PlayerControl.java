
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class PlayerControl extends Mover {

    public float moveSpeed;
    public float dashRatio;

    public int curGun;
    public Weapon[] guns;
    public Weapon gun;

    public boolean dash;
    public boolean trigger = false;
    public boolean triggerDown = false;

    public static PlayerControl main;

    public PlayerControl() {
        super();
        main = this;

        guns = new Weapon[4];
        guns[0] = Weapon.M16();
        guns[1] = Weapon.USP();
        guns[2] = Weapon.AK0048();
        guns[3] = Weapon.Deagle();

        gun = guns[0];
		//print(""+gun.fireTime);

        moveSpeed = 25;
        dashRatio = 3;
        dash = false;
    }

    public void collideWith(GameObject other) {
        if (other.hasComponent("Wall")) {
            gameObject.pushFrom(other, velocity(), speed * World.deltaTime);
            //print("pushing");
        }
    }

    public void update(float time) {
        Vector3 mousePos = Input.getMousePosition();
        Vector3 center = new Vector3(Display.getWidth(), Display.getHeight(), 0).scaled(.5f);
        Vector3 dir = center.to(mousePos);
        transform().rotation.z = Mathf.RAD2DEG * Mathf.atan2(dir.y, dir.x);

        //ammoText.baseMessage = player.gun.name + " | Ammo: ";
        float ammoPickup = World.stats.get("ammoPickup");
        for (Weapon g : guns) {
            g.pickupAmmo(ammoPickup);
        }
        World.stats.put("ammoPickup", 0f);

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
        if (triggerDown) {
            Mouse.setGrabbed(true);
        }

        if (Input.getButtonDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(false);
        }

        Vector3 vel = Vector3.zero();

        if (Input.getButtonDown(Keyboard.KEY_R)) {
            gun.startReload();
        }

        if (Input.getButtonDown(Keyboard.KEY_Q)) {
            curGun--;
            if (curGun < 0) {
                curGun += guns.length;
            }
        }
        if (Input.getButtonDown(Keyboard.KEY_E)) {
            curGun++;
            if (curGun >= guns.length) {
                curGun -= guns.length;
            }
        }

        if (Input.getButtonDown(Keyboard.KEY_1)) {
            curGun = 0;
        }
        if (Input.getButtonDown(Keyboard.KEY_2)) {
            curGun = 1;
        }
        if (Input.getButtonDown(Keyboard.KEY_3)) {
            curGun = 2;
        }
        if (Input.getButtonDown(Keyboard.KEY_4)) {
            curGun = 3;
        }

        gun = guns[curGun];

        dash = Input.getButton(Keyboard.KEY_LSHIFT);

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

        float spd = moveSpeed;
        if (dash) {
            spd *= dashRatio;
        }
        setVelocity(vel.normalized().scaled(spd));

    }

    public void shoot() {
        if (!hasAmmo()) {
            return;
        }
        Vector3 mousePos = Input.getMousePosition();
        Vector3 center = new Vector3(Display.getWidth(), Display.getHeight(), 0).scaled(.5f);
        Vector3 dir = center.to(mousePos);

        GameObject obj = new GameObject();
        obj.transform.position = position().clone();
        obj.addComponent(new PlaneRenderer("bullet", false));
        obj.addComponent(new LineRenderer(Vector3.white()));

        Bullet bullet = (Bullet) obj.addComponent(new Bullet());

        bullet.team = "Player";
        bullet.setVelocity(dir.normalized());
        bullet.apply(gun);

        gun.fire();
        World.addMoving(obj);

    }

    public boolean hasAmmo() {
        return gun.hasAmmo();

    }

}
