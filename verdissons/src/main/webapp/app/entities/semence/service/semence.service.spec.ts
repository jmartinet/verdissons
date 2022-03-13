import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISemence, Semence } from '../semence.model';

import { SemenceService } from './semence.service';

describe('Semence Service', () => {
  let service: SemenceService;
  let httpMock: HttpTestingController;
  let elemDefault: ISemence;
  let expectedResult: ISemence | ISemence[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SemenceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      typeSemis: 'AAAAAAA',
      conseilSemis: 'AAAAAAA',
      periodeSemisDebut: currentDate,
      periodeSemisFin: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          periodeSemisDebut: currentDate.format(DATE_FORMAT),
          periodeSemisFin: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Semence', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          periodeSemisDebut: currentDate.format(DATE_FORMAT),
          periodeSemisFin: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          periodeSemisDebut: currentDate,
          periodeSemisFin: currentDate,
        },
        returnedFromService
      );

      service.create(new Semence()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Semence', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeSemis: 'BBBBBB',
          conseilSemis: 'BBBBBB',
          periodeSemisDebut: currentDate.format(DATE_FORMAT),
          periodeSemisFin: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          periodeSemisDebut: currentDate,
          periodeSemisFin: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Semence', () => {
      const patchObject = Object.assign(
        {
          periodeSemisFin: currentDate.format(DATE_FORMAT),
        },
        new Semence()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          periodeSemisDebut: currentDate,
          periodeSemisFin: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Semence', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeSemis: 'BBBBBB',
          conseilSemis: 'BBBBBB',
          periodeSemisDebut: currentDate.format(DATE_FORMAT),
          periodeSemisFin: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          periodeSemisDebut: currentDate,
          periodeSemisFin: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Semence', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSemenceToCollectionIfMissing', () => {
      it('should add a Semence to an empty array', () => {
        const semence: ISemence = { id: 123 };
        expectedResult = service.addSemenceToCollectionIfMissing([], semence);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(semence);
      });

      it('should not add a Semence to an array that contains it', () => {
        const semence: ISemence = { id: 123 };
        const semenceCollection: ISemence[] = [
          {
            ...semence,
          },
          { id: 456 },
        ];
        expectedResult = service.addSemenceToCollectionIfMissing(semenceCollection, semence);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Semence to an array that doesn't contain it", () => {
        const semence: ISemence = { id: 123 };
        const semenceCollection: ISemence[] = [{ id: 456 }];
        expectedResult = service.addSemenceToCollectionIfMissing(semenceCollection, semence);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(semence);
      });

      it('should add only unique Semence to an array', () => {
        const semenceArray: ISemence[] = [{ id: 123 }, { id: 456 }, { id: 66599 }];
        const semenceCollection: ISemence[] = [{ id: 123 }];
        expectedResult = service.addSemenceToCollectionIfMissing(semenceCollection, ...semenceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const semence: ISemence = { id: 123 };
        const semence2: ISemence = { id: 456 };
        expectedResult = service.addSemenceToCollectionIfMissing([], semence, semence2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(semence);
        expect(expectedResult).toContain(semence2);
      });

      it('should accept null and undefined values', () => {
        const semence: ISemence = { id: 123 };
        expectedResult = service.addSemenceToCollectionIfMissing([], null, semence, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(semence);
      });

      it('should return initial array if no Semence is added', () => {
        const semenceCollection: ISemence[] = [{ id: 123 }];
        expectedResult = service.addSemenceToCollectionIfMissing(semenceCollection, undefined, null);
        expect(expectedResult).toEqual(semenceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
