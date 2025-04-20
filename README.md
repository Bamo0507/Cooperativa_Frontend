# Proyecto para CSPI

Este es un proyecto **Kotlin Multiplataforma** que tiene como objetivo funcionar tanto en **Android** como en **iOS** utilizando **JetBrains Compose Multiplatform**.

La estructura del proyecto está pensada para compartir la mayor cantidad posible de código entre plataformas, permitiendo también implementar comportamientos específicos cuando sea necesario.

---

## Estructura del Proyecto

### `/composeApp`
Esta carpeta contiene el código **compartido** de la aplicación. Dentro de la carpeta `src` se encuentran tres subcarpetas importantes:

- `commonMain`: Aquí se encuentra toda la lógica compartida entre Android e iOS. Esto incluye modelos, lógica de negocio y componentes UI hechos con Compose Multiplatform.
- `androidMain`: Código específico para la plataforma **Android**. Aquí se implementa el comportamiento real (`actual`) de las funciones o clases declaradas como `expect` en `commonMain`.
- `iosMain`: Código específico para la plataforma **iOS**, implementando las versiones `actual` de lo declarado en `commonMain`.

Hay ciertos aspectos que por el momento no pueden ser completamente compartidos entre plataformas. Por ejemplo, el manejo del almacenamiento de datos local. En estos casos, se utiliza la palabra clave `expect` en `commonMain` para declarar lo que se espera, y luego se utiliza `actual` en `androidMain` e `iosMain` para definir cómo se implementa en cada plataforma.

---

## Dependencias por Plataforma
En los archivos `build.gradle.kts`, las dependencias se organizan de la siguiente forma:

- Las dependencias específicas de Android se colocan en el bloque correspondiente a Android.
- Las dependencias compartidas se declaran a nivel común y se incluyen tanto en Android como en iOS.

---

## Aplicación iOS (`/iosApp`)
Esta carpeta contiene el **punto de entrada** para la aplicación iOS. A pesar de que se comparte parte de la UI con Compose Multiplatform, este módulo es necesario para:

- Configuración y empaquetado de la aplicación iOS.
- Agregar código nativo en Swift o SwiftUI si es necesario.

---

## Carpeta de Diseño (`/Design`)
En esta carpeta se incluyen dos documentos PDF y un txt con el link a figma:

1. **Tipografía y paleta de colores**: Archivo con toda la información visual definida para la interfaz.
2. **Mockups**: Documento con el enlace de visualización de los mockups.

> ⚠️ Este enlace se irá actualizando para incluir los mockups correspondientes a las pantallas que usarán los **socios**.

---

## Aprende más
Para más información sobre Kotlin Multiplataforma y Compose Multiplatform, visita la [documentación oficial](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).

---
