<template>
<div class="bottom mb10 ml10 green-border transition">
  <div class="content">
    <h1 class="bold">{{$t('localization').warn}}</h1>
    <div class="info">
      <div class="per title bold">
        <span class="name">{{$t('localization').warn_index}}</span>
        <span class="value">{{$t('localization').remark}}</span>
        <span class="time">{{$t('localization').time}}</span>
      </div>
      <div class="per-parent">
        <div class="per-all" :class="{'top-transition': isScroll}">
          <div class="per" v-for="(item, index) in allData" :key="index + item.measure_id + item.time + item.dev_eui + 'warn'">
            <div class="per-top">{{$t('localization').dev_name + ': ' + item.dev_name}}</div>
            <div class="per-bot">
              <span class="name" :class="{'red': item.status == 2, 'yellow': item.status == 0}">{{item.status == 0 ? $t('localization').value_lit : $t('localization').value_big}} <span style="font-size: 0.1rem; color: #fff; line-height: 0.08rem;">({{$t('localization.measurements' + item.measure_id)}})</span></span>
              <span class="value">{{item.value}}{{item.unit}}</span>
              <span class="time">{{timefn(item.time)}}</span>
            </div>
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
import utils from "Assets/js/utils"
export default {
  name: "warning",
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
    getWarnData() {
      let end = new Date().getTime();
      ajax.getData(window.apiUrl.warning.url, {
        params: {
          start: end - config.warningDay * 86400000,
          end: end
        }
      }).then(res => {
        if (res.code == 0) {
          if (res.data && res.data.length > 0) {
            for (var i = 0; i < res.data.length; i++) {
              res.data[i].status = 2;
              if (res.data[i].value < res.data[i].minval) {
                res.data[i].status = 0
              };
            }
          };
          this.allData = [];
          // 去重
          this.allData = res.data && res.data.length > 0 ? res.data : [];
          if (this.timer != null) {
            clearInterval(this.timer)
            this.timer = null
          };
          if (this.allData.length > 3) {
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
    this.getWarnData();
    // 定时更新报警中心数据  默认5分钟更新
    this.timer1 = setInterval(() => {
      this.getWarnData();
    }, config.timeInterval)
  },
  beforeDestroy() {
    if (this.timer1 != null) {
      clearInterval(this.timer1);
      this.timer1 = null;
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
.bottom.mb10.ml10.green-border.transition {
    height: 30%;
    min-height: 3rem;
    .content {
        .info {
            text-indent: 0;
            height: calc(100% - 0.7rem);
            height: -webkit-calc(100% - 0.7rem);
            height: -moz-calc(100% - 0.7rem);
            height: -o-calc(100% - 0.7rem);
            .per-parent {
                min-height: 1.804rem;
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
                margin-top: -0.7rem;
            }
            .per {
                height: 0.6rem;
                line-height: 0.6rem;
                div.per-top {
                    line-height: 0.2rem;
                    height: 0.2rem;
                    font-size: 0.1rem;
                    border-bottom: 0.01rem solid #606266;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                }
                div.per-bot {
                    height: 0.3rem;
                    line-height: 0.3rem;
                }
                span.name,
                span.time {
                    width: 33%;
                }
                span.name,
                span.time,
                span.value {
                    line-height: 0.2rem;
                    font-size: 0.1rem;
                }
                span.value {
                    width: 26%;
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
                height: 0.3rem;
                line-height: 0.3rem;
                span.name,
                span.time,
                span.value {
                    font-size: 0.12rem;
                }
            }
        }
    }
}
</style>
