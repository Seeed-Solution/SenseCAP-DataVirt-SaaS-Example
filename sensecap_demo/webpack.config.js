var webpack = require('webpack')
var merge = require('webpack-merge')
var path = require('path')
var HtmlWebpackPlugin = require('html-webpack-plugin') // webpack html模板插件
var ExtractTextPlugin = require('extract-text-webpack-plugin') // css提取插件
var CopyWebpackPlugin = require('copy-webpack-plugin') // 复制文件
var CleanPlugin = require('clean-webpack-plugin') // webpack插件，用于清除目录文件
var FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin')
var production = process.env.NODE_ENV
var cssLoaders = function (options) {
  options = options || {}
  const cssLoader = {
    loader: 'css-loader',
    options: {
      sourceMap: options.sourceMap
    }
  }
  const postcssLoader = {
    loader: 'postcss-loader',
    options: {
      sourceMap: options.sourceMap
    }
  }

  // generate loader string to be used with extract text plugin
  function generateLoaders (loader, loaderOptions) {
    const loaders = options.usePostCSS ? [cssLoader, postcssLoader] : [cssLoader]

    if (loader) {
      loaders.push({
        loader: loader + '-loader',
        options: Object.assign({}, loaderOptions, {
          sourceMap: options.sourceMap
        })
      })
    }

    // Extract CSS when that option is specified
    // (which is the case during production build)
    if (options.extract) {
      return ExtractTextPlugin.extract({
        use: loaders,
        publicPath: '../../',
        fallback: 'vue-style-loader'
      })
    } else {
      return ['vue-style-loader'].concat(loaders)
    }
  }

  // https://vue-loader.vuejs.org/en/configurations/extract-css.html
  return {
    css: generateLoaders(),
    postcss: generateLoaders(),
    less: generateLoaders('less'),
    sass: generateLoaders('sass', {
      indentedSyntax: true
    }),
    scss: generateLoaders('sass'),
    stylus: generateLoaders('stylus'),
    styl: generateLoaders('stylus')
  }
}

var styleLoaders = function (options) {
  const output = []
  const loaders = cssLoaders(options)

  for (const extension in loaders) {
    const loader = loaders[extension]
    output.push({
      test: new RegExp('\\.' + extension + '$'),
      use: loader
    })
  }

  return output
}

const isProduction = process.env.NODE_ENV === 'production'
const sourceMapEnabled = isProduction

var vueLoaderOptions = {
  'loaders': cssLoaders({
    sourceMap: sourceMapEnabled,
    extract: isProduction
  }),
  'cssSourceMap': sourceMapEnabled,
  'cacheBusting': true,
  'transformToRequire': {
    'video': [
      'src',
      'poster'
    ],
    'source': 'src',
    'img': 'src',
    'image': 'xlink:href'
  }
}

var config = {
  // 入口文件地址
  entry: {
    index: ['babel-polyfill', './src/main.js'],
    vendor: ['vue', 'vue-router', 'vuex', 'axios', 'jquery']
  },
  // 输出
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
      'static': path.resolve(__dirname, './static/')
    }
  },

  // 加载器
  module: {
    // 加载器
    rules: [{
      test: /\.js$/,
      include: path.join(__dirname, 'src'),
      exclude: /node_modules/,
      use: ['babel-loader?cacheDirectory']
    },
    {
      test: /\.vue$/,
      loader: 'vue-loader',
      options: vueLoaderOptions
    },
    {
      test: /\.json$/,
      use: ['json-loader']
    },
    {
      test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
      loader: 'url-loader',
      options: {
        limit: 10000,
        name: 'img/[name].[hash:7].[ext]'
      }
    },
    {
      test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
      loader: 'url-loader?name=fonts/[name].[ext]',
      options: {
        limit: 10000,
        name: 'img/[name].[hash:7].[ext]'
      }
    },
    {
      test: /\.swf$/,
      loader: 'url-loader',
      options: {
        limit: 10000
      }
    }
    ]
  },
  plugins: [
    new webpack.ProvidePlugin({ // 全局配置加载
      '$': 'jquery',
      'jQuery': 'jquery',
      'window.jQuery': 'jquery'
    }),
    new CopyWebpackPlugin([{
      from: 'static',
      to: 'static'
    }]),
    new webpack.DefinePlugin({
      isDebug: JSON.stringify(production)
    }),
    new CleanPlugin(['dist']),
    new FriendlyErrorsPlugin(),
    new webpack.HotModuleReplacementPlugin()
  ],
  // 添加了此项，则表明从外部引入，内部不会打包合并进去.
  // jquery如需从外部加载，则可选择此项
  externals: {
    // jquery: 'window.jQuery',
  },
  // 开启source-map，webpack有多种source-map，在官网文档可以查到
  devtool: '#eval-source-map'
}

// 判断为生产模式，压缩js
if (isProduction) {
  var prodConfig = {
    devtool: '',
    output: {
      publicPath: '' // ttf文件打包'./'
    },
    module: {
      rules: styleLoaders({
        sourceMap: true,
        extract: true,
        usePostCSS: true
      })
    },
    plugins: [
      new webpack.DefinePlugin({
        'process.env': {
          NODE_ENV: '"production"'
        }
      }),
      new ExtractTextPlugin('css/[name].[hash:8].css', {
        allChunks: true
      }),
      new webpack.optimize.UglifyJsPlugin({
        compress: {
          warnings: false
        }
      }),
      new webpack.optimize.CommonsChunkPlugin({
        name: ['vendor'] // 将公共模块提取，生成名为`vendors`的chunk
      }),
      new HtmlWebpackPlugin({
        title: '智慧农业监控数据平台',
        keywords: '',
        description: '',
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
          removeAttributeQuotes: true
        },
        // necessary to consistently work with multiple chunks via CommonsChunkPlugin
        chunksSortMode: 'dependency'
      })
    ]
  }
  module.exports = merge(config, prodConfig)
} else if (process.env.NODE_ENV === 'dev') {
  var devConfig = {
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
    module: {
      rules: styleLoaders({
        sourceMap: false,
        usePostCSS: true
      })
    },
    plugins: [
      new CopyWebpackPlugin([{
        from: 'src/assets/images',
        to: 'images'
      },
      {
        from: 'src/assets/css',
        to: 'css'
      },
      {
        from: 'src/assets/fonts',
        to: 'fonts'
      }
      ]),
      new HtmlWebpackPlugin({
        title: '智慧农业监控数据平台',
        filename: 'index.html',
        template: './src/index.html',
        inject: true
      })
    ]
  }
  module.exports = merge(config, devConfig)
}
