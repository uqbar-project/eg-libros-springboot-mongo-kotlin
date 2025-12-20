# Ejemplo Libros con Springboot y MongoDB

[![build](https://github.com/uqbar-project/eg-libros-springboot-mongo-kotlin/actions/workflows/build.yml/badge.svg)](https://github.com/uqbar-project/eg-libros-springboot-mongo-kotlin/actions/workflows/build.yml) [![codecov](https://codecov.io/gh/uqbar-project/eg-libros-springboot-mongo-kotlin/branch/master/graph/badge.svg?token=KHXEZluyIv)](https://codecov.io/gh/uqbar-project/eg-libros-springboot-mongo-kotlin)

## Instalación del entorno Mongo

Solo hace falta tener instalado Docker Desktop (el pack docker engine y docker compose), seguí las instrucciones de [esta página](https://phm.uqbar-project.org/material/software) en el párrafo `Docker`.

```bash
docker compose up
```

Eso te levanta una base documental MongoDB en el puerto 27019, con usuario capo y contraseña eyra. El string de conexión es

```bash
mongodb://capo:eyra@127.0.0.1:27019/libros?authSource=admin
```

## Apunte completo

La explicación detallada está en [este apunte](https://docs.google.com/document/d/1kLAsruPYKZBNB0zi40_ORYavt_daQzEpaz2tf6pB6zw/edit#)

## Cómo testear la aplicación en Insomnia

Te dejamos [el archivo de Insomnia](./Libros_Insomnia.json) con ejemplos para probarlo.
