<template>

    <div class="header-breadcrumb">

        <Breadcrumb>
            <transition-group name="breadcrumb">
                <template v-for="(item,index) in levelList">
                    <BreadcrumbItem :key="item.path" v-if="item.meta.title">
                        <span class="header-breadcrumb-item" v-if="!item.redirect || index === levelList.length - 1">
                            / {{item.meta.title}}
                        </span>
                    </BreadcrumbItem>
                </template>
            </transition-group>
        </Breadcrumb>

    </div>

</template>

<script>
  export default {
    created() {
      this.getBreadcrumb()
    },
    data() {
      return {
        levelList: null
      }
    },
    watch: {
      $route() {
        this.getBreadcrumb()
      }
    },
    methods: {
      getBreadcrumb() {
        this.levelList = this.$route.matched;
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .header-breadcrumb {
        float: left;
        margin-left: 11px;

        &-item {
            color: azure;
            margin-left: 5px;
        }
    }
</style>