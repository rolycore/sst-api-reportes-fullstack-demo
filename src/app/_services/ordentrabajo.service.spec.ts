import { TestBed } from '@angular/core/testing';

import { OrdentrabajoService } from './ordentrabajo.service';

describe('OrdentrabajoService', () => {
  let service: OrdentrabajoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdentrabajoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
