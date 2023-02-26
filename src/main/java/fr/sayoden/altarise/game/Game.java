package fr.sayoden.altarise.game;

import fr.sayoden.altarise.AltarisePlugin;
import fr.sayoden.altarise.configurations.AltariseLocation;
import fr.sayoden.altarise.services.impl.ConfigurationServiceImpl;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

import java.util.Random;

public class Game {

    @Getter
    private final int roundAmount;

    @Getter
    private final int[] zombiesPerRound;

    private final AltariseLocation[] zombiesSpawnLocation;

    private final String[] zombieNames;

    @Getter
    private final Location playerSpawnLocation;

    private final ConfigurationServiceImpl configurationService;

    @Getter
    private int actualRound;

    private final Random random;

    @Getter
    private final Player player;

    @Getter
    private final GameTask gameTask;

    public Game(Player player) {
        this.configurationService = AltarisePlugin.getInstance().getConfigurationService();
        this.roundAmount = configurationService.getConfig().getRoundAmount();
        this.zombiesPerRound = new int[roundAmount];
        this.zombiesSpawnLocation = configurationService.getConfig().getRandomZombieSpawnLocations();
        this.playerSpawnLocation = configurationService.getConfig().getPlayerSpawnLocation().toBukkitLocation();
        this.zombieNames = configurationService.getConfig().getRandomZombieNames();
        this.random = new Random();
        this.player = player;
        fillZombies();

        this.gameTask = new GameTask(this);
    }

    /**
     * Fill zombies list by values in config
     */
    private void fillZombies() {
        System.arraycopy(configurationService.getConfig().getZombiesPerRound(), 0, zombiesPerRound, 0, zombiesPerRound.length);
    }

    public void nextRound() {
        this.actualRound++;
    }

    public Location getRandomZombieLocation() {
        return zombiesSpawnLocation[random.nextInt(this.zombiesSpawnLocation.length)]
                .toBukkitLocation();
    }

    public int getRandomZombieTimer() {
        return (int) ((Math.random() * (10 - 3)) + 3);
    }

    public String getRandomZombieName() {
        return zombieNames[random.nextInt(this.zombieNames.length)];
    }

    public void spawnZombie() {
        Bukkit.getScheduler().runTask(AltarisePlugin.getInstance(), () -> {
            Location randomLocation = getRandomZombieLocation();
            ParticleBuilder particleBuilder = new ParticleBuilder(ParticleEffect.CRIT_MAGIC, randomLocation)
                    .setAmount(10)
                    .setOffset(0,0,0);

            double radius = 0.7;
            for (double y = 0; y <= 2 * Math.PI; y += Math.PI / 8) {
                double x = Math.cos(y) * radius;
                double z = Math.sin(y) * radius;
                randomLocation.add(x, y, z);
                particleBuilder.setLocation(randomLocation).display();
                randomLocation.subtract(x, y, z);
            }
            Zombie zombie = (Zombie) randomLocation.getWorld().spawnEntity(randomLocation, EntityType.ZOMBIE);
            zombie.setCustomName("[Zombie] " + getRandomZombieName());
            zombie.setCustomNameVisible(true);
        });
    }
}
