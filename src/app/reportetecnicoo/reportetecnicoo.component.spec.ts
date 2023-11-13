import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportetecnicooComponent } from './reportetecnicoo.component';

describe('ReportetecnicooComponent', () => {
  let component: ReportetecnicooComponent;
  let fixture: ComponentFixture<ReportetecnicooComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportetecnicooComponent]
    });
    fixture = TestBed.createComponent(ReportetecnicooComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
