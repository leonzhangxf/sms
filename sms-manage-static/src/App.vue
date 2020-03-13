<template>
    <div id="app">

        <router-view></router-view>

    </div>
</template>

<script>
  export default {
    name: 'app',
    created() {
      //全局配置iView消息提示
      this.$Message.config({
        duration: 2.5
      });
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
        height: 100%;
    }
</style>
