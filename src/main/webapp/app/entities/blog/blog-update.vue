<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="blogApp.blog.home.createOrEditLabel" data-cy="BlogCreateUpdateHeading">Create or edit a Blog</h2>
        <div>
          <div class="form-group" v-if="blog.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="blog.id" readonly/>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="blog-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="blog-title"
              data-cy="title"
              :class="{ valid: !$v.blog.title.$invalid, invalid: $v.blog.title.$invalid }"
              v-model="$v.blog.title.$model"
              required
            />
            <div v-if="$v.blog.title.$anyDirty && $v.blog.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.blog.title.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.blog.title.maxLength">
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="blog-content">Content</label>
            <textarea
              class="form-control"
              name="content"
              id="blog-content"
              data-cy="content"
              :class="{ valid: !$v.blog.content.$invalid, invalid: $v.blog.content.$invalid }"
              v-model="$v.blog.content.$model"
              required
              :rows="10"
            />
            <div v-if="$v.blog.content.$anyDirty && $v.blog.content.$invalid">
              <small class="form-text text-danger" v-if="!$v.blog.content.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label for="blog-tag">Tag</label>
            <select
              class="form-control"
              id="blog-tags"
              data-cy="tag"
              multiple
              name="tag"
              :class="{ valid: !$v.blog.tags.$invalid, invalid: $v.blog.tags.$invalid }"
              v-if="blog.tags !== undefined"
              v-model="blog.tags"
              required
            >
              <option v-bind:value="getSelected(blog.tags, tagOption)" v-for="tagOption in tags" :key="tagOption.id">
                {{ tagOption.name }}
              </option>
            </select>
            <div v-if="$v.blog.tags.$anyDirty && $v.blog.tags.$invalid">
              <small class="form-text text-danger" v-if="!$v.blog.tags.required"> This field is required. </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="blog-category">Category</label>
            <select class="form-control"
                    id="blog-category"
                    data-cy="category"
                    name="category"
                    v-model="blog.category"
                    :class="{ valid: !$v.blog.category.$invalid, invalid: $v.blog.category.$invalid }"
                    required
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="blog.category && categoryOption.id === blog.category.id ? blog.category : categoryOption"
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.name }}
              </option>
            </select>
            <div v-if="$v.blog.category.$anyDirty && $v.blog.category.$invalid">
              <small class="form-text text-danger" v-if="!$v.blog.category.required"> This field is required. </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary"
                  v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.blog.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./blog-update.component.ts"></script>
