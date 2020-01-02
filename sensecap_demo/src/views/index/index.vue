<template>
<div class="all" id="index">
  <div class="left">
    <div class="left-top mt10 mb10"><img src="../../assets/images/seeed.png" /></div>
    <div class="top mb10 ml10 green-border transition">
      <div class="content">
        <h1 class="bold">{{$t('localization').project_title}}</h1>
        <div class="info scollbar" style="max-height: 2.5rem;overflow: auto">{{$t('localization').project_content}}</div>
      </div>
    </div>
    <!-- 控制中心 -->
    <my-control :langVal="langVal"></my-control>
    <!-- 报警中心 -->
    <my-warning :langVal="langVal"></my-warning>
  </div>
  <div class="center">
    <h1 class="bold">{{$t('localization').smart_agricul}}</h1>
    <!-- 图表 -->
    <my-chart :langVal="langVal" :meaNewData="meaNewData"></my-chart>
    <!-- 历史数据 -->
    <my-history :langVal="langVal"></my-history>
    <div class="status">
      <!-- 报警阈值范围 -->
      <my-range :langVal="langVal"></my-range>
      <!-- 设备运行情况 -->
      <my-run :langVal="langVal"></my-run>
    </div>
  </div>
  <div class="right">
    <div class="right-top mt10 mb10">
      <span>{{timefn(nowDate)}}</span>
      <span class="img" @mouseout.stop="isBlue = false" @mouseover.stop="isBlue = true" @click.stop="clickFullscreen()" v-show="!isOpen">
        <img src="../../assets/images/open.png" alt="" v-show="!isBlue">
        <img src="../../assets/images/open_blue.png" alt="" v-show="isBlue">
      </span>
      <span class="img" @mouseout.stop="isBlue = false" @mouseover.stop="isBlue = true" @click.stop="clickFullscreen()" v-show="isOpen">
        <img src="../../assets/images/close.png" alt="" v-show="!isBlue">
        <img src="../../assets/images/close_blue.png" alt="" v-show="isBlue">
      </span>
      <div class="lang-box search-input" @blur.stop="blurFn($event)" tabindex="0">
        <span @click.stop="isShowLang = !isShowLang">
          <img class="lang" src="../../assets/images/cn.png" alt="" v-if="langVal == 'cn'">
          <img class="lang" src="../../assets/images/en.png" alt="" v-else>
          <span>{{langVal == 'cn' ? '简体中文' : 'English'}}</span>
          <img style="width: 0.1rem" src="../../assets/images/down.png" alt="" v-if="!isShowLang" />
          <img style="width: 0.1rem" src="../../assets/images/up.png" alt="" v-else />
        </span>
        <div class="box" v-show="isShowLang">
          <p @click.stop="changeFn('cn')" :class="{'active': langVal == 'cn'}">简体中文</p>
          <p @click.stop="changeFn('en')" :class="{'active': langVal == 'en'}">English</p>
        </div>
      </div>
    </div>
    <my-soil :langVal="langVal" :soilData="soilData"></my-soil>
    <my-meteorology :langVal="langVal" :meteorologyData="meteorologyData"></my-meteorology>
    <my-video></my-video>
  </div>
</div>
</template>
<script>
// 浏览器全屏
import screenfull from 'screenfull'
// 控制中心
import myControl from "./components/control"
// 报警中心
import myWarning from "./components/warning"
// 图表
import myChart from "./components/chart"
// 历史数据
import myHistory from "./components/history"
// 报警阈值范围
import myRange from "./components/range"
// 设备运行情况
import myRun from "./components/run"
// 土壤状况
import mySoil from "./components/soil"
// 气象状况
import myMeteorology from "./components/meteorology"
// 视频
import myVideo from "./components/video"
import {
  ajax
} from "Services/ajax"
import config from "../../config"
import utils from "../../assets/js/utils"

export default {
  name: "index",
  components: {
    myControl,
    myWarning,
    myChart,
    myHistory,
    myRange,
    myRun,
    mySoil,
    myMeteorology,
    myVideo
  },
  props: {
    langVal: {
      type: String,
      default: utils.getLanguage()
    }
  },
  data() {
    return {
      isShowLang: false,
      meaNewData: [], // 测量类型最新值列表
      soilData: [],
      meteorologyData: [],
      isBlue: false,
      isOpen: false,
      nowDate: new Date().getTime() // 获取当前时间
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
    getChartList() {
      ajax.getData(window.apiUrl.measurement_list.url).then(res => {
        if (res.code == 0) {
          let soilData = [];
          let meteorologyData = [];
          let oriArr = [];
          if (res.data && res.data.length > 0) {
            // 去掉重复的测量类型 最多展示16个
            oriArr = utils.deduplication(res.data, 'measure_id', 'class_id');
            oriArr = oriArr.slice(0, 16);
            for (var i = 0; i < oriArr.length; i++) {
              // 环境状况的数据
              if (oriArr[i].class_id == 1) {
                meteorologyData.push(oriArr[i])
              } else if (oriArr[i].class_id == 2) {
                // 土壤状况的数据
                soilData.push(oriArr[i])
              }
            }
            // 最多展示8个
            meteorologyData = meteorologyData.slice(0, 8);
            // 最多展示8个
            soilData = soilData.slice(0, 8)
          }
          this.meaNewData = [];
          this.meaNewData = oriArr && oriArr.length > 0 ? oriArr : [];
          this.soilData = [];
          this.soilData = soilData;
          this.meteorologyData = [];
          this.meteorologyData = meteorologyData;
          // 关闭loading状态
          $('#app').show();
          $('#appLoading').hide();
        } else {
          this.tipsfn('error', res.msg && res.msg.length > 0 ? res.msg : this.$t('localization').network_error)
        }
      }).catch(err => {
        this.tipsfn('error', this.$t('localization').network_error)
      })
    },
    changeFn(lang) {
      this.langVal = lang;
      this.$emit('get-langVal', lang);
      this.$i18n.locale = lang;
      utils.setLanguage(lang);
      this.isShowLang = false;
    },
    clickFullscreen() {
      if (!screenfull.isEnabled) {
        this.$message({
          message: 'you browser can not work',
          type: 'warning'
        })
        return false
      }
      this.isOpen = !this.isOpen;
      screenfull.toggle()
    },
    blurFn(e) {
      if (e.relatedTarget && e.relatedTarget.className && e.relatedTarget.className.indexOf('search-input') != -1) {
        return false;
      } else {
        this.isShowLang = false;
      }
    }
  },
  created() {
    // 每隔1秒获取最新时间
    setInterval(() => {
      this.nowDate = new Date().getTime()
    }, 1000);

    this.getChartList();
    // 定时更新测量最新值
    setInterval(() => {
      this.getChartList();
    }, config.timeInterval)
  }
}
</script>
<style lang="scss" scoped>
@import "./index.scss";
</style>
