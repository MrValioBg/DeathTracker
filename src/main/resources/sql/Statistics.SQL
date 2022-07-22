/*CREATE TABLE IF NOT EXISTS main_statistics
(
    id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    player_uuid VARCHAR(36) unique NOT NULL,
    death_count INT(10),
    sheep_killed_count INT(10),
    cows_killed_count INT(10)
);
*/

/* ALTER TABLE SQL */
ALTER TABLE main_statistics
    ADD cows_killed_count INT(10);