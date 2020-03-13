<template>

    <div class="sider">
        <Menu theme="dark" width="auto" :class="menuitemClasses">

            <template v-for="item in routes" v-if="item.children && item.meta.sideShow">

                <MenuItem v-if="item.children.length <= 1 && !item.children[0].children"
                          :name="item.children[0].name" :to="item.path + '/' + item.children[0].path">
                    <Icon v-if="item.children[0].meta && item.children[0].meta.icon"
                          :type="item.children[0].meta.icon"
                          v-on:click="noCollapsed"></Icon>
                    <span v-if="item.children[0].meta && item.children[0].meta.title">
                            {{item.children[0].meta.title}}
                    </span>
                </MenuItem>

                <Submenu v-else :name="item.name">
                    <template slot="title">
                        <Icon v-if="item.meta && item.meta.icon" :type="item.meta.icon" v-on:click="noCollapsed"></Icon>
                        <span v-if="item.meta && item.meta.title">{{ item.meta.title }}</span>
                    </template>
                    <template v-for="child in item.children" v-if="child.meta.sideShow">

                        <layout-sider v-if="child.children && child.children.length > 0"
                                      :routes="[child]">
                        </layout-sider>

                        <MenuItem v-else :name="child.name" :to="item.path + '/' + child.path">
                            <Icon v-if="child.meta && child.meta.icon"
                                  :type="child.meta.icon"></Icon>
                            <span v-if="child.meta && child.meta.title" :title="child.meta.title">
                                {{child.meta.title}}
                            </span>
                        </MenuItem>
                    </template>

                </Submenu>

            </template>

        </Menu>
    </div>

</template>

<script>
  import {mapGetters} from 'vuex'
  import {TOGGLE_COLLAPSE} from '../../../store/types'

  export default {
    name: 'LayoutSider',
    props: {
      routes: {
        type: Array
      }
    },
    computed: {
      ...mapGetters([
        'siderCollapsed'
      ]),
      menuitemClasses() {
        return [
          'menu-item',
          this.siderCollapsed ? 'collapsed-menu' : ''
        ]
      }
    },
    methods: {
      noCollapsed() {
        if (this.siderCollapsed) {
          this.$store.dispatch(TOGGLE_COLLAPSE, false);
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
    .menu-icon {
        transition: all .3s;
    }

    .menu-item {
        span {
            display: inline-block;
            overflow: hidden;
            width: 90px;
            text-overflow: ellipsis;
            white-space: nowrap;
            vertical-align: bottom;
            transition: width .2s ease .2s;
        }

        i {
            transform: translateX(0px);
            transition: font-size .2s ease, transform .2s ease;
            vertical-align: middle;
            font-size: 16px;
        }
    }

    .collapsed-menu {
        span {
            width: 0;
            transition: width .2s ease;
        }

        i {
            transform: translateX(5px);
            transition: font-size .2s ease .2s, transform .2s ease .2s;
            vertical-align: middle;
            font-size: 28px;
        }

        ul {
            display: none;
        }

        .ivu-menu-submenu-title-icon {
            display: none;
        }
    }

</style>

