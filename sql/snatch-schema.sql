# Reset Schema and Tables
DROP TABLE IF EXISTS loot CASCADE;
DROP TABLE IF EXISTS heist CASCADE;
DROP TABLE IF EXISTS personnel_skill CASCADE;
DROP TABLE IF EXISTS skill CASCADE;
DROP TABLE IF EXISTS personnel CASCADE;
DROP TABLE IF EXISTS crew CASCADE;
DROP TABLE IF EXISTS captain CASCADE;
DROP TABLE IF EXISTS leader CASCADE;

DROP SCHEMA IF EXISTS snatch;

CREATE SCHEMA `snatch` ;

CREATE TABLE `snatch`.`leader` (
  `leader_id` INT NOT NULL AUTO_INCREMENT,
  `leader_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`leader_id`),
  UNIQUE INDEX `leader_name_UNIQUE` (`leader_name` ASC) VISIBLE);

CREATE TABLE `snatch`.`captain` (
  `captain_id` INT NOT NULL AUTO_INCREMENT,
  `captain_name` VARCHAR(45) NOT NULL,
  `leader_id` INT NULL,
  PRIMARY KEY (`captain_id`),
  UNIQUE INDEX `captain_name_UNIQUE` (`captain_name` ASC) VISIBLE,
  CONSTRAINT `leader_id`
    FOREIGN KEY (`leader_id`)
    REFERENCES `snatch`.`leader` (`leader_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);

CREATE TABLE `snatch`.`crew` (
  `crew_id` INT NOT NULL AUTO_INCREMENT,
  `crew_name` VARCHAR(45) NOT NULL,
  `max_capacity` INT NOT NULL,
  `availability` TINYINT NOT NULL,
  `has_captain` TINYINT NOT NULL,
  `captain_id` INT NULL,
  PRIMARY KEY (`crew_id`),
  UNIQUE INDEX `crew_name_UNIQUE` (`crew_name` ASC) VISIBLE,
  INDEX `captain_id_idx` (`captain_id` ASC) VISIBLE,
  CONSTRAINT `captain_id`
    FOREIGN KEY (`captain_id`)
    REFERENCES `snatch`.`captain` (`captain_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);

CREATE TABLE `snatch`.`personnel` (
  `personnel_id` INT NOT NULL AUTO_INCREMENT,
  `personnel_name` VARCHAR(45) NOT NULL,
  `species` VARCHAR(45) NOT NULL,
  `profile_img` VARCHAR(255) NULL,
  `is_assigned` TINYINT NOT NULL,
  `crew_id` INT NULL,
  PRIMARY KEY (`personnel_id`),
  UNIQUE INDEX `personnel_name_UNIQUE` (`personnel_name` ASC) VISIBLE,
  INDEX `crew_id_idx` (`crew_id` ASC) VISIBLE,
  CONSTRAINT `personnel_crew_id`
    FOREIGN KEY (`crew_id`)
    REFERENCES `snatch`.`crew` (`crew_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);
    
CREATE TABLE `snatch`.`skill` (
  `skill_id` INT NOT NULL AUTO_INCREMENT,
  `skill_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`skill_id`),
  UNIQUE INDEX `skill_name_UNIQUE` (`skill_name` ASC) VISIBLE);
  
CREATE TABLE `snatch`.`personnel_skill` (
  `personnel_skill_id` INT NOT NULL AUTO_INCREMENT,
  `personnel_id` INT NOT NULL,
  `skill_id` INT NOT NULL,
  PRIMARY KEY (`personnel_skill_id`),
  INDEX `personnel_id_idx` (`personnel_id` ASC) VISIBLE,
  INDEX `skill_id_idx` (`skill_id` ASC) VISIBLE,
  CONSTRAINT `personnel_id`
    FOREIGN KEY (`personnel_id`)
    REFERENCES `snatch`.`personnel` (`personnel_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `skill_id`
    FOREIGN KEY (`skill_id`)
    REFERENCES `snatch`.`skill` (`skill_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `snatch`.`heist` (
  `heist_id` INT NOT NULL AUTO_INCREMENT,
  `description` TEXT(500) NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `is_assigned` TINYINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `crew_id` INT NULL,
  PRIMARY KEY (`heist_id`),
  INDEX `heist_crew_id_idx` (`crew_id` ASC) VISIBLE,
  CONSTRAINT `heist_crew_id`
    FOREIGN KEY (`crew_id`)
    REFERENCES `snatch`.`crew` (`crew_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE);

CREATE TABLE `snatch`.`loot` (
  `loot_id` INT NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`loot_id`),
  UNIQUE INDEX `item_name_UNIQUE` (`item_name` ASC) VISIBLE);

select * from information_schema.table_constraints where constraint_schema = 'snatch'
