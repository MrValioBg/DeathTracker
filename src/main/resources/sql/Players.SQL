CREATE TABLE IF NOT EXISTS players
(
    id        INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    player_uuid varchar(100) unique not null
);