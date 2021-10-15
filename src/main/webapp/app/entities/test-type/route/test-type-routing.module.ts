import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TestTypeComponent } from '../list/test-type.component';
import { TestTypeDetailComponent } from '../detail/test-type-detail.component';
import { TestTypeUpdateComponent } from '../update/test-type-update.component';
import { TestTypeRoutingResolveService } from './test-type-routing-resolve.service';

const testTypeRoute: Routes = [
  {
    path: '',
    component: TestTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TestTypeDetailComponent,
    resolve: {
      testType: TestTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TestTypeUpdateComponent,
    resolve: {
      testType: TestTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TestTypeUpdateComponent,
    resolve: {
      testType: TestTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(testTypeRoute)],
  exports: [RouterModule],
})
export class TestTypeRoutingModule {}
