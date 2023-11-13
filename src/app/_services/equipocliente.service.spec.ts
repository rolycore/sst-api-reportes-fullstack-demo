import { TestBed } from '@angular/core/testing';

import { EquipoclienteService } from './equipocliente.service';

describe('EquipoclienteService', () => {
  let service: EquipoclienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipoclienteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
