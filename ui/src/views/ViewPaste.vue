<template>
  <div>
    <a-row v-if="pasteNotFound">
      <a-result
          status="error"
          title="Paste不存在"
      >
        <template #icon>
          <a-icon type="disconnect" />
        </template>
        <template #extra>
          <router-link to="/">
            <a-button key="console" icon="edit" type="primary">
              新建Paste
            </a-button>
          </router-link>
        </template>

        <div class="desc">
          <p style="font-size: 16px;">
            <strong>可能是以下原因:</strong>
          </p>
          <p>
            <a-icon :style="{ color: 'red' }" type="close-circle" />
            链接拼写错误或未正确区分大小写。

          </p>
          <p>
            <a-icon :style="{ color: 'red' }" type="close-circle" /> Paste因超过有效期已被系统自动删除。
          </p>
          <p>
            <a-icon :style="{ color: 'red' }" type="close-circle" /> Paste已被创建者删除或因违反《<router-link to="/tos">使用条款</router-link>》已被管理员删除。
          </p>

        </div>
      </a-result>
    </a-row>

    <a-row v-if="burnAfterReading">
      <a-result
          status="warning"
          title="访问警告"
          sub-title="当前Paste为阅后即焚，一旦访问，您将无法再次访问，它将永远删除。"
      >
        <template #extra>
          <a-button icon="eye" type="danger">
            继续访问
          </a-button>
        </template>
      </a-result>
    </a-row>

    <!-- 需要解锁的情况 -->
    <template v-if="locked">
      <a-result status="warning" title="Paste需要密码" sub-title="当前Paste需要授权或解密才能访问，请在下方输入密码解锁页面。" >
        <template #icon>
          <a-icon type="lock" />
        </template>
        <template #extra>
          <div style="width: 260px; display: inline-block">
            <a-input type="password" placeholder="请输入密码" />
          </div>
          <a-button icon="unlock" type="primary">
            解锁
          </a-button>
        </template>
      </a-result>
    </template>

    <template v-if="!locked && !pasteNotFound && ! burnAfterReading">
      <a-row>
        <div style="height: 40px; float: left">
          <img src="https://pastebin.com/themes/pastebin/img/guest.png" style="height: 40px; width: 40px" />
        </div>

        <div style="float: left; margin-left: 10px">
          <div style="font-size: 16px; color: #333333; font-weight: 600">
            未命名
          </div>
          <div>
            <div style="margin-right: 15px; display: inline-block">
              <a-tooltip placement="topLeft" title="Paste创建人" arrow-point-at-center>
                <a-icon type="user" />
                <span style="margin-left: 5px; color: #666666; font-size: 14px">访客</span>
              </a-tooltip>
            </div>

            <div style="margin-right: 15px; display: inline-block">
              <a-tooltip placement="topLeft" title="粘贴板创建时间" arrow-point-at-center>
                <a-icon type="calendar" />
                <span style="margin-left: 5px; color: #666666; font-size: 14px">2021-06-11 22:00:00</span>
              </a-tooltip>
            </div>

            <div style="margin-right: 15px;  display: inline-block">
              <a-tooltip placement="topLeft" title="粘贴板被访问的次数" arrow-point-at-center>
                <a-icon type="eye" />
                <span style="margin-left: 5px; color: #666666; font-size: 14px">10</span>
              </a-tooltip>
            </div>

            <div style="margin-right: 15px;  display: inline-block">
              <a-tooltip placement="topLeft" title="粘贴板将于此时间后自动删除" arrow-point-at-center>
                <a-icon type="global" />
                <span style="margin-left: 5px; color: #666666; font-size: 14px">1小时</span>
              </a-tooltip>
            </div>
          </div>
        </div>
      </a-row>

      <a-row style="margin-top: 10px;">
        <a-alert message="该粘贴板为阅后即焚，意味着您关闭当前页面后将丢失粘贴板中的内容。" banner />
      </a-row>

      <div style="margin-top: 10px; border: 1px solid #dddddd; border-radius: 3px">
        <div style="border-bottom: 1px solid #dddddd; padding: 5px; background-color: #F7F7F7">
          <a-tag>Python</a-tag>
          <span>10KB</span>

          <div style="display: inline-block; float: right; ">
            <a-tag id="copyRawData">复制到剪贴板</a-tag>
            <template v-if="!burnAfterReading">
              <a-tag @click="goRawPage">原始格式</a-tag>
              <a-tag @click="goDownloadPage">下载</a-tag>
              <a-tag>举报</a-tag>
            </template>
          </div>

        </div>
        <div style="width: 100%">
          <MonacoEditor class="editor" width="100%" height="500" theme="vs" language="plaintext" :options="options" :value="content"></MonacoEditor>
        </div>
      </div>


      <div style="margin-top: 30px; font-size: 16px; color: #333333; font-weight: 600">
        原始数据
      </div>
      <div style="margin-top: 10px">
        <a-textarea class="rawData" style="height: 300px; resize: none" :default-value="content" disabled />
      </div>
    </template>
  </div>
