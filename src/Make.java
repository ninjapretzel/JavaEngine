
public class Make {

    public static GameObject AmmoBox() {
        return new AmmoBox();
    }

    public static GameObject AmmoBox(Vector3 position) {
        return new AmmoBox(position);
    }

    public static GameObject AmmoBox(Vector3 position, float val) {
        return new AmmoBox(position, val);
    }

    public static GameObject FoodBox() {
        return new FoodBox();
    }

    public static GameObject FoodBox(Vector3 position) {
        return new FoodBox(position);
    }

    public static GameObject Thug() {
        return Thug(Vector3.zero());
    }

    public static GameObject Thug(Vector3 position) {
        GameObject obj = new GameObject("Enemy");
        obj.transform.position = position.clone();

        obj.addComponent(new PlaneRenderer("thug", false));
        obj.addComponent(new Mortal(50));
        obj.addComponent(new Enemy());
        obj.addComponent(new BoundPosition(new Vector3(10000, 10000, 1)));

        obj.scale(3);
        return obj;
    }

    public static GameObject ThugSpawner(Vector3 position) {
        GameObject spawner = new GameObject();
        spawner.transform.position = position;
        spawner.addComponent(new PlaneRenderer("spawner", false));
        spawner.addComponent(new Mortal(250, false));
        spawner.addComponent(new EnemySpawner().setArea(Vector3.one(1)));
		//spawner.addComponent(new Wall());

        spawner.scale(3);
        return spawner;
    }

}
