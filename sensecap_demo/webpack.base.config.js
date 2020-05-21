const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
// const CopyWebpackPlugin = require('copy-webpack-plugin');
const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const webpack = require('webpack');
const isProduction = process.env.NODE_ENV == 'production' || process.env.NODE_ENV == 'server';

module.exports = {
  entry: {
    index: ['babel-polyfill', './src/main.js'],
    vendor: ['vue', 'vue-router', 'vuex', 'axios', 'jquery', 'echarts'],
    element: ['element-ui']
  },
  output: {
    path: path.resolve(__dirname, './dist'), // 入口文件的输出地址
    filename: isProduction ? 'js/[name].[hash:8].js' : '[name].js',
    publicPath: isProduction ? './' : '', // 模板、样式、脚本、图片等资源对应的server上的路径
    chunkFilename: 'js/[name].[hash:8].js'
  },
  resolve: {
    // 设置require或import的时候可以不需要带后缀
    extensions: ['.js', '.vue', '.scss', '.less', '.css'],
    alias: {
      '@': path.join(__dirname, 'src'),
      'Services': path.resolve(__dirname, './src/services/'),
      'Components': path.resolve(__dirname, './src/components/'),
      'Views': path.resolve(__dirname, './src/views/'),
      'Utils': path.resolve(__dirname, './src/utils/'),
      'vue': 'vue/dist/vue.min.js',
      'vue-router': 'vue-router/dist/vue-router.min.js',
      'vuex': 'vuex/dist/vuex.min.js',
      // 'static': path.join(__dirname, 'static'),
      'Assets': path.resolve(__dirname, './src/assets/')
    }
  },

  module: {
    rules: [{
      test: /\.js$/,
      use: ['babel-loader?cacheDirectory'],
      include: [path.resolve(__dirname, 'src')],
      exclude: [path.resolve(__dirname, 'node_modules')]
    }, {
      test: /\.vue$/,
      loader: 'vue-loader'
    }, {
      test: /\.json$/,
      use: ['json-loader']
    }, {
      test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
      use: [{
        loader: 'url-loader',
        options: {
          limit: 5000,
          name: 'img/[name].[hash:7].[ext]'
        }
      }, {
        loader: 'image-webpack-loader'
      }]
    }, {
      test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
      loader: 'url-loader',
      options: {
        limit: 5000,
        name: '[name].[hash:7].[ext]'
      }
    }, {
      test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
      loader: 'url-loader?name=fonts/[name].[ext]',
      options: {
        limit: 5000,
        name: 'fonts/[name].[hash:7].[ext]'
      }
    }, {
      test: /\.css$/,
      use: [
        isProduction ? {
          loader: MiniCssExtractPlugin.loader,
          options: {
            // 这里可以指定一个 publicPath
            // 默认使用 webpackOptions.output中的publicPath
            publicPath: '../'
          }
        } : 'vue-style-loader',
        'css-loader'
      ]
    }, {
      test: /\.styl(us)?$/,
      use: [
        'vue-style-loader',
        'css-loader',
        'style-loader'
      ]
    }, {
      test: /\.less$/,
      use: ["style-loader", 'css-loader', 'less-loader']
    }, {
      test: /\.scss$/,
      use: ['style-loader', 'css-loader', 'sass-loader']
    }, {
      test: /\.swf$/,
      loader: 'file-loader',
      options: {
        limit: 10000,
        name: 'swf/[name].[hash:7].[ext]'
      }
    }]
  },
  plugins: [
    new CleanWebpackPlugin(['dist']),
    new webpack.ProvidePlugin({ // 全局配置加载
      '$': 'jquery',
      'jQuery': 'jquery',
      'window.jQuery': 'jquery'
    }),
    new FriendlyErrorsPlugin(),
    new VueLoaderPlugin(),
    // new CopyWebpackPlugin([{
    //   from: 'static',
    //   to: 'static'
    // }]),
    new webpack.DefinePlugin({
      isDebug: JSON.stringify(process.env.NODE_ENV)
    })
  ]
}
