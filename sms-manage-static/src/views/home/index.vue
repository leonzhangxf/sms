<template>
    <div class="home-container">
        <div class="home-text">{{name}} 欢迎访问短信服务管理系统=。=</div>
    </div>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'home',
    data() {
      return {
      }
    },
    computed: {
      ...mapGetters([
        'name'
      ])
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .home {
        &-container {
            margin: 30px;
        }
        &-text {
            font-size: 30px;
            line-height: 46px;
        }
    }
</style>
