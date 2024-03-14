import { Roles } from "./roles";


export class User {
    id!: number;
    creado_por!: string;
    fecha_hora_creacion!: Date;
    fecha_hora_ultima_actualizacion!: Date;
    ultima_actualizacion_por!: string;
    email!: string;
    password!: string;
    reset_password_token!: string;
    username!: string;
    roles!: Roles[]; // Array de roles

  }
