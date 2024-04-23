import { Component, OnInit } from '@angular/core';
import { GoogleLoginProvider, SocialAuthService } from "@abacritt/angularx-social-login";
import { SocialUser } from "@abacritt/angularx-social-login";
import {
  FacebookLoginProvider,
  AmazonLoginProvider,
  VKLoginProvider,
  MicrosoftLoginProvider,
} from "@abacritt/angularx-social-login";
import {  GoogleSigninButtonModule, SocialAuthServiceConfig, SocialLoginModule, GoogleSigninButtonDirective } from "@abacritt/angularx-social-login";
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BASE_API_URL } from '../interfaces/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent implements OnInit {
  user: SocialUser | undefined;
  GoogleLoginProvider = GoogleLoginProvider;
  FacebookLoginProvider = FacebookLoginProvider;

  constructor(private readonly _authService: SocialAuthService, private router: Router, private http: HttpClient) {}

  ngOnInit() {
    this._authService.authState.subscribe((user) => {
      this.user = user;
    });
  }

  signInWithFB(): void {
    this._authService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signInWithAmazon(): void {
    this._authService.signIn(AmazonLoginProvider.PROVIDER_ID);
  }

  signInWithVK(): void {
    this._authService.signIn(VKLoginProvider.PROVIDER_ID);
  }

  signInWithMicrosoft(): void {
    this._authService.signIn(MicrosoftLoginProvider.PROVIDER_ID);
  }

  signOut(): void {
    this._authService.signOut();
  }

  refreshGoogleToken(): void {
    this._authService.refreshAuthToken(GoogleLoginProvider.PROVIDER_ID);
  }

  redirect(): void {
    this._authService.authState.subscribe(isLoggedIn => {   
      if (isLoggedIn) {
        localStorage.setItem('userName',isLoggedIn.name);
        localStorage.setItem('userEmail',isLoggedIn.email);
        localStorage.setItem('userId',isLoggedIn.id);
        localStorage.setItem('userToken',isLoggedIn.authToken);      
        const body = {};
        const headers = new HttpHeaders({ idUser: isLoggedIn.id, tokenUser: isLoggedIn.authToken, emailUser: isLoggedIn.email, 'Access-Control-Allow-Origin': 'http://localhost:4200' });
        this.http.post(BASE_API_URL+'/api/v1/validate/token/oauth2', body, { headers: headers }).subscribe(
          (response: any) => {
            console.log('Token OAUTH2 válido para acesso ao backend!');
            localStorage.setItem('tokenJWT','Bearer '+response.tokenJWT);
            this.router.navigate(['/tickets-list']);
          },
          (error) => {
            console.error('Erro ao validar token OAUTH2 e criar Token JWT!');
            console.error(error);
            this.router.navigate(['/']);
          }
        );
        
      }
    });
  }
}