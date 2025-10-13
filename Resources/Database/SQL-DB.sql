
CREATE TABLE abonos_compras (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_CTAP] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Secuencial_Proveedor] INT NOT NULL,
    [Fecha] NVARCHAR(MAX) NOT NULL,
    [Monto] FLOAT NOT NULL,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Abonos_Compras PRIMARY KEY ([Secuencial])
);

CREATE TABLE abonos_ventas (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_CTAC] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Secuencial_Cliente] INT NOT NULL,
    [Fecha] NVARCHAR(MAX) NOT NULL,
    [Monto] FLOAT NOT NULL,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Abonos_Ventas PRIMARY KEY ([Secuencial])
);

CREATE TABLE actividades (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX) NOT NULL,
    [Descripcion] NVARCHAR(MAX),
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Actividades PRIMARY KEY ([Secuencial])
);

CREATE TABLE categorias (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Nombre] NVARCHAR(MAX),
    [Descripcion] NVARCHAR(MAX),
    [Imagen] VARBINARY(MAX),
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Categorias PRIMARY KEY ([Secuencial])
);

CREATE TABLE clientes (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Codigo] NVARCHAR(MAX),
    [Nombre] NVARCHAR(MAX),
    [Telefono] NVARCHAR(MAX),
    [Direccion] NVARCHAR(MAX),
    [Email] NVARCHAR(MAX),
    [Imagen] VARBINARY(MAX),
    [Activo] BIT,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Clientes PRIMARY KEY ([Secuencial])
);

CREATE TABLE comentarios (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Factura_C] INT NOT NULL,
    [Secuencial_Factura_V] INT NOT NULL,
    [Secuencial_Producto] INT NOT NULL,
    [Secuencial_Cotizacion] INT NOT NULL,
    [Secuencial_Orden] INT NOT NULL,
    [Contenido] NVARCHAR(MAX) NOT NULL,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Comentarios PRIMARY KEY ([Secuencial])
);

CREATE TABLE compras (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Proveedor] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Tipo] NVARCHAR(MAX),
    [Forma_Pago] NVARCHAR(MAX),
    [Total] FLOAT,
    [Otros_Cargos] FLOAT,
    [Descuento] FLOAT,
    [Impuesto] FLOAT,
    [Gran_Total] FLOAT,
    [Secuencial_Empresa] INT NOT NULL,
    [Documento] VARBINARY(MAX),
    CONSTRAINT PK_Compras PRIMARY KEY ([Secuencial])
);

CREATE TABLE compras_detalles (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Factura] INT NOT NULL,
    [Secuencial_Proveedor] INT NOT NULL,
    [Secuencial_Producto] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Codigo] NVARCHAR(MAX),
    [Descripcion] NVARCHAR(MAX),
    [Cantidad] FLOAT,
    [Precio] FLOAT,
    [Total] FLOAT,
    [Tipo] NVARCHAR(MAX),
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Compras_Detalles PRIMARY KEY ([Secuencial])
);

CREATE TABLE cotizaciones (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Cliente] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Total] FLOAT,
    [Otros_Cargos] FLOAT,
    [Descuento] FLOAT,
    [Impuesto] FLOAT,
    [Gran_Total] FLOAT,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Cotizaciones PRIMARY KEY ([Secuencial])
);

CREATE TABLE cotizaciones_detalles (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Cotizacion] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Secuencial_Cliente] INT NOT NULL,
    [Secuencial_Producto] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Codigo] NVARCHAR(MAX),
    [Descripcion] NVARCHAR(MAX),
    [Cantidad] FLOAT,
    [Precio] FLOAT,
    [Tipo] NVARCHAR(MAX),
    [Total] FLOAT,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Cotizaciones_Detalles PRIMARY KEY ([Secuencial])
);

CREATE TABLE kardex (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Fecha] NVARCHAR(MAX) NOT NULL,
    [Secuencial_Producto] INT NOT NULL,
    [Descripcion] NVARCHAR(MAX) NOT NULL,
    [Cantidad] FLOAT NOT NULL,
    [Costo] FLOAT NOT NULL,
    [Costo_Total] FLOAT NOT NULL,
    [Venta] FLOAT NOT NULL,
    [Venta_Total] FLOAT NOT NULL,
    [Saldo] FLOAT NOT NULL,
    [Movimiento] NVARCHAR(MAX) NOT NULL,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Kardex PRIMARY KEY ([Secuencial])
);

