CREATE TABLE `asteriskcdrdb`.`ut_grupousuario` (
  `Sys_PK` BIGINT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(34) NOT NULL,
  `Descripcion` LONGTEXT NOT NULL,
  PRIMARY KEY (`Sys_PK`),
  UNIQUE INDEX `Nombre_UNIQUE` (`Nombre` ASC));
  
CREATE TABLE `asteriskcdrdb`.`ut_roles` (
  `Sys_PK` BIGINT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
  `CodigoItem` VARCHAR(32) NOT NULL,
  `Descripcion` LONGTEXT NOT NULL,
  PRIMARY KEY (`Sys_PK`),
  UNIQUE INDEX `Sys_PK_UNIQUE` (`Sys_PK` ASC),
  UNIQUE INDEX `CodigoItem_UNIQUE` (`CodigoItem` ASC));
  
  CREATE TABLE `asteriskcdrdb`.`ut_rolgruposusuario` (
  `Sys_PK` BIGINT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
  `GrupoUsuarioFK` BIGINT(16) UNSIGNED NOT NULL,
  `RolFK` BIGINT(16) UNSIGNED NOT NULL,
  PRIMARY KEY (`Sys_PK`),
  UNIQUE INDEX `Sys_PK_UNIQUE` (`Sys_PK` ASC),
  INDEX `roles_idx` (`RolFK` ASC),
  INDEX `grupoUsuario_idx` (`GrupoUsuarioFK` ASC),
  CONSTRAINT `roles`
    FOREIGN KEY (`RolFK`)
    REFERENCES `asteriskcdrdb`.`ut_roles` (`Sys_PK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `grupoUsuario`
    FOREIGN KEY (`GrupoUsuarioFK`)
    REFERENCES `asteriskcdrdb`.`ut_grupousuario` (`Sys_PK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);