import { TestBed, inject } from '@angular/core/testing';

import { PhotoServiceService } from './photo-service.service';

describe('PhotoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PhotoServiceService]
    });
  });

  it('should be created', inject([PhotoServiceService], (service: PhotoServiceService) => {
    expect(service).toBeTruthy();
  }));
});
