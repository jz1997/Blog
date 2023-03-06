import { IComment } from '@/shared/model/comment.model';
import { IUser } from '@/shared/model/user.model';
import { ITag } from '@/shared/model/tag.model';
import { ICategory } from '@/shared/model/category.model';

export interface IBlog {
  id?: number;
  title?: string;
  content?: string;
  comments?: IComment[] | null;
  author?: IUser | null;
  tags?: ITag[] | null;
  category?: ICategory | null;
}

export class Blog implements IBlog {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public comments?: IComment[] | null,
    public author?: IUser | null,
    public tags?: ITag[] | null,
    public category?: ICategory | null
  ) {}
}
