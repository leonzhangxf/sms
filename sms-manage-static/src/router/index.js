import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import InitAuth from '../components/keycloak'

Vue.use(VueRouter);

const load = (component) => {
  return () => import(`@/views/${component}.vue`)
};

//router
const routeMap = [
  {
    path: '/',
    name: 'index',
    component: load('layout/index'),
    meta: {requiresAuth: true},
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'home',
      component: load('home/index'),
      meta: {requiresAuth: true, cacheView: false}
    }]
  },
  {
    path: '/channel',
    name: 'channel',
    component: load('layout/index'),
    meta: {
      requiresAuth: true,
      sideShow: true,
      title: '渠道管理',
      icon: 'ios-switch'
    },
    children: [
      {
        path: 'config',
        name: 'channel-config',
        component: load('channel/config/index'),
        meta: {
          requiresAuth: true,
          sideShow: true,
          title: '渠道配置管理',
          icon: 'md-battery-charging',
          cacheView: false
        }
      },
      {
        path: 'signature',
        name: 'channel-signature',
        component: load('channel/signature/index'),
        meta: {
          requiresAuth: true,
          sideShow: true,
          title: '渠道签名管理',
          icon: 'md-clipboard',
          cacheView: false
        }
      },
      {
        path: 'template',
        name: 'channel-template',
        component: load('channel/template/index'),
        meta: {
          requiresAuth: true,
          sideShow: true,
          title: '渠道模板管理',
          icon: 'md-pricetags',
          cacheView: false
        }
      }
    ]
  },
  {
    path: '/service',
    name: 'service',
    component: load('layout/index'),
    meta: {
      requiresAuth: true,
      sideShow: true,
      title: '服务管理',
      icon: 'ios-albums'
    },
    children: [
      {
        path: 'client',
        name: 'service-client',
        component: load('service/client/index'),
        meta: {
          requiresAuth: true,
          sideShow: true,
          title: '接入方管理',
          icon: 'md-link',
          cacheView: false
        }
      },
      {
        path: 'template',
        name: 'service-template',
        component: load('service/template/index'),
        meta: {
          requiresAuth: true,
          sideShow: true,
          title: '模板管理',
          icon: 'ios-copy',
          cacheView: false
        }
      },
      {
        path: 'log',
        name: 'service-log',
        component: load('service/log/index'),
        meta: {
          requiresAuth: true,
          sideShow: true,
          title: '发送记录查询',
          icon: 'md-book',
          cacheView: false
        }
      }
    ]
  },
  {
    path: '/404',
    name: '404',
    component: load('404'),
    meta: {requiresAuth: false}
  },
  {
    //其他所有未知跳转到首页
    path: '*',
    name: 'others',
    redirect: '/'
  }
];

const router = new VueRouter({
  routes: routeMap
});

//路由拦截，加入鉴权
router.beforeEach((to, from, next) => {

  if (to.meta.requiresAuth) {
    let keycloak = store.getters.keycloak;
    if (keycloak === undefined || !keycloak.authenticated) {
      //认证组件未初始化或未认证
      InitAuth(next);
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router