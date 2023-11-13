import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipoclienteComponent } from './equipocliente.component';

describe('EquipoclienteComponent', () => {
  let component: EquipoclienteComponent;
  let fixture: ComponentFixture<EquipoclienteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EquipoclienteComponent]
    });
    fixture = TestBed.createComponent(EquipoclienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
