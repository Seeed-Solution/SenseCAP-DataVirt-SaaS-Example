// 参数配置
export default {
  lang: 'lang', // 本地存储语言
  timeout: 1000 * 60 * 10, // 接口超时时间
  warningDay: 7, // 报警中心获取最近多长时间的数据  默认为7天
  timeInterval: 300000, // 默认5分钟更新下数据(包括报警中心数据、图表数据、土壤检测、环境监测、设备运行情况部分)
  historyInterval: 60000, // 默认1分钟更新一次历史数据列表
  measurementTnterval: 60000, // 默认1分钟切换一个测量类型的图表数据
  historyLength: 100, // 历史数据获取多少条 默认100条
  detectChartDay: 1 // 环境检测 土壤检测默认展示1天数据
}
