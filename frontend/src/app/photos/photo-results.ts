export interface PhotoResults {
  pagination: Pagination;
  facets: Facet[];
  results: Photo[];
}

export interface Facet {
  name: string;
  values: FacetValue[];
}

export interface FacetValue {
  name: string,
  count: number,
  selected: boolean,
  subFacet: Facet
}

export interface Photo {
  albums: string[];
  categories: string[];
  person: string[];
  filename: string;
  location: string;
  year: number;
  month: number;
  day: number;
  takenOnDate: Date;
  uploadDate: Date;
}

export interface Pagination {
  resultsShowing: number;
  pageNumber: number;
  totalResults: number;
}
