-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: monitux
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `abonos_compras`
--

DROP TABLE IF EXISTS `abonos_compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abonos_compras` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_CTAP` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Secuencial_Proveedor` int DEFAULT NULL,
  `Fecha` varchar(30) DEFAULT NULL,
  `Monto` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `abonos_ventas`
--

DROP TABLE IF EXISTS `abonos_ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abonos_ventas` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_CTAC` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Secuencial_Cliente` int DEFAULT NULL,
  `Fecha` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Monto` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `actividades`
--

DROP TABLE IF EXISTS `actividades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actividades` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Descripcion` tinytext,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  `Descripcion` tinytext,
  `Imagen` mediumblob,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(20) DEFAULT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `Direccion` tinytext,
  `Email` varchar(50) DEFAULT NULL,
  `Imagen` mediumblob,
  `Activo` int DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comentarios`
--

DROP TABLE IF EXISTS `comentarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentarios` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura_C` int DEFAULT NULL,
  `Secuencial_Factura_V` int DEFAULT NULL,
  `Secuencial_Producto` int DEFAULT NULL,
  `Secuencial_Cotizacion` int DEFAULT NULL,
  `Secuencial_Orden` int DEFAULT NULL,
  `Contenido` tinytext,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Proveedor` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Tipo` varchar(25) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Otros_Cargos` double DEFAULT NULL,
  `Descuento` double DEFAULT NULL,
  `Impuesto` double DEFAULT NULL,
  `Forma_Pago` varchar(25) DEFAULT NULL,
  `Gran_Total` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  `Documento` mediumblob,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compras_detalles`
--

DROP TABLE IF EXISTS `compras_detalles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras_detalles` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Proveedor` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Secuencial_Producto` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Codigo` varchar(20) DEFAULT NULL,
  `Descripcion` tinytext,
  `Cantidad` double DEFAULT NULL,
  `Precio` double DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cotizaciones`
--

DROP TABLE IF EXISTS `cotizaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cotizaciones` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Cliente` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Otros_Cargos` double DEFAULT NULL,
  `Impuesto` double DEFAULT NULL,
  `Descuento` double DEFAULT NULL,
  `Gran_Total` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cotizaciones_detalles`
--

DROP TABLE IF EXISTS `cotizaciones_detalles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cotizaciones_detalles` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Cotizacion` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Secuencial_Cliente` int DEFAULT NULL,
  `Secuencial_Producto` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Codigo` int DEFAULT NULL,
  `Descripcion` tinytext,
  `Cantidad` double DEFAULT NULL,
  `Precio` double DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cuentas_cobrar`
--

DROP TABLE IF EXISTS `cuentas_cobrar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentas_cobrar` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Cliente` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Fecha_Vencimiento` varchar(25) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Saldo` double DEFAULT NULL,
  `Pagado` double DEFAULT NULL,
  `Descuento` double DEFAULT NULL,
  `Impuesto` double DEFAULT NULL,
  `Otros_Cargos` double DEFAULT NULL,
  `Gran_Total` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cuentas_pagar`
--

DROP TABLE IF EXISTS `cuentas_pagar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentas_pagar` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Proveedor` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Fecha_Vencimiento` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Saldo` double DEFAULT NULL,
  `Pagado` double DEFAULT NULL,
  `Otros_Cargos` double DEFAULT NULL,
  `Descuento` double DEFAULT NULL,
  `Impuesto` double DEFAULT NULL,
  `Gran_Total` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `egresos`
--

DROP TABLE IF EXISTS `egresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egresos` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Usuario` int NOT NULL,
  `Fecha` varchar(25) NOT NULL,
  `Tipo_Egreso` varchar(20) NOT NULL,
  `Total` double NOT NULL,
  `Descripcion` tinytext NOT NULL,
  `Secuencial_Empresa` int NOT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empresas`
--

DROP TABLE IF EXISTS `empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresas` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Nombre` tinytext,
  `Direccion` tinytext,
  `Telefono` tinytext,
  `Email` tinytext,
  `Moneda` varchar(10) DEFAULT NULL,
  `ISV` double DEFAULT NULL,
  `Activa` bit(1) DEFAULT NULL,
  `Imagen` mediumblob,
  `Secuencial_Usuario` int DEFAULT NULL,
  `RSS` text,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gastos`
