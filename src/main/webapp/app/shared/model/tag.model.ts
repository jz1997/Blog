import { IBlog } from '@/shared/model/blog.model';

export interface ITag {
  id?: number;
  name?: string;
  blogs?: IBlog[] | null;
}

export class Tag implements ITag {
  constructor(public id?: number, public name?: string, public blogs?: IBlog[] | null) {}
}
