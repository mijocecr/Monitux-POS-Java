CREATE TABLE public.abonos_compras (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_CTAP" INTEGER NOT NULL,
    "Secuencial_Usuario" INTEGER NOT NULL,
    "Secuencial_Proveedor" INTEGER NOT NULL,
    "Fecha" VARCHAR(25) NOT NULL,
    "Monto" DOUBLE PRECISION NOT NULL,
    "Secuencial_Empresa" INTEGER NOT NULL
);

CREATE TABLE public.abonos_ventas (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_CTAC" INTEGER NOT NULL,
    "Secuencial_Usuario" INTEGER NOT NULL,
    "Secuencial_Cliente" INTEGER NOT NULL,
    "Fecha" VARCHAR(25) NOT NULL,
    "Monto" DOUBLE PRECISION NOT NULL,
    "Secuencial_Empresa" INTEGER NOT NULL
);

CREATE TABLE public.actividades (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Usuario" INTEGER NOT NULL,
    "Fecha" VARCHAR(25),
    "Descripcion" TEXT,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.categorias (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Nombre" VARCHAR(150),
    "Descripcion" TEXT,
    "Secuencial_Empresa" INTEGER NOT NULL,
    "Imagen" BYTEA
);

CREATE TABLE public.clientes (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Codigo" VARCHAR(50),
    "Nombre" VARCHAR(150),
    "Telefono" VARCHAR(20),
    "Direccion" TEXT,
    "Email" VARCHAR(150),
    "Secuencial_Empresa" INTEGER,
    "Activo" BOOLEAN,
    "Imagen" BYTEA
);

CREATE TABLE public.comentarios (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura_C" INTEGER,
    "Secuencial_Factura_V" INTEGER,
    "Secuencial_Producto" INTEGER,
    "Secuencial_Cotizacion" INTEGER,
    "Secuencial_Orden" INTEGER,
    "Contenido" TEXT,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.compras (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Proveedor" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Tipo" VARCHAR(50),
    "Forma_Pago" VARCHAR(50),
    "Total" DOUBLE PRECISION,
    "Otros_Cargos" DOUBLE PRECISION,
    "Descuento" DOUBLE PRECISION,
    "Impuesto" DOUBLE PRECISION,
    "Gran_Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER,
    "Documento" BYTEA
);

CREATE TABLE public.compras_detalles (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura" INTEGER,
    "Secuencial_Proveedor" INTEGER,
    "Secuencial_Producto" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Codigo" VARCHAR(25),
    "Descripcion" TEXT,
    "Cantidad" DOUBLE PRECISION,
    "Precio" DOUBLE PRECISION,
    "Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER,
    "Tipo" VARCHAR(50)
);

CREATE TABLE public.cotizaciones (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Cliente" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Total" DOUBLE PRECISION,
    "Otros_Cargos" DOUBLE PRECISION,
    "Descuento" DOUBLE PRECISION,
    "Impuesto" DOUBLE PRECISION,
    "Gran_Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.cotizaciones_detalles (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Cotizacion" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Secuencial_Cliente" INTEGER,
    "Secuencial_Producto" INTEGER,
    "Fecha" VARCHAR(25),
    "Codigo" VARCHAR(25),
    "Descripcion" TEXT,
    "Cantidad" DOUBLE PRECISION,
    "Precio" DOUBLE PRECISION,
    "Tipo" VARCHAR(50),
    "Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.cuentas_cobrar (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura" INTEGER,
    "Secuencial_Cliente" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Fecha_Vencimiento" VARCHAR(25),
    "Total" DOUBLE PRECISION,
    "Saldo" DOUBLE PRECISION,
    "Pagado" DOUBLE PRECISION,
    "Otros_Cargos" DOUBLE PRECISION,
    "Descuento" DOUBLE PRECISION,
    "Impuesto" DOUBLE PRECISION,
    "Gran_Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.cuentas_pagar (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura" INTEGER,
    "Secuencial_Proveedor" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Fecha_Vencimiento" VARCHAR(25),
    "Total" DOUBLE PRECISION,
    "Saldo" DOUBLE PRECISION,
    "Pagado" DOUBLE PRECISION,
    "Otros_Cargos" DOUBLE PRECISION,
    "Descuento" DOUBLE PRECISION,
    "Impuesto" DOUBLE PRECISION,
    "Gran_Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.egresos (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Tipo_Egreso" VARCHAR(50),
    "Descripcion" TEXT,
    "Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.empresas (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Nombre" VARCHAR(200) NOT NULL,
    "Direccion" TEXT NOT NULL,
    "Telefono" VARCHAR(20),
    "Email" VARCHAR(150),
    "Moneda" VARCHAR(20),
    "ISV" NUMERIC,
    "Imagen" BYTEA,
    "Secuencial_Usuario" INTEGER,
    "RSS" TEXT,
    "Activa" BOOLEAN
);

CREATE TABLE public.ingresos (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Tipo_Ingreso" VARCHAR(50),
    "Descripcion" TEXT,
    "Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.kardex (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Fecha" VARCHAR(25),
    "Secuencial_Producto" INTEGER,
    "Descripcion" TEXT,
    "Cantidad" DOUBLE PRECISION,
    "Costo" DOUBLE PRECISION,
    "Costo_Total" DOUBLE PRECISION,
    "Venta" DOUBLE PRECISION,
    "Venta_Total" DOUBLE PRECISION,
    "Saldo" DOUBLE PRECISION,
    "Movimiento" VARCHAR(20),
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.ordenes (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Proveedor" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Total" DOUBLE PRECISION,
    "Otros_Cargos" DOUBLE PRECISION,
    "Descuento" DOUBLE PRECISION,
    "Impuesto" DOUBLE PRECISION,
    "Gran_Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.ordenes_detalles (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Orden" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Secuencial_Proveedor" INTEGER,
    "Secuencial_Producto" INTEGER,
    "Fecha" VARCHAR(25),
    "Codigo" VARCHAR(25),
    "Descripcion" TEXT,
    "Cantidad" DOUBLE PRECISION,
    "Precio" DOUBLE PRECISION,
    "Tipo" VARCHAR(50),
    "Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER
);

CREATE TABLE public.productos (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Proveedor" INTEGER NOT NULL,
    "Codigo" VARCHAR(25) NOT NULL,
    "Descripcion" TEXT NOT NULL,
    "Cantidad" DOUBLE PRECISION NOT NULL,
    "Precio_Costo" DOUBLE PRECISION NOT NULL,
    "Precio_Venta" DOUBLE PRECISION NOT NULL,
    "Marca" VARCHAR(50),
    "Codigo_Barra" VARCHAR(50),
    "Codigo_Fabricante" VARCHAR(50),
    "Imagen" BYTEA,
    "Fecha_Caducidad" VARCHAR(25),
    "Tipo" VARCHAR(50),
    "Secuencial_Categoria" INTEGER NOT NULL,
    "Existencia_Minima" DOUBLE PRECISION NOT NULL,
    "Secuencial_Empresa" INTEGER NOT NULL,
    "Expira" BOOLEAN
);

CREATE TABLE public.proveedores (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Nombre" VARCHAR(150),
    "Telefono" VARCHAR(25),
    "Direccion" TEXT,
    "Email" VARCHAR(150),
    "Contacto" VARCHAR(50),
    "Tipo" VARCHAR(50),
    "Imagen" BYTEA,
    "Secuencial_Empresa" INTEGER NOT NULL,
    "Activo" BOOLEAN
);

CREATE TABLE public.usuarios (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Codigo" VARCHAR(25),
    "Nombre" VARCHAR(50),
    "Password" TEXT,
    "Acceso" VARCHAR(50),
    "Secuencial_Empresa" INTEGER,
    "Activo" BOOLEAN,
    "Imagen" BYTEA
);

CREATE TABLE public.ventas (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Cliente" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Tipo" VARCHAR(25),
    "Forma_Pago" VARCHAR(25),
    "Total" DOUBLE PRECISION,
    "Otros_Cargos" DOUBLE PRECISION,
    "Descuento" DOUBLE PRECISION,
    "Impuesto" DOUBLE PRECISION,
    "Gran_Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER,
    "Documento" BYTEA
);

CREATE TABLE public.ventas_detalles (
    "Secuencial" INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "Secuencial_Factura" INTEGER,
    "Secuencial_Cliente" INTEGER,
    "Secuencial_Producto" INTEGER,
    "Secuencial_Usuario" INTEGER,
    "Fecha" VARCHAR(25),
    "Codigo" VARCHAR(25),
    "Descripcion" TEXT,
    "Cantidad" DOUBLE PRECISION,
    "Precio" DOUBLE PRECISION,
    "Total" DOUBLE PRECISION,
    "Secuencial_Empresa" INTEGER,
    "Tipo" VARCHAR(25)
);
