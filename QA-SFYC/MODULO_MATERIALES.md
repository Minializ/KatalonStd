# Módulo de MATERIALES - Descripción Funcional

## 1. Descripción General

El módulo de **Materiales** gestiona el ciclo de vida completo del material de campo: desde la requisición, entrega a cuadrillas, devolución, liquidación, aclaraciones, recuperación, hasta catálogos de configuración y reportes. Está dividido en dos capas:

- **materials-ui** — Capa de presentación (componentes Angular, rutas lazy-loaded)
- **materials-data** — Capa de datos (servicios HTTP, modelos, NgRx Store, guards de acceso)

Utiliza **DevExtreme** para la UI, **NgRx** para estado global, **@ngx-translate** para internacionalización, y registra eventos de auditoría mediante `LogbookEventService`.

---

## 2. Ciclo de Vida de un Material

```
[Catálogo de materiales]
         ↓
[Requisición (RQ)] → Solicitud de material para cuadrilla
         ↓
[Entrega (EN)] → Material sale del almacén a cuadrilla
         ↓
[Trabajo en campo] → Cuadrilla utiliza material en Órdenes de Trabajo
         ↓
[Devolución (DE)] → Material sobrante regresa al almacén
   [Aclaración (AC)] → Corrección de diferencias
         ↓
[Liquidación (LQ)] → Cierre del ciclo: consolida EN + DE + AC
         ↓
[Recuperación] → Material retirado de instalaciones regresa
         ↓
[Traspaso (TP)] → Redistribución entre almacenes/cuadrillas
         ↓
[Reportes] → Verificadores, acumulados, prorrateos, resúmenes
```

---

## 3. Estructura de Rutas y Guards de Acceso

| Ruta | Función | Guard de Acceso |
|------|---------|-----------------|
| `/` | Pantalla de inicio (MainContainer) | — |
| `/requisitions` | Requisiciones de material | `RequisitionMaterialGuard` |
| `/liquidation-material` | Liquidación de material | `LiquidationMaterialGuard` |
| `/return-material` | Devolución de material | `ReturnMaterialGuard` |
| `/delivery-material` | Entrega de material | `DeliveryMaterialGuard` |
| `/clarification-material` | Aclaración de material | `ClarificationMaterialGuard` |
| `/move-stock` | Traspaso de inventario | — |
| `/work-orders` | Órdenes de trabajo | — |
| `/reports` | Reportes | — |
| `/materials-catalog` | Catálogo de materiales | `CatalogMaterialGuard` |
| `/materials-crew-catalog` | Reporte de material por cuadrilla | — |
| `/maintenance-of-movement-subtype-catalog` | Catálogo de subtipos de movimiento | — |
| `/orders` | Consulta de órdenes | — |
| `/crew-catalog` | Catálogo de cuadrillas | — |
| `/materials-description` | Descripción de materiales | — |
| `/tecnico-catalog` | Catálogo técnico | — |
| `/query-contracts` | Consulta de contratos | — |
| `/materials-recovery-catalog` | Catálogo de recuperación de materiales | — |
| `/material-recovery` | Recuperación de material | — |

Cada guard controla el acceso basado en los roles del usuario autenticado (OpenID Connect).

---

## 4. Tipos de Movimiento de Material

| Código | Tipo de Movimiento |
|--------|--------------------|
| `RQ` | Requisiciones |
| `EN` | Entregas |
| `DE` | Devoluciones |
| `LQ` | Liquidaciones |
| `TP` | Traspasos |
| `AC` | Aclaraciones |

---

## 5. Funciones y Subfunciones del Módulo

---

### 5.1 Pantalla Principal (MainContainer)

**Ruta:** `/`

**Descripción:** Punto de entrada del módulo. Muestra el logo de la aplicación. Verifica que exista una sucursal seleccionada; si no la hay, muestra un popup de error y ejecuta el logout.

**Subfunciones:**
1. Verificar sucursal seleccionada al iniciar.
2. Mostrar indicador de carga.
3. Mostrar popup de error si no hay sucursal.
4. Redirigir al logout si no hay sucursal.

---

### 5.2 Requisiciones de Material

**Ruta:** `/requisitions`

