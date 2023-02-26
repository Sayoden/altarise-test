package fr.sayoden.altarise;

import fr.sayoden.altarise.listeners.GameListener;
import fr.sayoden.altarise.services.impl.ConfigurationServiceImpl;
import fr.sayoden.altarise.services.impl.GameServiceImpl;
import fr.sayoden.altarise.services.impl.WeaponServiceImpl;
import fr.sayoden.altarise.weapons.Weapon;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AltarisePlugin extends JavaPlugin {

    @Getter
    private static AltarisePlugin instance;

    private ConfigurationServiceImpl configurationService;

    private GameServiceImpl gameService;

    private WeaponServiceImpl weaponService;

    @Override
    public void onEnable() {
        instance = this;

        /*
        Services
         */
        configurationService = new ConfigurationServiceImpl();
        gameService = new GameServiceImpl();
        weaponService = new WeaponServiceImpl();

        registerListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerListeners() {
        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new GameListener(), this);
    }


}
