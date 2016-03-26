var webpack = require("webpack");

module.exports = {
  module: {
    loaders: [
      {
        test: /\.js$/,
        loaders: [ 'babel-loader' ],
        exclude: /node_modules/
      }
    ]
  },
  plugins: [
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: false
      }
    }),
    new webpack.DefinePlugin({
      'process.env.NODE_ENV': '"production"'
    })
  ]
};
