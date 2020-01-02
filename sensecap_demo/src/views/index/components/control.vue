<template>
<div class="middle mb10 ml10 green-border transition">
  <div class="content">
    <h1 class="bold">{{$t('localization').control_title}}</h1>
    <div class="info">
      <div class="per" :class="{'online': irrigation.status}" v-if="!isEmptyObject(irrigation)">
        <span class="icon" :class="{'active': irrigation.status}">
          <img :src="require(irrigation.status ? '../../../assets/images/control/irrigation.png' : '../../../assets/images/control/irrigation_black.png')" />
        </span>
        <span class="control-name bold">{{$t('localization').control_water}}<span style="font-size:10px;"
            :style="{'color': irrigation.status ? '#3f8c4b' : '#9a9a9a'}">（{{irrigation.status ? $t('localization').online : $t('localization').offline}}）</span></span>
        <span class="control-switch">
          <label class="first transition" :class="{'white': !irrigation.switch, 'no': !irrigation.status}" @click.stop="changeStatus(1)">{{$t('localization').open}}</label>
          <label class="last transition" :class="{'white': irrigation.switch, 'no': !irrigation.status}" @click.stop="changeStatus(2)">{{$t('localization').close}}</label>
        </span>
      </div>
      <div class="per" :class="{'online': fan.status}" v-if="!isEmptyObject(fan)">
        <span class="icon" :class="{'active': fan.status}">
          <img :src="require(fan.status ? '../../../assets/images/control/fan.png' : '../../../assets/images/control/fan_black.png')" />
        </span>
        <span class="control-name bold">{{$t('localization').control_wind}}<span style="font-size:10px;" :style="{'color': fan.status ? '#3f8c4b' : '#9a9a9a'}">（{{fan.status ? $t('localization').online : $t('localization').offline}}）</span></span>
        <span class="control-switch">
          <label class="first transition" :class="{'white': !fan.switch, 'no': !fan.status}" @click.stop="changeStatus(3)">{{$t('localization').open}}</label>
          <label class="last transition" :class="{'white': fan.switch, 'no': !fan.status}" @click.stop="changeStatus(4)">{{$t('localization').close}}</label>
        </span>
      </div>
      <div class="per" :class="{'online': light.status}" v-if="!isEmptyObject(light)">
        <span class="icon" :class="{'active': light.status}">
          <img :src="require(light.status ? '../../../assets/images/control/light.png' : '../../../assets/images/control/light_black.png')" />
        </span>
        <span class="control-name bold">{{$t('localization').control_light}}<span style="font-size:10px;" :style="{'color': light.status ? '#3f8c4b' : '#9a9a9a'}">（{{light.status ? $t('localization').online :
            $t('localization').offline}}）</span></span>
        <span class="control-switch">
          <label class="first transition" :class="{'white': !light.switch, 'no': !light.status}" @click.stop="changeStatus(5)">{{$t('localization').open}}</label>
          <label class="last transition" :class="{'white': light.switch, 'no': !light.status}" @click.stop="changeStatus(6)">{{$t('localization').close}}</label>
        </span>
      </div>
      <div class="per" :class="{'online': shade.status}" v-if="!isEmptyObject(shade)">
        <span class="icon" :class="{'active': shade.status}">
          <img :src="require(shade.status ? '../../../assets/images/control/sunshade.png' : '../../../assets/images/control/sunshade_black.png')" />
        </span>
        <span class="control-name bold">{{$t('localization').control_shade}}<span style="font-size:10px;"
            :style="{'color': shade.status ? '#3f8c4b' : '#9a9a9a'}">（{{shade.status ? $t('localization').online : $t('localization').offline}}）</span></span>
        <span class="control-switch">
          <label class="first transition" :class="{'white': shade.switch !== 1}" @click.stop="changeStatus(7)">{{$t('localization').open}}</label>
          <label class="transition" :class="{'stop': shade.switch !== -1}" @click.stop="changeStatus(8)">{{$t('localization').stop}}</label>
          <label class="last transition" :class="{'white': shade.switch !== 0}" @click.stop="changeStatus(9)">{{$t('localization').close}}</label>
        </span>
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
  name: "control",
  data() {
    return {
      irrigation: {}, // 控制灌溉水泵的开关 true为开启状态，false为关闭状态
      fan: {}, // 控制通风风机的开关 true为开启状态，false为关闭状态
      light: {}, // 控制补光灯的开关 true为开启状态，false为关闭状态
      shade: {}, // 控制遮阳帘的开关 1为开启状态，0为关闭状态 -1为停止状态
    }
  },
  methods: {
    tipsfn(type, text) {
      this.$store.dispatch('showMessageTip', {
        type: type,
        text: text
      });
    },
    isEmptyObject(obj) {
      return utils.isEmptyObject(obj)
    },
    // 获取控制器状态
    getStatus() {
      ajax.getData(window.apiUrl.control_api.url).then(res => {
        if (res.code == 0) {
          if (res.data && res.data.length > 0) {
            for (var i = 0; i < res.data.length; i++) {
              this.irrigation = {
                'switch': res.data[i].id == 5 ? (res.data[i].onOff == 1 ? true : false) : this.irrigation.switch,
                'status': res.data[i].id == 5 ? res.data[i].online : this.irrigation.status,
                'id': '5'
              };
              this.fan = {
                'switch': res.data[i].id == 6 ? (res.data[i].onOff == 1 ? true : false) : this.fan.switch,
                'status': res.data[i].id == 6 ? res.data[i].online : this.fan.status,
                'id': '6'
              };
              this.light = {
                'switch': res.data[i].id == 7 ? (res.data[i].onOff == 1 ? true : false) : this.light.switch,
                'status': res.data[i].id == 7 ? res.data[i].online : this.light.status,
                'id': '7'
              };
              this.shade = {
                'switch': res.data[i].id == 8 ? res.data[i].onOff : this.shade.switch,
                'status': res.data[i].id == 8 ? res.data[i].online : this.shade.status,
                'id': '8'
              };
            }
          }
        } else {
          this.tipsfn('error', res.msg && res.msg.length > 0 ? res.msg : this.$t('localization').network_error)
        }
      }).catch(err => {
        this.tipsfn('error', this.$t('localization').network_error)
      })
    },
    // 更新开关状态
    changeStatus(type) {
      let id = null;
      let op = null;
      // 灌溉水泵开关控制
      if (type == 1 || type == 2) {
        if (!this.irrigation.status) {
          return false;
        } else {
          if (type == 1) {
            this.irrigation.switch = true;
            op = 1;
          } else {
            this.irrigation.switch = false;
            op = 0;
          }
          id = this.irrigation.id;
        }
        // 通风风机开关控制
      } else if (type == 3 || type == 4) {
        if (!this.fan.status) {
          return false;
        } else {
          if (type == 3) {
            this.fan.switch = true;
            op = 1;
          } else {
            this.fan.switch = false;
            op = 0;
          }
          id = this.fan.id;
        }
        // 补光灯开关控制
      } else if (type == 5 || type == 6) {
        if (!this.light.status) {
          return false;
        } else {
          if (type == 5) {
            this.light.switch = true;
            op = 1;
          } else {
            this.light.switch = false;
            op = 0;
          }
          id = this.light.id;
        }
        // 遮阳帘开关控制
      } else {
        if (!this.shade.status) {
          return false;
        } else {
          if (type == 7) {
            this.shade.switch = 1;
          } else if (type == 8) {
            this.shade.switch = -1;
          } else {
            this.shade.switch = 0;
          }
          id = this.shade.id;
          op = this.shade.switch;
        }
      }
      ajax.postData(window.apiUrl.control_change.url + '?id=' + id + '&op=' + op).then(res => {
        if (res.code == 0) {
          this.tipsfn('success', this.$t('localization').control_status_ok);
          this.getStatus()
        } else {
          this.tipsfn('error', res.msg && res.msg.length > 0 ? res.msg : this.$t('localization').network_error)
        }
      }).catch(err => {
        this.tipsfn('error', this.$t('localization').network_error)
      })
    }
  },
  created() {
    this.getStatus()
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
.middle.mb10.ml10 {
    .content {
        .info {
            .per {
                opacity: 0.5;
                padding: 0;
                span.icon {
                    width: 0.4rem;
                    height: 0.4rem;
                    line-height: 0.4rem;
                    background: #fff;
                    text-align: center;
                    text-indent: 0;
                    border-radius: 50%;

                    img {
                        width: 0.3rem;
                        height: auto;
                        vertical-align: middle;
                    }
                }
                span.icon.active {
                    background: #32bd80;
                }
                span.control-name {
                    font-size: 0.13rem;
                    text-indent: 0.05rem;
                }
                span.control-switch {
                    float: right;
                    height: 0.5rem;
                    line-height: 0.5rem;
                    font-size: 0;
                    margin-right: 0.2rem;
                    text-align: center;
                    label {
                        display: inline-block;
                        vertical-align: middle;
                        height: 0.2rem;
                        line-height: 0.2rem;
                        min-width: 0.4rem;
                        background: #32bd80;
                        font-size: 0.12rem;
                        cursor: pointer;
                    }
                    label.first {
                        border-radius: 0.04rem 0 0 0.04rem;
                    }
                    label.last {
                        border-radius: 0 0.04rem 0.04rem 0;
                    }
                    label.white {
                        background: #fff;
                        color: #4F4D4D;
                    }
                    label.stop {
                        background: #ccc;
                        color: #4F4D4D;
                    }
                    label:hover {
                        opacity: 0.8;
                    }
                    label.no {
                        cursor: no-drop;
                    }
                    label.no:hover {
                        opacity: 1;
                    }
                }
            }
            div.per.online {
                opacity: 1;
            }
        }
    }
}
</style>
