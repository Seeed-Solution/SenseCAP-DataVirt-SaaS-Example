<!-- 自定义 echart 组件 -->
<template>
<div :style="{'width':width}" class="charts-data-per">
  <!-- echart表格 折线图-->
  <div :id="idStr" class="myChart" :style="{'width': width, 'height': height}"></div>
</div>
</template>

<script>
import utils from "../../assets/js/utils"
export default {
  props: {
    idStr: {
      type: String,
      default: 'charts' + new Date().getTime()
    },
    // 标题文本
    titleText: {
      type: String,
      default: ''
    },
    // 折线标题
    opinion: {
      type: Array,
      default () {
        return []
      }
    },
    // 折线图数据
    opinionData: {
      type: Array,
      default () {
        return []
      }
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '2.5rem'
    },
    isShowDataZoom: {
      type: Boolean, // 是否展示底部滑动栏
      default: true
    },
    fontSize: {
      type: String,
      default: '14'
    },
    langVal: {
      type: String,
      default: utils.getLanguage()
    }
  },
  data() {
    return {
      series: [],
      // 线条颜色
      colors: ['#32bd80', '#3a8ee6', '#8ec31f', '#a6c717', '#cd8c1a', '#b1c7de', '#dd6e73', '#de9da0', '#f8c9ee', '#fff']
    }
  },
  methods: {
    // 绘制折线图
    drawPie(idStr) {
      let num = this.opinionData.length ? this.opinionData.length : 0;
      this.series = [];
      for (var i = 0; i < num; i++) {
        this.series.push({
          data: this.opinionData[i],
          type: 'line',
          smooth: true,
          ymbol: 'circle',
          showSymbol: true,
          connectNulls: true,
          // name: this.opinion[i], // 每条线的名称
          itemStyle: {
            normal: {
              color: this.colors[Math.floor(Math.random() * 10)]
            }
          }
        });
      }
      if (this.idStr && this.idStr.length > 0) {
        this.charts = this.$echarts.init(document.getElementById(idStr));
      } else {
        return false;
      }
      let datazoom = this.isShowDataZoom ? [{
          show: true,
          realtime: true,
          start: 0,
          end: 100
        },
        {
          type: 'inside',
          realtime: true,
          start: 0,
          end: 100
        }
      ] : [];
      this.charts.setOption({
        // backgroundColor: '#fff', // 图表背景色
        title: {
          text: this.titleText,
          padding: [20, 0, 0, 20],
          textStyle: {
            color: '#8ec31f',
            fontSize: this.fontSize
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            animation: false,
            label: {
              backgroundColor: '#fff',
              borderColor: '#f1f1f1',
              borderWidth: 1,
              shadowBlur: 0,
              shadowOffsetX: 0,
              shadowOffsetY: 0,
              textStyle: {
                color: '#222'
              }
            }
          },
          formatter: (params) => {
            let str = '';
            if (params && params.length > 1) {
              for (var i = 0; i < params.length; i++) {
                let data = params[i];
                let dateN = new Date(data.value[0].replace(new RegExp(/-/gm) ,"/"));
                str = str + (i === 0 ? ((dateN.getFullYear() + '-') + ((dateN.getMonth() + 1 < 10 ? '0' + (dateN.getMonth() + 1) : dateN.getMonth() + 1) + '-') + (dateN.getDate() > 9 ? dateN.getDate() + ' ' : '0' + dateN.getDate() + ' ') + (dateN.getHours() > 9 ? dateN.getHours() + ':' : '0' + dateN.getHours() + ':') + (dateN.getMinutes() > 9 ? dateN.getMinutes() + ':' : '0' + dateN.getMinutes() + ':') + (dateN.getSeconds() > 9 ? dateN.getSeconds() : '0' + dateN.getSeconds()) + '<br/>') : '') + data.name + ': ' + data.value[1] + (i !== data.length ? '<br />' : '')
              }
            } else {
              let data = params[0];
              let dateN = new Date(data.value[0].replace(new RegExp(/-/gm) ,"/"));
              str = str + (dateN.getFullYear() + ((dateN.getMonth() + 1 < 10 ? '0' + (dateN.getMonth() + 1) : dateN.getMonth() + 1) + '-') + (dateN.getDate() > 9 ? dateN.getDate() + ' ' : '0' + dateN.getDate() + ' ') + (dateN.getHours() > 9 ? dateN.getHours() + ':' : '0' + dateN.getHours() + ':') + (dateN.getMinutes() > 9 ? dateN.getMinutes() + ':' : '0' + dateN.getMinutes() + ':') + (dateN.getSeconds() > 9 ? dateN.getSeconds() : '0' + dateN.getSeconds())) + ': ' + data.value[1]
            }
            return str
          },
        },
        legend: {
          data: this.opinion,
          padding: [10, 0, 0, 0],
          textStyle: {
            color: '#f1f1f1'
          }
        },
        toolbox: {
          show: true,
          x: 'right',
          y: 25,
          padding: [0, 40, 0, 0],
          feature: {
            mark: {
              show: true
            },
            dataView: {
              show: false,
              readOnly: true
            },
            magicType: {
              show: true,
              type: ['line', 'bar'],
              title: {
                line: this.langVal == 'cn' ? '切换为折线图' : 'Line Chart',
                bar: this.langVal == 'cn' ? '切换为柱形图' : 'Bar Chart'
              },
              iconStyle: {
                borderColor: '#f1f1f1'
              }
            },
            restore: {
              show: true,
              title: this.langVal == 'cn' ? '还原' : 'Restore',
              iconStyle: {
                borderColor: '#f1f1f1'
              }
            },
            saveAsImage: {
              title: this.langVal == 'cn' ? '保存为图片' : 'Save as image',
              iconStyle: {
                borderColor: '#f1f1f1'
              }
            }
          }
        },
        // 数据区域缩放
        dataZoom: datazoom,
        grid: {
          left: '20',
          right: '20',
          bottom: '3%',
          containLabel: true
        },
        // 图表为时间格式
        xAxis: {
          type: 'time',
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: '#32bd80'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: '#32bd80'
            }
          }
        },
        series: this.series
      }, true);
      this.charts.resize();
    }
  },
  mounted() {
    $(window).resize(() => {
      if (this.idStr && this.idStr.length > 0) {
        this.charts.resize();
      }
    })
  },
  watch: {
    width(val) {
      this.width = val;
    },
    opinionData: {
      handler(val, oldval) {
        this.$set(this, 'opinionData', val);
        if (this.idStr && this.idStr.length > 0) {
          this.$nextTick(function() {
            if (this.opinionData.length > 0) {
              this.drawPie(this.idStr)
            }
          })
        }
      },
      deep: true
    },
    idStr(val, oldval) {
      if (val !== oldval && val.length > 0) {
        this.$nextTick(function() {
          if (this.opinionData.length > 0) {
            this.drawPie(this.idStr)
          }
        })
      }
    },
    langVal(val, oldval) {
      if (val !== oldval) {
        this.$nextTick(function() {
          this.drawPie(this.idStr)
        });
      }
    }
  }
}
</script>

<style lang="less" scoped>
.myChart {
    width: 100%;
    height: 3rem;
}
</style>
