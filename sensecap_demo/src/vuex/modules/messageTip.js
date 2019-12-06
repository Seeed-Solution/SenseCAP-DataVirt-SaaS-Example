import * as types from '../mutation-types'
const state = {
  messageTipIsShow: false, // 是否显示
  messageText: '', // 消息提示文本
  duration: 2000, // 动画持续时间
  type: 'info', // 消息提示类型
  isShowCloseBtn: true // 是否显示关闭按钮
}
const mutations = {
  [types.SHOW_MESSAGE_TIP] (state, {
    messageTipData
  }) { // 显示
    let messageData = Object.assign({}, {
      duration: state.duration,
      type: state.type,
      isShowCloseBtn: state.isShowCloseBtn
    }, messageTipData)
    state.messageTipIsShow = true
    state.messageText = messageData.text
    state.duration = messageData.duration
    state.type = messageData.type
    state.isShowCloseBtn = messageData.isShowCloseBtn
  },
  [types.HIDE_MESSAGE_TIP] (state) { // 隐藏
    state.messageTipIsShow = false
  }
}
export default {
  state,
  mutations
}
