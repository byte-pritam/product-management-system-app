import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../services/storage/user-storage.service';

const BASIC_URL= "http://localhost:8081/";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient) { }

  addCategory(categoryDto:any): Observable<any>{

    return this.httpClient.post(BASIC_URL +'api/admin/category',categoryDto, {
      headers: this.createAuthorizationHeader()
    })
  }

  getAllCategories(): Observable<any>{

    return this.httpClient.get(BASIC_URL +'api/admin/categories', {
      headers: this.createAuthorizationHeader()
    })
  }

  addproduct(productDto:any):Observable<any>{

    return this.httpClient.post(BASIC_URL+"api/admin/product", productDto, 
    { headers: this.createAuthorizationHeader() })
  }

  getAllProducts(): Observable<any>{

    return this.httpClient.get(BASIC_URL +'api/admin/products', {
      headers: this.createAuthorizationHeader()
    })
  }

  getAllProductsByName(name:string): Observable<any>{

    return this.httpClient.get(BASIC_URL +`api/admin/search/${name}`, {
      headers: this.createAuthorizationHeader()
    })
  }

  private createAuthorizationHeader(): HttpHeaders{

    return new HttpHeaders().set(
      'Authorization', 'Bearer '+ UserStorageService.getToken()
    )
  }
}
