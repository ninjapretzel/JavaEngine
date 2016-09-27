
public class FoodBox extends GameObject {

    public FoodBox() {
        super();
        addFoodStuff();
    }

    public FoodBox(Vector3 pos) {
        super();
        addFoodStuff();
        transform.position = pos;
    }

    public void addFoodStuff() {
        ItemPickup p = (ItemPickup) addComponent(new ItemPickup("Score", 100));
        p.msg = "Picked up some food.";
        scale(2);
        addComponent(new PlaneRenderer("food", false));

    }

}
