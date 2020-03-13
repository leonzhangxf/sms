<template>
    <div>
        <Row class="expand-row">
            <Col span="8">
                <span class="expand-key">标识编号: </span>
                <span class="expand-value">{{ row.code }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">名称: </span>
                <span class="expand-value">{{ row.name }}</span>
            </Col>
            <Col span="8">
                <span class="expand-key">密钥: </span>
                <span class="expand-value">{{ row.key }}</span>
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
    name: 'service-client-expand-row',
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