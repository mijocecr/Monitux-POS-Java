
CREATE TABLE [dbo].[Abonos_Compras](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_CTAP] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Fecha] [nvarchar](max) NOT NULL,
	[Monto] [float] NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Abonos_Compras] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Abonos_Ventas]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Abonos_Ventas](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_CTAC] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Secuencial_Cliente] [int] NOT NULL,
	[Fecha] [nvarchar](max) NOT NULL,
	[Monto] [float] NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Abonos_Ventas] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Actividades]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Actividades](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NOT NULL,
	[Descripcion] [nvarchar](max) NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Actividades] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categorias]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categorias](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [nvarchar](max) NULL,
	[Descripcion] [nvarchar](max) NULL,
	[Imagen] [varbinary](max) NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Categorias] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Clientes](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Codigo] [nvarchar](max) NULL,
	[Nombre] [nvarchar](max) NULL,
	[Telefono] [nvarchar](max) NULL,
	[Direccion] [nvarchar](max) NULL,
	[Email] [nvarchar](max) NULL,
	[Imagen] [varbinary](max) NULL,
	[Activo] [bit] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Clientes] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Comentarios]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comentarios](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura_C] [int] NOT NULL,
	[Secuencial_Factura_V] [int] NOT NULL,
	[Secuencial_Producto] [int] NOT NULL,
	[Secuencial_Cotizacion] [int] NOT NULL,
	[Secuencial_Orden] [int] NOT NULL,
	[Contenido] [nvarchar](max) NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Comentarios] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Compras]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Compras](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Tipo] [nvarchar](max) NULL,
	[Forma_Pago] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Otros_Cargos] [float] NULL,
	[Descuento] [float] NULL,
	[Impuesto] [float] NULL,
	[Gran_Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
	[Documento] [varbinary](max) NULL,
 CONSTRAINT [PK_Compras] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Compras_Detalles]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Compras_Detalles](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura] [int] NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Secuencial_Producto] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Codigo] [nvarchar](max) NULL,
	[Descripcion] [nvarchar](max) NULL,
	[Cantidad] [float] NULL,
	[Precio] [float] NULL,
	[Total] [float] NULL,
	[Tipo] [nvarchar](max) NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Compras_Detalles] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cotizaciones]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cotizaciones](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Cliente] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Otros_Cargos] [float] NULL,
	[Descuento] [float] NULL,
	[Impuesto] [float] NULL,
	[Gran_Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Cotizaciones] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cotizaciones_Detalles]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cotizaciones_Detalles](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Cotizacion] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Secuencial_Cliente] [int] NOT NULL,
	[Secuencial_Producto] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Codigo] [nvarchar](max) NULL,
	[Descripcion] [nvarchar](max) NULL,
	[Cantidad] [float] NULL,
	[Precio] [float] NULL,
	[Tipo] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Cotizaciones_Detalles] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cuentas_Cobrar]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cuentas_Cobrar](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura] [int] NOT NULL,
	[Secuencial_Cliente] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Fecha_Vencimiento] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Saldo] [float] NULL,
	[Pagado] [float] NULL,
	[Otros_Cargos] [float] NULL,
	[Descuento] [float] NULL,
	[Impuesto] [float] NULL,
	[Gran_Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Cuentas_Cobrar] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cuentas_Pagar]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cuentas_Pagar](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura] [int] NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Fecha_Vencimiento] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Saldo] [float] NULL,
	[Pagado] [float] NULL,
	[Otros_Cargos] [float] NULL,
	[Descuento] [float] NULL,
	[Impuesto] [float] NULL,
	[Gran_Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Cuentas_Pagar] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Egresos]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Egresos](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura] [int] NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NOT NULL,
	[Tipo_Egreso] [nvarchar](max) NOT NULL,
	[Descripcion] [nvarchar](max) NOT NULL,
	[Total] [float] NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Egresos] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Empresas]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Empresas](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [nvarchar](max) NOT NULL,
	[Direccion] [nvarchar](max) NOT NULL,
	[Telefono] [nvarchar](max) NOT NULL,
	[Email] [nvarchar](max) NOT NULL,
	[Moneda] [nvarchar](max) NOT NULL,
	[ISV] [decimal](18, 2) NOT NULL,
	[Activa] [bit] NOT NULL,
	[Imagen] [varbinary](max) NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[RSS] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Empresas] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ingresos]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ingresos](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura] [int] NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NOT NULL,
	[Tipo_Ingreso] [nvarchar](max) NOT NULL,
	[Descripcion] [nvarchar](max) NOT NULL,
	[Total] [float] NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Ingresos] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Kardex]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Kardex](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Fecha] [nvarchar](max) NOT NULL,
	[Secuencial_Producto] [int] NOT NULL,
	[Descripcion] [nvarchar](max) NOT NULL,
	[Cantidad] [float] NOT NULL,
	[Costo] [float] NOT NULL,
	[Costo_Total] [float] NOT NULL,
	[Venta] [float] NOT NULL,
	[Venta_Total] [float] NOT NULL,
	[Saldo] [float] NOT NULL,
	[Movimiento] [nvarchar](max) NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Kardex] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ordenes]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ordenes](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Otros_Cargos] [float] NULL,
	[Descuento] [float] NULL,
	[Impuesto] [float] NULL,
	[Gran_Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Ordenes] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ordenes_Detalles]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ordenes_Detalles](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Orden] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Secuencial_Producto] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Codigo] [nvarchar](max) NULL,
	[Descripcion] [nvarchar](max) NULL,
	[Cantidad] [float] NULL,
	[Precio] [float] NULL,
	[Tipo] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Ordenes_Detalles] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Productos]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Productos](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Proveedor] [int] NOT NULL,
	[Codigo] [nvarchar](max) NOT NULL,
	[Descripcion] [nvarchar](max) NOT NULL,
	[Cantidad] [float] NOT NULL,
	[Precio_Costo] [float] NOT NULL,
	[Precio_Venta] [float] NOT NULL,
	[Marca] [nvarchar](max) NULL,
	[Codigo_Barra] [nvarchar](max) NULL,
	[Codigo_Fabricante] [nvarchar](max) NULL,
	[Imagen] [varbinary](max) NULL,
	[Fecha_Caducidad] [nvarchar](max) NULL,
	[Tipo] [nvarchar](max) NULL,
	[Secuencial_Categoria] [int] NOT NULL,
	[Expira] [bit] NOT NULL,
	[Existencia_Minima] [float] NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Productos] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Proveedores]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Proveedores](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Nombre] [nvarchar](max) NULL,
	[Telefono] [nvarchar](max) NULL,
	[Direccion] [nvarchar](max) NULL,
	[Email] [nvarchar](max) NULL,
	[Contacto] [nvarchar](max) NULL,
	[Tipo] [nvarchar](max) NULL,
	[Imagen] [varbinary](max) NULL,
	[Activo] [bit] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Proveedores] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuarios](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Codigo] [nvarchar](max) NOT NULL,
	[Nombre] [nvarchar](max) NULL,
	[Password] [nvarchar](max) NOT NULL,
	[Imagen] [varbinary](max) NULL,
	[Acceso] [nvarchar](max) NOT NULL,
	[Activo] [bit] NOT NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Usuarios] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ventas]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ventas](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Cliente] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Tipo] [nvarchar](max) NULL,
	[Forma_Pago] [nvarchar](max) NULL,
	[Total] [float] NULL,
	[Otros_Cargos] [float] NULL,
	[Descuento] [float] NULL,
	[Impuesto] [float] NULL,
	[Gran_Total] [float] NULL,
	[Secuencial_Empresa] [int] NOT NULL,
	[Documento] [varbinary](max) NULL,
 CONSTRAINT [PK_Ventas] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ventas_Detalles]    Script Date: 09/08/2025 1:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ventas_Detalles](
	[Secuencial] [int] IDENTITY(1,1) NOT NULL,
	[Secuencial_Factura] [int] NOT NULL,
	[Secuencial_Cliente] [int] NOT NULL,
	[Secuencial_Producto] [int] NOT NULL,
	[Secuencial_Usuario] [int] NOT NULL,
	[Fecha] [nvarchar](max) NULL,
	[Codigo] [nvarchar](max) NULL,
	[Descripcion] [nvarchar](max) NULL,
	[Cantidad] [float] NULL,
	[Precio] [float] NULL,
	[Total] [float] NULL,
	[Tipo] [nvarchar](max) NULL,
	[Secuencial_Empresa] [int] NOT NULL,
 CONSTRAINT [PK_Ventas_Detalles] PRIMARY KEY CLUSTERED 
(
	[Secuencial] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
USE [master]
GO
ALTER DATABASE [monitux] SET  READ_WRITE 
GO
