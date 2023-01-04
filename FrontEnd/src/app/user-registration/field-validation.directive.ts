import {Directive, ElementRef, Input, Renderer2} from '@angular/core';
import {FormGroupDirective} from "@angular/forms";

@Directive({
  selector: '[appFieldValidation]'
})
export class FieldValidationDirective {
  @Input() fieldName: string = "";

  constructor(private el: ElementRef,
              private renderer: Renderer2) { }

  ngOnInit() {
    const formControl = this.el.nativeElement;
    this.renderer.setAttribute(formControl, 'ngClass',
      ""
    );
  }
}
