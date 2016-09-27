
public class AmmoBox extends GameObject {

    public AmmoBox() {
        super();
        addStuff(100);
    }

    public AmmoBox(Vector3 pos) {
        super();
        addStuff(100);
        transform.position = pos;
    }

    public AmmoBox(Vector3 pos, float amt) {
        super();
        addStuff(amt);
        transform.position = pos;
    }

    public void addStuff(float amt) {
        ItemPickup p = (ItemPickup) addComponent(new ItemPickup("ammoPickup", "ammo", amt));
        p.msg = "Picked up some ammo.";
        scale(2);
        addComponent(new PlaneRenderer("ammo", false));
    }

}
