import { TestBed } from '@angular/core/testing';

import { ReportemantenimientoService } from './reportemantenimiento.service';

describe('ReportemantenimientoService', () => {
  let service: ReportemantenimientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportemantenimientoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
