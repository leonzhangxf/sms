<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">模板Code: </span>
                <span class="expand-value">{{ row.code }}</span>
            </Col>
            <Col span="16">
                <span class="expand-key">模板参数: </span>
                <span class="expand-value">{{ row.params }}</span>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <span class="expand-key">模板内容: </span>
                <span class="expand-value">{{ row.content }}</span>
            </Col>
        </Row>
    </div>
</template>

<script>
  export default {
    name: 'channel-template-expand-row',
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