<template>
<div class="viewport-wrap-box scollbar" id="container">
  <keep-alive>
    <router-view v-if="!$route.meta.keepAlive" :langVal="langVal" v-on:get-langVal="getLanVal"></router-view>
  </keep-alive>
  <!-- start message-tip -->
  <message-tip :is-show="isShowMessageTip" :message-text="messageTipText" :duration="messageTipDuration" :type="messageTipType" @off="offMessageTip" :is-show-close-btn="messageTipIsShowCloseBtn">
  </message-tip>
  <!-- end message-tip -->
</div>
</template>
<script>
import {
  mapActions,
  mapGetters
} from 'vuex'
import messageTip from 'Components/message-tip/message-tip'
export default {
  props: {},
  components: {
    messageTip
  },
  data() {
    return {
      langVal: this.$i18n.locale
    }
  },
  computed: {
    ...mapGetters({
      isShowMessageTip: 'isShowMessageTip', //是否显示messageTip
      messageTipText: 'messageTipText', //消息提示内容
      messageTipDuration: 'messageTipDuration', //messageTip动画持续时间
      messageTipType: 'messageTipType', // messageTip 图标提示类型
      messageTipIsShowCloseBtn: 'messageTipIsShowCloseBtn', // 是否显示messageTip关闭按钮
    })
  },
  methods: {
    ...mapActions({
      hideMessageTip: 'hideMessageTip'
    }),
    offMessageTip() {
      this.$store.dispatch('hideMessageTip')
    },
    getLanVal(lang) {
      this.langVal = lang
    }
  }
}
</script>
<style lang="scss" scoped>
.viewport-wrap-box {
    width: 100%;
    height: 100%;
    background: url("./assets/images/bg.jpg") no-repeat top/cover;
    overflow: auto;
    font-size: 0;
}
</style>