CREATE TABLE ordenes (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Proveedor] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Total] FLOAT,
    [Otros_Cargos] FLOAT,
    [Descuento] FLOAT,
    [Impuesto] FLOAT,
    [Gran_Total] FLOAT,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Ordenes PRIMARY KEY ([Secuencial])
);

CREATE TABLE ordenes_detalles (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Orden] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Secuencial_Proveedor] INT NOT NULL,
    [Secuencial_Producto] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Codigo] NVARCHAR(MAX),
    [Descripcion] NVARCHAR(MAX),
    [Cantidad] FLOAT,
    [Precio] FLOAT,
    [Tipo] NVARCHAR(MAX),
    [Total] FLOAT,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Ordenes_Detalles PRIMARY KEY ([Secuencial])
);

CREATE TABLE productos (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Proveedor] INT NOT NULL,
    [Codigo] NVARCHAR(MAX) NOT NULL,
    [Descripcion] NVARCHAR(MAX) NOT NULL,
    [Cantidad] FLOAT NOT NULL,
    [Precio_Costo] FLOAT NOT NULL,
    [Precio_Venta] FLOAT NOT NULL,
    [Marca] NVARCHAR(MAX),
    [Codigo_Barra] NVARCHAR(MAX),
    [Codigo_Fabricante] NVARCHAR(MAX),
    [Imagen] VARBINARY(MAX),
    [Fecha_Caducidad] NVARCHAR(MAX),
    [Tipo] NVARCHAR(MAX),
    [Secuencial_Categoria] INT NOT NULL,
    [Expira] BIT NOT NULL,
    [Existencia_Minima] FLOAT NOT NULL,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Productos PRIMARY KEY ([Secuencial])
);

CREATE TABLE proveedores (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Nombre] NVARCHAR(MAX),
    [Telefono] NVARCHAR(MAX),
    [Direccion] NVARCHAR(MAX),
    [Email] NVARCHAR(MAX),
    [Contacto] NVARCHAR(MAX),
    [Tipo] NVARCHAR(MAX),
    [Imagen] VARBINARY(MAX),
    [Activo] BIT,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Proveedores PRIMARY KEY ([Secuencial])
);

CREATE TABLE usuarios (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Codigo] NVARCHAR(MAX) NOT NULL,
    [Nombre] NVARCHAR(MAX),
    [Password] NVARCHAR(MAX) NOT NULL,
    [Imagen] VARBINARY(MAX),
    [Acceso] NVARCHAR(MAX) NOT NULL,
    [Activo] BIT NOT NULL,
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Usuarios PRIMARY KEY ([Secuencial])
);

CREATE TABLE ventas (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Cliente] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Tipo] NVARCHAR(MAX),
    [Forma_Pago] NVARCHAR(MAX),
    [Total] FLOAT,
    [Otros_Cargos] FLOAT,
    [Descuento] FLOAT,
    [Impuesto] FLOAT,
    [Gran_Total] FLOAT,
    [Secuencial_Empresa] INT NOT NULL,
    [Documento] VARBINARY(MAX),
    CONSTRAINT PK_Ventas PRIMARY KEY ([Secuencial])
);

CREATE TABLE ventas_detalles (
    [Secuencial] INT IDENTITY(1,1) NOT NULL,
    [Secuencial_Factura] INT NOT NULL,
    [Secuencial_Cliente] INT NOT NULL,
    [Secuencial_Producto] INT NOT NULL,
    [Secuencial_Usuario] INT NOT NULL,
    [Fecha] NVARCHAR(MAX),
    [Codigo] NVARCHAR(MAX),
    [Descripcion] NVARCHAR(MAX),
    [Cantidad] FLOAT,
    [Precio] FLOAT,
    [Total] FLOAT,
    [Tipo] NVARCHAR(MAX),
    [Secuencial_Empresa] INT NOT NULL,
    CONSTRAINT PK_Ventas_Detalles PRIMARY KEY ([Secuencial])
);

ALTER DATABASE monitux SET READ_WRITE;

