
public class EnemySpawner extends AutoSpawner {

    public GameObject factory() {
        return Make.Thug();
    }

    public void onDestroy() {
        World.stats.add("score", 100);

        World.add(new AmmoBox(position(), 100));

    }
}
