import { EquipoCliente } from "./equipocliente";
import { OrdenTrabajo } from "./ordentrabajo";
import { ReporteTecnico } from "./reportetecnico";

export class Cliente {
  idCliente!: number;
  nombre!: string;
  apellido!: string;
  email!: string;
  createAt!: Date;
  telefono_empresa!: string;
  cod_cliente!: string;
  razon_social!: string;
  nombre_comercial!: string;
  ruc!: string;
  dv!: string;
  direccion!: string;
  telefono_jefe!: string;
  celular_jefe!: string;
  correo_electronico!: string;
  actividad_economica!: string;
  abreviatura!: string;
  nombre_contacto!: string;
  cargo_servicio!: string;
  celular_servicio!: string;
  correo_servicio!: string;
  telefono_servicio!: string;
  nombre_cobro!: string;
  cargo_cobro!: string;
  telefono_cobro!: string;
  celular_cobro!: string;
  correo_cobro!: string;
  activo!: boolean;
  equipos: EquipoCliente[] = [];
  ordenTrabajos: OrdenTrabajo[] = [];
  reporteTecnicos: ReporteTecnico[] = [];


}