**Descripción:** Gestión de requisiciones de material para cuadrillas. Permite solicitar materiales, asociarlos a equipos y limpiar stock negativo.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppRequisitionContainerComponent` | Contenedor principal | Orquesta formulario + grids + botones |
| `AppRequisitionFormComponent` | Formulario | Captura folio de requisición, sucursal, parámetros |
| `AppRequisitionGridComponent` | Grid | Lista de requisiciones existentes |
| `AppRequisitionMaterialGridComponent` | Grid | Materiales del folio de requisición |
| `AppRequisitionEquipmentGridComponent` | Grid | Equipos asociados a la requisición |
| `AppCleanStockFormComponent` | Formulario popup | Limpieza de stock negativo/positivo |

#### Flujo paso a paso:
1. El usuario selecciona la sucursal (automático por el store).
2. En el formulario selecciona tipo de movimiento: **por cuadrilla** u otras opciones.
3. Busca requisiciones existentes en el grid.
4. Selecciona un folio → se cargan materiales asociados en el grid de materiales.
5. Opcionalmente consulta equipos asociados en el grid de equipos.
6. Si hay stock negativo, se habilita el popup de limpieza de stock.
7. Puede imprimir la requisición.

#### Botones de acción:
- **Buscar** — Busca requisiciones existentes
- **Nuevo** — Crea una nueva requisición
- **Imprimir** — Genera reporte de la requisición
- **Cancelar** — Cancela la operación
- **Limpiar stock** — Abre popup para limpiar stock negativo

#### Endpoints API:
- `POST /GetMaterialEquipments` — Obtener equipos de material
- `POST /GetMaterialEquipmentsByFolio` — Obtener equipos por folio
- OData CRUD para requisiciones

---

### 5.3 Entrega de Material

**Ruta:** `/delivery-material`

**Descripción:** Gestión de entregas de material desde el almacén a cuadrillas. Normalmente se origina desde una requisición aprobada.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppDeliveryMaterialContainerComponent` | Contenedor | Orquesta todo el proceso de entrega |
| `AppDeliveryMaterialFormComponent` | Formulario | Folio de entrega, cuadrilla, fecha |
| `AppDeliveryMaterialGridComponent` | Grid | Materiales a entregar con cantidades |
| `AppQueryMaterialMovementMainComponent` | Popup | Consulta de movimientos previos |
| `AppRequisitionContainerComponent` | Popup | Requisición asociada a la entrega |

#### Flujo paso a paso:
1. Consultar folio de requisición origen.
2. Revisar y ajustar cantidades en el grid de materiales.
3. Confirmar entrega → se ejecuta el proceso de entrega.
4. Imprimir comprobante de entrega.
5. Opcional: eliminar entrega (requiere permisos de administrador).

#### Validaciones:
- Verifica si el folio ya tiene liquidación (`folioLiqMat === 0`) para habilitar edición.
- `allowDynamicsMovements` — permite movimientos dinámicos según preferencias.

#### Endpoints API:
- `POST /GetMaterialsByRequisitionFolio` — Obtener materiales por folio de requisición
- `POST /UpdateDeliveryMaterialProcess` — Ejecutar proceso de entrega

---

### 5.4 Devolución de Material

**Ruta:** `/return-material`

**Descripción:** Registro de devoluciones de material de cuadrillas al almacén. Permite generar folios de devolución y eliminar folios previos.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppReturnMaterialMainContainerComponent` | Contenedor | Orquesta formulario + grid + popups |
| `AppReturnMaterialFormComponent` | Formulario | Datos del folio de devolución (cuadrilla, materiales) |
| `AppReturnMaterialGridComponent` | Grid | Lista de materiales a devolver |
| `AppQueryMaterialMovementMainComponent` | Popup | Consulta de movimientos de material previos |
| `AppDeleteReturnFoliosMainComponent` | Popup | Eliminación de folios de devolución |

#### Flujo paso a paso:
1. Seleccionar cuadrilla y fecha.
2. Visualizar materiales sugeridos a devolver.
3. Ajustar cantidades en el grid.
4. Ejecutar devolución → genera folio de devolución.
5. Imprimir comprobante.
6. Opcional: consultar movimientos previos o eliminar folios erróneos.

#### Atajos de teclado:
- `Alt+1` — Consultar
- `Alt+2` — Eliminar
- `Alt+3` — Imprimir
- `Alt+4` — Eliminar folios

#### Endpoints API:
- `POST /processWeb/MaterialReturns` — Generar proceso de devolución
- `POST /processWeb/DeleteMaterialReturn` — Eliminar devolución

---

### 5.5 Aclaración de Material

**Ruta:** `/clarification-material`

**Descripción:** Proceso para corregir diferencias entre entregas y devoluciones de material de cuadrillas.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppClarificationMaterialMainContainerComponent` | Contenedor | Orquesta formulario + grid |
| `AppClarificationMaterialFormComponent` | Formulario | Datos del folio de aclaración |
| `AppClarificationMaterialGridComponent` | Grid | Materiales a aclarar con cantidades |
| `AppClarificationWorkOrderComponent` | Sub-componente | Órdenes de trabajo relacionadas |
| `AppQueryMaterialMovementMainComponent` | Popup | Consulta de movimientos previos |

