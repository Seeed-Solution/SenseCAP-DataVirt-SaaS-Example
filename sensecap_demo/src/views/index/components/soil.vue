<template>
<div class="top mb10 ml10 green-border transition">
  <div class="content">
    <h1 v-if="soilData && soilData.length > 0">{{$t('localization.class' + soilData[0].class_id)}}</h1>
    <div class="tabs">
      <span @click.stop="getIndex(inde)" v-for="(elem, inde) in soilData" :key="elem.measure_id + inde + 'mea' + selIndex" v-if="inde < 4" :class="{'active': selIndex == inde}">
        <p>{{elem.value + elem.unit}}</p>
        <p>{{$t('localization.measurements' + elem.measure_id)}}</p>
      </span>
    </div>
    <div class="tabs" v-if="soilData.length > 4">
      <span @click.stop="getIndex(inde)" v-for="(elem, inde) in soilData" :key="elem.measure_id + inde + 'measure' + selIndex" v-if="inde > 3" :class="{'active': selIndex == inde}">
        <p>{{elem.value + elem.unit}}</p>
        <p>{{$t('localization.measurements' + elem.measure_id)}}</p>
      </span>
    </div>
    <my-line :langVal="langVal" fontSize="10" :height="soilData.length <= 4 ? '2rem' : '1.5rem'" :idStr="'soilline' + soilData[selIndex].measure_id + selIndex"
      :titleText="$t('localization.measurements' + soilData[selIndex].measure_id) + '(' + soilData[selIndex].unit + ') ' + '(' + soilData[selIndex].dev_eui + ')'" :opinion="[soilData[selIndex].measure_id]" :opinionData="opinionData"
      class="charts-all" v-if="selIndex != null && soilData[selIndex] && soilData[selIndex].measure_id"></my-line>
  </div>
</div>
</template>
<script>
import myLine from 'Components/echarts/line'
import {
  ajax
} from "Services/ajax"
import utils from "../../../assets/js/utils"
import config from "../../../config"
export default {
  name: "soil",
  components: {
    myLine
  },
  props: {
    soilData: {
      typr: Array,
      default: []
    },
    langVal: {
      type: String,
      default: utils.getLanguage()
    }
  },
  data: () => ({
    selIndex: 0,
    timer: null,
    opinionData: []
  }),
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
        if (this.selIndex == this.soilData.length) {
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
      let time_end = this.soilData && this.soilData[index].time && this.soilData[index].time.toString().length > 10 ? this.soilData[index].time : (this.soilData[index].time && this.soilData[index].time.toString().length == 10 ? Number(this.soilData[
        index].time) * 10 : new Date().getTime());
      let time_start = Number(time_end) - 86400000 * config.detectChartDay;
      let opinionData = [];
      ajax.getData(window.apiUrl.line_data.url + '?dev_eui=' + this.soilData[index].dev_eui + '&measure_id=' + this.soilData[index].measure_id + '&start=' + time_start + '&end=' + time_end).then(res => {
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
    soilData(val) {
      this.timerStart();
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
</style>
