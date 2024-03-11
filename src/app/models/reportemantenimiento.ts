import { Cliente } from './cliente';
import { EquipoCliente } from './equipocliente';

export class ReporteMantenimiento {
  idrepmant!: number;
  no_reporte!: string;
  nombrecliente!: string;
  nombreequipo!: string;
  tecnico!: string;
  horaentrada!: string; // Puedes cambiar esto según tus necesidades de manejo de tiempo en TypeScript
  horasalida!: string; // Puedes cambiar esto según tus necesidades de manejo de tiempo en TypeScript
  horaviajes!: string;
  fechareporte!: Date;
  fecha!: Date;
  contacto!: string;
  cargo!: string;
  direccion!: string;
  no_cotizacion!: string;
  ubicacionequipo!: string;
  fabricanteindicador!: string;
  fabricantemarco!: string;
  fabricantetransductor!: string;
  modeloindicador!: string;
  modelomarco!: string;
  modelotransductor!: string;
  serieindicador!: string;
  seriemarco!: string;
  serietransductor!: string;
  capacidadindicador!: string;
  capacidadmarco!: string;
  capacidadtransductor!: string;
  notamantprevent!: string;
  notahallazgo!: string;
  recomendaciones!: string;
  imagen_1!: string;
  imagen_2!: string;
  imagen_3!: string;
  imagen_4!: string;
  imagen_5!: string;
  imagen_6!: string;
  imagen_7!: string;
  imagen_8!: string;
  imagen_9!: string;
  imagen_10!: string;
  imagen_11!: string;
  imagen_12!: string;
  descripcion1!: string;
  descripcion2!: string;
  descripcion3!: string;
  descripcion4!: string;
  descripcion5!: string;
  descripcion6!: string;
  descripcion7!: string;
  descripcion8!: string;
  descripcion9!: string;
  descripcion10!: string;
  descripcion11!: string;
  descripcion12!: string;
  rutaImagen1!: string;
  rutaImagen2!: string;
  rutaImagen3!: string;
  rutaImagen4!: string;
  rutaImagen5!: string;
  rutaImagen6!: string;
  rutaImagen7!: string;
  rutaImagen8!: string;
  rutaImagen9!: string;
  rutaImagen10!: string;
  rutaImagen11!: string;
  rutaImagen12!: string;
  ev_desgaste!: boolean;
  ev_aflojamiento!: boolean;
  ev_vibraciones!: boolean;
  ev_fallas!: boolean;
  ev_corrocion!: boolean;
  ev_condiciones_ambiantales!: boolean;
  ev_utiliza_masa!: boolean;
  ev_identifica_masa!: boolean;
  inspeccion_estructura_maq!: boolean;
  imam_ascendente!: boolean;
  imam_ajustar!: boolean;
  ipcm_platos!: boolean;
  ipcm_plato_inferior!: boolean;
  ipcm_pruebas!: boolean;
  va_accesorios!: boolean;
  va_usada!: boolean;
  observaciones!: string;
  cliente!: Cliente;
  equipo!: EquipoCliente;
}
