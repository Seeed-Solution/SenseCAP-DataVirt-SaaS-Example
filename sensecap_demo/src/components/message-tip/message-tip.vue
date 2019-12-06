<template>
    <transition name="message-fade">
        <div class="message-tip-box" v-show="isShow" @mouseover="clearTimer" @mouseout="startTimer">
            <img class="message-icon" :src="typeImg" alt="">
            <div class="message-group">
                <p>{{ messageText }}</p>
                <div v-if="isShowCloseBtn" class="message-closeBtn icon-tip icon-tip-close" @click="handleClose"></div>
            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        props: {
            isShow: { //是否显示。 默认 false
                type: Boolean,
                default: false
            },
            messageText: { //消息文本信息。 默认''
                type: String,
                default: ''
            },
            duration: { //动画持续时间。默认2000ms
                type: Number,
                default: 2000
            },
            type: { // 消息提示图标 可选success/warning/info/error 默认值： 'info'
                type: String,
                default: 'info'
            },
            isShowCloseBtn: { //是否显示关闭按钮
                type: Boolean,
                default: true
            }
        },
        data() {
            return {
                timer: null
            };
        },

        computed: {
            typeImg() {
                return require(`./icons/${ this.type }.svg`);
            }
        },

        watch: {
            isShow(newVal) {
                if (newVal) {
                    this.clearTimer()
                    this.startTimer()
                }
            }
        },

        methods: {
            handleClose() {
                this.clearTimer()
                this.$emit('off', false)
            },

            clearTimer() {
                clearTimeout(this.timer);
            },

            startTimer() {
                if (this.duration > 0) {
                    this.timer = setTimeout(() => {
                        this.$emit('off', false)
                    }, this.duration);
                }
            }
        },

        mounted() {
            //this.startTimer();
        }
    };
</script>
<style lang="scss" scoped>
    @import './message-tip'
</style>
