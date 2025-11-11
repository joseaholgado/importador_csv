# 📦 Proyecto de Gestión de Pedidos CSV (Java)

## Descripción
Este proyecto consiste en una aplicación Java que permite **importar, visualizar y ordenar pedidos** almacenados en un archivo **CSV**.  
El usuario puede seleccionar el archivo desde una interfaz gráfica y gestionar los datos desde un **menú interactivo en consola**.

La aplicación valida que el archivo seleccionado tenga la extensión correcta (`.csv`) y evita la carga de ficheros no válidos.  
Además, los pedidos importados se almacenan en una **base de datos relacional SQLite** mediante **JDBC**, desde donde se genera un **resumen** y un **nuevo fichero CSV ordenado**.

---

## ⚙️ Funcionalidades principales

- **Selección de archivo CSV** mediante `JFileChooser`.
- **Validación del formato del archivo** (solo se aceptan archivos `.csv`).
- **Lectura e importación** de los registros a objetos `Order`.
- **Almacenamiento de los pedidos** en una base de datos SQLite mediante JDBC.
- **Limpieza de la tabla** antes de cada importación para evitar duplicados.
- **Generación de un resumen** del número de pedidos agrupados por:
    - Region
    - Country
    - Item Type
    - Sales Channel
    - Order Priority
- **Exportación a un nuevo archivo CSV** con:
    - Registros ordenados por `Order ID`.
    - Fechas con formato `dd/MM/yyyy`.
    - Columnas en el orden especificado en el enunciado.
- **Menú interactivo en consola** que permite:
    - Mostrar todos los pedidos cargados.
    - Ordenar pedidos por `OrderID`.
    - Cambiar el archivo CSV durante la ejecución.
    - Mostrar resumen desde la base de datos.
    - Exportar el CSV final ordenado.
- **Pruebas unitarias con JUnit** para verificar el funcionamiento de la clase `Order`.
---

## 🧱 Estructura del proyecto

```text
src/
├── app/
│   └── Main.java
│
├── clases/
│   ├── Menu.java
│   ├── Order.java
│   └── OrderImporter.java
│
└── clasesTest/
    └── OrderTest.java
