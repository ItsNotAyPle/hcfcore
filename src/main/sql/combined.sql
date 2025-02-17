-- This file is used for resetting and remaking the db
-- this came in useful to have many times during
-- development!

USE `hcf`;

SET SQL_SAFE_UPDATES = 0;

-- DELETE FROM `faction_members`;
-- DELETE FROM `player_data`;
-- DELETE FROM `factions`;

DROP TABLE `faction_members`;
DROP TABLE `player_data` ;
DROP TABLE `factions`;

CREATE TABLE IF NOT EXISTS `factions` (
    `id` VARCHAR(100) NOT NULL DEFAULT (UUID()) UNIQUE ,
    `faction_name` VARCHAR(20) NOT NULL UNIQUE,
    `dtr` FLOAT NOT NULL DEFAULT 1.01,
    `ally_faction_id` VARCHAR(100),
    `faction_bal` INT NOT NULL DEFAULT 0,
    `faction_hq_x` INT,
    `faction_hq_y` INT,
    `faction_hq_z` INT,

--  claims
    `corner_1_x` INT,
    `corner_1_z` INT,
    `corner_2_x` INT,
    `corner_2_z` INT,
--

    `datetime_created` TIMESTAMP DEFAULT NOW() NOT NULL


);

CREATE TABLE IF NOT EXISTS `player_data` (
    `player_id` VARCHAR(100) NOT NULL UNIQUE,
    `lives` INT NOT NULL DEFAULT 0,
    `kills` INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `faction_members` (
    `faction_id` VARCHAR(100) NOT NULL,
    `player_uuid` VARCHAR(100) NOT NULL UNIQUE,

--    3 = leader, 2 = co-owner, 1 = officer, 0 = member
    `rank` SMALLINT NOT NULL DEFAULT 0,

    FOREIGN KEY (`faction_id`) REFERENCES factions(`id`),
    FOREIGN KEY (`player_uuid`) REFERENCES player_data(`player_id`)
);