import { Component } from '@angular/core';
import { StorageService } from './auth/services/storage/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  isUserLoggedIn: boolean = StorageService.isUserLoggedIn();
  constructor(private router:Router) {}

  ngOnInit() {
    this.router.events.subscribe(event => {
      this.isUserLoggedIn = StorageService.isUserLoggedIn();
    });
  }

  logout(): void {
    StorageService.logout();
    this.router.navigate(['/login']);
  }
}
