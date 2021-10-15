import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TestTypeComponent } from './list/test-type.component';
import { TestTypeDetailComponent } from './detail/test-type-detail.component';
import { TestTypeUpdateComponent } from './update/test-type-update.component';
import { TestTypeDeleteDialogComponent } from './delete/test-type-delete-dialog.component';
import { TestTypeRoutingModule } from './route/test-type-routing.module';

@NgModule({
  imports: [SharedModule, TestTypeRoutingModule],
  declarations: [TestTypeComponent, TestTypeDetailComponent, TestTypeUpdateComponent, TestTypeDeleteDialogComponent],
  entryComponents: [TestTypeDeleteDialogComponent],
})
export class TestTypeModule {}
