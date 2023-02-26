package fr.sayoden.altarise.services;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IWeaponService {

    void weaponUse(Player player, ItemStack itemStack);

}
