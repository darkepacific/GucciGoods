import { TestBed } from '@angular/core/testing';

import { ItemReviewService } from './ItemReview.service';

describe('ItemService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ItemReviewService = TestBed.get(ItemReviewService);
    expect(service).toBeTruthy();
  });
});
