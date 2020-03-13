<template>

    <div>

        <div class="criteria-search">
            <span class="criteria-search-element">
                <span>短信服务模板：</span>
                <Select v-model="criteria.templateId" clearable style="width:150px;">
                    <template v-for="item in templateList">
                        <Option :value="item.id" :key="item.id">{{item.name}}</Option>
                    </template>
                </Select>
            </span>

            <span class="criteria-search-element">
                <span>接入方：</span>
                <Select v-model="criteria.clientId" clearable style="width:150px;">
                    <template v-for="item in clientList">
                        <Option :value="item.id" :key="item.id">{{item.name}}</Option>
                    </template>
                </Select>
            </span>

            <span class="criteria-search-element">
                <span>渠道签名：</span>
                <Select v-model="criteria.channelSignatureId" clearable style="width:150px;">
                    <template v-for="item in channelSignatureList">
                        <Option :value="item.id" :key="item.id">{{item.signature}}</Option>
                    </template>
                </Select>
            </span>

            <span class="criteria-search-element">
                <span>渠道模板：</span>
                <Select v-model="criteria.channelTemplateId" clearable style="width:150px;">
                    <template v-for="item in channelTemplateList">
                        <Option :value="item.id" :key="item.id">{{item.name}}</Option>
                    </template>
                </Select>
            </span>

            <span class="criteria-search-element">
                <span>模板用途：</span>
                <Select v-model="criteria.usage" clearable style="width:80px;">
                    <template v-for="item in templateUsageList">
                        <Option :value="item.id" :key="item.id">{{item.name}}</Option>
                    </template>
                </Select>
            </span>

            <span class="criteria-search-element">
                <span>发送响应状态：</span>
                <Select v-model="criteria.status" clearable style="width:120px;">
                    <template v-for="item in sendResponseStatusList">
                        <Option :value="item.id" :key="item.id">{{item.name}}</Option>
                    </template>
                </Select>
            </span>

            <Button class="criteria-search-element" type="primary" shape="circle" icon="ios-search"
                    @click="sendLogs">搜索
            </Button>

            <br/>

            <span style="margin-left: 10px;">
                <template v-if="statisticsSummery.successRateFor1Hour > 90">
                    <span style="margin-right: 20px; font-size: 20px; color: forestgreen;">
                        1小时发送成功率：{{ statisticsSummery.successRateFor1Hour }}%
                    </span>
                </template>
                <template v-else-if="statisticsSummery.successRateFor1Hour > 80 && statisticsSummery.successRateFor1Hour < 89.99">
                    <span style="margin-right: 20px; font-size: 20px; color: dodgerblue;">
                        1小时发送成功率：{{ statisticsSummery.successRateFor1Hour }}%
                    </span>
                </template>
                <template v-else>
                    <span style="margin-right: 20px; font-size: 20px; color: red;">
                        1小时发送成功率：{{ statisticsSummery.successRateFor1Hour }}%
                    </span>
                </template>

                <template v-if="statisticsSummery.successRateFor3Hour > 90">
                    <span style="margin-right: 20px; font-size: 20px; color: forestgreen;">
                        3小时发送成功率：{{ statisticsSummery.successRateFor3Hour }}%
                    </span>
                </template>
                <template v-else-if="statisticsSummery.successRateFor3Hour > 80 && statisticsSummery.successRateFor3Hour < 89.99">
                    <span style="margin-right: 20px; font-size: 20px; color: dodgerblue;">
                        3小时发送成功率：{{ statisticsSummery.successRateFor3Hour }}%
                    </span>
                </template>
                <template v-else>
                    <span style="margin-right: 20px; font-size: 20px; color: red;">
                        3小时发送成功率：{{ statisticsSummery.successRateFor3Hour }}%
                    </span>
                </template>

                <template v-if="statisticsSummery.successRateFor24Hour > 90">
                    <span style="margin-right: 20px; font-size: 20px; color: forestgreen;">
                        24小时发送成功率：{{ statisticsSummery.successRateFor24Hour }}%
                    </span>
                </template>
                <template v-else-if="statisticsSummery.successRateFor24Hour > 80 && statisticsSummery.successRateFor24Hour < 89.99">
                    <span style="margin-right: 20px; font-size: 20px; color: dodgerblue;">
                        24小时发送成功率：{{ statisticsSummery.successRateFor24Hour }}%
                    </span>
                </template>
                <template v-else>
                    <span style="margin-right: 20px; font-size: 20px; color: red;">
                        24小时发送成功率：{{ statisticsSummery.successRateFor24Hour }}%
                    </span>
                </template>
            </span>
        </div>
        <Table stripe
               :columns="column"
               :data="page.items"
               :loading="pageLoading">
        </Table>
        <pagination :page="page"
                    @changePage="changePage" @changePageSize="changePageSize"
                    style="margin-top: 10px;">
        </pagination>

    </div>

</template>

