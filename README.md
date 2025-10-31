# 📦 Proyecto de Gestión de Pedidos CSV (Java)

## Descripción
Este proyecto consiste en una aplicación Java que permite **importar, visualizar y ordenar pedidos** almacenados en un archivo **CSV**.  
El usuario puede seleccionar el archivo desde una interfaz gráfica y gestionar los datos desde un **menú interactivo en consola**.

La aplicación valida que el archivo seleccionado tenga la extensión correcta (`.csv`) y evita la carga de ficheros no válidos.

---

## ⚙️ Funcionalidades principales

- **Selección de archivo CSV** mediante `JFileChooser`.
- **Validación del formato del archivo** (solo se aceptan archivos `.csv`).
- **Lectura e importación** de los registros a objetos `Order`.
- **Menú interactivo** en consola que permite:
  - Mostrar todos los pedidos cargados.
  - Ordenar pedidos por `OrderID`.
  - Cambiar el archivo CSV durante la ejecución.
- **Pruebas unitarias** con JUnit para verificar el funcionamiento de la clase `Order`.

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
