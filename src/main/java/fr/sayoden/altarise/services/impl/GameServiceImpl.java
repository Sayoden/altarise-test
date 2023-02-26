package fr.sayoden.altarise.services.impl;

import fr.sayoden.altarise.AltarisePlugin;
import fr.sayoden.altarise.game.Game;
import fr.sayoden.altarise.services.IGameService;
import fr.sayoden.altarise.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameServiceImpl implements IGameService {

    /*
     * Null : No game started
     */
    private Game game;

    private final AltarisePlugin plugin;

    public GameServiceImpl() {
        this.plugin = AltarisePlugin.getInstance();
    }

    @Override
    public boolean isGameStarted() {
        return game != null;
    }

    @Override
    public void startGame(Player player) {
        if (!isGameStarted()) {
            player.getInventory().clear();
            player.sendMessage("§aLa partie va commencer !");
            this.game = new Game(player);
        }
    }

    @Override
    public void endGame() {
        if (isGameStarted()) {
            Bukkit.broadcastMessage("§a§lLa partie est terminée !");
            Player player = game.getPlayer();
            player.getInventory().clear();
            player.teleport(game.getPlayerSpawnLocation());
            player.getInventory().setItem(4, plugin.getGameService().getStartItem());
            game = null;
        }
    }

    @Override
    public ItemStack getStartItem() {
        return new ItemBuilder(Material.NETHER_STAR)
                .name("§aLancer une partie")
                .amount(1)
                .make();
    }

    @Override
    public void zombieKilled() {
        if (isGameStarted()) {
            game.getGameTask().zombieKilled();
        }
    }
}
