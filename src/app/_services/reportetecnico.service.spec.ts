import { TestBed } from '@angular/core/testing';

import { ReportetecnicoService } from './reportetecnico.service';

describe('ReportetecnicoService', () => {
  let service: ReportetecnicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportetecnicoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
