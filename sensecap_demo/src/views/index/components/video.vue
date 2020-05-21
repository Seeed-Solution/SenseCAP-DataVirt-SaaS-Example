<template>
<div class="bottom mb10 ml10 green-border transition">
  <div class="content">
    <h1>{{$t('localization').video_con}}</h1>
    <div class="video-all">
      <div class="h5video" id="playerDiv1"></div>
    </div>
  </div>
</div>
</template>

<script>
require('Assets/js/flowplayer-3.2.13.min.js')
import swfStr from 'Assets/swf/flowplayer-3.2.18.swf'
export default {
  name: 'video-live',
  data() {
    return {
      video_uri: 'rtmp://rtmp01open.ys7.com/openlive/2f0eb60f32de4711899cfd127ba76e4b.hd',
      options: {
        clip: {
          provider: 'rtmp',
          bufferLength: 0,
          bufferTime: 0,
          autoPlay: true,
          live: true
        },
        plugins: {
          rtmp: {
            url: require("Assets/swf/flowplayer.rtmp-3.2.13.swf"),
            netConnectionUrl: 'rtmp://rtmp01open.ys7.com/openlive/2f0eb60f32de4711899cfd127ba76e4b.hd'
            // rtmp://rtmp01open.ys7.com/openlive/2f0eb60f32de4711899cfd127ba76e4b.hd
          },
          controls: {
            url: require("Assets/swf/flowplayer.controls-3.2.16.swf"),
            play: true, //开始按钮
            volume: true, //音量按钮
            mute: true, //静音按钮
            stop: false, //停止按钮
            fullscreen: true, //全屏按钮
            time: true, //是否显示时间信息
            tooltips: {
              buttons: false, //是否显示
              fullscreen: '全屏', //全屏按钮，鼠标指上时显示的文本
              stop: '停止',
              play: '开始',
              volume: '音量',
              mute: '静音'
            }
          }
        }
      }
    }
  },
  mounted() {
    var oplayerDiv1 = document.getElementById('playerDiv1');
    oplayerDiv1.setAttribute("data-rtmp", this.video_uri);
    oplayerDiv1.setAttribute("href", this.video_uri);
    let timer = setTimeout(() => {
      flowplayer('playerDiv1', swfStr, this.options);
      clearTimeout(timer);
      timer = null;
    }, 6000)
  }
}
</script>
<style lang="scss" scoped>
@import "../index.scss";
.bottom.mb10.ml10.green-border.transition {
    height: 30%;
    min-height: 3rem;
    overflow: hidden;
    .content {
        height: 100%;
        .video-all {
            width: 98%;
            width: calc(100% - 0.02rem);
            width: -webkit-calc(100% - 0.02rem);
            width: -moz-calc(100% - 0.02rem);
            margin: 0 auto;
            height: calc(100% - 0.54rem);
            height: -webkit-calc(100% - 0.54rem);
            height: -moz-calc(100% - 0.54rem);
            #playerDiv1 {
                width: 100%;
                min-height: 2.5rem;
                height: 100%;
            }
        }
    }
}
</style>
