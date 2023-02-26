package fr.sayoden.altarise.configurations;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Configuration {

    private String configVersion;

    private String author;

    private int roundAmount;

    private int[] zombiesPerRound;

    private AltariseLocation[] randomZombieSpawnLocations;

    private AltariseLocation playerSpawnLocation;

    private String[] randomZombieNames;

}
