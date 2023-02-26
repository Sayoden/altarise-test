package fr.sayoden.altarise.configurations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AltariseLocation {

    private String worldName;

    private double x, y, z;

    private int yaw, pitch;

    @JsonIgnore
    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
}
