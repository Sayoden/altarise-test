package fr.sayoden.altarise.listeners;

import fr.sayoden.altarise.AltarisePlugin;
import fr.sayoden.altarise.services.impl.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GameListener implements Listener {

    private final GameServiceImpl gameService;

    public GameListener() {
        this.gameService = AltarisePlugin.getInstance().getGameService();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!gameService.isGameStarted()) {
            player.getInventory().clear();
            player.getInventory().setItem(4, gameService.getStartItem());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!gameService.isGameStarted()) {
            if (event.getItem().getType().equals(gameService.getStartItem().getType())) {
                gameService.startGame(event.getPlayer());
            }
        } else {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                AltarisePlugin.getInstance().getWeaponService().weaponUse(event.getPlayer(), event.getItem());
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (gameService.isGameStarted() && event.getEntity() instanceof Zombie) {
            gameService.zombieKilled();
        } else if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            for (Entity nearbyEntity : player.getNearbyEntities(50, 50, 50)) {
                if (nearbyEntity instanceof Zombie) {
                    ((Zombie) nearbyEntity).damage(1000);
                }
            }

            player.sendMessage("§cVous avez échoué.");
            gameService.endGame();
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getDamager();
            if (snowball.hasMetadata("weapon_damage")) {
                int damage = snowball.getMetadata("weapon_damage").get(0).asInt();
                event.setDamage(damage);
            }
        }
    }
}
