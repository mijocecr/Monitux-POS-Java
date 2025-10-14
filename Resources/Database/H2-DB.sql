CREATE TABLE "abonos_compras" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_CTAP" INT NOT NULL,
  "Secuencial_Usuario" INT NOT NULL,
  "Secuencial_Proveedor" INT NOT NULL,
  "Fecha" VARCHAR(255) NOT NULL,
  "Monto" DOUBLE NOT NULL,
  "Secuencial_Empresa" INT NOT NULL
);

CREATE TABLE "abonos_ventas" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_CTAC" INT NOT NULL,
  "Secuencial_Usuario" INT NOT NULL,
  "Secuencial_Cliente" INT NOT NULL,
  "Fecha" VARCHAR(255) NOT NULL,
  "Monto" DOUBLE NOT NULL,
  "Secuencial_Empresa" INT NOT NULL
);

CREATE TABLE "actividades" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Usuario" INT NOT NULL,
  "Fecha" VARCHAR(255),
  "Descripcion" VARCHAR(1000),
  "Secuencial_Empresa" INT
);

CREATE TABLE "categorias" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Nombre" VARCHAR(150),
  "Descripcion" VARCHAR(1000),
  "Imagen" BLOB,
  "Secuencial_Empresa" INT NOT NULL
);

CREATE TABLE "clientes" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Codigo" VARCHAR(50),
  "Nombre" VARCHAR(150),
  "Telefono" VARCHAR(20),
  "Direccion" VARCHAR(1000),
  "Email" VARCHAR(150),
  "Imagen" BLOB,
  "Activo" BOOLEAN,
  "Secuencial_Empresa" INT
);

CREATE TABLE "comentarios" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Factura_C" INT,
  "Secuencial_Factura_V" INT,
  "Secuencial_Producto" INT,
  "Secuencial_Cotizacion" INT,
  "Secuencial_Orden" INT,
  "Contenido" VARCHAR(1000),
  "Secuencial_Empresa" INT
);

CREATE TABLE "compras" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Proveedor" INT,
  "Secuencial_Usuario" INT,
  "Fecha" VARCHAR(255),
  "Tipo" VARCHAR(50),
  "Forma_Pago" VARCHAR(50),
  "Total" DOUBLE,
  "Otros_Cargos" DOUBLE,
  "Descuento" DOUBLE,
  "Impuesto" DOUBLE,
  "Gran_Total" DOUBLE,
  "Secuencial_Empresa" INT,
  "Documento" BLOB
);

CREATE TABLE "compras_detalles" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Factura" INT,
  "Secuencial_Proveedor" INT,
  "Secuencial_Producto" INT,
  "Secuencial_Usuario" INT,
  "Fecha" VARCHAR(255),
  "Codigo" VARCHAR(25),
  "Descripcion" VARCHAR(1000),
  "Cantidad" DOUBLE,
  "Precio" DOUBLE,
  "Total" DOUBLE,
  "Tipo" VARCHAR(50),
  "Secuencial_Empresa" INT
);

CREATE TABLE "cotizaciones" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Cliente" INT,
  "Secuencial_Usuario" INT,
  "Fecha" VARCHAR(255),
  "Total" DOUBLE,
  "Otros_Cargos" DOUBLE,
  "Descuento" DOUBLE,
  "Impuesto" DOUBLE,
  "Gran_Total" DOUBLE,
  "Secuencial_Empresa" INT
);

CREATE TABLE "cotizaciones_detalles" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Cotizacion" INT,
  "Secuencial_Usuario" INT,
  "Secuencial_Cliente" INT,
  "Secuencial_Producto" INT,
  "Fecha" VARCHAR(255),
  "Codigo" VARCHAR(25),
  "Descripcion" VARCHAR(1000),
  "Cantidad" DOUBLE,
  "Precio" DOUBLE,
  "Tipo" VARCHAR(50),
  "Total" DOUBLE,
  "Secuencial_Empresa" INT
);

CREATE TABLE "kardex" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Fecha" VARCHAR(255) NOT NULL,
  "Secuencial_Producto" INT NOT NULL,
  "Descripcion" VARCHAR(1000) NOT NULL,
  "Cantidad" DOUBLE NOT NULL,
  "Costo" DOUBLE NOT NULL,
  "Costo_Total" DOUBLE NOT NULL,
  "Venta" DOUBLE NOT NULL,
  "Venta_Total" DOUBLE NOT NULL,
  "Saldo" DOUBLE NOT NULL,
  "Movimiento" VARCHAR(50) NOT NULL,
  "Secuencial_Empresa" INT NOT NULL
);

CREATE TABLE "ordenes" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Proveedor" INT NOT NULL,
  "Secuencial_Usuario" INT NOT NULL,
  "Fecha" VARCHAR(255),
  "Total" DOUBLE,
  "Otros_Cargos" DOUBLE,
  "Descuento" DOUBLE,
  "Impuesto" DOUBLE,
  "Gran_Total" DOUBLE,
  "Secuencial_Empresa" INT
);

CREATE TABLE "ordenes_detalles" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Orden" INT NOT NULL,
  "Secuencial_Usuario" INT NOT NULL,
  "Secuencial_Proveedor" INT NOT NULL,
  "Secuencial_Producto" INT NOT NULL,
  "Fecha" VARCHAR(255),
  "Codigo" VARCHAR(25),
  "Descripcion" VARCHAR(1000),
  "Cantidad" DOUBLE,
  "Precio" DOUBLE,
  "Tipo" VARCHAR(50),
  "Total" DOUBLE,
  "Secuencial_Empresa" INT
);

