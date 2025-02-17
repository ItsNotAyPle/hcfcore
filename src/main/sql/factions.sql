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