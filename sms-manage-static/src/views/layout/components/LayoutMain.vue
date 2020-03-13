<template>

    <Layout class="main">
        <div class="main-top-bar">
            <Icon v-on:click="collapsedSider" :class="rotateIcon" type="md-menu" size="25"></Icon>

            <TagsBar></TagsBar>
        </div>

        <Content class="main-content">
            <!-- 动画过度组件 -->
            <transition name="fade" mode="out-in">
                <!-- 页面缓存组件 -->
                <keep-alive :include="cachedViews">
                    <router-view></router-view>
                </keep-alive>
            </transition>
        </Content>
    </Layout>

</template>

<script>
  import {TOGGLE_COLLAPSE} from '../../../store/types'
  import {mapGetters} from 'vuex'
  import TagsBar from '@/components/tagsbar'

  export default {
    name: 'LayoutMain',
    components: {
      TagsBar
    },
    computed: {
      ...mapGetters([
        'siderCollapsed',
        'cachedViews'
      ]),
      rotateIcon() {
        return [
          'main-toggle-bar menu-icon',
          this.siderCollapsed ? 'main-rotate-icon' : ''
        ];
      }
    },
    methods: {
      collapsedSider() {
        this.$store.dispatch(TOGGLE_COLLAPSE);
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .main {
        padding: 0 24px 24px;
        height: 100%;

        &-content {
            padding: 24px;
            background: #fff;
        }

        &-top-bar {
            text-align: left;
            color: #5f6969;
            margin: 0 20px;
            height: 50px;
            padding-top: 10px;
        }

        &-toggle-bar {
            float: left;
        }

        &-rotate-icon {
            transform: rotate(-90deg);
        }
    }
</style>