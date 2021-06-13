<template>
  <a-result status="success" title="Paste创建成功">
    <div class="desc">
      <p style="font-size: 16px;">
        <strong>可通过以下方式访问Paste:</strong>
      </p>
      <p>
        <a-icon type="info-circle" /> 通过下方链接访问 <a-tag id="copy" style="margin-left: 10px">复制链接</a-tag>
      </p>
      <p>
        <a :href="visitUrl">{{ visitUrl }}</a>
      </p>
      <p>
        <a-icon type="info-circle" /> 扫描下方二维码访问 <a-tag style="margin-left: 10px">下载二维码</a-tag>
      </p>
      <p id="qrcode"></p>
    </div>

    <template #extra>
      <router-link :to="'/p/'+ pid">
        <a-button icon="eye" type="primary">
          访问Paste
        </a-button>
      </router-link>

      <a-button icon="edit">
        新建Paste
      </a-button>
    </template>
  </a-result>
</template>

<script>
import QRCode from 'qrcodejs2'
import Clipboard from "clipboard";

export default {
  name: "PasteCreateSuccessView",
  props: {
    pid: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      visitUrl: ''
    }
  },
  methods: {
    init() {
      this.visitUrl = 'http://10.1.1.210:8080/p/'+ this.pid;
      let qrcode = new QRCode('qrcode', {
        width: 132,
        height: 132,
        colorDark : "#000",
        colorLight : "#fff",
      });
      qrcode.makeCode(this.visitUrl);

      const self = this;
      const clipboard = new Clipboard('#copy', {
        text: function () {
          return self.visitUrl;
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
  mounted() {
    this.init();
  }
}
</script>

<style scoped>
</style>