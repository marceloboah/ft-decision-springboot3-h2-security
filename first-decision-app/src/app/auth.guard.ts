import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';


export const authGuard: CanActivateFn = (route, state) => {


  const router = inject(Router);
  const tokenUser = typeof window !== "undefined" ? window.localStorage.getItem('userName') : false;

  if(tokenUser != null){
    return true;
  }else{
    router.navigate(['/']);
    return false;
  }

  
  
};
