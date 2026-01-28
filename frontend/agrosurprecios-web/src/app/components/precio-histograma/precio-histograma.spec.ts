import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrecioHistograma } from './precio-histograma';

describe('PrecioHistograma', () => {
  let component: PrecioHistograma;
  let fixture: ComponentFixture<PrecioHistograma>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrecioHistograma]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrecioHistograma);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
