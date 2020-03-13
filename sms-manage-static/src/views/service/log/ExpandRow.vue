<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">短信服务模板: </span>
                <span class="expand-value">{{ row.template.name }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">发送手机号: </span>
                <span class="expand-value">{{ row.mobile }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">发送状态: </span>
                <span class="expand-value">{{ getStatus }}</span>
            </Col>
        </Row>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">参数列表: </span>
                <span class="expand-value">{{ row.params }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">发送时间: </span>
                <span class="expand-value">{{ row.sendTime }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">响应消息: </span>
                <span class="expand-value">{{ row.message }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="6">
                <span class="expand-key">接入方: </span>
                <span class="expand-value">{{ row.template.client.name }}</span>
            </Col>
            <Col span="6">
                <span class="expand-key">渠道签名: </span>
                <span class="expand-value">{{ row.template.channelSignature.signature }}</span>
            </Col>
            <Col span="6">
                <span class="expand-key">渠道模板: </span>
                <span class="expand-value">{{ row.template.channelTemplate.name }}</span>
            </Col>
            <Col span="6">
                <span class="expand-key">用途: </span>
                <span class="expand-value">{{ getUsage }}</span>
            </Col>
        </Row>
    </div>
</template>

<script>
  export default {
    name: 'service-logs-expand-row',
    props: {
      row: Object
    },
    computed: {
      getStatus() {
        switch (this.row.status) {
          case 'OK':
            return '正常';
          case 'BAD_REQUEST':
            return '请求异常';
          case 'FORBIDDEN':
            return '短信平台限流';
          case 'INTERNAL_SERVER_ERROR':
            return '响应异常';
          default:
            return '';
        }
      },
      getUsage() {
        let value = '';
        switch (this.row.template.usage) {
          case 'VERIFICATION':
            value = '验证';
            break;
          case 'NOTIFICATION':
            value = '通知';
            break;
          case 'PROMOTION':
            value = '推广';
            break;
        }
        return value;
      }
    }
  }
</script>

<style scoped>
    .expand-row {
        margin-bottom: 16px;
    }
</style>