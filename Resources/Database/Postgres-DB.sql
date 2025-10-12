--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-08-10 20:25:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16406)
-- Name: Abonos_Compras; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Abonos_Compras" (
    "Secuencial" integer NOT NULL,
    "Secuencial_CTAP" integer NOT NULL,
    "Secuencial_Usuario" integer NOT NULL,
    "Secuencial_Proveedor" integer NOT NULL,
    "Fecha" character varying(25) NOT NULL,
    "Monto" double precision NOT NULL,
    "Secuencial_Empresa" integer NOT NULL
);


ALTER TABLE public."Abonos_Compras" OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16405)
-- Name: Abonos_Compras_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Abonos_Compras" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Abonos_Compras_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 221 (class 1259 OID 16411)
-- Name: Abonos_Ventas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Abonos_Ventas" (
    "Secuencial" integer NOT NULL,
    "Secuencial_CTAC" integer NOT NULL,
    "Secuencial_Usuario" integer NOT NULL,
    "Secuencial_Cliente" integer NOT NULL,
    "Fecha" character varying(25) NOT NULL,
    "Monto" double precision NOT NULL,
    "Secuencial_Empresa" integer NOT NULL
);


ALTER TABLE public."Abonos_Ventas" OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 16635)
-- Name: Abonos_Ventas_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Abonos_Ventas" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Abonos_Ventas_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 16416)
-- Name: Actividades; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Actividades" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Usuario" integer NOT NULL,
    "Fecha" character varying(25),
    "Descripcion" text,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Actividades" OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16424)
-- Name: Categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Categorias" (
    "Secuencial" integer NOT NULL,
    "Nombre" character varying(150),
    "Descripcion" text,
    "Secuencial_Empresa" integer NOT NULL,
    "Imagen" bytea
);


ALTER TABLE public."Categorias" OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16432)
-- Name: Clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Clientes" (
    "Secuencial" integer NOT NULL,
    "Codigo" character varying(50),
    "Nombre" character varying(150),
    "Telefono" character varying(20),
    "Direccion" text,
    "Email" character varying(150),
    "Secuencial_Empresa" integer,
    "Activo" boolean,
    "Imagen" bytea
);


ALTER TABLE public."Clientes" OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16439)
-- Name: Clientes_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Clientes" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Clientes_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 228 (class 1259 OID 16440)
-- Name: Comentarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Comentarios" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura_C" integer,
    "Secuencial_Factura_V" integer,
    "Secuencial_Producto" integer,
    "Secuencial_Cotizacion" integer,
    "Secuencial_Orden" integer,
    "Contenido" text,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Comentarios" OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16447)
-- Name: Comentarios_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Comentarios" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Comentarios_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 230 (class 1259 OID 16448)
-- Name: Compras; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Compras" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Proveedor" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Tipo" character varying(50),
    "Forma_Pago" character varying(50),
    "Total" double precision,
    "Otros_Cargos" double precision,
    "Descuento" double precision,
    "Impuesto" double precision,
    "Gran_Total" double precision,
    "Secuencial_Empresa" integer,
    "Documento" bytea
);


ALTER TABLE public."Compras" OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16455)
-- Name: Compras_Detalles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Compras_Detalles" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura" integer,
    "Secuencial_Proveedor" integer,
    "Secuencial_Producto" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Codigo" character varying(25),
    "Descripcion" text,
    "Cantidad" double precision,
    "Precio" double precision,
    "Total" double precision,
    "Secuencial_Empresa" integer,
    "Tipo" character varying(50)
);


ALTER TABLE public."Compras_Detalles" OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16462)
-- Name: Cotizaciones; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cotizaciones" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Cliente" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Total" double precision,
    "Otros_Cargos" double precision,
    "Descuento" double precision,
    "Impuesto" double precision,
    "Gran_Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Cotizaciones" OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 16469)
