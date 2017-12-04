let path = require('path');

module.exports = {
    entry: './static/js/main.js',
    output: {
        path: path.resolve(__dirname, './static/dist/'),
        filename: 'build.js',
        publicPath: '/static/dist/'
    },
    resolve: {
        alias: {
            vue: 'vue/dist/vue.js'
        }
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    esModule: true,
                    loaders: {
                        's[c|a]ss': 'vue-style-loader!css-loader!sass-loader?indentedSyntax',
                        'pug|jade': 'pug-loader',
                        'ts': 'ts-loader'
                    }
                }
            },
            {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.(png|jpg|gif)$/,
                loader: 'file-loader',
                options: {
                    outputPath: '../assets/',
                    publicPath: '/',
                    name: '[name].[ext]'
                }
            }
        ]
    }
};