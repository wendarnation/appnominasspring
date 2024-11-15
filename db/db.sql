-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         MariaDB
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
SET NAMES utf8mb4;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
SET TIME_ZONE='+00:00';
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
SET FOREIGN_KEY_CHECKS=0;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
SET SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
SET SQL_NOTES=0;

-- Volcando estructura de base de datos para gestion_de_nominas
CREATE DATABASE IF NOT EXISTS `gestionnomina` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestionnomina`;

-- Volcando estructura para tabla gestion_de_nominas.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
                                           `nombre` VARCHAR(50) DEFAULT NULL,
    `dni` VARCHAR(50) NOT NULL,
    `sexo` CHAR(1) DEFAULT NULL,
    `categoria` INT DEFAULT 1,
    `anyos` INT DEFAULT 0,
    PRIMARY KEY (`dni`),
    CONSTRAINT `empleados_chk_1` CHECK (`sexo` IN ('m', 'f')),
    CONSTRAINT `empleados_chk_2` CHECK (`categoria` BETWEEN 1 AND 10),
    CONSTRAINT `empleados_chk_3` CHECK (`anyos` >= 0)
    );

-- Volcando datos para la tabla gestion_de_nominas.empleados
INSERT INTO `empleados` (`nombre`, `dni`, `sexo`, `categoria`, `anyos`) VALUES
                                                                            ('Ada Lovelace', '32000031R', 'F', 1, 0),
                                                                            ('James Gosling', '32000032G', 'M', 4, 7),
                                                                            ('Carlos Cortes', '28635398F', 'M', 1, 1);

-- Volcando estructura para tabla gestion_de_nominas.nominas
CREATE TABLE IF NOT EXISTS `nominas` (
                                         `dni` VARCHAR(50) DEFAULT NULL,
    `sueldo` INT DEFAULT NULL,
    KEY `fk_nominas_empleados` (`dni`),
    CONSTRAINT `fk_nominas_empleados` FOREIGN KEY (`dni`) REFERENCES `empleados` (`dni`) ON DELETE CASCADE
    );

-- Volcando datos para la tabla gestion_de_nominas.nominas
INSERT INTO `nominas` (`dni`, `sueldo`) VALUES
                                            ('32000032G', 145000),
                                            ('32000031R', 50000),
                                            ('28635398F', 55000);

-- Volcando estructura para disparador gestion_de_nominas.empleados_after_update
DELIMITER //
CREATE TRIGGER `empleados_after_update` AFTER UPDATE ON `empleados`
    FOR EACH ROW
BEGIN
    DECLARE sueldo_calculado INT;

    SET sueldo_calculado = 30000 + NEW.categoria * 20000 + NEW.anyos * 5000;

    UPDATE `nominas`
    SET sueldo = sueldo_calculado
    WHERE dni = NEW.dni;
END;
//
DELIMITER ;

-- Volcando estructura para disparador gestion_de_nominas.nominas_after_insert
DELIMITER //
CREATE TRIGGER `nominas_after_insert` AFTER INSERT ON `empleados`
    FOR EACH ROW
BEGIN
    DECLARE sueldo_calculado INT;

    SET sueldo_calculado = 30000 + NEW.categoria * 20000 + NEW.anyos * 5000;

    INSERT INTO `nominas` (`dni`, `sueldo`)
    VALUES (NEW.dni, sueldo_calculado);
END;
//
DELIMITER ;

-- Restaurar configuraciones originales
SET TIME_ZONE=@OLD_TIME_ZONE;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT;
SET SQL_NOTES=@OLD_SQL_NOTES;
