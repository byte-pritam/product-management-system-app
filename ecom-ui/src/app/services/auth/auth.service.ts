import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { UserStorageService } from '../storage/user-storage.service';

const BASIC_URL="http://localhost:8081/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
    private userStorage:UserStorageService
    ) { }

  register(signuprequest:any):Observable<any>{
    return this.http.post(BASIC_URL+"sign-up",signuprequest);
  }

  login(userName: string, password: string):any{
    const headers = new HttpHeaders().set('Content-Type','application/json');
    const body = {userName,password};


    return this.http.post(BASIC_URL+ 'authenticate', body, {headers, observe:'response' }).pipe(
      map((res) => { 
        const token = res.headers.get('Authorization').substring(7);
        console.log(token);
        const user = res.body;
        if(token && user){
          this.userStorage.saveToken(token);
          this.userStorage.saveUser(user);
          return true;

        }
        return false;
       
      })
    )

  }
}


