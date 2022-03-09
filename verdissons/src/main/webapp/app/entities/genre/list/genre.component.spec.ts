import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { GenreService } from '../service/genre.service';

import { GenreComponent } from './genre.component';

describe('Genre Management Component', () => {
  let comp: GenreComponent;
  let fixture: ComponentFixture<GenreComponent>;
  let service: GenreService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [GenreComponent],
    })
      .overrideTemplate(GenreComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GenreComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GenreService);

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
    expect(comp.genres?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