<script>
  import pagination from '@/components/page'
  import api from '@/components/axios'
  import expandRow from './ExpandRow'

  export default {
    name: 'service-log',
    components: {
      pagination,
      expandRow
    },
    data() {
      return {
        pageLoading: false,
        page: {
          currentPage: 1,
          pageSize: 10,
          totalCount: 100,
          items: []
        },
        clientList: [],
        channelTemplateList: [],
        channelSignatureList: [],
        templateList: [],
        templateUsageList: [],
        sendResponseStatusList: [],
        statisticsSummery: {
          successRateFor1Hour: 0.00,
          successRateFor3Hour: 0.00,
          successRateFor24Hour: 0.00
        },
        criteria: {
          clientId: null,
          channelSignatureId: null,
          channelTemplateId: null,
          templateId: null,
          usage: null,
          status: null,
          currentPage: 1,
          pageSize: 10
        },
        column: [
          {
            type: 'expand', width: 50,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  row: params.row
                }
              });
            }
          },
          {title: '序号', type: 'index', width: 70},
          {
            title: '短信服务模板', key: 'template',
            render: (h, params) => {
              return h('span', params.row.template.name);
            }
          },
          {title: '发送手机号', key: 'mobile'},
          {
            title: '发送状态', key: 'status',
            render: (h, params) => {
              switch (params.row.status) {
                case 'OK':
                  return h('span', '正常');
                case 'BAD_REQUEST':
                  return h('span', '请求异常');
                case 'FORBIDDEN':
                  return h('span', '短信平台限流');
                case 'INTERNAL_SERVER_ERROR':
                  return h('span', '响应异常');
                default:
                  return h('span', '');
              }
            }
          },
          {title: '参数列表', key: 'params', tooltip: true},
          {title: '发送时间', key: 'sendTime'},
          {title: '响应消息', key: 'message', tooltip: true},
          {
            title: '接入方', key: 'template',
            render: (h, params) => {
              return h('span', params.row.template.client.name);
            }
          },
          {
            title: '渠道签名', key: 'template',
            render: (h, params) => {
              return h('span', params.row.template.channelSignature.signature);
            }
          },
          {
            title: '渠道模板', key: 'template',
            render: (h, params) => {
              return h('span', params.row.template.channelTemplate.name);
            }
          },
          {title: '用途', key: 'template',
            render: (h, params) => {
              let value = '';
              switch (params.row.template.usage) {
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
              return h('span', value);
            }
          }
        ]
      }
    },
    mounted() {
      this.getClientList();
      this.getChannelTemplateList();
      this.getChannelSignatureList();
      this.getTemplateList();
      this.getTemplateUsageList();
      this.getSendResponseStatusList();

      this.sendLogs();
    },
    methods: {
      getStatisticSummery() {
        api.get('/api/service/logs/statistics/summery').then((res) => {
          this.statisticsSummery = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getClientList() {
        api.get('/api/service/logs/service/clients').then((res) => {
          this.clientList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getChannelTemplateList() {
        api.get('/api/service/logs/channel/templates').then((res) => {
          this.channelTemplateList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getChannelSignatureList() {
        api.get('/api/service/logs/channel/signatures').then((res) => {
          this.channelSignatureList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getTemplateList() {
        api.get('/api/service/logs/service/templates').then((res) => {
          this.templateList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getTemplateUsageList() {
        api.get('/api/service/logs/templates/template-usages').then((res) => {
          for (let i = 0; i < res.length; i++) {
            let obj = {id: null, name: null};
            obj.id = res[i];
            switch (obj.id) {
              case 'VERIFICATION':
                obj.name = '验证';
                break;
              case 'NOTIFICATION':
                obj.name = '通知';
                break;
              case 'PROMOTION':
                obj.name = '推广';
                break;
            }
            this.templateUsageList.push(obj);
          }
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getSendResponseStatusList() {
        api.get('/api/service/logs/send-response-status').then((res) => {
          for (let i = 0; i < res.length; i++) {
            let obj = {id: null, name: null};
            obj.id = res[i];
            switch (obj.id) {
              case 'OK':
                obj.name = '正常';
                break;
              case 'BAD_REQUEST':
                obj.name = '请求异常';
                break;
              case 'FORBIDDEN':
                obj.name = '短信平台限流';
                break;
              case 'INTERNAL_SERVER_ERROR':
                obj.name = '响应异常';
                break;
            }
            this.sendResponseStatusList.push(obj);
          }
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      sendLogs() {
        this.pageLoading = true;
        //查询统计信息
        this.getStatisticSummery();

        api.get('/api/service/logs/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
          params: {
            clientId: this.criteria.clientId,
            channelSignatureId: this.criteria.channelSignatureId,
            channelTemplateId: this.criteria.channelTemplateId,
            name: this.criteria.name,
            templateId: this.criteria.templateId,
            usage: this.criteria.usage,
            status: this.criteria.status
          }
        }).then((data) => {
          this.page.currentPage = data.currentPage;
          this.page.pageSize = data.pageSize;
          this.page.totalCount = data.totalCount;
          this.page.items = data.items;

          this.pageLoading = false;
        }).catch((err) => {
          this.pageLoading = false;
          this.disposeApiError(err);
        });
      },
      changePage(currentPage) {
        this.criteria.currentPage = currentPage;
        this.sendLogs();
      },
      changePageSize(pageSize) {
        this.criteria.pageSize = pageSize;
        this.criteria.currentPage = 1;
        this.sendLogs();
      },
      disposeApiError(err) {
        switch (err.status) {
          case 400:
            this.$Message.error(err.data);
            break;
          case 401:
            this.$Message.error('未认证！');
            break;
          case 403:
            this.$Message.error('您没有权限访问该资源！');
            break;
          case 500:
            this.$Message.error('系统异常！');
            break;
          default:
            console.log(err);
        }
      }
    }
  }
</script>