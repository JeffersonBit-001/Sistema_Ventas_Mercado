-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_sistema_ventas
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `idCategoria` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) NOT NULL,
  `estado` int NOT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'nom1',1),(2,'nom2',1),(5,'gffhfgh22',1);
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(500) NOT NULL,
  `apellido` varchar(500) NOT NULL,
  `dni` int NOT NULL,
  `direccion` varchar(500) NOT NULL,
  `celular` int NOT NULL,
  `tipo` varchar(10) NOT NULL DEFAULT 'NE',
  `fecha` date NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Yair','Aguero',12312314,'123@gmail.com',900123123,'NE','2024-11-06'),(2,'Preuba','qw',12676777,'112312',921912111,'NE','2024-11-06');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_factura`
--

DROP TABLE IF EXISTS `detalle_factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_factura` int DEFAULT NULL,
  `id_producto` int DEFAULT NULL,
  `cant_prod_usados` int DEFAULT '0',
  `precio` float DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_factura_idx` (`id_factura`),
  KEY `id_prod_idx` (`id_producto`),
  CONSTRAINT `id_factura` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`id`),
  CONSTRAINT `id_prod` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_factura`
--

LOCK TABLES `detalle_factura` WRITE;
/*!40000 ALTER TABLE `detalle_factura` DISABLE KEYS */;
INSERT INTO `detalle_factura` VALUES (16,10,3,5,60),(17,10,1,5,65),(18,11,1,5,65),(19,11,3,5,60),(20,11,4,6,24),(21,12,1,5,65),(22,12,3,5,60),(26,14,4,2,8),(27,14,1,2,26),(31,16,1,2,26),(32,16,3,2,24),(33,16,4,8,32),(34,17,1,5,65),(35,18,1,4,52),(36,18,3,4,48),(37,19,1,3,39),(38,19,3,2,24),(40,20,1,7,91),(41,20,3,2,24);
/*!40000 ALTER TABLE `detalle_factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `cod` varchar(10) NOT NULL,
  `ruc` varchar(45) DEFAULT NULL,
  `dueño` varchar(500) DEFAULT NULL,
  `direccion` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`cod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES ('EMP001','20609926989','Los Tintaya','lamolinax@gmail.com');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int DEFAULT NULL,
  `codigo` varchar(45) DEFAULT NULL,
  `total` float DEFAULT '0',
  `hora` time DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `estado` varchar(45) DEFAULT 'pendiente',
  `id_usuarios` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cliente_idx` (`id_cliente`),
  KEY `id_usuario_idx` (`id_usuarios`),
  CONSTRAINT `id_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`),
  CONSTRAINT `id_usuario` FOREIGN KEY (`id_usuarios`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,1,'qwe',12,'11:52:51','2024-11-06','pendiente',1),(2,1,'EUUXIAAYG9',0,'14:37:29','2024-11-06','pendiente',1),(3,1,'4FSY2DTDFV',0,'14:43:13','2024-11-06','pendiente',1),(4,1,'NY1NTXBD82',0,'14:44:53','2024-11-06','pendiente',1),(5,1,'36QQSP5DF1',0,'18:15:47','2024-11-06','pendiente',1),(7,1,'D68YIQKOEN',NULL,'22:59:43','2024-11-06','pendiente',1),(8,2,'96FXGWVAVP',NULL,'01:34:03','2024-11-07','pendiente',1),(9,1,'ILTJQWLLJK',NULL,'01:48:22','2024-11-07','pendiente',1),(10,1,'SRQSVZYYEZ',125,'02:07:52','2024-11-07','pagado',1),(11,2,'YVT2DUXZD2',149,'23:47:44','2024-11-07','proceso',2),(12,1,'7BZK2P2NE9',125,'16:26:37','2024-11-10','proceso',5),(14,1,'C1WHLLANG5',34,'17:36:44','2024-11-10','proceso',5),(16,1,'1NJWVQSOI0',82,'17:57:40','2024-11-10','proceso',5),(17,1,'C4QO052QCC',65,'18:04:31','2024-11-10','proceso',5),(18,2,'FILZRA343D',100,'18:11:21','2024-11-10','proceso',5),(19,2,'J73Z5LT4HT',63,'18:18:34','2024-11-10','proceso',5),(20,2,'SRSVEB2E38',115,'18:21:26','2024-11-10','proceso',5);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `cantidad` int NOT NULL,
  `precio` double(10,2) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `idCategoria` int NOT NULL,
  `estado` int NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `idCat_idx` (`idCategoria`),
  CONSTRAINT `idCat` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'nuevo',44,13.00,'paquete',1,1),(3,'ggf',100,12.00,'errt',1,1),(4,'ejm',66,4.00,'qwe',2,1);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrador'),(2,'Empleado');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(500) NOT NULL,
  `apellido` varchar(500) NOT NULL,
  `usuario` varchar(500) NOT NULL,
  `pass` varchar(500) NOT NULL,
  `id_rol` int DEFAULT NULL,
  `dni` int DEFAULT NULL,
  `correo` varchar(500) DEFAULT NULL,
  `celular` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rol_idx` (`id_rol`),
  CONSTRAINT `id_rol` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Felix','Tintaya','felix','123',2,11111111,'123@gmail.com',987123123),(2,'nose','nose','nose','nose',1,33333333,'nose@gmail.com',900123123),(5,'Felix','Tintaya','felix4','123',1,11114444,'123@gmail.com',987123124);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-10 18:31:14
