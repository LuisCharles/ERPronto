import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Alerta } from './alerta';

describe('Alerta', () => {
  let component: Alerta;
  let fixture: ComponentFixture<Alerta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Alerta],
    }).compileComponents();

    fixture = TestBed.createComponent(Alerta);
    fixture.componentRef.setInput('mensagem', 'Mensagem de teste');
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
