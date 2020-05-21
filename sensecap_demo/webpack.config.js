const webpack = require('webpack');
const merge = require('webpack-merge');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin'); // webpack html模板插件
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const baseWebpackConfig = require('./webpack.base.config.js');
const isProduction = process.env.NODE_ENV == 'production' || process.env.NODE_ENV == 'server';


// 判断为生产模式，压缩js
if (isProduction) {
  var prodConfig = {
    mode: 'production',
    devtool: '',
    output: {
      publicPath: '' // ttf文件打包'./'
    },
    optimization: {
      runtimeChunk: true,
      splitChunks: {
        chunks: 'all',
        minSize: 24400,
        // 分割后的最大文件大小 超过这个数字后继续分割
        maxSize: 0,
        // 默认，至少被引入一次就进行代码分隔
        minChunks: 1,
        // 默认，浏览器最多并行请求5个js文件,也就是说，分割数量超过5个时，就会停止代码分割了
        maxAsyncRequests: 5,
        // 默认，对于入口文件最多只分割5个js包，超过5个就停止
        maxInitialRequests: 5,
        // 默认，文件名连接符
        automaticNameDelimiter: '~',
        // 默认，分割后的文件名将根据chunks和cacheGroups自动生成名称。
        name: true,
        cacheGroups: {
          vendors: { // vendors是组名
            // 默认，只对node_modules里的代码进行分隔
            test: /[\\/]node_modules[\\/]/,
            // 默认，每个组都会有个优先级， 如果某个包满足多个组的test规则，就按优先级来判断归哪个组 数值越大，优先级越高
            priority: -10,
            // 分割后的文件名（默认是：组名~入口名.js，即verdors~main.js）
            // filename: 'vendors.js',
            // 强制分隔，无视minChunks、maxAsyncRequests等选项，默认false
            enforce: true
          },
          default: { // default是组名, 分隔不在node_modules里的代码
            minChunks: 2, // 默认
            priority: -20, // 默认
            // 复用已存在的chunk, 比如index.js里引入axios和test.js test里也引入了axios，那么axios就会被复用
            reuseExistingChunk: true
          },
          // 将项目所有css打包到一个文件中 还可以分入口打包：https://webpack.js.org/plugins/mini-css-extract-plugin
          styles: {
            name: 'styles',
            test: /\.less$/,
            chunks: 'all',
            enforce: true,
          }
        }
      }
    },
    plugins: [
      // 提取SourceMap到独立文件
      new webpack.SourceMapDevToolPlugin({
        filename: 'map/[name].js.map'
      }),
      new webpack.DefinePlugin({
        'process.env': {
          NODE_ENV: '"production"'
        }
      }),
      new webpack.optimize.ModuleConcatenationPlugin(), // 作用域提升

      new MiniCssExtractPlugin({
        filename: 'css/[name].[hash:8].css'
      }),
      new OptimizeCssAssetsPlugin({
        assetNameRegExp: /\.css$/g,
        cssProcessor: require('cssnano'),
        cssProcessorPluginOptions: {
          preset: ['default', {
            discardComments: {
              removeAll: true,
            },
            normalizeUnicode: false
          }]
        },
        canPrint: true
      }), // 压缩CSS文件的插件
      new HtmlWebpackPlugin({
        title: 'Smart Agriculture Monitoring Data Platform (智慧农业监控数据平台)',
        description: 'SENSECAP',
        favicon: './src/assets/images/favicon.ico', // favicon路径，通过webpack引入同时可以生成hash值
        filename: 'index.html', // 生成的html存放路径，相对于path
        template: './src/index.html', // html模板路径
        inject: true, // 允许插件修改哪些内容，包括head与body
        // hash: true, // 为静态资源生成hash值
        cache: false, // 是否缓存
        // chunks: ['main'],// 需要引入的chunk，不配置就会引入所有页面的资源
        minify: {
          removeComments: true,
          collapseWhitespace: true,
          removeAttributeQuotes: true,
          minifyJS: true,
          minifyCSS: true
        },
        chunksSortMode: 'dependency'
      })
    ]
  };
  module.exports = merge(baseWebpackConfig, prodConfig)
} else if (process.env.NODE_ENV === 'dev') {
  var devConfig = {
    mode: 'development',
    output: {
      filename: '[name].js',
      chunkFilename: '[name].js'
    },
    devtool: '#source-map',
    devServer: {
      clientLogLevel: 'warning',
      historyApiFallback: true,
      hot: true,
      compress: true,
      host: '0.0.0.0',
      open: true,
      noInfo: true,
      useLocalIp: true,
      port: 6060, // 端口
      proxy: {
        '/api': {
          target: 'http://104.42.255.178:49789',
          changeOrigin: true, // 是否允许跨越
          pathRewrite: {
            '^/api': '' // 重写
          }
        }
      }
    },
    plugins: [
      new webpack.HotModuleReplacementPlugin(),
      new HtmlWebpackPlugin({
        title: 'Smart Agriculture Monitoring Data Platform (智慧农业监控数据平台)',
        filename: 'index.html',
        template: './src/index.html',
        inject: true
      })
    ]
  };
  module.exports = merge(baseWebpackConfig, devConfig)
}
