<template>
  <div class="card jh-card">
    <div class="row justify-content-center">
      <div class="col-8">
        <div v-if="comment">
          <h2 class="jh-entity-heading" data-cy="commentDetailsHeading"><span>Comment</span> {{ comment.id }}</h2>
          <dl class="row jh-entity-details">
            <dt>
              <span>Nickname</span>
            </dt>
            <dd>
              <span>{{ comment.nickname }}</span>
            </dd>
            <dt>
              <span>Email</span>
            </dt>
            <dd>
              <span>{{ comment.email }}</span>
            </dd>
            <dt>
              <span>Content</span>
            </dt>
            <dd>
              <span>{{ comment.content }}</span>
            </dd>
            <dt>
              <span>Reference Comment</span>
            </dt>
            <dd>
              <div v-if="comment.referenceComment">
                <router-link :to="{ name: 'CommentView', params: { commentId: comment.referenceComment.id } }">{{
                    comment.referenceComment.id
                  }}
                </router-link>
              </div>
            </dd>
            <dt>
              <span>Blog</span>
            </dt>
            <dd>
              <div v-if="comment.blog">
                <router-link :to="{ name: 'BlogView', params: { blogId: comment.blog.id } }">{{
                    comment.blog.title
                  }}
                </router-link>
              </div>
            </dd>
          </dl>
          <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info"
                  data-cy="entityDetailsBackButton">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
          </button>
          <router-link v-if="comment.id" :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom
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

<script lang="ts" src="./comment-details.component.ts"></script>
