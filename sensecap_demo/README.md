# sensecap_demo

#### 介绍

sensecap demo

#### 软件架构

软件架构说明

#### npm install 安装所有依赖

#### npm run dev 本地运行(localhost:6060)

#### npm run build 打包

#### views/index/index.vue 主页

#### views/index/components/control.vue 控制中心

#### views/index/components/warning.vue 报警中心

#### views/index/components/chart.vue 测量类型图表

#### views/index/components/history.vue 历史数据

#### views/index/components/range.vue 测量正常范围

#### views/index/components/run.vue 设备运行情况

#### views/index/components/soil.vue 土壤状况

#### views/index/components/meteorology.vue 环境状况

#### views/index/components/video.vue 视频状况

#### services/ajax.js 封装ajax文件 便于对ajax调用的统一管理

##### 调用方式：

##### 例：get方式

      (```)
          ajax.getData(api_url, {
               params: {
                 id: 001
               }
            }).then(res => {
                if (res.code == 0) {

                } else {

                }
              }).catch(err => {

                })
      (```)

##### post方式(patch/patchData、delete/deleteData、put/putData)

      (```)
          ajax.postData(api_url, {
              id: 001
            }).then(res => {
                if (res.code == 0) {

                } else {

                }
              }).catch(err => {

                })
      (```)

#### services/api.js 封装所有的api 便于多页面调用

#### config/index.js 参数配置(更新数据时间)

#### assets/lang/cn.js 中文

#### assets/lang/en.js 英文

##### main.js 中可配置语言
