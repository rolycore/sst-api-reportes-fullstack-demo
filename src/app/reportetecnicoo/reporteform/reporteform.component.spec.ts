import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteformComponent } from './reporteform.component';

describe('ReporteformComponent', () => {
  let component: ReporteformComponent;
  let fixture: ComponentFixture<ReporteformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReporteformComponent]
    });
    fixture = TestBed.createComponent(ReporteformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