--

DROP TABLE IF EXISTS `gastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gastos` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Tipo_Gasto` varchar(20) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Descripcion` tinytext,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ingresos`
--

DROP TABLE IF EXISTS `ingresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingresos` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Usuario` int NOT NULL,
  `Fecha` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Tipo_Ingreso` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Total` double NOT NULL,
  `Descripcion` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Secuencial_Empresa` int NOT NULL,
  PRIMARY KEY (`Secuencial`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kardex`
--

DROP TABLE IF EXISTS `kardex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kardex` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Fecha` varchar(25) DEFAULT NULL,
  `Secuencial_Producto` int DEFAULT NULL,
  `Descripcion` tinytext,
  `Cantidad` double DEFAULT NULL,
  `Costo` double DEFAULT NULL,
  `Costo_Total` double DEFAULT NULL,
  `Venta` double DEFAULT NULL,
  `Venta_Total` double DEFAULT NULL,
  `Saldo` double DEFAULT NULL,
  `Movimiento` tinytext,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Proveedor` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Otros_Cargos` double DEFAULT NULL,
  `Impuesto` double DEFAULT NULL,
  `Descuento` double DEFAULT NULL,
  `Gran_Total` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordenes_detalles`
--

DROP TABLE IF EXISTS `ordenes_detalles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordenes_detalles` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Orden` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Secuencial_Proveedor` int DEFAULT NULL,
  `Secuencial_Producto` int DEFAULT NULL,
  `Fecha` varchar(25) DEFAULT NULL,
  `Codigo` varchar(20) DEFAULT NULL,
  `Descripcion` tinytext,
  `Cantidad` double DEFAULT NULL,
  `Precio` double DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Proveedor` int NOT NULL,
  `Codigo` varchar(20) NOT NULL,
  `Descripcion` tinytext NOT NULL,
  `Cantidad` double NOT NULL,
  `Precio_Costo` double NOT NULL,
  `Precio_Venta` double NOT NULL,
  `Marca` varchar(50) DEFAULT NULL,
  `Codigo_Barra` varchar(20) DEFAULT NULL,
  `Codigo_Fabricante` varchar(20) DEFAULT NULL,
  `Codigo_QR` text,
  `Imagen` mediumblob,
  `Secuencial_Categoria` int DEFAULT NULL,
  `Existencia_Minima` double DEFAULT NULL,
  `Fecha_Caducidad` varchar(25) DEFAULT NULL,
  `Expira` bit(1) DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) DEFAULT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `Direccion` tinytext,
  `Email` tinytext,
  `Contacto` varchar(50) DEFAULT NULL,
  `Tipo` varchar(20) DEFAULT NULL,
  `Imagen` mediumblob,
  `Activo` bit(1) DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Codigo` varchar(21) DEFAULT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Password` text,
  `Imagen` mediumblob,
  `Acceso` varchar(20) DEFAULT NULL,
  `Activo` bit(1) DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Cliente` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Fecha` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Tipo` varchar(25) DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Otros_Cargos` double DEFAULT NULL,
  `Descuento` double DEFAULT NULL,
  `Impuesto` double DEFAULT NULL,
  `Forma_Pago` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Gran_Total` double DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  `Documento` mediumblob,
  PRIMARY KEY (`Secuencial`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ventas_detalles`
--

DROP TABLE IF EXISTS `ventas_detalles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas_detalles` (
  `Secuencial` int NOT NULL AUTO_INCREMENT,
  `Secuencial_Factura` int DEFAULT NULL,
  `Secuencial_Cliente` int DEFAULT NULL,
  `Secuencial_Usuario` int DEFAULT NULL,
  `Secuencial_Producto` int DEFAULT NULL,
  `Fecha` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Codigo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Descripcion` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `Cantidad` double DEFAULT NULL,
  `Precio` double DEFAULT NULL,
  `Total` double DEFAULT NULL,
  `Tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Secuencial_Empresa` int DEFAULT NULL,
  PRIMARY KEY (`Secuencial`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-12  2:08:38
