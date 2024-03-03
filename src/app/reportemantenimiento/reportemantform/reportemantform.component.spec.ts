import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportemantformComponent } from './reportemantform.component';

describe('ReportemantformComponent', () => {
  let component: ReportemantformComponent;
  let fixture: ComponentFixture<ReportemantformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportemantformComponent]
    });
    fixture = TestBed.createComponent(ReportemantformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
