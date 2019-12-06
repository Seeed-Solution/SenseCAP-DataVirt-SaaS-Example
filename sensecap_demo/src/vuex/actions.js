import * as types from './mutation-types'

/* 全局 message-tip */
export const showMessageTip = ({
  commit
}, messageTipData) => {
  commit(types.SHOW_MESSAGE_TIP, {
    messageTipData
  })
}

export const hideMessageTip = ({
  commit
}) => {
  commit(types.HIDE_MESSAGE_TIP)
}
