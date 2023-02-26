package fr.sayoden.altarise.weapons;

import fr.sayoden.altarise.AltarisePlugin;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;

public class Mitraillette extends Weapon {

    public Mitraillette() {
        super("Mitraillette", 5);
    }

    @Override
    public void use(Player player) {
        Snowball arrow = player.launchProjectile(Snowball.class);
        AltarisePlugin plugin = AltarisePlugin.getInstance();
        arrow.setMetadata("weapon_damage", new FixedMetadataValue(plugin, getDamage()));
        arrow.setMetadata("weapon_type", new FixedMetadataValue(plugin, "mitraillette"));
    }
}
