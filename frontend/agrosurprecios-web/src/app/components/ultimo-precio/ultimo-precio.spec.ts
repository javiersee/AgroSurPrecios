import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UltimoPrecio } from './ultimo-precio';

describe('UltimoPrecio', () => {
  let component: UltimoPrecio;
  let fixture: ComponentFixture<UltimoPrecio>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UltimoPrecio]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UltimoPrecio);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
