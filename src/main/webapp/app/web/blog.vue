<template>
  <div class="jh-card">
    <!-- Categpry Title -->
    <div v-if="selectedCategory" class="row mb-3">
      <div class="col-12">
        <h2>{{ selectedCategory.name }}</h2>
      </div>
    </div>
    <div class="row">
      <div class="col-9">
        <template v-for="(blog, index) in blogs">
          <div class="card border-secondary hand mb-3">
            <div class="card-body">
              <router-link :to="{ name: 'WebBlogDetail', params: { blogId: blog.id } }">
                <h4 class="card-title">{{ blog.title }}</h4>
              </router-link>
              <p class="card-text">{{ blog.description }}</p>
            </div>
          </div>
        </template>

        <!-- Pagination -->
        <div v-show="blogs && blogs.length > 0">
          <div class="row justify-content-center">
            <jhi-item-count :page="page" :total="totalItems" :itemsPerPage="itemsPerPage"></jhi-item-count>
          </div>
          <div class="row justify-content-center">
            <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
          </div>
        </div>
      </div>
      <div class="col-3">
        <div class="card">
          <div class="card-body">
            <h4 class="card-title">Category</h4>
            <ul class="list-group">
              <li v-for="(item, index) in categories"
                  @click="onCategoryClick(item)"
                  class="list-group-item d-flex justify-content-between align-items-center hand">
                {{ item.name }}
                <span class="badge bg-primary rounded-pill">{{ item.blogs ? item.blogs.length : 0 }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./blog.component.ts"></script>
