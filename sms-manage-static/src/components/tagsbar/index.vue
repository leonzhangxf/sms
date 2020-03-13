<template>

    <div class="tags-bar">
        <Tabs v-model="currentTab" closable type="card" @on-click="targetView" @on-tab-remove="closeSelectedTag">

            <template v-for="view in visitedViews" v-if="visitedViews && visitedViews.length > 0">
                <TabPane :label="view.title" :name="view.name" :key="view.name"></TabPane>
            </template>

        </Tabs>
    </div>

</template>

<script>
  import {mapGetters} from 'vuex'
  import {ADD_VISITED_VIEW, DEL_VISITED_VIEW} from '../../store/types'

  export default {
    name: 'TagsBar',
    data() {
      return {
        currentTab: ''
      }
    },
    computed: {
      ...mapGetters([
        'visitedViews'
      ])
    },
    mounted() {
      this.addViewTags();
    },
    watch: {
      $route() {
        this.addViewTags();
        this.moveToCurrentTag();
      }
    },
    methods: {
      generateRoute() {
        if (this.$route.name && this.$route.name !== 'home') {
          return this.$route
        }
        return false
      },
      isActive(viewName) {
        return viewName === this.$route.name;
      },
      addViewTags() {
        const route = this.generateRoute();
        if (!route) {
          return false;
        }
        this.$store.dispatch(ADD_VISITED_VIEW, route);
      },
      moveToCurrentTag() {
        this.currentTab = this.$route.name;
      },
      targetView(name) {
        this.$router.push({'name': name});
      },
      closeSelectedTag(viewName) {

        this.$store.dispatch(DEL_VISITED_VIEW, {name: viewName}).then((views) => {

          if (this.isActive(viewName)) {
            const latestView = views.slice(-1)[0];
            if (latestView) {
              this.$router.push({name: latestView.name});
            } else {
              this.$router.push('/');
            }
          }

        });
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .tags-bar {
        float: left;
        margin-left: 19px;
        margin-top: 8px;
    }

</style>