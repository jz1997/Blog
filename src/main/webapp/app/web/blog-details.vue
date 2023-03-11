<template>
  <div class="jh-card">
    <div class="row justify-content-center">
      <div class="col-8">
        <div v-if="blog">
          <h2 class="jh-entity-heading" data-cy="blogDetailsHeading">
            <span>{{ blog.title }}</span>
          </h2>
          <div class="mt-4 d-flex justify-content-start flex-wrap">
            <div class="mr-3"><span>Author: </span>{{ blog && blog.author && blog.author.firstName }}</div>
            <div class="mr-3"><span>CreateDate: </span>{{ blog.createdDate | formatDate }}</div>
            <div class="mr-3"><span>Category: </span>{{ blog && blog.category && blog.category.name }}</div>
          </div>
          <div class="card mt-4">
            <div class="card-body">
              <vue-markdown :source="blog.content"></vue-markdown>
            </div>
          </div>

          <div class="d-flex flex-wrap mt-4">
            <div v-for="tag in blog.tags" :key="tag.id">
              <router-link :to="{ name: 'TagView', params: { tagId: tag.id } }">
                <span class="badge rounded-pill bg-info mr-1 text-light">{{ tag.name }}</span>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./blog-details.component.ts"></script>
