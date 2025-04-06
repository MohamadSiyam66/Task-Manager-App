import { Injectable } from '@angular/core';

const TOKEN_KEY = 'token';
const USER_KEY = 'user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() {}

  // Save JWT token
  static saveToken(token: string): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  // Get JWT token
  static getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  // Save user object
  static saveUser(user: any): void {
    localStorage.removeItem(USER_KEY);
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  // Get user object
  static getUser(): any | null {
    const userJson = localStorage.getItem(USER_KEY);
    try {
      return userJson ? JSON.parse(userJson) : null;
    } catch {
      return null;
    }
  }

  // Get logged in user's ID
  static getUserId(): string | null {
    const user = this.getUser();
    return user && user.id ? user.id : null;
  }

  // Check if user is logged in
  static isUserLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  // Clear token and user info
  static logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  }
}
