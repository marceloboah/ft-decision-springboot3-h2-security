import { Component, OnInit, ElementRef  } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SocialAuthService } from "@abacritt/angularx-social-login";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Ticket } from '../interfaces/ticket';
import { BASE_API_URL } from '../interfaces/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tickets-list',
  templateUrl: './tickets-list.component.html',
  styleUrl: './tickets-list.component.scss'
})
export class TicketsListComponent implements OnInit {

  contactForm!: FormGroup;
  buttonColor: string = '#1e88e5'; 
  isButtonDisabled: boolean = false; 
  mostrarErroGeral: boolean = false; 
  mensagemErroGeral: string = '';
  mostrarSucesso: boolean = false; 
  mensagemSucesso : string = ''; 
  userName: string = '';
  userToken: string = '';
  tokenJWT: string = '';


  constructor(private readonly _authService: SocialAuthService, private formBuilder: FormBuilder, private elementRef: ElementRef, private http: HttpClient, private router: Router) {
    this.createContactForm();
  }
  

  ngOnInit() {
    this.userToken = localStorage.getItem('userToken') ?? '';
    if(this.userToken != ''){
      this.contactForm = this.formBuilder.group({
        nome: [localStorage.getItem('userName'), Validators.required],
        email: [localStorage.getItem('userEmail'), [Validators.required, Validators.email]],
        departamento: ['', Validators.required],
        tituloProblema: ['', Validators.required],
        descricaoProblema: ['', Validators.required],
        categoriaProblema: ['', Validators.required],
        prioridade: ['', Validators.required],
        statusAndamento: ['Aberto', Validators.required],
        dataAbertura: [this.getCurrentDate(), Validators.required],
        dataUltimaAtualizacao: [this.getCurrentDate(), Validators.required]
      });
      this.userName = localStorage.getItem('userName')+'';

    }else{
      this.logout();

    }
    
  }

  createContactForm(){
    this.contactForm = this.formBuilder.group({
      fullName: [''],  
      email: [''],
      message: ['']
    });
  }

  onSubmit() {
    this.mostrarErroGeral = false;
    this.mensagemErroGeral = '';    
    if (!this.validarCampo(this.contactForm.controls['nome'], 'Nome é obrigatório.')) return;
    if (!this.validarCampo(this.contactForm.controls['email'], 'E-mail é obrigatório.')) return;
    if (!this.validarCampo(this.contactForm.controls['departamento'], 'Departamento é obrigatório.')) return;
    if (!this.validarCampo(this.contactForm.controls['tituloProblema'], 'Título do problema é obrigatório.')) return;
    if (!this.validarCampo(this.contactForm.controls['descricaoProblema'], 'Descrição detalhada do problema é obrigatória.')) return;
    if (!this.validarCampo(this.contactForm.controls['categoriaProblema'], 'Categoria do problema é obrigatória.')) return;
    if (!this.validarCampo(this.contactForm.controls['prioridade'], 'Prioridade do chamado é obrigatória.')) return;
    if (!this.validarCampo(this.contactForm.controls['statusAndamento'], 'Status de andamento é obrigatório.')) return;
    if (!this.validarCampo(this.contactForm.controls['dataAbertura'], 'Data de abertura é obrigatória.')) return;
    if (!this.validarCampo(this.contactForm.controls['dataUltimaAtualizacao'], 'Data de última atualização é obrigatória.')) return;
  
      if (this.contactForm.valid) {      
        const formData = this.contactForm.value;
        const ticketDTO: Ticket = {
          id: 0,
          nome: formData.nome,
          email: formData.email,
          departamento: formData.departamento,
          tituloProblema: formData.tituloProblema,
          descricaoProblema: formData.descricaoProblema,
          categoriaProblema: formData.categoriaProblema,
          prioridade: formData.prioridade,
          statusAndamento: formData.statusAndamento,
          dataAbertura: formData.dataAbertura,
          dataUltimaAtualizacao: formData.dataUltimaAtualizacao,
          feedback: formData.feedback
        };

        this.tokenJWT = localStorage.getItem('userToken') ?? '';
        const headers = new HttpHeaders({ 'Access-Control-Allow-Origin': 'http://localhost:4200', 'Authorization': this.tokenJWT});
        this.http.post(BASE_API_URL+'/api/v1/ticket/create', ticketDTO, { headers: headers }).subscribe(
          (response) => {
            this.buttonColor = '#4caf50';
            this.mensagemSucesso = 'Enviado com sucesso:';
            this.mostrarSucesso = true;
            setTimeout(() => 
            {
              this.router.navigate(['/tickets-list-all']);
            },
            2000);         
          },
          (error) => {
            console.error('Erro ao enviar o ticket:', error);
          }
        );      
      } 
  }

  validarCampo(campo: any, mensagemErro: string): boolean {
    if (!campo || !campo.value || campo.value.trim() === '') {
      this.mostrarErroGeral = true;
      this.mensagemErroGeral = mensagemErro;
      const erroElement = this.elementRef.nativeElement.querySelector('.error-row');
      if (erroElement) {
        this.buttonColor = '#1e88e5';
        erroElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
        
      }
      return false; // Retorna falso indicando que a validação falhou
    }
    return true; // Retorna verdadeiro indicando que a validação foi bem sucedida
  }

  getCurrentDate(): string {
    const today = new Date();
    const year = today.getFullYear();
    const month = this.addZeroPrefix(today.getMonth() + 1);
    const day = this.addZeroPrefix(today.getDate());
    return `${year}-${month}-${day}`;
  }

  addZeroPrefix(number: number): string {
    return number < 10 ? `0${number}` : `${number}`;
  }

  signOut(): void {
    this._authService.signOut();
  }

  goToTable() {
    this.router.navigate(['/tickets-list-all']);
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

}
