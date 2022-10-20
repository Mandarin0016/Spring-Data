USE `minions_db`;

# 2

SELECT `name`, COUNT(DISTINCT `minion_id`) AS 'minions_count'
FROM `villains`
         INNER JOIN `minions_villains` `mv` on `villains`.`id` = `mv`.`villain_id`
GROUP BY `name`
HAVING `minions_count` > 15
ORDER BY `minions_count` DESC;

# 3

SELECT `name`
FROM `villains`
WHERE `id` LIKE 1;

SELECT CONCAT((@`cnt` := @`cnt` + 1), '. ') AS 'rowNumber',
       CONCAT(`minions`.`name`, ' ', `age`) AS 'minion_information'
FROM `villains`
         INNER JOIN `minions_villains` `mv` on `villains`.`id` = `mv`.`villain_id`
         INNER JOIN `minions` ON `mv`.`minion_id` = `minions`.`id`
         CROSS JOIN (SELECT @`cnt` := 0) as `c`
WHERE `villain_id` LIKE 1;

# 4

SELECT *
FROM `towns`
WHERE `name` LIKE 'Berlin';

SELECT *
FROM `villains`
WHERE `name` LIKE 'Gru';

INSERT INTO `minions`(`name`, `age`, `town_id`)
VALUES ('Robert', 14, (SELECT `id` FROM `towns` WHERE `towns`.`name` LIKE 'Berlin'));

INSERT INTO `minions_villains`(`minion_id`, `villain_id`)
VALUES ((SELECT `id`
         FROM `minions`
         WHERE `name` LIKE 'Robert'),
        (SELECT `id`
         FROM `villains`
         WHERE `name` LIKE 'Gru'));


# 5

INSERT INTO `towns`(`name`, `country`)
VALUES ('Test', 'CountryTest');

UPDATE `towns`
SET `name` = UPPER(`name`)
WHERE `country` LIKE 'CountryTest';

SELECT `name`
FROM `towns`
WHERE `country` LIKE 'Bulgaria';


# 6

SELECT `name`
FROM `villains`
WHERE `id` = 1;

DELETE `minions_villains`
FROM `minions_villains`
WHERE `villain_id` = 8;

DELETE `villains`
FROM `villains`
WHERE `id` LIKE 9;

# 8

UPDATE `minions`
SET `age`  = `age` + 1,
    `name` = LOWER(`name`)
WHERE `id` = 1;

SELECT CONCAT_WS(' ', `name`, `age`)
FROM `minions`;

# 9

CREATE PROCEDURE `usp_get_older`(`minion_id` INT)
BEGIN
    UPDATE `minions`
    SET `age` = `age` + 1
    WHERE `id` LIKE `minion_id`;
END;

CALL usp_get_older(1);