#### Flujo paso a paso:
1. Buscar cuadrilla con diferencias en movimientos.
2. Visualizar materiales en discrepancia.
3. Capturar justificación y cantidades correctas.
4. Generar folio de aclaración.

#### Endpoints API:
- `POST /processWeb/ClarificationMaterials` — Generar proceso de aclaración

---

### 5.6 Liquidación de Material

**Ruta:** `/liquidation-material`

**Descripción:** Proceso de liquidación de material de cuadrillas. Consolida entregas, devoluciones, aclaraciones y órdenes de trabajo para generar un folio de liquidación.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppLiquidationMaterialMainContainerComponent` | Contenedor | Orquesta todo el proceso de liquidación |
| `AppLiquidationMaterialFormComponent` | Formulario | Datos del folio y parámetros de liquidación |
| `AppLiquidationMaterialCrewGridComponent` | Grid | Cuadrillas a liquidar |
| `AppMaterialCrewGridComponent` (×4) | Grids | Entregas, Devoluciones, Requisiciones y Aclaraciones por cuadrilla |
| `AppMatOrdWorkGridComponent` | Grid | Órdenes de trabajo y materiales asociados |
| `AppQueryLiquidationMaterialMainComponent` | Popup | Consulta de liquidaciones previas |
| `AppLiquidationRangeMaterialMainComponent` | Popup | Liquidación por rango de fechas |
| `AppCancelFoliosMainComponent` | Popup | Cancelación de folios de liquidación |

#### Flujo paso a paso:
1. Seleccionar cuadrilla(s) a liquidar en el formulario.
2. Consultar entregas, devoluciones y aclaraciones pendientes por cuadrilla (4 grids).
3. Revisar órdenes de trabajo relacionadas.
4. Ejecutar el proceso de liquidación.
5. Consultar liquidaciones existentes por folio o por rango de fechas.
6. Cancelar folios si es necesario.

#### Atajos de teclado:
- `Alt+1` — Buscar
- `Alt+2` — Generar liquidación

#### Endpoints API:
- `POST /processWeb/LiquidationMaterials` — Ejecutar proceso de liquidación
- `POST /processWeb/CancelLiquidationMaterials` — Cancelar liquidación

---

### 5.7 Traspaso de Inventario (Move Stock)

**Ruta:** `/move-stock`

**Descripción:** Transferencia o prorrateo de materiales entre almacenes o cuadrillas.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMoveStockMainContainerComponent` | Contenedor | Orquesta formulario + grid + confirmación |
| `AppMoveStockFormComponent` | Formulario | Rango de fechas, almacén origen |
| `AppMoveStockGridComponent` | Grid | Movimientos pendientes de traspaso |

#### Flujo paso a paso:
1. Seleccionar rango de fechas y almacén.
2. Visualizar movimientos pendientes en el grid.
3. Confirmar traspaso → se ejecuta el prorrateo.
4. Guardar traspaso.

#### Atajos de teclado:
- `Alt+I` — Imprimir
- `Alt+N` — Iniciar traspaso

#### Endpoints API:
- `POST /processWeb/GenerateProrationMaterial` — Generar prorrateo de material
- `POST /processWeb/SaveInventoryTransfer` — Guardar traspaso de inventario

---

### 5.8 Órdenes de Trabajo

**Ruta:** `/work-orders`

