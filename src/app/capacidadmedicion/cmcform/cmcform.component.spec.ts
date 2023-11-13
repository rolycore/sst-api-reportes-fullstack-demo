import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CmcformComponent } from './cmcform.component';

describe('CmcformComponent', () => {
  let component: CmcformComponent;
  let fixture: ComponentFixture<CmcformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CmcformComponent]
    });
    fixture = TestBed.createComponent(CmcformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
