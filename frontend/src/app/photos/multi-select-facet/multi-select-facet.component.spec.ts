import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultiSelectFacetComponent } from './multi-select-facet.component';

describe('MultiSelectFacetComponent', () => {
  let component: MultiSelectFacetComponent;
  let fixture: ComponentFixture<MultiSelectFacetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultiSelectFacetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultiSelectFacetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