</template>

<script>
import MonacoEditor from 'monaco-editor-vue';
import Clipboard from 'clipboard';

export default {
  name: "ViewPaste",
  components: {
    MonacoEditor
  },
  data() {
    return {
      pasteNotFound: false,
      burnAfterReading: false,
      content: 'PHRlbXBsYXRlPgogIDxkaXY+CiAgICA8IS0tIOmcgOimgeino+mUgeeahOaDheWGtSAtLT4KICAgIDx0ZW1wbGF0ZSB2LWlmPSJsb2NrZWQiPgogICAgICA8ZGl2CiAgICAgICAgICBzdHlsZT0ibWFyZ2luLXRvcDogMTVweDsgZm9udC1zaXplOiAxOHB4OyBmb250LXdlaWdodDogNjAwOyBjb2xvcjogIzMzMzMzMzsgYm9yZGVyLWJvdHRvbTogMnB4IHNvbGlkICNkZGRkZGQ7IHBhZGRpbmctYm90dG9tOiA1cHgiPgogICAgICAgIOino+mUgeeymOi0tOadvwogICAgICA8L2Rpdj4KCiAgICAgIDxkaXYgc3R5bGU9Im1hcmdpbi10b3A6IDE1cHgiPgogICAgICAgIDxkaXYgc3R5bGU9ImRpc3BsYXk6IGlubGluZS1ibG9jayI+CiAgICAgICAgICA8ZGl2PuWvhueggTwvZGl2PgogICAgICAgIDwvZGl2PgogICAgICAgIDxkaXYgc3R5bGU9ImRpc3BsYXk6IGlubGluZS1ibG9jazsgbWFyZ2luLWxlZnQ6IDIwcHg7IHdpZHRoOiAzMDBweCI+CiAgICAgICAgICA8YS1pbnB1dCBwbGFjZWhvbGRlcj0iQmFzaWMgdXNhZ2UiLz4KICAgICAgICA8L2Rpdj4KICAgICAgPC9kaXY+CgogICAgICA8ZGl2IHN0eWxlPSJtYXJnaW4tdG9wOiAxNXB4OyBtYXJnaW4tbGVmdDogNzVweCI+CiAgICAgICAgPGEtYnV0dG9uPuino+mUgeeymOi0tOadvzwvYS1idXR0b24+CiAgICAgIDwvZGl2PgogICAgPC90ZW1wbGF0ZT4KCiAgICA8dGVtcGxhdGUgdi1pZj0iIWxvY2tlZCI+CiAgICAgIDxhLXJvdz4KICAgICAgICA8ZGl2IHN0eWxlPSJoZWlnaHQ6IDQwcHg7IGZsb2F0OiBsZWZ0Ij4KICAgICAgICAgIDxpbWcgc3JjPSJodHRwczovL3Bhc3RlYmluLmNvbS90aGVtZXMvcGFzdGViaW4vaW1nL2d1ZXN0LnBuZyIgc3R5bGU9ImhlaWdodDogNDBweDsgd2lkdGg6IDQwcHgiIC8+CiAgICAgICAgPC9kaXY+CgogICAgICAgIDxkaXYgc3R5bGU9ImZsb2F0OiBsZWZ0OyBtYXJnaW4tbGVmdDogNXB4Ij4KICAgICAgICAgIDxkaXYgc3R5bGU9ImZvbnQtc2l6ZTogMTZweDsgY29sb3I6ICMzMzMzMzM7IGZvbnQtd2VpZ2h0OiA2MDAiPgogICAgICAgICAgICDmnKrlkb3lkI0KICAgICAgICAgIDwvZGl2PgogICAgICAgICAgPGRpdj4KICAgICAgICAgICAgPGRpdiBzdHlsZT0ibWFyZ2luLXJpZ2h0OiAxNXB4OyBkaXNwbGF5OiBpbmxpbmUtYmxvY2siPgogICAgICAgICAgICAgIDxhLWljb24gdHlwZT0iY2FsZW5kYXIiIC8+CiAgICAgICAgICAgICAgPHNwYW4gc3R5bGU9Im1hcmdpbi1sZWZ0OiA1cHg7IGNvbG9yOiAjNjY2NjY2OyBmb250LXNpemU6IDE0cHgiPjIwMjEtMDYtMTEgMjI6MDA6MDA8L3NwYW4+CiAgICAgICAgICAgIDwvZGl2PgoKICAgICAgICAgICAgPGRpdiBzdHlsZT0ibWFyZ2luLXJpZ2h0OiAxNXB4OyAgZGlzcGxheTogaW5saW5lLWJsb2NrIj4KICAgICAgICAgICAgICA8YS1pY29uIHR5cGU9ImV5ZSIgLz4KICAgICAgICAgICAgICA8c3BhbiBzdHlsZT0ibWFyZ2luLWxlZnQ6IDVweDsgY29sb3I6ICM2NjY2NjY7IGZvbnQtc2l6ZTogMTRweCI+MTA8L3NwYW4+CiAgICAgICAgICAgIDwvZGl2PgoKICAgICAgICAgICAgPGRpdiBzdHlsZT0ibWFyZ2luLXJpZ2h0OiAxNXB4OyAgZGlzcGxheTogaW5saW5lLWJsb2NrIj4KICAgICAgICAgICAgICA8YS1pY29uIHR5cGU9Imdsb2JhbCIgLz4KICAgICAgICAgICAgICA8c3BhbiBzdHlsZT0ibWFyZ2luLWxlZnQ6IDVweDsgY29sb3I6ICM2NjY2NjY7IGZvbnQtc2l6ZTogMTRweCI+MeWwj+aXtjwvc3Bhbj4KICAgICAgICAgICAgPC9kaXY+CiAgICAgICAgICA8L2Rpdj4KICAgICAgICA8L2Rpdj4KICAgICAgPC9hLXJvdz4KCiAgICAgIDxkaXYgc3R5bGU9Im1hcmdpbi10b3A6IDI1cHg7IGJvcmRlcjogMXB4IHNvbGlkICNkZGRkZGQ7IGJvcmRlci1yYWRpdXM6IDNweCI+CiAgICAgICAgPGRpdiBzdHlsZT0iYm9yZGVyLWJvdHRvbTogMXB4IHNvbGlkICNkZGRkZGQ7IHBhZGRpbmc6IDVweCI+CiAgICAgICAgICA8YS10YWc+UHl0aG9uPC9hLXRhZz4KICAgICAgICAgIDxzcGFuPjEwS0I8L3NwYW4+CgogICAgICAgICAgPGRpdiBzdHlsZT0iZGlzcGxheTogaW5saW5lLWJsb2NrOyBmbG9hdDogcmlnaHQiPgogICAgICAgICAgICA8YS10YWc+5aSN5Yi25Yiw5Ymq6LS05p2/PC9hLXRhZz4KICAgICAgICAgICAgPGEtdGFnPuWOn+Wni+agvOW8jzwvYS10YWc+CiAgICAgICAgICAgIDxhLXRhZz7kuIvovb08L2EtdGFnPgogICAgICAgICAgICA8YS10YWc+5Li+5oqlPC9hLXRhZz4KICAgICAgICAgIDwvZGl2PgogICAgICAgIDwvZGl2PgogICAgICAgIDxkaXYgc3R5bGU9IndpZHRoOiAxMDAlIj4KICAgICAgICAgIDxNb25hY29FZGl0b3IgY2xhc3M9ImVkaXRvciIgd2lkdGg9IjEwMCUiIGhlaWdodD0iNTAwIiB0aGVtZT0idnMiIGxhbmd1YWdlPSJwbGFpbnRleHQiIDpvcHRpb25zPSJvcHRpb25zIj48L01vbmFjb0VkaXRvcj4KICAgICAgICA8L2Rpdj4KICAgICAgPC9kaXY+CgoKICAgICAgPGRpdiBzdHlsZT0ibWFyZ2luLXRvcDogMzBweDsgZm9udC1zaXplOiAxNnB4OyBjb2xvcjogIzMzMzMzMzsgZm9udC13ZWlnaHQ6IDYwMCI+CiAgICAgICAg5Y6f5aeL5pWw5o2u57KY6LS05p2/CiAgICAgIDwvZGl2PgogICAgICA8ZGl2IHN0eWxlPSJtYXJnaW4tdG9wOiAxMHB4Ij4KICAgICAgICA8YS10ZXh0YXJlYSBzdHlsZT0iaGVpZ2h0OiAzMDBweDsgcmVzaXplOiBub25lIiBkaXNhYmxlZCAvPgogICAgICA8L2Rpdj4KICAgIDwvdGVtcGxhdGU+CiAgPC9kaXY+CjwvdGVtcGxhdGU+Cgo8c2NyaXB0PgppbXBvcnQgTW9uYWNvRWRpdG9yIGZyb20gJ21vbmFjby1lZGl0b3ItdnVlJzsKCmV4cG9ydCBkZWZhdWx0IHsKICBuYW1lOiAiVmlld1Bhc3RlIiwKICBjb21wb25lbnRzOiB7CiAgICBNb25hY29FZGl0b3IKICB9LAogIGRhdGEoKSB7CiAgICByZXR1cm4gewogICAgICBvcHRpb25zOiB7CiAgICAgICAgbGluZU51bWJlcnM6ICdvbicsCiAgICAgICAgcmVhZE9ubHk6IHRydWUsCiAgICAgICAgY29udGVudDogJ+i/meaYr+a1i+ivleaVsOaNruWViicsCiAgICAgICAgbWluaW1hcDogewogICAgICAgICAgZW5hYmxlZDogZmFsc2UKICAgICAgICB9CiAgICAgIH0sCiAgICAgIGxvY2tlZDogZmFsc2UsCiAgICB9CiAgfSwKICBjcmVhdGVkKCkgewogICAgdGhpcy5sb2NrZWQgPSB0aGlzLiRyb3V0ZS5wYXJhbXMucGlkID09PSAnYSc7CiAgfQp9Cjwvc2NyaXB0PgoKPHN0eWxlIHNjb3BlZD4KCi5lZGl0b3IgewogIGJvcmRlcjogbm9uZTsKfQoKPC9zdHlsZT4=',
      options: {
        lineNumbers: 'on',
        readOnly: true,
        content: '这是测试数据啊',
        minimap: {
          enabled: false
        }
      },
      locked: false,
    }
  },
  methods: {
    goRawPage() {
      this.$message.warn('当前粘贴板为阅后即焚，不支持查看Raw数据');
    },
    goDownloadPage() {
      this.$message.warn('当前粘贴板为阅后即焚，不支持下载');
    },
    initCopy() {
      const self = this;
      const clipboard = new Clipboard('#copyRawData', {
        text: function () {
          return self.content;
        }
      });
      clipboard.on('success', function() {
        self.$message.success('复制成功');
      });

      clipboard.on('error', function() {
        self.$message.error('复制失败');
      });
    }
  },
  created() {
    this.locked = this.$route.params.pid === '12345678';
    this.pasteNotFound = this.$route.params.pid === '00000000';
    this.burnAfterReading = this.$route.params.pid === '00000001';
    this.initCopy();
  }
}
</script>

<style scoped>

.editor {
  border: none;
}


.rawData[disabled] {
  color: #333333;
  background-color: #ffffff;
  cursor: copy;
}
</style>