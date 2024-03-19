import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  products:any[]=[];
  searchForm : FormGroup;

  constructor(private adminService: AdminService,
    private fb: FormBuilder
    ){

  }

  ngOnInit(): void {
    this.getAllProducts();
    this.searchForm = this.fb.group({
      title : [null, Validators.required]
    })

  }

  getAllProducts(){
    this.products=[];
    this.adminService.getAllProducts().subscribe(res=>{
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,'+ element.byteImg;
        this.products.push(element);
      });
    })
  }

  getAllProductsByName(){
    this.products=[];
    const title = this.searchForm.get('title')!.value;
    this.adminService.getAllProductsByName(title).subscribe(res=>{
      res.forEach(element => {
        element.processedImg = 'data:image/jpeg;base64,'+ element.byteImg;
        this.products.push(element);
      });
    })
  }

}
