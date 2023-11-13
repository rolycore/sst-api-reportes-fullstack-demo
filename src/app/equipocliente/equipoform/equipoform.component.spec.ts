import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipoformComponent } from './equipoform.component';

describe('EquipoformComponent', () => {
  let component: EquipoformComponent;
  let fixture: ComponentFixture<EquipoformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EquipoformComponent]
    });
    fixture = TestBed.createComponent(EquipoformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
