module.exports = {
    chainWebpack: config =>{
        config.plugin('html')
            .tap(args => {
                args[0].title = "Quick Paste - 一款开源文本分享平台";
                return args;
            })
    },
}