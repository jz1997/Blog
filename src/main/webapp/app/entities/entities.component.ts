import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import BlogService from './blog/blog.service';
import CategoryService from './category/category.service';
import TagService from './tag/tag.service';
import CommentService from './comment/comment.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('blogService') private blogService = () => new BlogService();
  @Provide('categoryService') private categoryService = () => new CategoryService();
  @Provide('tagService') private tagService = () => new TagService();
  @Provide('commentService') private commentService = () => new CommentService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
