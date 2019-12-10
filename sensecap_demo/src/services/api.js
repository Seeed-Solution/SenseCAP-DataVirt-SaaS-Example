let apiHost = 'https://sensecap-demo2.seeed.cn:9001'
let apiUrl = {
  control_api: {
    url: apiHost + '/dev/list', // 获取控制器状态
    method: 'get'
  },
  control_change: {
    url: apiHost + '/dev/switch', // ?id=5&op=1 表示修改灌溉水泵的开关状态 1为开 0为关 -1为停止
    method: 'post' // 修改控制器开关状态
  },
  warning: {
    url: apiHost + '/sensor/node/warnings', // 报警记录 ?start=1574074476704&end=1574165465424
    method: 'get'
  },
  measurement_list: {
    url: apiHost + '/sensor/node/currentvalues', // 中间栏各个物理量的最新值
    method: 'get'
  },
  line_data: {
    url: apiHost + '/sensor/node/values', // ?dev_eui=2CF7F1221210007C&measure_id=4104&start=20191117235959&end=20191118230000
    method: 'get' // 图表数据
  },
  history_api: {
    url: apiHost + '/sensor/node/recentvalues', // 历史数据列表
    method: 'get'
  },
  range_list: {
    url: apiHost + '/sensor/node/normalranges',
    method: 'get' // 测量值正常范围
  },
  run_api: {
    url: apiHost + '/sensor/node/resume', // 设备运行情况
    method: 'get'
  }
}

window.apiUrl = apiUrl

export {
  apiUrl
}
