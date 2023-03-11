import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBlog } from '@/shared/model/blog.model';
import { ICategory } from '@/shared/model/category.model';
import BlogService from '@/entities/blog/blog.service';
import TagService from '@/entities/tag/tag.service';
import CategoryService from '@/entities/category/category.service';

import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Blog extends Vue {
  @Inject('blogService') private blogService: () => BlogService;
  @Inject('tagService') private tagService: () => TagService;
  @Inject('categoryService') private categoryService: () => CategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public page = 1;
  public previousPage = 1;
  public itemsPerPage = 20;
  public sort = ['createdDate,desc'];
  public totalItems = 0;

  public selectedCategory: ICategory = null;
  public blogs: IBlog[] = [];
  public categories: ICategory[] = [];

  public mounted(): void {
    this.retrieveAllCategories();
    this.retrieveBlogs();
  }

  /**
   * 查询全部的分类
   */
  public retrieveAllCategories(): void {
    this.categoryService()
      .retrieve({
        page: 0,
        size: 1000000,
        sort: ['name,asc'],
      })
      .then(
        res => {
          this.categories = res.data;
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public retrieveBlogs(): void {
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort,
    };
    this.blogService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.blogs = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
        },
          err => {
            this.alertService().showHttpError(this, err.response);
          };
      );
  }

  // 分页请求
  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.retrieveBlogs();
    }
  }

  public onCategoryClick(category): void {
    this.selectedCategory = category;
  }
}
