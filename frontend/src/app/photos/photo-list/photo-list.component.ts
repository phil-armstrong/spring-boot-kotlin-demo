import {Component, OnInit} from '@angular/core';
import {PhotoService} from '../photo.service';
import {Photo} from '../photo-results';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements OnInit {

  private results: Photo[];

  constructor(private photoService: PhotoService) {
  }

  ngOnInit() {
    this.photoService.photoResultsObservable.subscribe(results => {
      this.results = results;
      console.log(results);
    });
  }
}
