import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Botao } from './botao';

describe('Botao', () => {
  let component: Botao;
  let fixture: ComponentFixture<Botao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Botao],
    }).compileComponents();

    fixture = TestBed.createComponent(Botao);
    fixture.componentRef.setInput('texto', 'Salvar');
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
