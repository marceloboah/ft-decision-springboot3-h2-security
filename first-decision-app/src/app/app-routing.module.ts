import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TicketsListComponent } from './tickets-list/tickets-list.component';
import { authGuard } from './auth.guard';
import { TicketsListAllTableComponent } from './tickets-list-all-table/tickets-list-all-table.component';
import { ViewTicketComponent } from './view-ticket/view-ticket.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'tickets-list', component: TicketsListComponent, canActivate: [authGuard] },
  { path: 'tickets-list-all', component: TicketsListAllTableComponent, canActivate: [authGuard] },
  { path: 'view-ticket', component: ViewTicketComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
