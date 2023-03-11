import {IBlog} from '@/shared/model/blog.model';

export interface ICategory {
  id?: number;
  name?: string;
  blogs?: IBlog[] | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public blogs?: IBlog[] | null) {
  }
}
