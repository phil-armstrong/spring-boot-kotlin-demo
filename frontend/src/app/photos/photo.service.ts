import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Facet, Pagination, Photo, PhotoResults} from './photo-results';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class PhotoService {
  private url: string = '/api/photos';

  private _searchCriteriaObservable: Subject<Facet[]> = new Subject();
  private _photoResultsObservable: Subject<Photo[]> = new Subject();
  private _paginationObservable: Subject<Pagination> = new Subject();

  constructor(private httpClient: HttpClient) {
    this.get([]);
  }

  updateSearchCriteria(searchCriteria: Facet[]) {
    this.get(searchCriteria);
  }

  private get(searchCriteria: Facet[]) {
    this.httpClient.get<PhotoResults>(this.url, {
      params: this.createHttpParams(searchCriteria)
    }).subscribe(data => {
      this._photoResultsObservable.next(data.results);
      this._searchCriteriaObservable.next(data.facets);
      this._paginationObservable.next(data.pagination);
    });
  }

  private createHttpParams(facets: Facet[]): HttpParams {
    let httpParams = new HttpParams();

    // for each facet
    facets.forEach(facet => {
      let selectedValues = this.getFacetSelectedValues(facet);
      selectedValues.forEach(selectedValue => {
        selectedValue.values.forEach(value => {
          httpParams = httpParams.append(selectedValue.name, value);
        });
      });
    });

    return httpParams;
  }

  private getFacetSelectedValues(facet: Facet): SelectedFacet[] {
    const facetsToSearchWith: SelectedFacet[] = [];
    let selectedFacetValues = facet.values.filter(value => value.selected);
    facetsToSearchWith.push({name: facet.name, values: selectedFacetValues.map(value => value.name)});

    const map: SelectedFacet[][] = facet.values.filter(value => value.subFacet)
      .map(value => this.getFacetSelectedValues(value.subFacet));

    map.forEach(thing => {
      thing.forEach(selectedFacet => {
        facetsToSearchWith.push(selectedFacet);
      });
    });

    return facetsToSearchWith;
  }

  get searchCriteriaObservable(): Subject<Facet[]> {
    return this._searchCriteriaObservable;
  }

  get photoResultsObservable(): Subject<Photo[]> {
    return this._photoResultsObservable;
  }

  get paginationObservable(): Subject<Pagination> {
    return this._paginationObservable;
  }
}

interface SelectedFacet {
  name: string;
  values: string[]
}
