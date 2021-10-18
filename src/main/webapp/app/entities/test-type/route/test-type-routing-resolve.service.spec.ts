jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITestType, TestType } from '../test-type.model';
import { TestTypeService } from '../service/test-type.service';

import { TestTypeRoutingResolveService } from './test-type-routing-resolve.service';

describe('TestType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TestTypeRoutingResolveService;
  let service: TestTypeService;
  let resultTestType: ITestType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TestTypeRoutingResolveService);
    service = TestBed.inject(TestTypeService);
    resultTestType = undefined;
  });

  describe('resolve', () => {
    it('should return ITestType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTestType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTestType).toEqual({ id: 'ABC' });
    });

    it('should return new ITestType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTestType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTestType).toEqual(new TestType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TestType })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTestType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTestType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
