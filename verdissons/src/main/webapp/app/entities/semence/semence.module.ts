import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SemenceComponent } from './list/semence.component';
import { SemenceDetailComponent } from './detail/semence-detail.component';
import { SemenceUpdateComponent } from './update/semence-update.component';
import { SemenceDeleteDialogComponent } from './delete/semence-delete-dialog.component';
import { SemenceRoutingModule } from './route/semence-routing.module';

@NgModule({
  imports: [SharedModule, SemenceRoutingModule],
  declarations: [SemenceComponent, SemenceDetailComponent, SemenceUpdateComponent, SemenceDeleteDialogComponent],
  entryComponents: [SemenceDeleteDialogComponent],
})
export class SemenceModule {}
