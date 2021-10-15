import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LaboratoryComponent } from './list/laboratory.component';
import { LaboratoryDetailComponent } from './detail/laboratory-detail.component';
import { LaboratoryUpdateComponent } from './update/laboratory-update.component';
import { LaboratoryDeleteDialogComponent } from './delete/laboratory-delete-dialog.component';
import { LaboratoryRoutingModule } from './route/laboratory-routing.module';

@NgModule({
  imports: [SharedModule, LaboratoryRoutingModule],
  declarations: [LaboratoryComponent, LaboratoryDetailComponent, LaboratoryUpdateComponent, LaboratoryDeleteDialogComponent],
  entryComponents: [LaboratoryDeleteDialogComponent],
})
export class LaboratoryModule {}