**Descripción:** Consulta y gestión de órdenes de trabajo de campo. Permite ver datos generales, equipos, visitas, materiales utilizados y atender la orden.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppWorkOrdersMainContainerComponent` | Contenedor | Datos generales de la OT |
| `AppWorkOrdersFormComponent` | Formulario | Búsqueda por folio de trabajo |

#### Sub-módulos (Tabs):
| Sub-módulo | Función |
|-----------|---------|
| `general/` | Formulario general de la OT y grid de llamadas |
| `equipments/` | Equipos asociados (grid + formulario de edición) |
| `visits/` | Grid de visitas realizadas |
| `material-work-order/` | Materiales utilizados en la OT (grid + detalle de contrato) |
| `attention/` | Atención de la orden de trabajo |
| `bonus/` | Bonos relacionados |
| `assignation/` | Asignación de la OT a técnicos/cuadrillas |
| `works/` | Trabajos realizados (grid + formulario) |

#### Flujo paso a paso:
1. Capturar folio de trabajo en el formulario.
2. Revisar tabs: **General**, **Equipos**, **Visitas**, **Materiales**, **Atención**, **Bonos**, **Asignación**, **Trabajos**.
3. Crear nueva OT si es necesario.
4. Procesos especiales: cambio de tecnología, migración a fibra.

#### Endpoints API:
- `POST /oDataUrisWorks.processWeb/GetGeneralData` — Obtener datos generales de la OT
- `POST /InsertWorkOrder` — Crear nueva orden de trabajo
- `POST /GetActiveEquipmentDetails` — Obtener equipos activos
- `POST /TechnologyChangeInInstallation` — Cambio de tecnología
- `POST /MigrateToFiberProcess` — Migración a fibra

---

### 5.9 Consulta de Órdenes

**Ruta:** `/orders`

**Descripción:** Consulta de órdenes de trabajo en grid, con visualización de detalle en popup.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppOrdersMainContainerComponent` | Contenedor | Orquesta grid y popup |
| `AppOrdersWorksGridComponent` | Grid | Lista de órdenes de trabajo |
| `AppOrdersDataSubscriberFormComponent` | Formulario | Datos del suscriptor de la orden |
| `AppOrdersDetailsGridComponent` | Grid | Detalle de una orden específica |
| `AppOrderWorkPopupMainComponent` | Popup | Vista completa del detalle de la OT |

#### Flujo paso a paso:
1. Buscar órdenes en el grid principal.
2. Hacer clic en una orden → se abre el popup con detalle completo.
3. Ver datos del suscriptor, detalles y acciones disponibles.

---

### 5.10 Reportes

**Ruta:** `/reports`

**Descripción:** Centro de reportes del módulo de materiales. Permite generar distintos reportes mediante un selector dinámico.

#### Componentes:
| Componente | Descripción |
|-----------|------------|
| `AppReportingMainContainerComponent` | Contenedor principal con dropdown de tipo de reporte |
| `AppVerificatorDevolutionsFormComponent` | Verificador de Entregas, Devoluciones, Liquidaciones, Diferencias |
| `AppAccumulatedLiquidationsFormComponent` | Reporte de Liquidaciones Acumuladas |
| `AppUsedMaterialSummaryFormComponent` | Resumen de Material Utilizado, Auxiliar, Prorrateo por Trabajo, Prorrateo por Material |
| `AppSeriesDeliveredByCrewReportFormComponent` | Series Entregadas por Cuadrilla |
| `AppReportMaterialRecoveryContainerComponent` | Reporte de Recuperación de Material |
| `AppMaterialStockByCrewReportContainerComponent` | Reporte de Stock de Material por Cuadrilla |

#### Tipos de reportes disponibles:
1. **Verificador de Entregas** — Valida entregas realizadas
2. **Verificador de Devoluciones** — Valida devoluciones realizadas
3. **Verificador de Liquidaciones** — Valida liquidaciones generadas
4. **Verificador de Diferencias** — Muestra diferencias entre movimientos
5. **Liquidaciones Acumuladas** — Resumen acumulado de liquidaciones
6. **Resumen de Material Utilizado** — Material utilizado por material
7. **Auxiliar de Material Utilizado** — Detalle auxiliar de uso
8. **Prorrateo de Material por Trabajo** — Distribución de material por OT
9. **Prorrateo de Material por Material** — Distribución por tipo de material
10. **Series Entregadas por Cuadrilla** — Series de materiales entregados
11. **Reporte de Material por Cuadrilla** — Inventario por cuadrilla
12. **Reporte de Stock por Cuadrilla** — Stock actual por cuadrilla

#### Flujo paso a paso:
1. Seleccionar tipo de reporte en el dropdown.
2. Completar parámetros según el tipo de reporte (fechas, cuadrilla, sucursal, etc.).
3. Generar reporte → se abre en nueva pestaña del navegador.

