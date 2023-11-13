import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdenesformComponent } from './ordenesform.component';

describe('OrdenesformComponent', () => {
  let component: OrdenesformComponent;
  let fixture: ComponentFixture<OrdenesformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrdenesformComponent]
    });
    fixture = TestBed.createComponent(OrdenesformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
