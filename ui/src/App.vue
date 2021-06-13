<template>
  <a-config-provider :locale="zh_CN">
    <div id="app">
      <a-row type="flex" justify="center" class="header">
        <a-col :md="24" :xl="12" class="header-container">
          <div class="site-title">
            <router-link class="site-title" style="line-height: 50px" to="/">
              Quick Paste
            </router-link>
          </div>

          <div style="float: right; line-height: 50px">
            <router-link style="color: #ffffff;" to="/tos">
              使用条款
            </router-link>

            <router-link style="color: #ffffff; margin-left: 25px" to="/about">
              关于
            </router-link>
          </div>
        </a-col>
      </a-row>

      <div class="container">
        <a-row type="flex" justify="center">
          <a-col :md="24" :xl="12" class="main-container">
            <router-view/>
          </a-col>
        </a-row>
      </div>

      <div class="footer">
        © 2021 Powered by Quick Paste
      </div>
    </div>
  </a-config-provider>
</template>

<script>
import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN'

export default {
  name: 'app',
  data() {
    return {
      zh_CN,
    }
  },
  components: {
  }
}
</script>

<style scoped>
#app, body {
  width: 100%;
  height: 100%;
}

.header {
  height: 50px;
  background-color: #000000;
  display: flex;
  align-content: center
}

.header-container {
  padding: 0 24px;
}

.site-title {
  color: #ffffff;
  font-size: 20px;
  font-weight: 700;
  display: inline-block;
}

.container {
  height: calc(100% - 100px);
  background-color: #F0F2F5;
  padding-bottom: 30px;
  padding-top: 24px;
}

.main-container {
  background-color: #ffffff;
  padding: 24px;
  min-height: 280px;
}

.footer {
  height: 50px;
  text-align: center;
  line-height: 50px;
}
</style>
