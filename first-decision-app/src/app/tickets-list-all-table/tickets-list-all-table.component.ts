import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { SocialAuthService } from "@abacritt/angularx-social-login";
import { SocialUser } from "@abacritt/angularx-social-login";
import { BASE_API_URL } from '../interfaces/environment';
import {
  FacebookLoginProvider,
} from "@abacritt/angularx-social-login";
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-tickets-list-all-table',
  templateUrl: './tickets-list-all-table.component.html',
  styleUrl: './tickets-list-all-table.component.scss'
})
export class TicketsListAllTableComponent  {
  user: SocialUser | undefined;
  FacebookLoginProvider = FacebookLoginProvider;
  userName: string = '';
  dadosTabela: any[] = [];
  paginaAtual: number = 1;
  itensPorPagina: number = 10;
  totalRegistros: number = 0;
  tokenJWT: string = '';
  userEmail: string= '';
  contactForm!: FormGroup;
  exibeMsg: boolean = false;
  textoMsg: string = '';
  
  constructor(private readonly _authService: SocialAuthService, private router: Router, private http: HttpClient, private formBuilder: FormBuilder){   
  }

  ngOnInit() {
    this.contactForm = this.formBuilder.group({
      email: [localStorage.getItem('userEmail'), [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      searchInput: ['', Validators.required],
    });
    this._authService.authState.subscribe((user) => {
      this.user = user;
    });
    this.loadTickets();
  }

  signOut() {
    this._authService.signOut();
  }

  goToCreateNew() {
    this.router.navigate(['/tickets-list']);
  }

  loadTickets(): void {
    this.tokenJWT = localStorage.getItem('userToken') ?? '';
    this.userEmail = localStorage.getItem('userEmail') ?? '';
    this.http.get<any>(`${BASE_API_URL}/api/v1/ticket/by-email-paginated?page=${this.paginaAtual-1}&size=10&userEmail=${this.userEmail}`, {
      headers: {
          Authorization: `Bearer ${this.tokenJWT}`,
          userEmail: this.userEmail

      }}).subscribe(response => {
        this.dadosTabela = response.content; 
        this.totalRegistros = response.totalElements;
    });
  }
    
  goToViewTicket(id:any){
    const parametros = {
      ticketId: id
    };
    this.router.navigate(['/view-ticket'], { queryParams: parametros });
  }

  searchTickets(subject: string, searchInput:string, email:string, page: number, size:number ) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const searchInputEncoded: string = encodeURIComponent(searchInput);

    return this.http.get<any>(`${BASE_API_URL}/api/v1/ticket/by-subject-and-search?subject=${subject}&searchInput=${searchInputEncoded}&page=${this.paginaAtual-1}&size=10&email=${this.userEmail}`,  {
      headers: {
          Authorization: `Bearer ${this.tokenJWT}`,
          userEmail: this.userEmail

      }}).subscribe(response => {
        this.dadosTabela = response.content; 
        this.totalRegistros = response.totalElements;
    }, error => {
      this.exibeMsg = true;
      this.textoMsg = "Ocorreu um erro na requisição!"
    });
  }

  nextPage(){
    this.paginaAtual++;
    if(this.contactForm.controls['searchInput'].status != 'INVALID'){
      this.search()
    }else{
      this.loadTickets();
    }
    
  }
  previousPage(){
    this.paginaAtual--;
    if(this.contactForm.controls['searchInput'].status != 'INVALID'){
      this.search()
    }else{
      this.loadTickets();
    }
  }

  logout(){
      localStorage.removeItem('userName');
      localStorage.removeItem('userEmail');
      localStorage.removeItem('userId');
      localStorage.removeItem('userToken');
      localStorage.removeItem('tokenJWT');
      this.signOut();
      this.router.navigate(['/']);
  }

  search(){
    console.log(this.contactForm.controls);
    console.log(this.contactForm.controls['email'].value);
    console.log(this.contactForm.controls['subject'].value);
    console.log(this.contactForm.controls['searchInput'].value);
    if (this.contactForm.controls['subject'].status == 'INVALID') {
      this.exibeMsg = true;
      this.textoMsg = "Selecione o Assunto para realizar a busca!"
      return;
    }
    if (this.contactForm.controls['searchInput'].status == 'INVALID') {
      this.exibeMsg = true;
      this.textoMsg = "Digite o texto para realizar a busca!"
      return;
    }
    if(this.contactForm.controls['subject'].value == 'id'){
      if (isNaN(this.contactForm.controls['searchInput'].value)) {
        this.exibeMsg = true;
        this.textoMsg = "O campo de busca deve ser um número se você selecionar o id!"
        return;
      }
      
    }
    this.exibeMsg = false;
    this.searchTickets(this.contactForm.controls['subject'].value, this.contactForm.controls['searchInput'].value, this.contactForm.controls['email'].value, this.paginaAtual, 10);
  }

  onSubjectChange(){
    this.exibeMsg = false;
  }

  onSearchInputFocus(){
    this.exibeMsg = false;
  }
}
