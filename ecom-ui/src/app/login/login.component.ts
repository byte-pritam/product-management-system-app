import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { first } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { UserStorageService } from '../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  hidePassword = true;
  loginForm : FormGroup;

  constructor(private fb:FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
    private userStorage: UserStorageService
    ){

  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email : [null, [Validators.required,Validators.email]],
      password : [null, Validators.required]
    })
  }


  togglePasswordVisibilty(){
    this.hidePassword = !this.hidePassword;
  }
  onSubmit(){
    const userName = this.loginForm.get('email')!.value;
    const password = this.loginForm.get('password')!.value;

    this.authService.login(userName,password)
    .subscribe(
      (response:HttpResponse<any>)=>{
        
        if(UserStorageService.isAdminLoggedIn){
          this.router.navigateByUrl('admin/dashboard')
        }else if(UserStorageService.isCustomerLoggedIn){
          this.router.navigateByUrl('customer/dashboard');
        }

        this.snackBar.open('Login successfull','Ok',{duration:5000});

      },
      (error)=>{
        console.log(error);
        this.snackBar.open('Bad Credentials','ERROR',{duration:5000});
      }
    )

  }

  

}
