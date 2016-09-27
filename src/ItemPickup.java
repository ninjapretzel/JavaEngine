
public class ItemPickup extends Component {

    public float amt = 200;
    public String stat = "ammo";
    public String thing = "";
    public String msg = "";
    public boolean used = false;

    public ItemPickup(String s, float a) {
        used = false;
        stat = s;
        amt = a;
    }

    public ItemPickup(String s, String t, float a) {
        used = false;
        stat = s;
        thing = t;
        amt = a;
    }

    public void collideWith(GameObject other) {
        if (used) {
            return;
        }
        if (other.hasComponent("PlayerControl")) {
            use();
        }

    }

    public void use() {
        used = true;
        World.stats.add(stat, amt);

        String str = msg;
        if (str.length() == 0) {
            str = "Picked up " + (int) amt + " ";
            if (thing.length() == 0) {
                str += stat;
            } else {
                str += thing;
            }
        }

        StringDisplay message = new StringDisplay(str, Camera.main.worldToScreen(position()), Vector3.down(100), Vector3.up(90));
        message.position.x -= 60;
        message.color = Vector3.red();
        World.addText(message);
        World.remove(gameObject);
    }

}
