import { IBlog } from '@/shared/model/blog.model';

export interface IComment {
  id?: number;
  nickname?: string;
  email?: string;
  content?: string;
  referenceComment?: IComment | null;
  blog?: IBlog | null;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public nickname?: string,
    public email?: string,
    public content?: string,
    public referenceComment?: IComment | null,
    public blog?: IBlog | null
  ) {}
}
