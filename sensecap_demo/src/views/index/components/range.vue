<template>
<div class="status-per">
  <div class="title bold">{{$t('localization').mea_range}}</div>
  <div class="per-all scollbar" :class="{'big': isBig}">
    <div class="per" v-for="(elem, inde) in dataList" :key="inde + 'range'" v-if="dataList.length > 0">
      <span class="img">
        <img :src="require('../../../assets/images/physical_white/' + elem.measure_id + '.png')" alt="">
      </span>
      <p>{{$t('localization')['measurements' + elem.measure_id]}}({{elem.unit}})</p>
      <p>{{elem.minval}}~{{elem.maxval}}</p>
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
export default {
  name: "range",
  props: {
    isBig: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dataList: []
    }
  },
  methods: {
    tipsfn(type, text) {
      this.$store.dispatch('showMessageTip', {
        type: type,
        text: text
      });
    },
    // 获取范围列表
    getRange() {
      this.dataList = [];
      ajax.getData(window.apiUrl.range_list.url).then(res => {
        if (res.code == 0) {
          this.dataList = res.data && res.data.length > 0 ? res.data : [];
        } else {
          this.tipsfn('error', res.msg && res.msg.length > 0 ? res.msg : this.$t('localization').network_error)
        }
      }).catch(err => {
        this.tipsfn('error', this.$t('localization').network_error)
      })
    }
  },
  created() {
    this.getRange()
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
div.status-per {
    display: inline-block;
    vertical-align: top;
    width: 49%;
    text-align: center;
    background: rgba(175, 211, 249, 0.1);
    min-width: 4rem;
    min-height: 1.32rem;
    div.title {
        background: rgba(2,22,42,0.6);
        font-size: 0.12rem;
        color: #3a8ee6;
        line-height: 0.30rem;
        text-align: left;
        text-indent: 0.24rem;
    }
    div.per-all {
        height: 1rem;
        overflow-y: auto;
        overflow-x: hidden;
    }
    div.per-all.big {
        height: 2.5rem;
    }
    div.per {
        display: inline-block;
        vertical-align: middle;
        width: 20%;
        min-height: 1.25rem;
        padding: 0.05rem 0.08rem;
        font-size: 0.13rem;
        line-height: 0.2rem;
        span.img {
            display: block;
            margin: 0 auto;
            border-radius: 50%;
            width: 0.5rem;
            height: 0.5rem;
            line-height: 0.5rem;
            background: #1a2935;
            text-align: center;
            img {
                width: auto;
                height: auto;
                vertical-align: middle;
            }
        }
        p:nth-of-type(1) {
            font-size: 0.12rem;
            color: #aaa;
        }
    }
}
</style>
