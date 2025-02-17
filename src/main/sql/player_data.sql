CREATE TABLE IF NOT EXISTS `player_data` (
    `player_id` VARCHAR(100) NOT NULL UNIQUE,
    `lives` INT NOT NULL DEFAULT 0,
    `kills` INT NOT NULL DEFAULT 0
);