**Mecanismo:** El servicio de reportes abre una nueva pestaña apuntando al servidor de reportes con los parámetros en la URL y el token de acceso.

---

### 5.11 Catálogo de Materiales

**Ruta:** `/materials-catalog`

**Descripción:** Alta, Baja y Modificación (ABM) del catálogo de materiales. Permite gestionar plantillas de materiales y configurar máximos/mínimos de stock.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMaterialsCatalogContainerComponent` | Contenedor | Orquesta grid + formulario + popup de plantillas |
| `AppMaterialsCatalogGridComponent` | Grid OData | Lista de materiales del catálogo |
| `AppMaterialsCatalogFormComponent` | Formulario popup | Alta/edición de un material |

#### Subfunciones:
1. **Gestión de materiales** — CRUD de materiales del catálogo
2. **Plantillas de material** — Crear/aplicar plantillas que cargan un conjunto predefinido de materiales
3. **Máximos y mínimos** — Configurar límites de stock por material

#### Control de acceso por roles:
- `stockMaterialCatalog` — Permiso para ver stock
- `stockMaterialApplyTemplateCatalog` — Permiso para aplicar plantillas

---

### 5.12 Catálogo de Cuadrillas

**Ruta:** `/crew-catalog`

**Descripción:** ABM de cuadrillas de campo. Gestiona la composición, almacén y técnicos de cada cuadrilla.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppCrewCatalogContainerComponent` | Contenedor | Orquesta grid + formulario de inserción |
| `AppCrewCatalogGridComponent` | Grid | Lista de cuadrillas con filtros |
| `AppCrewCatalogInsertFormComponent` | Formulario popup | Alta de nueva cuadrilla |
| `AppCrewCatalogFormComponent` | Formulario | Edición de cuadrilla existente |
| `AppCrewCatalogDetailGridComponent` | Grid | Detalle de miembros de la cuadrilla |

#### Flujo paso a paso:
1. Filtrar cuadrillas por tipo, sucursal.
2. Crear nueva cuadrilla → capturar datos y guardar.
3. Editar cuadrilla existente → modificar datos y actualizar.
4. Eliminar cuadrilla si ya no es necesaria.

#### Control de acceso:
- Rol `smnuModificaAlmacenCuadrilla` — Controla si puede modificar el almacén de la cuadrilla

#### Endpoints API:
- `POST /processWeb/SaveCrew` — Crear cuadrilla
- `POST /processWeb/UpdateCrew` — Actualizar cuadrilla
- `POST /processWeb/DeleteCrew` — Eliminar cuadrilla

---

### 5.13 Reporte de Material por Cuadrilla

**Ruta:** `/materials-crew-catalog`

**Descripción:** Reporte que muestra el inventario de materiales asignados a cada cuadrilla.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMaterialCrewReportContainerComponent` | Contenedor | Orquesta formulario + grid de resultados |
| `AppMaterialCrewReportFormComponent` | Formulario | Parámetros: tipo cuadrilla, cuadrilla, materiales |
| `AppMaterialCrewReportGridComponent` | Grid | Resultados del reporte |

#### Flujo paso a paso:
1. Seleccionar tipo de cuadrilla y cuadrilla específica.
2. Filtrar por materiales si es necesario.
3. Ejecutar búsqueda → se muestra el inventario en el grid.

#### Endpoints API:
- `POST /processWeb/MaterialCrewReport` — Generar reporte de material por cuadrilla

---

### 5.14 Catálogo de Subtipos de Movimiento

**Ruta:** `/maintenance-of-movement-subtype-catalog`

**Descripción:** Configuración de los subtipos de movimiento de material (tipos de motivo para los distintos movimientos).

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMaintenanceOfMovementSubtypeCatalogContainerComponent` | Contenedor | Carga y gestiona los subtipos |
| `AppMaintenanceOfMovementSubtypeCatalogGridComponent` | Grid | Lista de subtipos |
| `AppMaintenanceOfMovementSubtypesCatalogFormComponent` | Formulario | Alta/edición de un subtipo |

#### Flujo paso a paso:
1. Consultar subtipos de movimiento existentes.
2. Crear nuevo subtipo con descripción y tipo de movimiento asociado.
3. Editar subtipo existente.