-- Name: Cotizaciones_Detalles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cotizaciones_Detalles" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Cotizacion" integer,
    "Secuencial_Usuario" integer,
    "Secuencial_Cliente" integer,
    "Secuencial_Producto" integer,
    "Fecha" character varying(25),
    "Codigo" character varying(25),
    "Descripcion" text,
    "Cantidad" double precision,
    "Precio" double precision,
    "Tipo" character varying(50),
    "Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Cotizaciones_Detalles" OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 16477)
-- Name: Cuentas_Cobrar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cuentas_Cobrar" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura" integer,
    "Secuencial_Cliente" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Fecha_Vencimiento" character varying(25),
    "Total" double precision,
    "Saldo" double precision,
    "Pagado" double precision,
    "Otros_Cargos" double precision,
    "Descuento" double precision,
    "Impuesto" double precision,
    "Gran_Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Cuentas_Cobrar" OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 16483)
-- Name: Cuentas_Pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cuentas_Pagar" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura" integer,
    "Secuencial_Proveedor" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Fecha_Vencimiento" character varying(25),
    "Total" double precision,
    "Saldo" double precision,
    "Pagado" double precision,
    "Otros_Cargos" double precision,
    "Descuento" double precision,
    "Impuesto" double precision,
    "Gran_Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Cuentas_Pagar" OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 16487)
-- Name: Egresos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Egresos" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Tipo_Egreso" character varying(50),
    "Descripcion" text,
    "Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Egresos" OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 16495)
-- Name: Empresas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Empresas" (
    "Secuencial" integer NOT NULL,
    "Nombre" character varying(200) NOT NULL,
    "Direccion" text NOT NULL,
    "Telefono" character varying(20),
    "Email" character varying(150),
    "Moneda" character varying(20),
    "ISV" numeric,
    "Imagen" bytea,
    "Secuencial_Usuario" integer,
    "RSS" text,
    "Activa" boolean
);


ALTER TABLE public."Empresas" OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 16503)
-- Name: Ingresos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ingresos" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Tipo_Ingreso" character varying(50),
    "Descripcion" text,
    "Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Ingresos" OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 16509)
-- Name: Kardex; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Kardex" (
    "Secuencial" integer NOT NULL,
    "Fecha" character varying(25),
    "Secuencial_Producto" integer,
    "Descripcion" text,
    "Cantidad" double precision,
    "Costo" double precision,
    "Costo_Total" double precision,
    "Venta" double precision,
    "Venta_Total" double precision,
    "Saldo" double precision,
    "Movimiento" character varying(20),
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Kardex" OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 16517)
-- Name: Ordenes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ordenes" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Proveedor" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Total" double precision,
    "Otros_Cargos" double precision,
    "Descuento" double precision,
    "Impuesto" double precision,
    "Gran_Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Ordenes" OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 16521)
-- Name: Ordenes_Detalles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ordenes_Detalles" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Orden" integer,
    "Secuencial_Usuario" integer,
    "Secuencial_Proveedor" integer,
    "Secuencial_Producto" integer,
    "Fecha" character varying(25),
    "Codigo" character varying(25),
    "Descripcion" text,
    "Cantidad" double precision,
    "Precio" double precision,
    "Tipo" character varying(50),
    "Total" double precision,
    "Secuencial_Empresa" integer
);


ALTER TABLE public."Ordenes_Detalles" OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 16527)
-- Name: Productos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Productos" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Proveedor" integer NOT NULL,
    "Codigo" character varying(25) NOT NULL,
    "Descripcion" text NOT NULL,
    "Cantidad" double precision NOT NULL,
    "Precio_Costo" double precision NOT NULL,
    "Precio_Venta" double precision NOT NULL,
    "Marca" character varying(50),
    "Codigo_Barra" character varying(50),
    "Codigo_Fabricante" character varying(50),
    "Imagen" bytea,
    "Fecha_Caducidad" character varying(25),
    "Tipo" character varying(50),
    "Secuencial_Categoria" integer NOT NULL,
    "Existencia_Minima" double precision NOT NULL,
    "Secuencial_Empresa" integer NOT NULL,
    "Expira" boolean
);


