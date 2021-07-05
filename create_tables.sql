CREATE TABLE `delivery`.`individual` (
  `idIndividual` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `phoneNumber` VARCHAR(45) NULL,
  `emailAddress` VARCHAR(45) NULL,
  PRIMARY KEY (`idIndividual`),
  UNIQUE INDEX `idIndividual_UNIQUE` (`idIndividual` ASC) VISIBLE)
ENGINE = InnoDB;

CREATE TABLE `delivery`.`location` (
  `locationId` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `street_number` INT NULL,
  `building` VARCHAR(45) NULL,
  `entrance` VARCHAR(45) NULL,
  PRIMARY KEY (`locationId`),
  UNIQUE INDEX `clientId_UNIQUE` (`locationId` ASC) VISIBLE);

  
  CREATE TABLE `delivery`.`client` (
  `clientId` VARCHAR(36) NOT NULL,
  `client_address` INT NOT NULL,
  PRIMARY KEY (`clientId`),
  INDEX `location_idx` (`client_address` ASC) VISIBLE,
  UNIQUE INDEX `clientId_UNIQUE` (`clientId` ASC) VISIBLE,
  CONSTRAINT `individual`
    FOREIGN KEY (`clientId`)
    REFERENCES `delivery`.`individual` (`idIndividual`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `location`
    FOREIGN KEY (`client_address`)
    REFERENCES `delivery`.`location` (`locationId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE `delivery`.`deliveryman` (
  `deliveryManId` VARCHAR(36) NOT NULL,
  `completedDeliveries` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`deliveryManId`),
  UNIQUE INDEX `deliveryManId_UNIQUE` (`deliveryManId` ASC) VISIBLE,
  CONSTRAINT `individual_deliv`
    FOREIGN KEY (`deliveryManId`)
    REFERENCES `delivery`.`individual` (`idIndividual`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

