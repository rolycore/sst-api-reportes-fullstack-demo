import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CapacidadmedicionComponent } from './capacidadmedicion.component';

describe('CapacidadmedicionComponent', () => {
  let component: CapacidadmedicionComponent;
  let fixture: ComponentFixture<CapacidadmedicionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CapacidadmedicionComponent]
    });
    fixture = TestBed.createComponent(CapacidadmedicionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
