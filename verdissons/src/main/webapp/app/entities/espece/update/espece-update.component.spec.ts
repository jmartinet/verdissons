import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EspeceService } from '../service/espece.service';
import { IEspece, Espece } from '../espece.model';

import { EspeceUpdateComponent } from './espece-update.component';

describe('Espece Management Update Component', () => {
  let comp: EspeceUpdateComponent;
  let fixture: ComponentFixture<EspeceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let especeService: EspeceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EspeceUpdateComponent],
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
      .overrideTemplate(EspeceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EspeceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    especeService = TestBed.inject(EspeceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const espece: IEspece = { id: 456 };

      activatedRoute.data = of({ espece });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(espece));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Espece>>();
      const espece = { id: 123 };
      jest.spyOn(especeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ espece });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: espece }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(especeService.update).toHaveBeenCalledWith(espece);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Espece>>();
      const espece = new Espece();
      jest.spyOn(especeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ espece });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: espece }));
      saveSubject.complete();

      // THEN
      expect(especeService.create).toHaveBeenCalledWith(espece);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Espece>>();
      const espece = { id: 123 };
      jest.spyOn(especeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ espece });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(especeService.update).toHaveBeenCalledWith(espece);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
