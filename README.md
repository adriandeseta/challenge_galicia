# Challenge Galicia App

Aplicación Android desarrollada con **Jetpack Compose** y arquitectura **MVVM**, diseñada para mostrar una lista de usuarios, sus detalles y favoritos. La UI utiliza **states** para controlar cambios dinámicos de la interfaz de manera reactiva.

## Estructura del proyecto

```text
com.example.challengegalicia
├── data
│   ├── dao                  # Interfaces DAO de Room
│   ├── database             # Definición de la DB (AppDatabase)
│   ├── local                # Entidades y repositorios locales
│   ├── remote               # Repositorios y servicios remotos (API)
│   └── response             # Modelos de respuesta de la API y mappers
├── di                       # Módulos para inyección de dependencias (Hilt)
├── presentation
│   ├── favorites            # Pantalla y ViewModel de favoritos
│   ├── userdetail           # Pantalla detalle de usuario
│   ├── userlist             # Pantalla lista de usuarios y ViewModel
│   ├── model                # Modelo de dominio (UserModel)
│   ├── ui.theme             # Temas y estilos Compose
│   └── utils                # Clases utilitarias
├── ChallengeGaliciaApplication.kt   # Clase Application
└── MainActivity.kt                  # Activity principal con Compose Navigation
