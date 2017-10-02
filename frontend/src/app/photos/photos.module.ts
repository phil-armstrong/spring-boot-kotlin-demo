import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PhotoService} from './photo.service';
import {HttpClientModule} from '@angular/common/http';
import {PhotoListComponent} from './photo-list/photo-list.component';
import { MultiSelectFacetComponent } from './multi-select-facet/multi-select-facet.component';
import {FormsModule} from '@angular/forms';
import { FacetsComponent } from './facets/facets.component';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    PhotoService
  ],
  declarations: [
    PhotoListComponent,
    MultiSelectFacetComponent,
    FacetsComponent
  ],
  exports: [
    PhotoListComponent,
    FacetsComponent
  ]
})
export class PhotosModule {
}
