import config from '../../config'
import Cookies from 'js-cookie'
export default {
  localStorage: window.localStorage,
  sessionStorage: window.sessionStorage,
  getType (object) {
    return Object.prototype.toString.call(object).match(/^\[object\s(.*)\]$/)[1]
  },
  setTitle (title) {
    let t = document.querySelector('title')
    t.textContent = title
  },
  getSessionStorage (key) {
    return this.sessionStorage.getItem(key)
  },
  setSessionStorage (key, value) {
    this.sessionStorage.setItem(key, value)
  },
  removeSessionStorage (key) {
    this.sessionStorage.removeItem(key)
  },
  getLocal (key) {
    return this.localStorage.getItem(key)
  },
  // 存储数据到localstorage
  setLocal (key, value) {
    this.localStorage.setItem(key, value)
  },
  // 根据key移除localstorage存储数据
  removeLocalStorage (key) {
    this.localStorage.removeItem(key)
    this.removeSessionStorage(key)
    Cookies.remove(key)
  },
  getLanguage () {
    return (this.getSessionStorage(config.lang) || this.getLocal(config.lang) || 'null')
  },
  setLanguage (langVal) {
    this.setLocal('lang', langVal)
    this.setSessionStorage('lang', langVal)
  },
  // 判断对象是否为空
  isEmptyObject (obj) {
    if (!obj) return true
    for (let i in obj) {
      return false
    }
    return true
  },
  // 时间戳转换
  timefn (timestamp) {
    var date, Y, M, D, h, m, s
    timestamp = Number(timestamp)
    if (timestamp > 100000000) {
      date = new Date(parseInt(timestamp))
    } else {
      date = new Date(parseInt(timestamp) * 1000) // 时间戳转换为毫秒
    }
    Y = date.getFullYear() + '-'
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
    D = date.getDate() > 9 ? date.getDate() + ' ' : '0' + date.getDate() + ' '
    h = date.getHours() > 9 ? date.getHours() + ':' : '0' + date.getHours() + ':'
    m = date.getMinutes() > 9 ? date.getMinutes() + ':' : '0' + date.getMinutes() + ':'
    s = date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds()
    return Y + M + D + h + m + s
  },
  enTimefn (timestamp) {
    var date, Y, M, D, h, m, s
    timestamp = Number(timestamp)
    if (timestamp > 100000000) {
      date = new Date(parseInt(timestamp))
    } else {
      date = new Date(parseInt(timestamp) * 1000) // 时间戳转换为毫秒
    }
    Y = date.getFullYear() + ' '
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/'
    D = date.getDate() > 9 ? date.getDate() + '/' : '0' + date.getDate() + '/'
    h = date.getHours() > 9 ? date.getHours() + ':' : '0' + date.getHours() + ':'
    m = date.getMinutes() > 9 ? date.getMinutes() + ':' : '0' + date.getMinutes() + ':'
    s = date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds()
    return M + D + Y + h + m + s
  },
  // 根据数组某个属性排序
  sortByKey (array, key) {
    return array.sort(function (a, b) {
      return (parseInt(a[key]) - parseInt(b[key]))
    })
  },
  // 去重
  deduplication (arr, key, key1) {
    var result = []
    for (var t of arr) {
      if (result.find(c => c[key] === t[key] && c[key1] === t[key1])) {
        continue
      }
      result.push(t)
    }
    return result
  }
}
