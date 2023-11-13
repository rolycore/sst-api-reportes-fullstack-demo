import { TestBed } from '@angular/core/testing';

import { CapacidadmedicionService } from './capacidadmedicion.service';

describe('CapacidadmedicionService', () => {
  let service: CapacidadmedicionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CapacidadmedicionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
