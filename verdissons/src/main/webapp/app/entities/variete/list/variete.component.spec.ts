import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VarieteService } from '../service/variete.service';

import { VarieteComponent } from './variete.component';

describe('Variete Management Component', () => {
  let comp: VarieteComponent;
  let fixture: ComponentFixture<VarieteComponent>;
  let service: VarieteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [VarieteComponent],
    })
      .overrideTemplate(VarieteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VarieteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VarieteService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.varietes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
