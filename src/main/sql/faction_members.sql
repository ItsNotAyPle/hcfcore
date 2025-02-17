CREATE TABLE IF NOT EXISTS `faction_members` (
    `faction_id` VARCHAR(100) NOT NULL,
    `player_uuid` VARCHAR(100) NOT NULL UNIQUE,

--    3 = leader, 2 = co-owner, 1 = officer, 0 = member
    `rank` SMALLINT NOT NULL DEFAULT 0,

    FOREIGN KEY (`faction_id`) REFERENCES factions(`id`),
    FOREIGN KEY (`player_uuid`) REFERENCES player_data(`player_id`)
);