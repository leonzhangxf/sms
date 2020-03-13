<template>
    <Page show-sizer show-elevator show-total
          :current="page.currentPage"
          :total="page.totalCount"
          :page-size="page.pageSize"
          :page-size-opts="pageSizeOpts"
          @on-change="changePage"
          @on-page-size-change="changePageSize"/>
</template>

<script>
  export default {
    name: 'pagination',
    props: {
      page: {
        default: () => {
          return {
            currentPage: 1,
            pageSize: 10,
            totalCount: 100
          }
        }
      },
      pageSizeOpts: {
        default: () => {
          return [10, 20, 50, 100]
        }
      }
    },
    methods: {
      changePage(page) {
        this.$emit('changePage', page);
      },
      changePageSize(pageSize) {
        this.$emit('changePageSize', pageSize);
      }
    }
  }
</script>