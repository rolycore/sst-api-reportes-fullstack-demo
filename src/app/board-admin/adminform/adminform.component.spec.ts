import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminformComponent } from './adminform.component';

describe('AdminformComponent', () => {
  let component: AdminformComponent;
  let fixture: ComponentFixture<AdminformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminformComponent]
    });
    fixture = TestBed.createComponent(AdminformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
