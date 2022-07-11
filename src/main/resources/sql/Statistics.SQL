
CREATE TABLE IF NOT EXISTS main_statistics
(
    id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    player_uuid VARCHAR(36) unique NOT NULL,
    deaths INT(10)
);