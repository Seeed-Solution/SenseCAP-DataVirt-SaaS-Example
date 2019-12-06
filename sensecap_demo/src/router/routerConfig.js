// 首页
const index = resolve => {
  require.ensure(['../views/index/index'], () => {
    resolve(require('../views/index/index'))
  })
}

export const routes = [{
  path: '/',
  redirect: '/index'
},
{
  path: '/index',
  component: index,
  name: 'index',
  meta: {
    title: 'Seeed智慧农业监控数据平台',
    path: 'index',
    auth: true,
    keepAlive: false
  }
},
{
  path: '*',
  redirect: '/index'
}
]

module.exports = {
  routes
}