ALTER TABLE public."Productos" OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 16536)
-- Name: Proveedores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Proveedores" (
    "Secuencial" integer NOT NULL,
    "Nombre" character varying(150),
    "Telefono" character varying(25),
    "Direccion" text,
    "Email" character varying(150),
    "Contacto" character varying(50),
    "Tipo" character varying(50),
    "Imagen" bytea,
    "Secuencial_Empresa" integer NOT NULL,
    "Activo" boolean
);


ALTER TABLE public."Proveedores" OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16392)
-- Name: Usuarios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Usuarios" (
    "Secuencial" integer NOT NULL,
    "Codigo" character varying(25),
    "Nombre" character varying(50),
    "Password" text,
    "Acceso" character varying(50),
    "Secuencial_Empresa" integer,
    "Activo" boolean,
    "Imagen" bytea
);


ALTER TABLE public."Usuarios" OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 16544)
-- Name: Ventas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ventas" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Cliente" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Tipo" character varying(25),
    "Forma_Pago" character varying(25),
    "Total" double precision,
    "Otros_Cargos" double precision,
    "Descuento" double precision,
    "Impuesto" double precision,
    "Gran_Total" double precision,
    "Secuencial_Empresa" integer,
    "Documento" bytea
);


ALTER TABLE public."Ventas" OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 16549)
-- Name: Ventas_Detalles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ventas_Detalles" (
    "Secuencial" integer NOT NULL,
    "Secuencial_Factura" integer,
    "Secuencial_Cliente" integer,
    "Secuencial_Producto" integer,
    "Secuencial_Usuario" integer,
    "Fecha" character varying(25),
    "Codigo" character varying(25),
    "Descripcion" text,
    "Cantidad" double precision,
    "Precio" double precision,
    "Total" double precision,
    "Secuencial_Empresa" integer,
    "Tipo" character varying(25)
);


ALTER TABLE public."Ventas_Detalles" OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 16627)
-- Name: Ventas_Detalles_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Ventas_Detalles" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Ventas_Detalles_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 260 (class 1259 OID 16583)
-- Name: Ventas_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Ventas" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Ventas_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 223 (class 1259 OID 16423)
-- Name: actividades_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Actividades" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."actividades_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 225 (class 1259 OID 16431)
-- Name: categorias_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Categorias" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."categorias_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 232 (class 1259 OID 16460)
-- Name: compras_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Compras" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."compras_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 233 (class 1259 OID 16461)
-- Name: compras_detalles_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Compras_Detalles" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."compras_detalles_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 235 (class 1259 OID 16467)
-- Name: cotizaciones_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Cotizaciones" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."cotizaciones_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 237 (class 1259 OID 16476)
-- Name: cotizaciones_detalles_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Cotizaciones_Detalles" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."cotizaciones_detalles_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 239 (class 1259 OID 16482)
-- Name: cuentas_cobrar_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Cuentas_Cobrar" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."cuentas_cobrar_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 241 (class 1259 OID 16486)
-- Name: cuentas_pagar_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Cuentas_Pagar" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."cuentas_pagar_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 243 (class 1259 OID 16494)
-- Name: egresos_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Egresos" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."egresos_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 245 (class 1259 OID 16502)
-- Name: empresas_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Empresas" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."empresas_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 247 (class 1259 OID 16508)
-- Name: ingresos_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Ingresos" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."ingresos_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 249 (class 1259 OID 16516)
-- Name: kardex_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Kardex" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."kardex_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 251 (class 1259 OID 16520)
-- Name: ordenes_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Ordenes" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."ordenes_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 253 (class 1259 OID 16526)
-- Name: ordenes_detalles_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Ordenes_Detalles" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."ordenes_detalles_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 255 (class 1259 OID 16535)
-- Name: productos_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Productos" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."productos_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 257 (class 1259 OID 16543)
-- Name: proveedores_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Proveedores" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."proveedores_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16402)
-- Name: usuarios_Secuencial_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."Usuarios" ALTER COLUMN "Secuencial" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."usuarios_Secuencial_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 4855 (class 2606 OID 16410)
-- Name: Abonos_Compras Abonos_Compras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Abonos_Compras"
    ADD CONSTRAINT "Abonos_Compras_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4863 (class 2606 OID 16438)