#### Endpoints API:
- `GET /processWeb/GetTypeMovementsProcess` — Obtener subtipos de movimiento
- `POST /processWeb/SaveTypeMovement` — Guardar subtipo

---

### 5.15 Catálogo Técnico

**Ruta:** `/tecnico-catalog`

**Descripción:** Catálogo de técnicos y sus movimientos históricos. Permite ver el historial de movimientos de inventario por técnico.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppTechnicalCatalogContainerComponent` | Contenedor | Carga técnicos y gestiona popups |
| `AppTechnicalCatalogGridComponent` | Grid | Lista de técnicos |
| `AppTechnicalCatalogFormComponent` | Formulario popup | Edición de técnico (estado, cuadrilla) |
| `AppTechnicalCatalogMovementsGridComponent` | Grid popup | Movimientos históricos del técnico |
| `AppRadioOperatorsMulticheckGridComponent` | Grid | Selección múltiple de radio-operadores |

#### Flujo paso a paso:
1. Consultar lista de técnicos por sucursal.
2. Seleccionar técnico → ver detalles.
3. Editar técnico (cambiar estado, cuadrilla).
4. Consultar movimientos históricos del técnico.

#### Endpoints API:
- `GET /oDataUrisUsers.processWeb/Technicians/{branchId}` — Obtener técnicos por sucursal
- `POST /processWeb/MovementTechnicalCatalog` — Obtener movimientos del técnico
- `POST /processWeb/UpdateTechnicalCatalog` — Actualizar técnico

---

### 5.16 Descripción de Materiales

**Ruta:** `/materials-description`

**Descripción:** Catálogo de descripciones de materiales. Permite crear y editar las descripciones textuales del catálogo maestro.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMaterialsDescriptionCatalogContainerComponent` | Contenedor | Gestiona grid + popup de edición |
| `AppMaterialsDescriptionCatalogGridComponent` | Grid OData | Lista de descripciones |
| `AppMaterialsDescriptionCatalogFormComponent` | Formulario popup | Alta/edición de descripción |

#### Flujo paso a paso:
1. Consultar descripciones de materiales existentes en el grid.
2. Crear nueva descripción de material.
3. Editar descripción existente.

#### Endpoints API:
- `GET /processWeb/GetCatalogMaterialsDescription` — Obtener descripciones
- `POST /processWeb/InsertNewMaterialCatalogMaterialsDescription` — Crear descripción
- `PATCH /processWeb/UpdateCatalogMaterialsDescription` — Actualizar descripción

---

### 5.17 Catálogo de Recuperación de Materiales

**Ruta:** `/materials-recovery-catalog`

**Descripción:** Configuración de los materiales elegibles para recuperación (materiales que se pueden recuperar de instalaciones al retirarlos).

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMaterialsRecoveryCatalogContainerComponent` | Contenedor | Gestiona grid + formulario |
| `AppMaterialsRecoveryCatalogGridComponent` | Grid | Lista de configuraciones |
| `AppMaterialsRecoveryCatalogFormComponent` | Formulario | Alta de nueva configuración |

#### Flujo paso a paso:
1. Consultar configuraciones de recuperación existentes.
2. Crear nueva configuración indicando qué materiales son recuperables.
3. Editar configuraciones existentes.

#### Endpoints API:
- `GET /processWeb/GetMaterialsRecoveryCatalog` — Obtener configuraciones
- `POST /processWeb/SaveMaterialsRecoveryCatalog` — Guardar configuración

---

### 5.18 Recuperación de Material

**Ruta:** `/material-recovery`

**Descripción:** Proceso de recuperación de material de campo (materiales retirados de instalaciones que regresan al inventario).

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppMaterialRecoveryContainerComponent` | Contenedor | Orquesta todo el proceso de recuperación |
| `AppMaterialRecoveryFormComponent` | Formulario | Parámetros: cuadrilla, fecha, tipo |
| `AppMaterialRecoveryGridComponent` | Grid | Folios de recuperación existentes |
| `AppMaterialRecoverySuggestedGridComponent` | Grid | Materiales sugeridos para recuperar |
| `AppRecoveredMaterialGridComponent` | Grid | Materiales ya recuperados |
| `AppMaterialsRecoveryPopupComponent` | Popup | Vista de movimientos de recuperación por folio |

