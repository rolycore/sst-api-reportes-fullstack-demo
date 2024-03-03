import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportemantenimientoComponent } from './reportemantenimiento.component';

describe('ReportemantenimientoComponent', () => {
  let component: ReportemantenimientoComponent;
  let fixture: ComponentFixture<ReportemantenimientoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportemantenimientoComponent]
    });
    fixture = TestBed.createComponent(ReportemantenimientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
