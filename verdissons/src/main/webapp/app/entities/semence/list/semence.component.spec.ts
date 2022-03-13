import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SemenceService } from '../service/semence.service';

import { SemenceComponent } from './semence.component';

describe('Semence Management Component', () => {
  let comp: SemenceComponent;
  let fixture: ComponentFixture<SemenceComponent>;
  let service: SemenceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SemenceComponent],
    })
      .overrideTemplate(SemenceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SemenceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SemenceService);

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
    expect(comp.semences?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
