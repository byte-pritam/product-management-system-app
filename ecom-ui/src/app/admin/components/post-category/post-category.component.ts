import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserStorageService } from '../../../services/storage/user-storage.service';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-post-category',
  templateUrl: './post-category.component.html',
  styleUrl: './post-category.component.scss'
})
export class PostCategoryComponent implements OnInit {

  categoryForm: FormGroup;

  constructor(
    private router: Router,
    private snackBar: MatSnackBar,
    private userStorage: UserStorageService,
    private formbuilder: FormBuilder,
    private adminService: AdminService
    ){

  }

  ngOnInit(): void {
    this.categoryForm = this.formbuilder.group({
      name:[null, Validators.required],
      description: [null, Validators.required]
  })
    
  }

  addCategory():void{

    if(this.categoryForm.valid){
      this.adminService.addCategory(this.categoryForm.value).subscribe((res)=>{
        if(res.id != null){
          this.snackBar.open("Category added successfully", 'close',{duration:5000});
          this.router.navigateByUrl('admin/dashboard');
        }else{
          this.snackBar.open(res.message, 'close',{duration:5000, panelClass:'error-snackbar'});
        }
      })

    }else{
      this.categoryForm.markAllAsTouched();
    }
  }

}
