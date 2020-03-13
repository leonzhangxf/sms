<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">签名: </span>
                <span class="expand-value">{{ row.signature }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">渠道配置: </span>
                <span class="expand-value">{{ row.channelConfig.name }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <span class="expand-key">描述: </span>
                <span class="expand-value">{{ row.desc }}</span>
            </Col>
        </Row>
    </div>
</template>

<script>
  export default {
    name: 'channel-signature-expand-row',
    props: {
      row: Object
    }
  }
</script>

<style scoped>
    .expand-row {
        margin-bottom: 16px;
    }
</style>