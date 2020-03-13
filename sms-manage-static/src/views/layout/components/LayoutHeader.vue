<template>

    <Header style="height: 80px;" class="header">
        <div class="header-home">
            <router-link to="/home">
                <span class="header-title">SMS 短信服务</span>
            </router-link>
        </div>

        <HeaderBreadcrumb></HeaderBreadcrumb>

        <div class="header-user">
            <Dropdown trigger="click" @on-click="ifLogout">
                <a href="javascript:void(0)">
                    <Avatar size="large" style="background: #2a93a5;">
                        <span class="header-user-name">{{name}}</span>
                    </Avatar>
                    <Icon type="ios-arrow-down"></Icon>
                </a>
                <DropdownMenu slot="list" class="header-user-options">
                    <DropdownItem name="logout">
                        <span>退出</span>
                    </DropdownItem>
                </DropdownMenu>
            </Dropdown>
        </div>
    </Header>

</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import HeaderBreadcrumb from '@/components/breadcrumb'

  export default {
    name: 'LayoutHeader',
    components: {
      HeaderBreadcrumb
    },
    computed: {
      ...mapGetters([
        'name'
      ])
    },
    methods: {
      ...mapActions([
        'logout'
      ]),
      ifLogout(name) {
        if (name && name === 'logout') {
          console.log('LOGOUT');
          this.logoutKeycloak();
        }
      },
      logoutKeycloak() {
        try {
          this.$store.getters.keycloak.logout();
          this.logout();
        } catch (ex) {
          //存在 Cannot read property 'token' of null 异常，当调用keycloak登出时
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .header {
        height: 80px;

        &-home {
            text-align: left;
            margin-left: -27px;
        }

        &-title {
            color: azure;
            font-size: 25px;
            float: left;
        }

        &-user {
            float: right;
            margin-right: -40px;

            &-name {
                color: #e1eac6;
            }

            &-options {
                text-align: center;
            }
        }
    }

</style>