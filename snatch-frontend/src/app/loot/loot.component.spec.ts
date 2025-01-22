import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LootComponent } from './loot.component';

describe('LootComponent', () => {
  let component: LootComponent;
  let fixture: ComponentFixture<LootComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LootComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LootComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
