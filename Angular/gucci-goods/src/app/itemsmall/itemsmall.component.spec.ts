import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsmallComponent } from './itemsmall.component';

describe('ItemsmallComponent', () => {
  let component: ItemsmallComponent;
  let fixture: ComponentFixture<ItemsmallComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemsmallComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsmallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
