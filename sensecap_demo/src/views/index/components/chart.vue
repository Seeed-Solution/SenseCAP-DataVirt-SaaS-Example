<template>
<div class="chart-big">
  <div class="physical-all">
    <div class="physical" :class="{'big': meaNewData.length > 14}">
      <div class="per" v-for="(item, index) in meaNewData" :key="index + item.measure_id + 'charts' + selIndex">
        <div class="per-icon transition" :class="{'active': selIndex == index}" @click.stop="getIndex(index)">
          <div class="per-content">
            <div class="img"><img :src="require('../../../assets/images/physical/' + item.measure_id + '.png')" /></div>
            <div class="text bold">{{item.value}} {{item.unit}}</div>
          </div>
        </div>
        <p>{{$t('localization.measurements' + item.measure_id)}}</p>
      </div>
    </div>
  </div>
  <div class="chart">
    <div class="content">
      <my-line :langVal="langVal" :idStr="'linechart' + meaNewData[selIndex].measure_id + selIndex" :titleText="$t('localization.measurements' + meaNewData[selIndex].measure_id) + '(' + meaNewData[selIndex].unit + ') ' + '(' + meaNewData[selIndex].dev_name + ')'"
        :opinion="[meaNewData[selIndex].measure_id]" :opinionData="opinionData" class="charts-all" v-if="selIndex != null && meaNewData[selIndex] && meaNewData[selIndex].measure_id"></my-line>
    </div>
  </div>
</div>
</template>
<script>
import {
  ajax
} from "Services/ajax"
import utils from "../../../assets/js/utils"
import config from "../../../config"
import myLine from 'Components/echarts/line'
export default {
  name: "chart",
  components: {
    myLine
  },
  props: {
    meaNewData: {
      typr: Array,
      default: []
    },
    langVal: {
      type: String,
      default: utils.getLanguage()
    }
  },
  data() {
    return {
      selIndex: 0,
      timer: null,
      opinionData: []
    }
  },
  methods: {
    timefn(timestamp) {
      return utils.timefn(timestamp)
    },
    tipsfn(type, text) {
      this.$store.dispatch('showMessageTip', {
        type: type,
        text: text
      });
    },
    // 开启定时器 每1分钟切换一个测量类型
    timerStart() {
      if (this.timer) {
        clearInterval(this.timer);
        this.timer = null;
      }
      this.getChart(this.selIndex);
      this.timer = setInterval(() => {
        this.selIndex = this.selIndex + 1;
        if (this.selIndex == this.meaNewData.length) {
          this.selIndex = 0;
        }
        this.getChart(this.selIndex);
      }, config.measurementTnterval)
    },
    // 点击某个测量光圈可手动切换到该测量图表
    getIndex(index) {
      this.selIndex = index;
      this.timerStart()
    },
    getChart(index) {
      // 最近一周数据
      let time_end = this.meaNewData[index].time && this.meaNewData[index].time.toString().length > 10 ? this.meaNewData[index].time : (this.meaNewData[index].time && this.meaNewData[index].time.toString().length == 10 ? Number(this.meaNewData[index]
          .time) * 10 :
        new Date().getTime());
      let time_start = Number(time_end) - 86400000 * 7;
      let opinionData = [];
      ajax.getData(window.apiUrl.line_data.url + '?dev_eui=' + this.meaNewData[index].dev_eui + '&measure_id=' + this.meaNewData[index].measure_id + '&start=' + time_start + '&end=' + time_end).then(res => {
        if (res.code == 0) {
          if (res.data && res.data.length > 0) {
            for (var i = 0; i < res.data.length; i++) {
              opinionData.push({
                value: res.data[i] ? [this.timefn(res.data[i].time), res.data[i].value] : []
              })
            }
          }
          this.opinionData = [];
          this.opinionData.push(opinionData)
        } else {
          this.tipsfn('error', res.msg && res.msg.length > 0 ? res.msg : this.$t('localization').network_error)
        }
      }).catch(err => {
        this.tipsfn('error', this.$t('localization').network_error)
      })
    }
  },
  watch: {
    meaNewData(val) {
      this.timerStart();
    }
  }
}
</script>
<style lang="scss" scoped>
div.chart-big {
    min-width: 3.5rem;
    margin: 0 0 0 0.1rem;
    background: rgba(175, 211, 249, 0.1);
    div.physical-all {
        margin: 0.1rem auto 0;
    }
    div.physical {
        font-size: 0;
        margin: 0 auto;
        text-align: center;
        width: 7.5rem;
        div.per {
            display: inline-block;
            vertical-align: middle;
            padding: 0.1rem 0.1rem 0;
            min-height: 1.54rem;
            p {
                width: 0.85rem;
                font-size: 0.12rem;
                line-height: 0.25rem;
                color: #f1f1f1;
                white-space: pre-wrap;
            }
        }
        div.per-icon {
            display: block;
            width: 0.85rem;
            height: 0.85rem;
            font-size: 0.16rem;
            overflow: hidden;
            border-radius: 50%;
            background-image: -webkit-linear-gradient(rgba(212,252,121,0.6), rgba(103,194,58,0.6));
            background-image: -moz-linear-gradient(rgba(212,252,121,0.6), rgba(103,194,58,0.6));
            background-image: linear-gradient(rgba(212,252,121,0.6), rgba(103,194,58,0.6));
            box-sizing: border-box;
            padding: 0.02rem;
            cursor: pointer;
            margin: 0 auto;
            div.per-content {
                width: 100%;
                height: 100%;
                border-radius: 50%;
                background: #3c4446;
                overflow: hidden;
                position: relative;
                div.img {
                    height: 0.3rem;
                    line-height: 0.3rem;
                    overflow: hidden;
                    position: absolute;
                    top: 0.1rem;
                    left: 0;
                    right: 0;
                    img {
                        width: auto;
                        height: auto;
                        vertical-align: middle;
                    }
                }
                div.text {
                    font-size: 0.12rem;
                    line-height: 0.2rem;
                    position: absolute;
                    top: 0.4rem;
                    left: 0;
                    right: 0;
                    white-space: pre-wrap;
                }
            }
        }
        div.per-icon.active {
            background: #fff;
            padding: 0.04rem;
        }
        div.per-icon:hover {
            padding: 0.04rem;
        }
    }
    div.physical.big {
        width: 9rem;
    }
    div.chart {
        margin: -0.2rem auto 0;
        height: 2.5rem;
    }
}
</style>
