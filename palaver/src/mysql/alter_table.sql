/*
 * Erstellt von Eike Becher am 10.06.2013
 * In DB ausgeführt: 
 */
ALTER TABLE `palaver`.`regel` 
	CHANGE COLUMN `zeile` `zeile` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `spalte` `spalte` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `regeltyp` `regeltyp` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `operator` `operator` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `kriterien` `kriterien` VARCHAR(500) NULL DEFAULT NULL  , 
	CHANGE COLUMN `fehlermeldung` `fehlermeldung` VARCHAR(500) NULL DEFAULT NULL;
	
/*
 * Erstellt von Eike Becher am 10.06.2013
 * In DB ausgeführt: 
 */
ALTER TABLE `palaver`.`menueplan_has_menues` 
	ADD COLUMN `portion` INT(11) NOT NULL  AFTER `zeile` ;
	
ALTER TABLE `palaver`.`menueplan` 
	ADD COLUMN `freigegeben` TINYINT(1) NULL  AFTER `year` ;
	
ALTER TABLE `palaver`.`ansprechpartner` 
	ADD COLUMN `email` VARCHAR(45) NULL  AFTER `fax` ;	
	
ALTER TABLE `palaver`.`fussnote` CHANGE COLUMN `abkuerzung` `abkuerzung` VARCHAR(45) NOT NULL  
	, ADD UNIQUE INDEX `abkuerzung_UNIQUE` (`abkuerzung` ASC)
	, ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC) ;

ALTER TABLE `palaver`.`geschmack` 
	ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC) ;	
	
ALTER TABLE `palaver`.`zubereitung` 
	ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC) ;

ALTER TABLE `palaver`.`regel` 
	ADD COLUMN `ignorierbar` TINYINT(1) NULL  AFTER `aktiv` ;
	
ALTER TABLE `palaver`.`kuchenrezept` DROP FOREIGN KEY `fk_mitarbeiter_kuchen` ;
ALTER TABLE `palaver`.`kuchenrezept` DROP COLUMN `mitarbeiter_fk` 
, DROP INDEX `fk_mitarbeiter_kuchen_idx` ;

ALTER TABLE `palaver`.`menue` ADD COLUMN `aktiv` TINYINT(1) NULL  AFTER `favorit` ;
ALTER TABLE `palaver`.`rezept` ADD COLUMN `aktiv` TINYINT(1) NULL  AFTER `erstellt` ;
ALTER TABLE `palaver`.`kuchenrezept` ADD COLUMN `aktiv` TINYINT(1) NULL  AFTER `erstellt` ;



	