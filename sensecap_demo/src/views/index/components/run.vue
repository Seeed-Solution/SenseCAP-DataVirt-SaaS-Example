<template>
<div class="status-per">
  <div class="title bold">{{$t('localization').dev_run}}</div>
  <div class="per-all scollbar" :class="{'big': isBig}">
    <div class="per-big" v-for="(elem, inde) in list" :key="elem.classId + inde + 'run'">
      <div class="left">
        <div class="img"><img :src="require('../../../assets/images/' + (elem.classId == 1 ? 'surrounding' : 'soil') + '.png')" alt=""></div>
        <div class="name">{{$t('localization.class' + elem.classId)}}</div>
      </div>
      <div class="right">
        <p>{{$t('localization').online}}</p>
        <p class="font-right">{{elem.onlineCount}}</p>
        <p>{{$t('localization').offline}}</p>
        <p class="font-right">{{elem.unlineCount}}</p>
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
export default {
  name: "run",
  props: {
    isBig: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    list: [],
    timer: null
  }),
  methods: {
    tipsfn(type, text) {
      this.$store.dispatch('showMessageTip', {
        type: type,
        text: text
      });
    },
    getList() {
      ajax.getData(window.apiUrl.run_api.url).then(res => {
        if (res.code == 0) {
          this.list = [];
          if (res.data && res.data.length > 0) {
            for (var i = 0; i < res.data.length; i++) {
              // 仅展示土壤与环境
              if (res.data[i].classId == 1 || res.data[i].classId == 2) {
                this.list.push(res.data[i])
              }
            }
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
    this.getList()
    this.timer = setInterval(() => {
      this.getList();
    }, config.timeInterval)
  },
  beforeDestroy() {
    if (this.timer != null) {
      clearInterval(this.timer);
      this.timer = null;
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
div.status-per {
    min-width: 4rem;
    min-height: 1.32rem;
    display: inline-block;
    vertical-align: top;
    width: 49%;
    text-align: center;
    background: rgba(175, 211, 249, 0.1);
    margin-left: 1.5%;

    div.title {
        background: rgba(2,22,42,0.6);
        font-size: 0.12rem;
        color: #3a8ee6;
        line-height: 0.3rem;
        text-align: left;
        text-indent: 0.24rem;
    }
    div.per-all {
        width: 100%;
        font-size: 0;
        height: 1rem;
        overflow: auto;
        div.per-big {
            display: inline-block;
            vertical-align: middle;
            min-width: 25%;
            font-size: 0;
            padding: 0.1rem 0.1rem 0;
            div.left,
            div.right {
                display: inline-block;
                vertical-align: middle;
                width: 50%;
                text-align: center;
                div.img {
                    width: 0.5rem;
                    line-height: 0.5rem;
                    margin: 0 auto;
                    background: #1a2935;
                    border-radius: 0.05rem;
                    img {
                        width: 0.3rem;
                        height: auto;
                        vertical-align: middle;
                    }
                }
                p {
                    line-height: 0.2rem;
                    font-size: 0.1rem;
                    text-align: left;
                    text-indent: 0.1rem;
                }
                p.font-right {
                    text-align: right;
                    text-indent: 0;
                    color: #3a8ee6;
                }
            }
            div.left {
                div.name {
                    line-height: 0.25rem;
                    color: #aaa;
                    font-size: 0.12rem;
                }
            }
        }
    }
    div.per-all.big {
        height: 2.5rem;
    }
}
</style>
