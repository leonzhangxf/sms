import Vue from 'vue'
import router from './router'
import store from './store'
import App from './App'
import './components/iview'
import '@/styles/index.scss' // global scss

Vue.config.productionTip = false;
Vue.config.devtools = true;

new Vue({
  el: '#app',
  render: h => h(App),
  store,
  router,
  template: '<App/>',
  components: {App}
});
