import { TestBed } from '@angular/core/testing';

import { PrecioArvejaService  } from './precio-arveja-service';

describe('PrecioArveja', () => {
  let service: PrecioArvejaService ;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrecioArvejaService );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
