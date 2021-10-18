jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISampleType, SampleType } from '../sample-type.model';
import { SampleTypeService } from '../service/sample-type.service';

import { SampleTypeRoutingResolveService } from './sample-type-routing-resolve.service';

describe('SampleType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SampleTypeRoutingResolveService;
  let service: SampleTypeService;
  let resultSampleType: ISampleType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SampleTypeRoutingResolveService);
    service = TestBed.inject(SampleTypeService);
    resultSampleType = undefined;
  });

  describe('resolve', () => {
    it('should return ISampleType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSampleType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSampleType).toEqual({ id: 'ABC' });
    });

    it('should return new ISampleType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSampleType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSampleType).toEqual(new SampleType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SampleType })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSampleType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSampleType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
