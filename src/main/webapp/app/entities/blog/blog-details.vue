<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="blog">
        <h2 class="jh-entity-heading" data-cy="blogDetailsHeading"><span>{{ blog.title }}</span></h2>

        <div class="mt-4 d-flex justify-content-start flex-wrap">
          <div class="mr-3"><span>Author: </span>{{ blog && blog.author && blog.author.firstName }}</div>
          <div class="mr-3"><span>CreateDate: </span>{{ blog.createdDate | formatDate }}</div>
          <div class="mr-3"><span>Category: </span>{{ blog && blog.category && blog.category.name }}</div>
        </div>
        <div class="card mt-4">
          <div class="card-body">
            <vue-markdown :source="blog.content"></vue-markdown>
          </div>
          <!--<dd>-->
          <!--</dd>-->
          <!--<dt>-->
          <!--  <span>Author</span>-->
          <!--</dt>-->
          <!--<dd>-->
          <!--  {{ blog.author ? blog.author.firstName : '' }}-->
          <!--</dd>-->
          <!--<dt>-->
          <!--  <span>Tag</span>-->
          <!--</dt>-->
          <!--<dd>-->
          <!--  <span v-for="(tag, i) in blog.tags" :key="tag.id"-->
          <!--  >{{ i > 0 ? ', ' : '' }}-->
          <!--    <router-link :to="{ name: 'TagView', params: { tagId: tag.id } }">{{ tag.name }}</router-link>-->
          <!--  </span>-->
          <!--</dd>-->
          <!--<dt>-->
          <!--  <span>Category</span>-->
          <!--</dt>-->
          <!--<dd>-->
          <!--  <div v-if="blog.category">-->
          <!--    <router-link :to="{ name: 'CategoryView', params: { categoryId: blog.category.id } }">-->
          <!--      {{ blog.category.name }}-->
          <!--    </router-link>-->
          <!--  </div>-->
          <!--</dd>-->
        </div>

        <div class="d-flex flex-wrap mt-4">
          <div v-for="(tag, i) in blog.tags">
            <router-link :to="{ name: 'TagView', params: { tagId: tag.id } }">
              <span class="badge rounded-pill bg-info mr-1 text-light">{{ tag.name }}</span>
            </router-link>
          </div>

        </div>

        <div class="mt-4">
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info"
                  data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
          </button>
          <router-link v-if="blog.id" :to="{ name: 'BlogEdit', params: { blogId: blog.id } }" custom
                       v-slot="{ navigate }">
            <button @click="navigate" class="btn btn-primary">
              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./blog-details.component.ts"></script>
