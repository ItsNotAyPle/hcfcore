package org.ayple.hcfcore.playerdata;

import java.util.UUID;

public class PlayerData {
    public final UUID player_id;
    int lives;
    int kills;

    public PlayerData(UUID player_id, int lives, int kills) {
        this.player_id = player_id;
        this.lives = lives;
        this.kills = kills;
    }

    public int getKills() { return this.kills; }
    public void incrementKills() { this.kills++; }
    public int getLives() { return this.lives; }
    public void setPlayerLives(int new_value) { this.lives = new_value; }
    public void incrementPlayerLives() { this.lives++; }
    public void decrementPlayerLives() { this.lives--; }

}
