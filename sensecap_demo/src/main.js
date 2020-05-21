import 'babel-polyfill'
import Es6Promise from 'es6-promise'
require('es6-promise').polyfill()
require('es6-promise/auto')
Es6Promise.polyfill()

import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Axios from 'axios'
import App from './App'
import store from './vuex/index'

// 多语言
const ElementUI = () =>
  import('element-ui')
import VueI18n from 'vue-i18n'
import locale from 'element-ui/lib/locale'
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'

import {
  routes
} from './router/routerConfig'
import {
  msWebApiRoot
} from './services/api'
import {
  ajax
} from "Services/ajax"
import utils from 'Assets/js/utils'

// 引入echarts
import echarts from 'echarts'

Vue.config.productionTip = false
Vue.config.devtools = true
Vue.prototype.$echarts = echarts
Vue.config.debug = true // 开启错误提示

require('Assets/fonts/font-style')
require('Assets/scss/common') // 加载全局公共样式
require('Assets/js/rem.js')

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


// 响应超时配置
Axios.defaults.retry = 2
Axios.defaults.retryDelay = 3000
Axios.defaults.crossDomain = true
Axios.interceptors.request.use(function(config) {
  // 用于路由切换前 中断上一个路由的ajax请求
  config.cancelToken = new Axios.CancelToken(cancel => {
    window.cancelTokens.push({
      cancel
    })
  })
  return config
}, function(error) {
  // 请求失败的处理
  return Promise.reject(error)
})

// ajax响应超时设置
Axios.interceptors.response.use(undefined, function axiosRetryInterceptor(err) {
  var config = err.config
  if (!config || !config.retry) return Promise.reject(err)
  config.__retryCount = config.__retryCount || 0
  if (config.__retryCount >= config.retry) {
    return Promise.reject(err)
  }
  config.__retryCount += 1
  var backoff = new Promise(function(resolve) {
    setTimeout(function() {
      resolve()
    }, config.retryDelay || 1)
  })
  return backoff.then(function() {
    return Axios(config)
  })
})


router.beforeEach((to, from, next) => {
  // 路由跳转前 关闭上一页所有ajax请求
  if (window.cancelTokens && window.cancelTokens.length > 0) {
    for (var i = 0; i < window.cancelTokens.length; i++) {
      if (window.cancelTokens[i].cancel()) {
        window.cancelTokens[i].cancel()
        delete window.cancelTokens[i]
      }
    }
  }
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

const i18n = new VueI18n({
  locale: utils.getLanguage() && (utils.getLanguage() === 'cn' || utils.getLanguage() === 'en') ? utils.getLanguage() : (window.location.host.indexOf('.seeed.cn') !== -1 ? 'cn' : 'en'), // 语言标识,默认英语,先去缓存中查找，如果存在并有效，缓存值即为默认语言类型；否则默认为英语
  messages: {
    'en': Object.assign(require('Assets/lang/en'), enLocale),
    'cn': Object.assign(require('Assets/lang/cn'), zhLocale)
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
