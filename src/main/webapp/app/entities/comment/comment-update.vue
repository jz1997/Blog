<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="blogApp.comment.home.createOrEditLabel" data-cy="CommentCreateUpdateHeading">Create or edit a Comment</h2>
        <div>
          <div class="form-group" v-if="comment.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="comment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-nickname">Nickname</label>
            <input
              type="text"
              class="form-control"
              name="nickname"
              id="comment-nickname"
              data-cy="nickname"
              :class="{ valid: !$v.comment.nickname.$invalid, invalid: $v.comment.nickname.$invalid }"
              v-model="$v.comment.nickname.$model"
              required
            />
            <div v-if="$v.comment.nickname.$anyDirty && $v.comment.nickname.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.nickname.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.comment.nickname.maxLength">
                This field cannot be longer than 64 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="comment-email"
              data-cy="email"
              :class="{ valid: !$v.comment.email.$invalid, invalid: $v.comment.email.$invalid }"
              v-model="$v.comment.email.$model"
              required
            />
            <div v-if="$v.comment.email.$anyDirty && $v.comment.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.email.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.comment.email.maxLength">
                This field cannot be longer than 255 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-content">Content</label>
            <input
              type="text"
              class="form-control"
              name="content"
              id="comment-content"
              data-cy="content"
              :class="{ valid: !$v.comment.content.$invalid, invalid: $v.comment.content.$invalid }"
              v-model="$v.comment.content.$model"
              required
            />
            <div v-if="$v.comment.content.$anyDirty && $v.comment.content.$invalid">
              <small class="form-text text-danger" v-if="!$v.comment.content.required"> This field is required. </small>
              <small class="form-text text-danger" v-if="!$v.comment.content.maxLength">
                This field cannot be longer than 1024 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-referenceComment">Reference Comment</label>
            <select
              class="form-control"
              id="comment-referenceComment"
              data-cy="referenceComment"
              name="referenceComment"
              v-model="comment.referenceComment"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  comment.referenceComment && commentOption.id === comment.referenceComment.id ? comment.referenceComment : commentOption
                "
                v-for="commentOption in comments"
                :key="commentOption.id"
              >
                {{ commentOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="comment-blog">Blog</label>
            <select class="form-control" id="comment-blog" data-cy="blog" name="blog" v-model="comment.blog">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.blog && blogOption.id === comment.blog.id ? comment.blog : blogOption"
                v-for="blogOption in blogs"
                :key="blogOption.id"
              >
                {{ blogOption.title }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.comment.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./comment-update.component.ts"></script>
