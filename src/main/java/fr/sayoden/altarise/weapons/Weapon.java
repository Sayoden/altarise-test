package fr.sayoden.altarise.weapons;

import org.bukkit.entity.Player;

public abstract class Weapon {

    private String name;

    private int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void use(Player player);

}
