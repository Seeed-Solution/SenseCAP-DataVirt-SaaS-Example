import Axios from 'axios'
import config from '../config'
const Qs = require('qs')

let _options = (options) => {
  return options || {}
}

/**
 * url添加query参数，默认带上token
 * @param token
 * @param params
 */

const addQueryString = (token = true, params) => {
  let obj = {}
  if (token) {
    obj = Object.assign({}, obj, {})
  }
  if (params) {
    obj = Object.assign({}, obj, params)
  }
  return obj
}

let _request = (method, url, data, options) => {
  let reqestOptions = {
    method: method,
    data: data,
    url: url,
    timeout: config.timeout,
    paramsSerializer: function (params) {
      return Qs.stringify(params, {
        arrayFormat: 'brackets'
      })
    }
  }

  if (options.headers) {
    reqestOptions.headers = options.headers
  } else {
    reqestOptions.headers = {
      // Authorization: ''
    }
  }

  // 响应超时配置
  Axios.defaults.retry = 4
  Axios.defaults.retryDelay = 1000
  Axios.defaults.crossDomain = true
  reqestOptions.params = Object.assign({}, reqestOptions.params, addQueryString(options.token, options.params))
  return new Promise((resolve, reject) => {
    Axios.request(reqestOptions)
      .then(res => {
        resolve(res.data ? res.data : res)
      })
      .catch(err => {
        reject(err)
      })
    // ajax响应超时设置
    Axios.interceptors.response.use(undefined, function axiosRetryInterceptor (err) {
      var configStr = err.configStr
      if (!configStr || !configStr.retry) return Promise.reject(err)
      configStr.__retryCount = configStr.__retryCount || 0
      if (configStr.__retryCount >= configStr.retry) {
        return Promise.reject(err)
      }
      configStr.__retryCount += 1
      var backoff = new Promise(function (resolve) {
        setTimeout(function () {
          resolve()
        }, configStr.retryDelay || 1)
      })
      return backoff.then(function () {
        return Axios(configStr)
      })
    })
  })
}

/*
 * GET/POST data
 * @param url api地址
 * @param options 配置项
 * @param data 需要提交的数据
 */

const ajax = {
  getData (url, options) {
    return _request('get', url, null, _options(options))
  },
  postData (url, data, options) {
    return _request('post', url, data, _options(options))
  },
  patchData (url, data, options) {
    return _request('PATCH', url, data, _options(options))
  },
  putData (url, data, options) {
    return _request('put', url, data, _options(options))
  },
  deleteData (url, data, options) {
    return _request('delete', url, data, _options(options))
  }
}

export {
  ajax
}
