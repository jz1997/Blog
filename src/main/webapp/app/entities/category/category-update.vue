<template>
  <div class="card jh-card">
    <div class="row justify-content-center">
      <div class="col-8">
        <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
          <h2 id="blogApp.category.home.createOrEditLabel" data-cy="CategoryCreateUpdateHeading">Create or edit a
            Category</h2>
          <div>
            <div class="form-group" v-if="category.id">
              <label for="id">ID</label>
              <input type="text" class="form-control" id="id" name="id" v-model="category.id" readonly/>
            </div>
            <div class="form-group">
              <label class="form-control-label" for="category-name">Name</label>
              <input
                type="text"
                class="form-control"
                name="name"
                id="category-name"
                data-cy="name"
                :class="{ valid: !$v.category.name.$invalid, invalid: $v.category.name.$invalid }"
                v-model="$v.category.name.$model"
                required
              />
              <div v-if="$v.category.name.$anyDirty && $v.category.name.$invalid">
                <small class="form-text text-danger" v-if="!$v.category.name.required"> This field is required. </small>
                <small class="form-text text-danger" v-if="!$v.category.name.maxLength">
                  This field cannot be longer than 64 characters.
                </small>
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
              :disabled="$v.category.$invalid || isSaving"
              class="btn btn-primary"
            >
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./category-update.component.ts"></script>
