<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">名称: </span>
                <span class="expand-value">{{ row.name }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">模板ID: </span>
                <span class="expand-value">{{ row.templateId }}</span>
            </Col>
            <Col span="4">
                <span class="expand-key">用途: </span>
                <span class="expand-value">{{ getUsage }}</span>
            </Col>
            <Col span="4">
                <span class="expand-key">状态: </span>
                <span class="expand-value">{{ getStatus }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="20">
                <span class="expand-key">描述: </span>
                <span class="expand-value">{{ row.desc }}</span>
            </Col>
        </Row>
    </div>
</template>

<script>
  export default {
    name: 'service-template-expand-row',
    props: {
      row: Object
    },
    computed: {
      getStatus() {
        console.log(this);
        if (this.row.status === 0) {
          return '禁用';
        } else if (this.row.status === 1) {
          return '启用';
        }
      },
      getUsage() {
        let value = '';
        switch (this.row.usage) {
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