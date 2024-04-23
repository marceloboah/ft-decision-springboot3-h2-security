import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BASE_API_URL, IS_EMAIL_SERVER_ENABLE } from '../interfaces/environment';
import { Ticket } from '../interfaces/ticket';
import { SocialAuthService } from '@abacritt/angularx-social-login';
import { EmailService } from '../email.service';

@Component({
  selector: 'app-view-ticket',
  templateUrl: './view-ticket.component.html',
  styleUrl: './view-ticket.component.scss'
})
export class ViewTicketComponent {
  buttonColor: string = '#1e88e5'; 
  isButtonDisabled: boolean = false; 
  mostrarErroGeral: boolean = false; 
  mensagemErroGeral: string = ''; 
  mostrarSucesso: boolean = false; 
  mensagemSucesso : string = ''; 
  userName: string = '';
  ticketId: string = '';
  ticket?: Ticket;
  name?: string;
  email?: string;
  message?: string;
  isButtonVisible: boolean = true;
  userToken: string = '';
  tokenJWT: string = '';

  constructor(private readonly _authService: SocialAuthService, private route: ActivatedRoute, private router: Router, private http: HttpClient, private emailService: EmailService) {

    const ticket: Ticket = {
      id: 0,
      nome: '',
      email: '',
      departamento: '',
      tituloProblema: '',
      descricaoProblema: '',
      categoriaProblema: '',
      prioridade: '',
      statusAndamento: '',
      dataAbertura: '',
      dataUltimaAtualizacao: '',
      feedback: ''
    };

    this.ticket = ticket;
   }

  ngOnInit() {
    this.userToken = localStorage.getItem('userToken') ?? '';
    if(this.userToken != ''){

      this.route.queryParams.subscribe(params => {
        this.isButtonVisible= true;
        const ticketId = params['ticketId'];
        this.ticketId = ticketId;
        this.getData();
      });
    }else{
      this.logout();
    }
  }

  goToTable(){
    this.router.navigate(['/tickets-list-all']);
  }
  signOut() {
    this._authService.signOut();
  }

  goToCreateNew() {
    this.router.navigate(['/tickets-list']);
  }

  getData(){
    this.tokenJWT = localStorage.getItem('userToken') ?? '';
    const headers = new HttpHeaders({ 'Access-Control-Allow-Origin': 'http://localhost:4200', 'Authorization': this.tokenJWT});
    this.http.get<any>(`${BASE_API_URL}/api/v1/ticket/get-by-id/${this.ticketId}`)
    .subscribe(response => {
        const formData = response;
        const ticket: Ticket = {
          id: formData.id,
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
        this.ticket = ticket;
        if(this.ticket.statusAndamento == 'Encerrado'){
          this.isButtonVisible = false;
        }
      },
      (error) => {
        console.error('Erro ao enviar o ticket:', error);
      }
    );
  }

  toRate(id:number, rate: string){
    this.populateUpdate(id, rate, ''); 
  }

  toFinish(id:any){
    this.populateUpdate(id, '', "Encerrado");
  }

  populateUpdate(id:any, rate: string, status: string){
    const ticket: Ticket = {
      id: id,
      nome: '',
      email: '',
      departamento: '',
      tituloProblema: '',
      descricaoProblema: '',
      categoriaProblema: '',
      prioridade: '',
      statusAndamento: status,
      dataAbertura: '',
      dataUltimaAtualizacao: '',
      feedback: rate
    };
    this.ticket = ticket;
    this.updateTicket(this.ticket);
  }

  updateTicket(ticketDTO: Ticket) {
    this.tokenJWT = localStorage.getItem('userToken') ?? '';
    const headers = new HttpHeaders({ 'Authorization': this.tokenJWT, 'Access-Control-Allow-Origin': 'http://localhost:4200', 'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT' });
    const url = `${BASE_API_URL}/api/v1/ticket/edit/${ticketDTO.id}`;
    this.http.put<any>(url, ticketDTO, { headers: headers }).subscribe((response) => {
      this.buttonColor = '#4caf50'; // Altera a cor do botão para verde
            this.mensagemSucesso = 'Enviado com sucesso:';
            this.mostrarSucesso = true;
            this.email = this.name = ticketDTO.email;
            this.message = "Ticket id = "+ ticketDTO.id +" atualizado!";
            if(IS_EMAIL_SERVER_ENABLE){ // Se o servidor de email estiver habilitado
              this.emailService.sendEmail(this.name, this.email, this.message).subscribe(
                response => {
                  console.log('Email sent successfully!');
                  setTimeout(() => 
                    {
                      this.router.navigate(['/tickets-list-all']);
                    },
                    2000);
                },
                error => {
                  console.log('Error sending email:', error);
                }
              );
              }else{
              setTimeout(() => 
                    {
                      this.router.navigate(['/tickets-list-all']);
                    },
                    2000);
            }
    },
    (error) => {
      console.error('Erro ao atualizar ticket:', error);
    });
  }

  logout(){
      localStorage.removeItem('userName');
      localStorage.removeItem('userEmail');
      localStorage.removeItem('userId');
      localStorage.removeItem('userToken');
      localStorage.removeItem('tokenJWT');
      this.router.navigate(['/']);
  }
  
}
