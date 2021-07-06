<template>
  <div>

    <paste-create-success-view v-if="pid !== ''" :pid="pid" />

    <template v-if="pid === ''">

      <a-row type="flex" justify="space-between">
        <div>
          <span class="title ">新Paste</span>
        </div>

        <span id="import" onclick="document.getElementById('fileSelector').click()">
      从文件中导入
      <input type="file" id="fileSelector" style="display: none" @change="importCodeWithFile">
      </span>

      </a-row>

      <a-row>
        <a-spin tip="导入文件中..." :spinning="fileImporting">
          <a-textarea class="editor" rows="20" v-model="content" />
        </a-spin>
      </a-row>

      <a-row class="title options-title">
        Paste设置
      </a-row>

      <a-row style="margin-top: 15px;">
        <a-col :md="24" :lg="12">
          <div class="option-item">
            <div class="option-name">
              <span>名称/标题</span>
              <a-tooltip placement="topLeft" title="给Paste起个好听的名字吧" arrow-point-at-center>
                <a-icon style="margin-left: 2px" type="info-circle"/>
              </a-tooltip>
            </div>
            <div class="option-value">
              <a-input placeholder="未命名"/>
            </div>
          </div>

          <div class="option-item">
            <div class="option-name">
              高亮显示
              <a-tooltip placement="topLeft" title="有助于代码展示和阅读" arrow-point-at-center>
                <a-icon style="margin-left: 2px" type="info-circle"/>
              </a-tooltip>
            </div>
            <div class="option-value">
              <a-select
                  default-value="plaintext"
                  show-search
                  option-filter-prop="children"
                  style="width: 100%"
              >
                <a-select-option value="plaintext">
                  纯文本
                </a-select-option>
                <a-select-option value="python">
                  Python
                </a-select-option>
                <a-select-option value="java">
                  Java
                </a-select-option>
              </a-select>
            </div>
          </div>

          <div class="option-item">
            <div class="option-name">
              过期策略
              <a-tooltip placement="topLeft" title="超过设定值后Paste将会由系统自动删除" arrow-point-at-center>
                <a-icon style="margin-left: 2px" type="info-circle"/>
              </a-tooltip>
            </div>
            <div class="option-value">
              <a-select
                  :default-value="defaultExpirationStrategy"
                  show-search
                  option-filter-prop="children"
                  style="width: 100%"
              >
                <a-select-option v-for="item in expirationStrategies" :key="item.value" :value="item.value">
                  {{ item.label }}
                </a-select-option>
              </a-select>
            </div>
          </div>

          <div class="option-item">
            <div class="option-name">隐私保护
              <a-tooltip placement="topLeft" title="正确的选择有助于保护您的隐私" arrow-point-at-center>
                <a-icon style="margin-left: 2px" type="info-circle"/>
              </a-tooltip>

            </div>
            <div class="option-value">
              <a-radio-group name="radioGroup" v-model="privacyPolicy">
                <a-radio value="NONE">
                  <a-tooltip placement="topLeft" title="内容明文存储在服务器中，并且所有知道链接的人都可以查看" arrow-point-at-center>
                    公开
                  </a-tooltip>
                </a-radio>
                <a-radio value="1">
                  <a-tooltip placement="topLeft" title="内容明文存储在服务器中，访问时需要密码授权才能查看内容" arrow-point-at-center>
                    私密分享
                  </a-tooltip>
                </a-radio>
                <a-radio value="2" disabled>
                  <a-tooltip placement="topLeft"
                             title="内容将在浏览器中使用AES加密后存储在服务器中，访问时系统会将密文直接返回给访问者，访问者需要提供正确的密码才能解密并查看内容。注：密码丢失后内容将永远无法解密"
                             arrow-point-at-center>
                    数据加密
                  </a-tooltip>
                </a-radio>
              </a-radio-group>
            </div>
          </div>

          <div class="option-item" v-if="privacyPolicy !== 'NONE'">
            <div class="option-name"></div>
            <div class="option-value">
              <a-input placeholder="请输入密码"/>
            </div>
          </div>

          <div class="option-item" style="margin-top: 20px">
            <div class="option-name">
            </div>
            <div class="option-value">
              <a-button @click="createPaste" icon="save" :loading="false">
                创建Paste
              </a-button>
            </div>
          </div>
        </a-col>
      </a-row>
    </template>
  </div>
</template>

<script>
import CryptoJS from "crypto-js";
import PasteCreateSuccessView from "@/components/PasteCreateSuccessView";

export default {
  name: 'Home',
  components: {
    PasteCreateSuccessView
  },
  data() {
    return {
      pid: '',
      fileImporting: false,
      privacyPolicy: 'NONE',
      content: '',
      expirationStrategies: [
        {
          label: '阅后即焚',
          value: '0',
        },
        {
          label: '15分钟',
          value: '15'
        },
        {
          label: '1小时',
          value: '60'
        },
        {
          label: '1天',
          value: '1440'
        },
        {
          label: '永不过期',
          value: '-1'
        }
      ],
      defaultExpirationStrategy: '15',
    }
  },
  methods: {
    createPaste() {
      let out = this.getEncryptedContent('aaa', 'aaa');
      console.log(out);
      // console.log(this.getDe(out, 'aaa'))
      this.pid = '00000000';

    },
    getEncryptedContent(content, secret) {
      const secretWithMd5 = CryptoJS.MD5(secret).toString();

      return CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(content), CryptoJS.enc.Utf8.parse(secretWithMd5), {
        iv: CryptoJS.enc.Utf8.parse(secretWithMd5),
        mode: CryptoJS.mode.CBC,
        adding: CryptoJS.pad.Pkcs7
      }).toString()
    },
    getDe(content, secret) {
      const secretWithMd5 = CryptoJS.MD5(secret).toString();

      return CryptoJS.AES.decrypt(
          CryptoJS.enc.Base64.stringify(content),
          CryptoJS.enc.Utf8.parse(secretWithMd5),
          {
        iv: CryptoJS.enc.Utf8.parse(secretWithMd5),
        mode: CryptoJS.mode.CBC,
        adding: CryptoJS.pad.Pkcs7
      }
      ).toString()
    },
    importCodeWithFile(e) {
      if (e.target.files.length == 0) {
        return;
      }

      const selectedFile = e.target.files[0];

      // 清空选择
      document.getElementById('fileSelector').value = '';

      // 限制文件大小
      if (selectedFile.size > 1 * 1024 * 1024) {
        this.$message.error('文件大小不能超过1M');
        return;
      }

      this.fileImporting = true;

      const self = this;
      const reader = new FileReader();
      reader.onload = function fileReadCompleted() {
        // 当读取完成时，内容只在`reader.result`中
        // console.log(reader.result);
        self.content = reader.result;
        self.fileImporting = false;
      };
      reader.readAsText(selectedFile);
    }
  },
  mounted() {

  }
}
</script>

<style>

.title {
  color: #333333;
  font-weight: 600;
  font-size: 18px;
}

.options-title {
  margin-top: 15px;
  border-bottom: 2px solid #dddddd;
  padding-bottom: 5px
}

#import {
  cursor: pointer;
}

.editor {
  width: 100%;
  resize: none;
  border: 1px solid #dddddd;
}

.option-item {
  margin-bottom: 10px;
}

.option-name {
  min-width: 100px;
  display: inline-block;
}

.option-value {
  width: calc(100% - 100px);
  display: inline-block;
}

</style>
