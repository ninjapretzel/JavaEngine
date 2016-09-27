
public class Mortal extends Component {

    public float health;
    public float maxHealth = 10;
    public float regen = 0;
    public String team = "Enemy";

    public StringDisplay message;
    public float messageTime = .5f;
    public float messageDamage = 0;
    public float lastHitTime = 0;

    public boolean bleeds = true;

    public Mortal() {
        health = maxHealth;
    }

    public Mortal(float hp) {
        maxHealth = hp;
        health = hp;
    }

    public Mortal(float hp, boolean bleed) {
        maxHealth = hp;
        health = hp;
        bleeds = bleed;
    }

    public Mortal(String t, float hp) {
        maxHealth = hp;
        health = hp;
        team = t;
    }

    public Mortal(String t, float hp, float r) {
        maxHealth = hp;
        health = hp;
        team = t;
        regen = r;
    }

    public void setStatsToWorld() {
        World.stats.put("health", health);
        World.caps.put("health", maxHealth);
    }

    public void update(float time) {
        if (team.equals("Player")) {
            setStatsToWorld();
        }
        lastHitTime += time;

        if (lastHitTime > messageTime && messageDamage > 0) {
            message = null;
            messageDamage = 0;
        }

        health += regen * time;
        if (health > maxHealth) {
            health = maxHealth;
        }

    }

    public void die() {
        if (gameObject.name.equals("Player")) {
            StringDisplay m = showMessage("YOU DIED");
            m.setColor(Vector3.red());
            m.acceleration = Vector3.zero();
            m.velocity = Vector3.zero();
            m.position.x -= 40;

            Debug.log("Player Died");
        }

        makeMessage();
        if (bleeds) {
            makeDeathBlood();
        }
        World.remove(gameObject);

    }

    public void giveHealth(float amt) {
        giveDamage(-amt);
    }

    public void giveDamage(float amt) {
        if (Mathf.abs(amt) < 1) {
            return;
        }

        health -= amt;
        messageDamage += amt;

        if (health < 0) {
            die();
            return;
        }
        if (health > maxHealth) {
            health = maxHealth;
        }
        makeMessage();

        lastHitTime = 0;

        if (bleeds) {
            makeDamageBlood(amt);
        }
    }

    public void makeMessage() {
        float amt = messageDamage;
        String str;
        if (amt > 0) {
            str = "-" + (int) amt;
        } else {
            str = "+" + (int) amt;
        }
        if (message != null) {
            World.remove(message);
        }
        message = showMessage(str);
        message.color = Vector3.red();
    }

    public void collideWith(GameObject other) {
        Bullet b = (Bullet) other.getComponent("Bullet");

        if (b != null) {
            Enemy e = (Enemy) getComponent("Enemy");
            if (e != null) {
                e.aggressive = true;
            }

            if (b.team.equals(team)) {
                return;
            }
            if (bleeds) {
                makeShotBlood(b);

            }

            Debug.log(team + b.damage);
            giveDamage(b.damage);
            b.die();
        }
    }

    public void makeDamageBlood(float amt) {
        ParticleSettings p = ParticleFactories.blood();
        p.minEmission *= amt / 5f;
        p.maxEmission *= amt / 5f;
        ParticleSystem.spawn(position(), "blood", p);
    }

    public void makeShotBlood(Bullet b) {
        ParticleSettings p = ParticleFactories.blood();
        p.minEmission = b.speed / 300f;
        p.maxEmission = p.minEmission;
        p.baseVelocity = b.velocity().scaled(Rand.range(-.05f, -.04f));
        p.randVelocity = Vector3.one(b.speed / Rand.range(100f, 400f));
        p.friction *= 4f * b.speed / 650f + 5f * Rand.value();
        ParticleSystem.spawn(position(), "blood", p);
    }

    public void makeDeathBlood() {

        ParticleSystem.spawn(position(), "blood", ParticleFactories.death());
    }

}
