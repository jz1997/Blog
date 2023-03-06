import {Component, Vue, Inject} from 'vue-property-decorator';
import {required, maxLength} from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CommentService from '@/entities/comment/comment.service';
import {IComment} from '@/shared/model/comment.model';

import UserService from '@/entities/user/user.service';

import TagService from '@/entities/tag/tag.service';
import {ITag} from '@/shared/model/tag.model';

import CategoryService from '@/entities/category/category.service';
import {ICategory} from '@/shared/model/category.model';

import {IBlog, Blog} from '@/shared/model/blog.model';
import BlogService from './blog.service';

const validations: any = {
  blog: {
    title: {
      required,
      maxLength: maxLength(255),
    },
    content: {
      required,
    },
    tags: {
      required
    },
    category: {
      required
    }
  },
};

@Component({
  // @ts-ignore
  validations,
})
export default class BlogUpdate extends Vue {
  @Inject('blogService') private blogService: () => BlogService;
  @Inject('alertService') private alertService: () => AlertService;

  public blog: IBlog = new Blog();

  @Inject('commentService') private commentService: () => CommentService;

  public comments: IComment[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('tagService') private tagService: () => TagService;

  public tags: ITag[] = [];

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.blogId) {
        vm.retrieveBlog(to.params.blogId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.blog.tags = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.blog.id) {
      this.blogService()
        .update(this.blog)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Blog is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.blogService()
        .create(this.blog)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Blog is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveBlog(blogId): void {
    this.blogService()
      .find(blogId)
      .then(res => {
        this.blog = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.commentService()
      .retrieve()
      .then(res => {
        this.comments = res.data;
      });

    this.tagService()
      .retrieve()
      .then(res => {
        this.tags = res.data;
      });
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      return selectedVals.find(value => option.id === value.id) ?? option;
    }
    return option;
  }
}
