import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import BlogService from '@/entities/blog/blog.service';
import { IBlog } from '@/shared/model/blog.model';

import { IComment, Comment } from '@/shared/model/comment.model';
import CommentService from './comment.service';

const validations: any = {
  comment: {
    nickname: {
      required,
      maxLength: maxLength(64),
    },
    email: {
      required,
      maxLength: maxLength(255),
    },
    content: {
      required,
      maxLength: maxLength(1024),
    },
  },
};

@Component({
  // @ts-ignore
  validations,
})
export default class CommentUpdate extends Vue {
  @Inject('commentService') private commentService: () => CommentService;
  @Inject('alertService') private alertService: () => AlertService;

  public comment: IComment = new Comment();

  public comments: IComment[] = [];

  @Inject('blogService') private blogService: () => BlogService;

  public blogs: IBlog[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.commentId) {
        vm.retrieveComment(to.params.commentId);
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
  }

  public save(): void {
    this.isSaving = true;
    if (this.comment.id) {
      this.commentService()
        .update(this.comment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Comment is updated with identifier ' + param.id;
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
      this.commentService()
        .create(this.comment)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A Comment is created with identifier ' + param.id;
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

  public retrieveComment(commentId): void {
    this.commentService()
      .find(commentId)
      .then(res => {
        this.comment = res;
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
    this.blogService()
      .retrieve()
      .then(res => {
        this.blogs = res.data;
      });
  }
}
