# language: es
@web @qa09 @pinturas
Característica: Catálogo de Pinturas y Adhesivos
  Antecedentes:
    Dado que ingreso a la página principal de Sodimac Chile
    Y gestiono el aviso de cookies si está visible
    Y selecciono la categoría "Pinturas y Adhesivos" desde el menú principal

  @smoke
  Escenario: Navegar desde menú a pinturas
    Entonces debo visualizar el contexto de "Pinturas y Adhesivos"
    Y debo disponer de enlaces o tarjetas para explorar productos

  @navegacion
  Escenario: Abrir una subcategoría de pintura desde enlaces de menú
    Cuando selecciono una subcategoría visible relacionada con pinturas
    Entonces debo observar el encabezado o breadcrumb de la subcategoría elegida
    Y deben aparecer productos o contenido específico de esa categoría

  @filtros
  Escenario: Filtrar y eliminar la selección de productos de pintura
    Dado que estoy en un listado de pinturas con filtro de marca disponible
    Cuando selecciono una marca que presenta productos
    Entonces la marca debe quedar marcada en el filtro
    Y el listado debe actualizarse para esa selección
    Cuando desmarco la marca desde el mismo control
    Entonces la marca debe dejar de estar seleccionada

  @detalle
  Escenario: Validar correspondencia entre tarjeta y ficha de pintura
    Dado que registro el nombre visible de una pintura en una tarjeta
    Cuando abro su ficha desde el listado
    Entonces debo visualizar el mismo nombre en la ficha
    Y debo encontrar una imagen del producto
    Y el precio debe estar visible cuando exista oferta comercial disponible