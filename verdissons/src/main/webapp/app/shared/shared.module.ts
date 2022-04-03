import { NgModule } from '@angular/core';

import { SharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { DurationPipe } from './date/duration.pipe';
import { FormatMediumDatetimePipe } from './date/format-medium-datetime.pipe';
import { FormatMediumDatePipe } from './date/format-medium-date.pipe';
import { SortByDirective } from './sort/sort-by.directive';
import { SortDirective } from './sort/sort.directive';
import { ItemCountComponent } from './pagination/item-count.component';
import { BotanicItemComponent } from 'app/entities/botanicItem/select/botanicItem.component';
import { ImageSelectorComponent } from 'app/entities/image/selector/image-selector.component';
import { ImageCropperDialogComponent } from 'app/entities/image/cropper/image-cropper-dialog.component';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [
    ImageSelectorComponent,
    ImageCropperDialogComponent,
    AlertComponent,
    AlertErrorComponent,
    BotanicItemComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
  ],
  exports: [
    SharedLibsModule,
    ImageSelectorComponent,
    ImageCropperDialogComponent,
    AlertComponent,
    AlertErrorComponent,
    BotanicItemComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent
  ],
})
export class SharedModule {}
