
public class Weapon {

    public String name = "M16";
    public float fireTime = .1f;
    public float fireTimePull = .1f;
    public float reloadTime = .8f;

    public float curTime = 0.0f;

    public float damage = 5;

    public float bulletSpeed = 1300;
    public float spread = 0;
    public float sightedRecoil = 1;
    public float normalRecoil = 2;
    public float sightedAbsorb = 7;
    public float normalAbsorb = 4;

    public boolean sighted = false;
    public boolean reloading = false;

    public float absorbtion() {
        return sighted ? sightedAbsorb : normalAbsorb;
    }

    public float recoil() {
        return sighted ? sightedRecoil : normalRecoil;
    }

    public float ammoUse = 1;
    public float ammoGain = 1;

    public float curAmmo = 30;
    public float maxAmmo = 30;

    public float curExtraAmmo = 100;
    public float maxExtraAmmo = 100;

    public boolean freeReload = false;

    public static Weapon M16() {
        Weapon wep = new Weapon();
        wep.damage = 7.5f;
        wep.bulletSpeed = 1300;
        wep.sightedRecoil = 1;
        wep.normalRecoil = 2;
        wep.sightedAbsorb = 7;
        wep.normalAbsorb = 6;
        wep.fireTime = .1f;
        wep.fireTimePull = .1f;
        wep.maxAmmo = 30;
        wep.curAmmo = 30;
        wep.ammoGain = 30f / 100f;
        wep.reloadTime = .8f;
        wep.curExtraAmmo = 90;
        wep.maxExtraAmmo = 1000;

        return wep;
    }

    public static Weapon USP() {
        Weapon wep = new Weapon("USP");
        wep.damage = 12;
        wep.bulletSpeed = 800;
        wep.normalRecoil = 8f;
        wep.sightedRecoil = 1;
        wep.sightedAbsorb = 8;
        wep.normalAbsorb = 35;
        wep.fireTime = .25f;
        wep.fireTimePull = .1f;
        wep.maxAmmo = 12;
        wep.curAmmo = 12;
        wep.reloadTime = .35f;
        wep.ammoGain = 24f / 100f;
        wep.curExtraAmmo = 36;
        wep.maxExtraAmmo = 1000;

        return wep;
    }

    public static Weapon AK0048() {
        Weapon wep = new Weapon("AK0048");
        wep.damage = 13;
        wep.bulletSpeed = 1400;
        wep.normalRecoil = 15;
        wep.sightedRecoil = 7;
        wep.normalAbsorb = 8;
        wep.sightedAbsorb = 10;
        wep.fireTime = .15f;
        wep.fireTimePull = .15f;
        wep.curAmmo = 20;
        wep.maxAmmo = 20;
        wep.reloadTime = .86f;
        wep.ammoGain = 20f / 100f;
        wep.curExtraAmmo = 40;
        wep.maxExtraAmmo = 400;

        return wep;
    }

    public static Weapon Deagle() {
        Weapon wep = new Weapon("Deagle");
        wep.damage = 21;
        wep.bulletSpeed = 2600;
        wep.normalRecoil = 20;
        wep.sightedRecoil = 10;
        wep.sightedAbsorb = 10;
        wep.normalAbsorb = 7;
        wep.fireTime = .55f;
        wep.fireTimePull = .15f;
        wep.maxAmmo = 7;
        wep.curAmmo = 7;
        wep.reloadTime = .45f;
        wep.ammoGain = 14f / 100f;
        wep.curExtraAmmo = 35;
        wep.maxExtraAmmo = 280;

        return wep;
    }

    public Weapon() {
    }

    public Weapon(String n) {
        name = n;
    }

    public void fire() {
        if (hasAmmo()) {
            curAmmo -= ammoUse;
            curTime = 0;
            spread += recoil();
        }
    }

    public String getStats() {
        String str = "" + name + (int) curAmmo + "/" + (int) maxAmmo + " | " + (int) curExtraAmmo;
        return str;
    }

    public void setStatsToWorld() {
        World.stats.put("ammo", curAmmo);
        World.caps.put("ammo", maxAmmo);

        World.stats.put("heldAmmo", curExtraAmmo);
        World.caps.put("heldAmmo", maxExtraAmmo);

        World.stats.put("spread", spread);

        float val = reloadTime;
        float cap = 0;
        if (reloading) {
            val = curTime;
            cap = reloadTime;
        }
        World.stats.put("reload", val);
        World.caps.put("reload", cap);

    }

    public boolean canFirePull() {
        return !reloading && curTime >= fireTimePull;
    }

    public boolean canFire() {
        return !reloading && curTime >= fireTime;
    }

    public boolean hasAmmo() {
        return (int) (curAmmo - ammoUse) >= 0;
    }

    public void pickupAmmo(float v) {
        float pickup = (float) Math.ceil(ammoGain * v);
        curExtraAmmo = Mathf.min(maxExtraAmmo, curExtraAmmo + pickup);
    }

    public boolean update(float time) {
        spread = Mathf.lerp(spread, 0, time * absorbtion());

        if (reloading) {
            curTime += time;
            if (curTime >= reloadTime) {
                reloaded();
            }
            return false;
        }

        if (canFire()) {
            return true;
        }
        curTime += time;
		//Debug.log(curTime + "/" + fireTime + " - " + canFire());

        return canFire();
    }

    public void startReloadInfinite() {
        startReload(true);
    }

    public void startReload() {
        startReload(false);
    }

    public void startReload(boolean free) {
        if (reloading) {
            return;
        }
        if (curAmmo == maxAmmo) {
            return;
        }
        freeReload = free;
        curTime = 0;
        reloading = true;
    }

    public void reloaded() {
        reloading = false;
        if (freeReload) {
            curTime = fireTime;
            curAmmo = maxAmmo;
        } else {
            int ammoToRefil = (int) (maxAmmo - curAmmo);
            if (ammoToRefil > curExtraAmmo) {
                curAmmo += curExtraAmmo;
                curExtraAmmo = 0;
            } else {
                curExtraAmmo -= ammoToRefil;
                curAmmo = maxAmmo;
            }
        }

    }

}