#### Flujo paso a paso:
1. Seleccionar cuadrilla, fecha y tipo de recuperación.
2. Consultar folios de recuperación existentes.
3. Ver materiales sugeridos para recuperar.
4. Confirmar materiales recuperados.
5. Generar folio de recuperación.
6. Opcional: eliminar folio de recuperación.

#### Endpoints API:
- `POST /processWeb/GetMaterialRecoveryFoliosByCrew` — Obtener folios por cuadrilla
- `POST /processWeb/GetMaterialRecoveryByFolio` — Obtener recuperación por folio
- `DELETE /processWeb/DeleteMaterialRecoveryByFolio` — Eliminar folio de recuperación
- `POST /processWeb/GetMaterialRecoverySuggested` — Obtener materiales sugeridos

---

### 5.19 Consulta de Contratos

**Ruta:** `/query-contracts`

**Descripción:** Consulta de información contractual de suscriptores (clientes). Muestra datos del contrato, dirección y estado del servicio.

#### Componentes:
| Componente | Tipo | Función |
|-----------|------|---------|
| `AppQueryContractsContainerComponent` | Contenedor | Orquesta formulario + dirección + grid |
| `AppQueryContractsFormComponent` | Formulario | Búsqueda por número de suscriptor |
| `AppQueryContractsAddressFormComponent` | Formulario | Datos de dirección del suscriptor |
| `AppQueryContractsGridComponent` | Grid | Contratos activos del suscriptor |

#### Flujo paso a paso:
1. Ingresar número de suscriptor en el formulario.
2. Se consultan contratos, dirección y estado.
3. Se muestran contratos activos en el grid.
4. Se muestran datos de dirección del suscriptor.

#### Endpoints API:
- `POST /oDataUrisContracts.processWeb/GetQueryContracts` — Consultar contratos

---

## 6. Componentes Compartidos (Dropdowns y Select Boxes)

### Dropdowns reutilizables:
| Componente | Fuente de datos |
|-----------|----------------|
| `AppCausesProblemsDropdownComponent` | Servicio de causas de problemas |
| `AppCrewCompanyDropdownComponent` | Servicio de compañías de cuadrillas |
| `AppGroupLiquidationMaterialsDropdownComponent` | Servicio de grupos de liquidación |
| `AppMaterialTemplateDetailsDropdownComponent` | Servicio de detalle de plantillas |
| `AppMaterialTemplatesDropdownComponent` | Servicio de plantillas de material |
| `AppRadioOperatorsDropdownComponent` | Servicio de radio-operadores |
| `AppTechnicianCategoriesDropdownComponent` | Servicio de categorías de técnicos |
| `AppTechnicianDropdownComponent` | Servicio de técnicos |
| `AppTechnicianWorkingDayDropdownComponent` | Servicio de jornada laboral |

### Componentes de materiales exportados globalmente:
| Componente | Función |
|-----------|---------|
| `AppMaterialSelectBoxComponent` | SelectBox de materiales por tipo |
| `AppTypeMaterialSelectBoxComponent` | SelectBox de tipos de material |
| `AppMaterialDropdownComponent` | Dropdown de materiales |
| `AppTypeMaterialDropdownComponent` | Dropdown de tipos de material |
| `AppCrewsSelectBoxComponent` | SelectBox de cuadrillas |
| `AppTypeCrewSelectBoxComponent` | SelectBox de tipos de cuadrilla |
| `AppMaterialSeriesComponent` | Selección de series de materiales |
| `AppMaterialFolioParameterComponent` | Captura de parámetros de folio |
| `AppSharedMaterialCrewParameterComponent` | Parámetros compartidos cuadrilla-material |
| `AppSharedMaterialCrewsParameterComponent` | Parámetros multi-cuadrilla |
| `AppSharedMaterialFolioParameterComponent` | Parámetros de folio compartido |
| `AppSharedMaterialInvoiceDropdownComponent` | Dropdown de facturas/folios activos |
| `QueryMaterialMovementComponent` | Popup de consulta de movimientos de material |

---

## 7. Estado Global (NgRx Store)

### Estado del módulo (`MaterialState`):
| Propiedad | Tipo | Descripción |
|-----------|------|------------|
| `materialInvoicesCreated` | `MaterialInvoiceModel[]` | Facturas creadas, persiste en localStorage |
| `materialInvoiceSelected` | `MaterialInvoiceModel` | Factura actualmente seleccionada |
| `materialCurrentProcessSelected` | `MaterialMovementTypeEnum` | Proceso activo (RQ/EN/DE/LQ/TP/AC) |

