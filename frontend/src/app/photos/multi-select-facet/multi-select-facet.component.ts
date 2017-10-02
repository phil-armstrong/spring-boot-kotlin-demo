import {Component, Input, OnInit} from '@angular/core';
import {PhotoService} from '../photo.service';
import {Facet, FacetValue} from '../photo-results';

@Component({
  selector: 'app-multi-select-facet',
  templateUrl: './multi-select-facet.component.html',
  styleUrls: ['./multi-select-facet.component.css']
})
export class MultiSelectFacetComponent implements OnInit {

  @Input()
  public name: string;

  private facets: Facet[] = [];

  constructor(private photoService: PhotoService) {
  }

  ngOnInit() {
    this.photoService.searchCriteriaObservable.subscribe(criteria => this.facets = criteria);
  }

  get facet(): Facet {
    return this.facets.find(facet => facet.name === this.name);
  }

  updateFacet(facet: FacetValue) {
    if (!facet.selected && facet.subFacet) {
      this.clearSubFacet(facet.subFacet);
    }
    this.photoService.updateSearchCriteria(this.facets);
  }

  private clearSubFacet(facet: Facet) {
    facet.values.forEach(value => {
      value.selected = false;
      if (value.subFacet) {
        this.clearSubFacet(value.subFacet);
      }
    });
  }
}
