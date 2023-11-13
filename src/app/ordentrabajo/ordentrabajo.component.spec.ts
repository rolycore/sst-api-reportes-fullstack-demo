import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdentrabajoComponent } from './ordentrabajo.component';

describe('OrdentrabajoComponent', () => {
  let component: OrdentrabajoComponent;
  let fixture: ComponentFixture<OrdentrabajoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdentrabajoComponent]
    });
    fixture = TestBed.createComponent(OrdentrabajoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
