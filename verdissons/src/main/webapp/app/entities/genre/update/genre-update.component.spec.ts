import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { GenreService } from '../service/genre.service';
import { IGenre, Genre } from '../genre.model';
import { IFamille } from 'app/entities/famille/famille.model';
import { FamilleService } from 'app/entities/famille/service/famille.service';

import { GenreUpdateComponent } from './genre-update.component';

describe('Genre Management Update Component', () => {
  let comp: GenreUpdateComponent;
  let fixture: ComponentFixture<GenreUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let genreService: GenreService;
  let familleService: FamilleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [GenreUpdateComponent],
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
      .overrideTemplate(GenreUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GenreUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    genreService = TestBed.inject(GenreService);
    familleService = TestBed.inject(FamilleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Famille query and add missing value', () => {
      const genre: IGenre = { id: 456 };
      const famille: IFamille = { id: 40766 };
      genre.famille = famille;

      const familleCollection: IFamille[] = [{ id: 10804 }];
      jest.spyOn(familleService, 'query').mockReturnValue(of(new HttpResponse({ body: familleCollection })));
      const additionalFamilles = [famille];
      const expectedCollection: IFamille[] = [...additionalFamilles, ...familleCollection];
      jest.spyOn(familleService, 'addFamilleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ genre });
      comp.ngOnInit();

      expect(familleService.query).toHaveBeenCalled();
      expect(familleService.addFamilleToCollectionIfMissing).toHaveBeenCalledWith(familleCollection, ...additionalFamilles);
      expect(comp.famillesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const genre: IGenre = { id: 456 };
      const famille: IFamille = { id: 8836 };
      genre.famille = famille;

      activatedRoute.data = of({ genre });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(genre));
      expect(comp.famillesSharedCollection).toContain(famille);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Genre>>();
      const genre = { id: 123 };
      jest.spyOn(genreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: genre }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(genreService.update).toHaveBeenCalledWith(genre);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Genre>>();
      const genre = new Genre();
      jest.spyOn(genreService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: genre }));
      saveSubject.complete();

      // THEN
      expect(genreService.create).toHaveBeenCalledWith(genre);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Genre>>();
      const genre = { id: 123 };
      jest.spyOn(genreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ genre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(genreService.update).toHaveBeenCalledWith(genre);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFamilleById', () => {
      it('Should return tracked Famille primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFamilleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
