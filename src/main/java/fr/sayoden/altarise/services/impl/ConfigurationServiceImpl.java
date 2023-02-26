package fr.sayoden.altarise.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.sayoden.altarise.AltarisePlugin;
import fr.sayoden.altarise.configurations.AltariseLocation;
import fr.sayoden.altarise.configurations.Configuration;
import fr.sayoden.altarise.services.IConfigurationService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ConfigurationServiceImpl implements IConfigurationService {

    private final JavaPlugin plugin;

    @Getter
    private Configuration config;

    private final ObjectMapper mapper;

    public ConfigurationServiceImpl() {
        this.plugin = AltarisePlugin.getInstance();
        this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        init();
    }

    @Override
    public void init() {
        File configFile = new File(plugin.getDataFolder(), "config.json");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        try {
            if (!configFile.exists()) {
                    configFile.createNewFile();
                    plugin.getLogger().info("Config file created!");
            } else {
                    config = new ObjectMapper().readValue(configFile, Configuration.class);
            }

            if (configFile.length() == 0) {
                plugin.getLogger().info("Write default values in config file...");
                writeDefaultValues();
            }
        } catch (IOException e) {
            plugin.getLogger().info("Config file error: " + e.getMessage());
        }

        plugin.getLogger().info("Config file found!");
    }

    private void writeDefaultValues() throws IOException {
        config = Configuration.builder()
                .configVersion("1.0.0")
                .author("Sayoden <3")
                .roundAmount(2)
                .zombiesPerRound(new int[]{2, 5, 10, 20, 40, 45})
                .randomZombieSpawnLocations(new AltariseLocation[]{AltariseLocation.builder()
                        .worldName("world")
                        .x(0)
                        .y(0)
                        .y(0)
                        .pitch(100)
                        .yaw(100)
                        .build()})
                .playerSpawnLocation(AltariseLocation.builder()
                        .worldName("world")
                        .x(0)
                        .y(0)
                        .y(0)
                        .pitch(100)
                        .yaw(100)
                        .build())
                .randomZombieNames(new String[]{"Albert", "Lucas", "Cedric", "Pedro", "Arthur", "Clément"})
                .build();
        mapper.writeValue(Paths.get(plugin.getDataFolder() + "/config.json").toFile(), config);
    }
}
