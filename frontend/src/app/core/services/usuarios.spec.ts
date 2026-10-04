import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Usuarios } from './usuarios';

describe('Usuarios', () => {
  let service: Usuarios;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(Usuarios);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