-- Name: Clientes Clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Clientes"
    ADD CONSTRAINT "Clientes_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4865 (class 2606 OID 16446)
-- Name: Comentarios Comentarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Comentarios"
    ADD CONSTRAINT "Comentarios_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4869 (class 2606 OID 16631)
-- Name: Compras_Detalles Compras_Detalles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Compras_Detalles"
    ADD CONSTRAINT "Compras_Detalles_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4867 (class 2606 OID 16454)
-- Name: Compras Compras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Compras"
    ADD CONSTRAINT "Compras_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4889 (class 2606 OID 16626)
-- Name: Ventas_Detalles Ventas_Detalles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ventas_Detalles"
    ADD CONSTRAINT "Ventas_Detalles_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4887 (class 2606 OID 16585)
-- Name: Ventas Ventas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ventas"
    ADD CONSTRAINT "Ventas_pkey" PRIMARY KEY ("Secuencial");


--
-- TOC entry 4857 (class 2606 OID 16415)
-- Name: Abonos_Ventas abonos_ventas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Abonos_Ventas"
    ADD CONSTRAINT abonos_ventas_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4859 (class 2606 OID 16422)
-- Name: Actividades actividades_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Actividades"
    ADD CONSTRAINT actividades_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4861 (class 2606 OID 16430)
-- Name: Categorias categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Categorias"
    ADD CONSTRAINT categorias_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4873 (class 2606 OID 16475)
-- Name: Cotizaciones_Detalles cotizaciones_detalles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cotizaciones_Detalles"
    ADD CONSTRAINT cotizaciones_detalles_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4871 (class 2606 OID 16466)
-- Name: Cotizaciones cotizaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cotizaciones"
    ADD CONSTRAINT cotizaciones_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4875 (class 2606 OID 16481)
-- Name: Cuentas_Cobrar cuentas_cobrar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cuentas_Cobrar"
    ADD CONSTRAINT cuentas_cobrar_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4877 (class 2606 OID 16493)
-- Name: Egresos egresos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Egresos"
    ADD CONSTRAINT egresos_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4879 (class 2606 OID 16501)
-- Name: Empresas empresas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Empresas"
    ADD CONSTRAINT empresas_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4881 (class 2606 OID 16515)
-- Name: Kardex kardex_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Kardex"
    ADD CONSTRAINT kardex_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4883 (class 2606 OID 16533)
-- Name: Productos productos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Productos"
    ADD CONSTRAINT productos_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4885 (class 2606 OID 16542)
-- Name: Proveedores proveedores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Proveedores"
    ADD CONSTRAINT proveedores_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 4853 (class 2606 OID 16404)
-- Name: Usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Usuarios"
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY ("Secuencial");


--
-- TOC entry 5040 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE "Usuarios"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public."Usuarios" TO pg_database_owner;


--
-- TOC entry 5041 (class 0 OID 0)
-- Dependencies: 217 5040
-- Name: COLUMN "Usuarios"."Secuencial"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL("Secuencial") ON TABLE public."Usuarios" TO postgres;


--
-- TOC entry 5042 (class 0 OID 0)
-- Dependencies: 217 5040
-- Name: COLUMN "Usuarios"."Codigo"; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL("Codigo") ON TABLE public."Usuarios" TO postgres;


-- Completed on 2025-08-10 20:25:18

--
-- PostgreSQL database dump complete
--

