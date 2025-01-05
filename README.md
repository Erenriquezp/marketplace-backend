# 🛒 Marketplace Backend

Bienvenido al repositorio del backend de **Marketplace**, una aplicación diseñada para la compra, venta y administración de servicios freelance y productos.

## 📋 Descripción del Proyecto
El sistema **Marketplace** permite a los usuarios:
- Publicar y gestionar servicios freelance.
- Comprar productos y servicios.
- Calificar y reseñar productos/servicios.
- Gestionar roles y permisos de usuario.

Este backend está construido en **Spring Boot**, usando las herramientas y dependencias más recientes para ofrecer una arquitectura robusta y escalable.

---

## 🛠️ Tecnologías Utilizadas

- **Java 21**: Lenguaje principal.
- **Spring Boot 3.4.1**: Framework para el backend.
- **Spring Data JPA**: Persistencia de datos.
- **MySQL**: Base de datos relacional.
- **MongoDB**: Base de datos no-relacional.
- **Maven**: Gestión de dependencias.
- **Postman**: Pruebas de los endpoints.
- **Git y GitHub**: Control de versiones.

---

## 📂 Estructura del Proyecto

```plaintext
src/main/java/ec/edu/uce/marketplace
├── config         # Configuración de seguridad y otras propiedades
├── controllers    # Controladores para los endpoints REST
├── dtos           # Objetos de transferencia de datos
├── entities       # Entidades del modelo de datos
├── exceptions     # Manejo de excepciones personalizadas
├── repositories   # Interfaces para la persistencia de datos
├── security       # Configuración de autenticación/autorización
├── services       # Lógica de negocio
├── utils          # Utilidades y herramientas auxiliares
