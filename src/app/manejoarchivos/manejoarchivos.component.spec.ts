import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManejoarchivosComponent } from './manejoarchivos.component';

describe('ManejoarchivosComponent', () => {
  let component: ManejoarchivosComponent;
  let fixture: ComponentFixture<ManejoarchivosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManejoarchivosComponent]
    });
    fixture = TestBed.createComponent(ManejoarchivosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