### Funciones del Facade (`MaterialFacade`):
- `addMaterialInvoiceCreated()` — Agregar factura al store
- `updateInvoice()` — Actualizar factura existente
- `removeMaterialInvoiceCreated()` — Eliminar factura del store
- `setMaterialCurrentProcessSelected()` — Cambiar proceso activo
- `setMaterialInvoicesCreated()` — Persiste facturas en localStorage
- `resetDropdown$` — Observable para resetear dropdowns compartidos

---

## 8. Servicios de Datos Principales

| Servicio | Endpoints Clave |
|---------|-----------------|
| `MaterialService` | OData GET de materiales, filtro por tipo |
| `MaterialRequisitionService` | Equipos de material, CRUD de requisiciones |
| `LiquidationMaterialsService` | Proceso de liquidación y cancelación |
| `DeliveryMaterialService` | Materiales por requisición, proceso de entrega |
| `MaterialReturnService` | Proceso de devolución, eliminación de devolución |
| `MaterialClarificationService` | Proceso de aclaración |
| `InventoryTranferService` | Prorrateo de material, traspaso de inventario |
| `MaterialWorkOrderService` | Materiales de orden de trabajo |
| `WorkOrderService` | Datos generales de OT, inserción, cambio de tecnología, migración a fibra |
| `TechnicalCatalogService` | Técnicos, movimientos, actualización |
| `CrewCatalogService` | CRUD de cuadrillas |
| `MaterialRecoveryService` | Materiales sugeridos para recuperación |
| `MaterialRecoveryQueryService` | Consulta y eliminación de folios de recuperación |
| `MaterialRecoveryCatalogService` | Configuración de recuperación |
| `MaterialsDescriptionService` | CRUD de descripciones de materiales |
| `MaterialCrewReportService` | Reporte de material por cuadrilla |
| `TypeMovementService` | Subtipos de movimiento |
| `QueryContractService` | Consulta de contratos |
| `ReportMaterialService` | Generación de reportes (abre en nueva pestaña) |
| `ReportMaterialRecoveryService` | Datos para reporte de recuperación |
| `MaterialStockReportService` | Reporte de stock |

### Servicios auxiliares:
| Servicio | Función |
|---------|---------|
| `DeliveryMaterialValidationService` | Validaciones de entrega |
| `MaterialReturnValidationService` | Validaciones de devolución |
| `LiquidationMaterialCrewService` | Liquidaciones por cuadrilla |
| `AccumulatedLiquidationToExcelService` | Exportación a Excel de liquidaciones |
| `MaterialVerificatorToExcelService` | Exportación a Excel de verificadores |
| `ExportMaterialReportsService` | Exportación de reportes |
| `SeriesDeliveredByCrewReportService` | Reporte de series por cuadrilla |

---

## 9. Modelos de Datos Principales

| Modelo | Descripción |
|--------|------------|
| `MaterialModel` | Entidad base de material (id, nombre, tipo) |
| `MaterialInvoiceModel` | Factura/folio de movimiento (persiste en localStorage) |
| `MaterialRequisitionResultDTOModel` | Resultado de una requisición |
| `LiquidationMaterialParameterModel` | Parámetros para proceso de liquidación |
| `MaterialLiquidationWrapperResultDTOModel` | Resultado de liquidación |
| `DeliveryMaterialParameterModel` | Parámetros de entrega |
| `MaterialReturnParameterModel` | Parámetros de devolución |
| `MaterialClarificationParameterModel` | Parámetros de aclaración |
| `InventoryTransferModel` | Modelo de traspaso de inventario |
| `TechnicalCatalogModel` | Datos de técnico del catálogo |
| `CrewCatalogParameterModel` | Parámetros para ABM de cuadrillas |
| `MaterialRecoveryConfigurationDTOModel` | Configuración de recuperación |
| `MaterialRecoveryResultDTOModel` | Resultados de recuperación |
| `WorkOrderParameterModel` | Parámetros para consulta de OT |
| `QueryContractsResultDtoModel` | Resultado de consulta de contratos |
| `MovementMaterialPendingDTOModel` | Movimiento de material pendiente |
| `MaterialCrewReportDTOModel` | Resultado de reporte por cuadrilla |
| `MaterialsDescriptionModel` | Descripción de material |
| `TypeMovementModel` | Tipo/subtipo de movimiento |
