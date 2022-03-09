import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VarieteService } from '../service/variete.service';
import { IVariete, Variete } from '../variete.model';
import { IGenre } from 'app/entities/genre/genre.model';
import { GenreService } from 'app/entities/genre/service/genre.service';

import { VarieteUpdateComponent } from './variete-update.component';

describe('Variete Management Update Component', () => {
  let comp: VarieteUpdateComponent;
  let fixture: ComponentFixture<VarieteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let varieteService: VarieteService;
  let genreService: GenreService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VarieteUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VarieteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VarieteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    varieteService = TestBed.inject(VarieteService);
    genreService = TestBed.inject(GenreService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Genre query and add missing value', () => {
      const variete: IVariete = { id: 456 };
      const genre: IGenre = { id: 96779 };
      variete.genre = genre;

      const genreCollection: IGenre[] = [{ id: 58299 }];
      jest.spyOn(genreService, 'query').mockReturnValue(of(new HttpResponse({ body: genreCollection })));
      const additionalGenres = [genre];
      const expectedCollection: IGenre[] = [...additionalGenres, ...genreCollection];
      jest.spyOn(genreService, 'addGenreToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ variete });
      comp.ngOnInit();

      expect(genreService.query).toHaveBeenCalled();
      expect(genreService.addGenreToCollectionIfMissing).toHaveBeenCalledWith(genreCollection, ...additionalGenres);
      expect(comp.genresSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const variete: IVariete = { id: 456 };
      const genre: IGenre = { id: 53518 };
      variete.genre = genre;

      activatedRoute.data = of({ variete });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(variete));
      expect(comp.genresSharedCollection).toContain(genre);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Variete>>();
      const variete = { id: 123 };
      jest.spyOn(varieteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ variete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: variete }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(varieteService.update).toHaveBeenCalledWith(variete);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Variete>>();
      const variete = new Variete();
      jest.spyOn(varieteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ variete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: variete }));
      saveSubject.complete();

      // THEN
      expect(varieteService.create).toHaveBeenCalledWith(variete);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Variete>>();
      const variete = { id: 123 };
      jest.spyOn(varieteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ variete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(varieteService.update).toHaveBeenCalledWith(variete);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackGenreById', () => {
      it('Should return tracked Genre primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGenreById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
