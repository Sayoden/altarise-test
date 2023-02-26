package fr.sayoden.altarise.game;

import fr.sayoden.altarise.AltarisePlugin;
import fr.sayoden.altarise.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final Game game;

    private int interRoundTimer;

    private int zombieRemaining;

    private int spawnZombieRandomTimer;

    private int spawnedZombies;

    public GameTask(Game game) {
        this.game = game;

        Player player = game.getPlayer();
        player.teleport(game.getPlayerSpawnLocation());
        player.getInventory().setItem(0, new ItemBuilder(Material.STONE_HOE).name("Mitraillette").make());

        this.runTaskTimerAsynchronously(AltarisePlugin.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        if (game.getActualRound() == 0 || zombieRemaining == 0) {
            game.nextRound();
            interRoundTimer = 5;
            spawnedZombies = 0;
            if (game.getActualRound() > 1) {
                Bukkit.broadcastMessage("§aTous les zombies de la manche §b§l" + (game.getActualRound() - 1) + " §asont morts");
            }
            if (game.getActualRound() > game.getRoundAmount()) {
                AltarisePlugin.getInstance().getGameService().endGame();
                cancel();
                return;
            }
            Bukkit.broadcastMessage("§cLa prochaine manche débutera dans 5 secondes");
            zombieRemaining = game.getZombiesPerRound()[game.getActualRound() - 1];
        }

        if (interRoundTimer > 0) {
            interRoundTimer--;
            if (interRoundTimer == 0) {
                Bukkit.broadcastMessage("§aLa manche §b§l" + game.getActualRound() + " §adébute!");
            }
            return;
        }

        /*
        Zombie spawn management
         */
        if (spawnZombieRandomTimer == 0) {
            spawnZombieRandomTimer = game.getRandomZombieTimer();
            if (spawnedZombies < game.getZombiesPerRound()[game.getActualRound() - 1]) {
                game.spawnZombie();
                spawnedZombies++;
            }
        } else {
            spawnZombieRandomTimer--;
        }
    }

    public void zombieKilled() {
        zombieRemaining--;
    }
}
