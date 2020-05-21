<template>
<div class="bottom mb10 ml10">
  <div class="content">
    <h1 class="bold">{{$t('localization').history_data}}</h1>
    <div class="info">
      <div class="per title bold">
        <span class="time">{{$t('localization').data_up_time}}</span>
        <span class="name">{{$t('localization').mea_type}}</span>
        <span class="value lit">{{$t('localization').mea_val}}</span>
        <span class="value">{{$t('localization').mea_status}}</span>
      </div>
      <div class="per-parent">
        <div class="per-all" :class="{'top-transition': isScroll}">
          <div class="per" v-for="(item, index) in allData" :key="index + item.time + item.measure_id + item.dev_eui + 'history'">
            <span class="time">{{item.time ? timefn(item.time) : ''}}</span>
            <span class="name">{{$t('localization.measurements' + item.measure_id)}}</span>
            <span class="value lit">{{item.value}}{{item.unit}}</span>
            <span class="value" :class="{'red': item.status == 2, 'yellow': item.status == 0, 'blue': item.status == 1}">{{item.status == 0 ? $t('localization').value_lit : (item.status == 1 ? $t('localization').normal : $t('localization').value_big)}}<label style="color: #fff"> ({{item.dev_name}})</label></span>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>
<script>
import {
  ajax
} from "Services/ajax"
import config from "../../../config"
import utils from "../../../assets/js/utils"
export default {
  name: "history",
  props: {
    langVal: {
      type: String,
      default: utils.getLanguage()
    }
  },
  data() {
    return {
      allData: [],
      isScroll: false,
      timer: null,
      timer1: null
    }
  },
  methods: {
    tipsfn(type, text) {
      this.$store.dispatch('showMessageTip', {
        type: type,
        text: text
      });
    },
    timefn(timestamp) {
      return this.langVal == 'cn' ? utils.timefn(timestamp) : utils.enTimefn(timestamp)
    },
    changeData() {
      this.isScroll = true;
      let timer = setTimeout(() => {
        this.allData.push(this.allData[0]);
        this.allData.shift();
        this.isScroll = false;
        clearTimeout(timer);
        timer = null;
      }, 2000)
    },
    getHistoryData() {
      ajax.getData(window.apiUrl.history_api.url, {
        params: {
          count: config.historyLength
        }
      }).then(res => {
        if (res.code == 0) {
          if (res.data && res.data.length > 0) {
            for (var i = 0; i < res.data.length; i++) {
              res.data[i].status = 1;
              if (res.data[i].value < res.data[i].minval) {
                res.data[i].status = 0
              } else if (res.data[i].value > res.data[i].maxval) {
                res.data[i].status = 2
              };
            }
          };
          this.allData = [];
          this.allData = res.data && res.data.length > 0 ? res.data : [];
          if (this.timer != null) {
            clearInterval(this.timer)
            this.timer = null
          };
          if (this.allData.length > 4) {
            this.timer = setInterval(this.changeData, 10000);
          }
        } else {
          this.tipsfn('error', res.msg && res.msg.length > 0 ? res.msg : this.$t('localization').network_error)
        }
      }).catch(err => {
        this.tipsfn('error', this.$t('localization').network_error)
      })
    }
  },
  created() {
    this.getHistoryData();
    // 定时更新历史数据 默认1小时更新
    this.timer1 = setInterval(() => {
      this.getHistoryData();
    }, config.historyInterval)
  },
  beforeDestroy() {
    if (this.timer != null) {
      clearInterval(this.timer);
      this.timer = null;
    }
    if (this.timer1 != null) {
      clearInterval(this.timer1);
      this.timer1 = null;
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
.bottom.mb10.ml10 {
    height: 23%;
    min-height: 1.8rem;
    min-width: 3.5rem;
    margin: 0 0 0 0.1rem;
    .content {
        height: 100%;
        min-height: 1.8rem;
        h1 {
            width: 100%;
            background: transparent;
            height: 0.3rem;
            line-height: 0.3rem;
            font-size: 0.16rem;
        }
        .info {
            text-indent: 0;
            height: calc(100% - 0.3rem);
            height: -webkit-calc(100% - 0.3rem);
            height: -moz-calc(100% - 0.3rem);
            height: -o-calc(100% - 0.3rem);
            .per-parent {
                min-height: 1.2rem;
                height: calc(100% - 0.3rem);
                height: -webkit-calc(100% - 0.3rem);
                height: -moz-calc(100% - 0.3rem);
                height: -o-calc(100% - 0.3rem);
                width: 100%;
                overflow: hidden;
                position: relative;
                .per-all {
                    position: absolute;
                    top: 0;
                    left: 0;
                    right: 0;
                    width: 100%;
                }
            }
            .per-all.top-transition {
                transition: all 0.5s;
                -webkit-transition: all 0.5s;
                -moz-transition: all 0.5s;
                -o-transition: all 0.5s;
                margin-top: -0.3rem;
            }
            .per {
                margin: 0 auto;
                height: 0.3rem;
                line-height: 0.3rem;
                span.name,
                span.time,
                span.value {
                    text-indent: 0.2rem;
                }
                span.name,
                span.time {
                    width: 26%;
                }
                span.value {
                    width: 32%;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                }
                span.name {
                  width: 20%;
                }
                span.value.lit {
                    width: 14%;
                }
                span.red {
                    color: #fe0d0d;
                }
                span.yellow {
                    color: #e6a23c;
                }
                span.blue {
                    color: #409eff;
                }
            }
            .per.title {
                background: rgba(2,22,42,0.6);
                span.name,
                span.time,
                span.value {
                    font-size: 0.14rem;
                }
            }
        }
    }
}
</style>
