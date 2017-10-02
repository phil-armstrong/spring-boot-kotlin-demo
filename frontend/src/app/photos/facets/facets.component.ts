import {Component, OnInit} from '@angular/core';
import {PhotoService} from '../photo.service';

@Component({
  selector: 'app-facets',
  templateUrl: './facets.component.html',
  styleUrls: ['./facets.component.css']
})
export class FacetsComponent implements OnInit {

  constructor(private photoService: PhotoService) {
  }

  ngOnInit() {
    // this.photoService.searchCriteriaObservable.subscribe(facets => this._facets = facets);
  }
}
