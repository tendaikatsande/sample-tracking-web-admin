import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISampleType, SampleType } from '../sample-type.model';
import { SampleTypeService } from '../service/sample-type.service';

@Injectable({ providedIn: 'root' })
export class SampleTypeRoutingResolveService implements Resolve<ISampleType> {
  constructor(protected service: SampleTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISampleType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sampleType: HttpResponse<SampleType>) => {
          if (sampleType.body) {
            return of(sampleType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SampleType());
  }
}
