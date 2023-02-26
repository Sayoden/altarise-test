package fr.sayoden.altarise.services.impl;

import fr.sayoden.altarise.services.IWeaponService;
import fr.sayoden.altarise.weapons.Mitraillette;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WeaponServiceImpl implements IWeaponService {

    private final Mitraillette mitraillette;

    public WeaponServiceImpl() {
        this.mitraillette = new Mitraillette();
    }

    @Override
    public void weaponUse(Player player, ItemStack itemStack) {
        String weaponName = itemStack.getItemMeta().getDisplayName();
        switch (weaponName) {
            case "Mitraillette": {
                mitraillette.use(player);
            }
        }
    }
}
