import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from './App'
import store from './vuex/index'
// 多语言
import ElementUI from 'element-ui'
import VueI18n from 'vue-i18n'
import locale from 'element-ui/lib/locale'
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'

import {
  routes
} from './router/routerConfig'
import './assets/fonts/font-style'
import 'babel-polyfill'
import Es6Promise from 'es6-promise'
// 引入echarts
import echarts from 'echarts'
// 引入api
import {
  msWebApiRoot
} from './services/api'
import utils from './assets/js/utils'

Vue.config.productionTip = false
Vue.config.devtools = true
Vue.prototype.$echarts = echarts
Vue.config.debug = true // 开启错误提示
require('./assets/scss/common') // 加载全局公共样式
require('es6-promise').polyfill()
require('es6-promise/auto')
require('./assets/js/rem.js')

Es6Promise.polyfill()
Vue.use(VueRouter) // 加载路由
Vue.use(Vuex) // 加载全局状态管理

// 多语言
Vue.use(VueI18n)
Vue.use(ElementUI, {
  enLocale
})

// 记录滚动条的高度
const scrollBehavior = (to, from, savedPosition) => {
  if (savedPosition) {
    return savedPosition
  } else {
    const position = {}
    if (to.hash) {
      position.selector = to.hash
    }
    if (to.matched.some(m => m.meta.scrollToTop)) { // 路由中存在scrollToTop字段，则默认滚动条为0
      position.x = 0
      position.y = 0
    }
    return position
  }
}

// 实例化路由
const router = new VueRouter({
  // mode: 'history', // 去掉hash模式
  routes,
  scrollBehavior: scrollBehavior
})
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

const i18n = new VueI18n({
  locale: utils.getLanguage() && (utils.getLanguage() === 'cn' || utils.getLanguage() === 'en') ? utils.getLanguage() : (window.location.host.indexOf('.seeed.cn') !== -1 ? 'cn' : 'en'), // 语言标识,默认英语,先去缓存中查找，如果存在并有效，缓存值即为默认语言类型；否则默认为英语
  messages: {
    'en': Object.assign(require('./assets/lang/en'), enLocale),
    'cn': Object.assign(require('./assets/lang/cn'), zhLocale)
  }
})

locale.i18n((key, value) => i18n.t(key, value)) // 为了之后将element-ui组件本地化

const app = new Vue({
  i18n,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
window.Router = router
