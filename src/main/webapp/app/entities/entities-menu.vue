<template>
  <div>
    <b-dropdown-item to="/blog">
      <font-awesome-icon icon="asterisk" />
      <span>Blog</span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span>Category</span>
    </b-dropdown-item>
    <b-dropdown-item to="/tag">
      <font-awesome-icon icon="asterisk" />
      <span>Tag</span>
    </b-dropdown-item>
    <b-dropdown-item to="/comment">
      <font-awesome-icon icon="asterisk" />
      <span>Comment</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
