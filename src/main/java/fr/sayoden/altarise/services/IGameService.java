package fr.sayoden.altarise.services;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IGameService {

    boolean isGameStarted();

    void startGame(Player player);

    void endGame();

    ItemStack getStartItem();

    void zombieKilled();

}