CREATE TABLE "productos" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Proveedor" INT NOT NULL,
  "Codigo" VARCHAR(50) NOT NULL,
  "Descripcion" VARCHAR(1000) NOT NULL,
  "Cantidad" DOUBLE NOT NULL,
  "Precio_Costo" DOUBLE NOT NULL,
  "Precio_Venta" DOUBLE NOT NULL,
  "Marca" VARCHAR(100),
  "Codigo_Barra" VARCHAR(50),
  "Codigo_Fabricante" VARCHAR(50),
  "Imagen" BLOB,
  "Fecha_Caducidad" VARCHAR(255),
  "Tipo" VARCHAR(50),
  "Secuencial_Categoria" INT NOT NULL,
  "Expira" BOOLEAN NOT NULL,
  "Existencia_Minima" DOUBLE NOT NULL,
  "Secuencial_Empresa" INT NOT NULL
);

CREATE TABLE "proveedores" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Nombre" VARCHAR(150),
  "Telefono" VARCHAR(20),
  "Direccion" VARCHAR(1000),
  "Email" VARCHAR(150),
  "Contacto" VARCHAR(100),
  "Tipo" VARCHAR(50),
  "Imagen" BLOB,
  "Activo" BOOLEAN,
  "Secuencial_Empresa" INT
);

CREATE TABLE "usuarios" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Codigo" VARCHAR(50) NOT NULL,
  "Nombre" VARCHAR(150),
  "Password" VARCHAR(255) NOT NULL,
  "Imagen" BLOB,
  "Acceso" VARCHAR(50) NOT NULL,
  "Activo" BOOLEAN NOT NULL,
  "Secuencial_Empresa" INT NOT NULL
);

CREATE TABLE "ventas" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Cliente" INT NOT NULL,
  "Secuencial_Usuario" INT NOT NULL,
  "Fecha" VARCHAR(255),
  "Tipo" VARCHAR(50),
  "Forma_Pago" VARCHAR(50),
  "Total" DOUBLE,
  "Otros_Cargos" DOUBLE,
  "Descuento" DOUBLE,
  "Impuesto" DOUBLE,
  "Gran_Total" DOUBLE,
  "Secuencial_Empresa" INT,
  "Documento" BLOB
);

CREATE TABLE "ventas_detalles" (
  "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
  "Secuencial_Factura" INT NOT NULL,
  "Secuencial_Cliente" INT NOT NULL,
  "Secuencial_Producto" INT NOT NULL,
  "Secuencial_Usuario" INT NOT NULL,
  "Fecha" VARCHAR(255),
  "Codigo" VARCHAR(25),
  "Descripcion" VARCHAR(1000),
  "Cantidad" DOUBLE,
  "Precio" DOUBLE,
  "Total" DOUBLE,
  "Tipo" VARCHAR(50),
  "Secuencial_Empresa" INT
);

CREATE TABLE IF NOT EXISTS "cuentas_cobrar" (
    "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
    "Secuencial_Factura" INT,
    "Secuencial_Cliente" INT,
    "Secuencial_Usuario" INT,
    "Fecha" VARCHAR(25),
    "Fecha_Vencimiento" VARCHAR(25),
    "Total" DOUBLE,
    "Saldo" DOUBLE,
    "Pagado" DOUBLE,
    "Descuento" DOUBLE,
    "Impuesto" DOUBLE,
    "Otros_Cargos" DOUBLE,
    "Gran_Total" DOUBLE,
    "Secuencial_Empresa" INT
);

CREATE TABLE IF NOT EXISTS "cuentas_pagar" (
    "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
    "Secuencial_Factura" INT,
    "Secuencial_Proveedor" INT,
    "Secuencial_Usuario" INT,
    "Fecha" VARCHAR(25),
    "Fecha_Vencimiento" VARCHAR(25),
    "Total" DOUBLE,
    "Saldo" DOUBLE,
    "Pagado" DOUBLE,
    "Otros_Cargos" DOUBLE,
    "Descuento" DOUBLE,
    "Impuesto" DOUBLE,
    "Gran_Total" DOUBLE,
    "Secuencial_Empresa" INT
);

CREATE TABLE IF NOT EXISTS "empresas" (
    "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
    "Nombre" VARCHAR(255),
    "Direccion" VARCHAR(255),
    "Telefono" VARCHAR(50),
    "Email" VARCHAR(100),
    "Moneda" VARCHAR(10),
    "ISV" DOUBLE,
    "Activa" BOOLEAN,
    "Imagen" BLOB,
    "Secuencial_Usuario" INT,
    "RSS" TEXT
);

CREATE TABLE IF NOT EXISTS "ingresos" (
    "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
    "Secuencial_Factura" INT,
    "Secuencial_Usuario" INT NOT NULL,
    "Fecha" VARCHAR(25) NOT NULL,
    "Tipo_Ingreso" VARCHAR(20) NOT NULL,
    "Total" DOUBLE NOT NULL,
    "Descripcion" VARCHAR(255) NOT NULL,
    "Secuencial_Empresa" INT NOT NULL
);


CREATE TABLE IF NOT EXISTS "egresos" (
    "Secuencial" INT AUTO_INCREMENT PRIMARY KEY,
    "Secuencial_Factura" INT,
    "Secuencial_Usuario" INT NOT NULL,
    "Fecha" VARCHAR(25) NOT NULL,
    "Tipo_Egreso" VARCHAR(20) NOT NULL,
    "Total" DOUBLE NOT NULL,
    "Descripcion" VARCHAR(255) NOT NULL,
    "Secuencial_Empresa" INT NOT NULL
);

