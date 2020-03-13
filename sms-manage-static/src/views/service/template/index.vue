<template>

    <div>

        <div class="criteria-search">
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
                <span>名称：</span>
                <Input v-model="criteria.name" placeholder="名称" clearable
                       style="width: 150px"/>
            </span>

            <span class="criteria-search-element">
                <span>模板ID：</span>
                <Input v-model="criteria.templateId" placeholder="模板ID" clearable
                       style="width: 150px"/>
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
                <span>状态：</span>
                <Select v-model="criteria.status" clearable style="width:100px;">
                    <Option :value="1" :key="1">启用</Option>
                    <Option :value="0" :key="0">禁用</Option>
                </Select>
            </span>

            <Button class="criteria-search-element" type="primary" shape="circle" icon="ios-search"
                    @click="templates">搜索
            </Button>

            <Button class="criteria-search-element" @click="getTemplateCreateModal">新增模板</Button>
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


        <div class="modal">

            <div class="modal-template-create">
                <Modal v-model="showTemplateCreateModal" title="新增模板"
                       :mask-closable="false" :loading="true"
                       @on-ok="createTemplate" @on-cancel="cleanModalData">

                    <Form ref="createTemplateForm" :model="template" :label-width="120" :rules="formRules">
                        <FormItem label="关联接入方" prop="client">
                            <Select v-model="template.client.id" style="width:200px;">
                                <Option v-for="item in clientList" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="关联渠道签名" prop="channelSignature">
                            <Select v-model="template.channelSignature.id" style="width:200px;">
                                <Option v-for="item in channelSignatureList" :value="item.id" :key="item.id">
                                    {{ item.signature }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="关联渠道模板" prop="channelTemplate">
                            <Select v-model="template.channelTemplate.id" style="width:200px;">
                                <Option v-for="item in channelTemplateList" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="名称" prop="name">
                            <Input v-model="template.name" size="large" placeholder="模板名称"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板ID" prop="templateId">
                            <Input v-model="template.templateId" size="large" placeholder="模板ID"
                                   :maxlength="60" style="width: 250px;" :readonly="true" />
                            <Button type="primary" v-on:click="generateTemplateId" style="margin-left: 5px;">生成模板ID</Button>
                        </FormItem>

                        <FormItem label="描述" prop="desc">
                            <Input v-model="template.desc" type="textarea" placeholder="描述"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板用途" prop="usage">
                            <Select v-model="template.usage" style="width:200px;">
                                <Option v-for="item in templateUsageList" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="模板状态" prop="status">
                            <i-switch v-model="template.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <span style="color: red;">启用模板，需要其配置的接入方、渠道签名、渠道模板都已经启用。</span>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-template-update">
                <Modal v-model="showTemplateUpdateModal" title="修改模板"
                       :mask-closable="false" :loading="true"
                       @on-ok="updateTemplate" @on-cancel="cleanModalData">

                    <Form ref="updateTemplateForm" :model="template" :label-width="120" :rules="formRules">

                        <FormItem label="关联接入方" prop="client">
                            <Select v-model="template.client.id" style="width:200px;">
                                <Option v-for="item in clientList" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="关联渠道签名" prop="channelSignature">
                            <Select v-model="template.channelSignature.id" style="width:200px;">
                                <Option v-for="item in channelSignatureList" :value="item.id" :key="item.id">
                                    {{ item.signature }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="关联渠道模板" prop="channelTemplate">
                            <Select v-model="template.channelTemplate.id" style="width:200px;">
                                <Option v-for="item in channelTemplateList" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="名称" prop="name">
                            <Input v-model="template.name" size="large" placeholder="模板名称"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板ID" prop="templateId">
                            <Input v-model="template.templateId" size="large" placeholder="模板ID"
                                   :maxlength="60" style="width: 250px;" :readonly="true" />
                            <Button type="primary" v-on:click="generateTemplateId" style="margin-left: 5px;">生成模板ID</Button>
                        </FormItem>

                        <FormItem label="描述" prop="desc">
                            <Input v-model="template.desc" type="textarea" placeholder="描述"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板用途" prop="usage">
                            <Select v-model="template.usage" style="width:200px;">
                                <Option v-for="item in templateUsageList" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="模板状态" prop="status">
                            <i-switch v-model="template.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <span style="color: red;">启用模板，需要其配置的接入方、渠道签名、渠道模板都已经启用。</span>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-template-delete">
                <Modal v-model="showTemplateDeleteModal" title="删除模板"
                       :mask-closable="false" :loading="true"
                       @on-ok="deleteTemplate" @on-cancel="cleanModalData">
                    <p style="text-align: center;">
                        确定要删除名称为“{{template.name}}”的短信服务模板么？<br/>
                        <span style="color: red;">（删除短信服务模板，将不能使用其作为筛选条件关联查询调用记录！）</span>
                    </p>
                </Modal>
            </div>
        </div>

    </div>

</template>

<script>
  import pagination from '@/components/page'
  import api from '@/components/axios'
  import expandRow from './ExpandRow'

  export default {
    name: 'service-template',
    components: {
      pagination,
      expandRow
    },
    data() {
      return {
        showTemplateCreateModal: false,
        showTemplateUpdateModal: false,
        showTemplateDeleteModal: false,
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
        templateUsageList: [],
        criteria: {
          clientId: null,
          channelSignatureId: null,
          channelTemplateId: null,
          name: '',
          templateId: '',
          usage: null,
          status: null,
          currentPage: 1,
          pageSize: 10
        },
        template: {
          id: null,
          client: {
            id: null,
            name: ''
          },
          channelSignature: {
            id: null,
            signature: ''
          },
          channelTemplate: {
            id: null,
            name: ''
          },
          name: '',
          templateId: '',
          desc: '',
          usage: '',
          status: 0
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
            title: '接入方', key: 'client',
            render: (h, params) => {
              return h('span', params.row.client.name);
            }
          },
          {
            title: '渠道签名', key: 'channelSignature',
            render: (h, params) => {
              return h('span', params.row.channelSignature.signature);
            }
          },
          {
            title: '渠道模板', key: 'channelTemplate',
            render: (h, params) => {
              return h('span', params.row.channelTemplate.name);
            }
          },
          {title: '名称', key: 'name'},
          {title: '模板ID', key: 'templateId', width: 200},
          {title: '描述', key: 'desc', tooltip: true},
          {title: '用途', key: 'usage',
            render: (h, params) => {
              let value = '';
              switch (params.row.usage) {
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
            }},
          {
            title: '状态', key: 'status',
            render: (h, params) => {
              if (params.row.status === 0) {
                return h('span', '禁用');
              } else if (params.row.status === 1) {
                return h('span', '启用');
              }
            }
          },
          {
            title: '操作', key: 'action', align: 'center',
            render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  style: {
                    marginRight: '5px'
                  },
                  on: {
                    click: () => {
                      this.getTemplateUpdateModal(params.row);
                    }
                  }
                }, '修改'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.getTemplateDeleteModal(params.row);
                    }
                  }
                }, '删除')
              ]);
            }
          }
        ],
        formRules: {
          client: [
            {type: 'object', required: true, trigger: 'change'},
            {
              validator(rule, value, callback, source, options) {
                let errors = [];
                if (!value || !value.id || null == value.id) {
                  errors.push(new Error('必须关联一个接入方！'));
                }
                callback(errors);
              }
            }
          ],
          channelSignature: [
            {type: 'object', required: true, trigger: 'change'},
            {
              validator(rule, value, callback, source, options) {
                let errors = [];
                if (!value || !value.id || null == value.id) {
                  errors.push(new Error('必须关联一个渠道签名！'));
                }
                callback(errors);
              }
            }
          ],
          channelTemplate: [
            {type: 'object', required: true, trigger: 'change'},
            {
              validator(rule, value, callback, source, options) {
                let errors = [];
                if (!value || !value.id || null == value.id) {
                  errors.push(new Error('必须关联一个渠道模板！'));
                }
                callback(errors);
              }
            }
          ],
          name: [
            {required: true, message: '模板名称不能为空！', trigger: 'change'}
          ],
          templateId: [
            {required: true, message: '模板ID不能为空！', trigger: 'change'}
          ],
          usage: [
            {required: true, message: '模板用途不能为空！', trigger: 'change'}
          ],
          status: [
            {type: 'number', required: true, message: '模板状态不能为空！', trigger: 'change'}
          ]
        }
      }
    },
    mounted() {
      this.getClientList();
      this.getChannelTemplateList();
      this.getChannelSignatureList();
      this.getTemplateUsageList();
      this.templates();
    },
    methods: {
      generateTemplateId() {
        api.get('/api/service/templates/template-id').then((res) => {
          this.template.templateId = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getClientList() {
        api.get('/api/service/templates/service/clients').then((res) => {
          this.clientList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getChannelTemplateList() {
        api.get('/api/service/templates/channel/templates').then((res) => {
          this.channelTemplateList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getChannelSignatureList() {
        api.get('/api/service/templates/channel/signatures').then((res) => {
          this.channelSignatureList = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      getTemplateUsageList() {
        api.get('/api/service/templates/template-usages').then((res) => {
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
      createTemplate() {
        this.$refs['createTemplateForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showTemplateCreateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.post('/api/service/templates', {
              client: {
                id: this.template.client.id
              },
              channelSignature: {
                id: this.template.channelSignature.id
              },
              channelTemplate: {
                id: this.template.channelTemplate.id
              },
              name: this.template.name,
              templateId: this.template.templateId,
              desc: this.template.desc,
              usage: this.template.usage,
              status: this.template.status
            }).then(() => {
              this.cleanModalData();
              this.showTemplateCreateModal = false;
              this.$Message.success('新增模板成功！');
              this.templates();
            }).catch((err) => {
              this.cleanModalData();
              this.showTemplateCreateModal = false;
              this.disposeApiError(err);
              this.templates();
            });
          }
        });
      },
      getTemplateCreateModal() {
        this.showTemplateCreateModal = true;
      },
      deleteTemplate() {
        api.delete('/api/service/templates/' + this.template.id).then(() => {
          this.$Message.success('删除模板成功！');
          this.cleanModalData();
          this.showTemplateDeleteModal = false;
          this.templates();
        }).catch((err) => {
          this.disposeApiError(err);
          this.cleanModalData();
          this.showTemplateDeleteModal = false;
          this.templates();
        });
      },
      getTemplateDeleteModal(row) {
        Object.assign(this.template, row);
        this.showTemplateDeleteModal = true;
      },
      updateTemplate() {
        this.$refs['updateTemplateForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showTemplateUpdateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.put('/api/service/templates/' + this.template.id, {
              client: {
                id: this.template.client.id
              },
              channelSignature: {
                id: this.template.channelSignature.id
              },
              channelTemplate: {
                id: this.template.channelTemplate.id
              },
              name: this.template.name,
              templateId: this.template.templateId,
              desc: this.template.desc,
              usage: this.template.usage,
              status: this.template.status
            }).then(() => {
              this.cleanModalData();
              this.showTemplateUpdateModal = false;
              this.$Message.success('修改模板成功！');
              this.templates();
            }).catch((err) => {
              this.cleanModalData();
              this.showTemplateUpdateModal = false;
              this.disposeApiError(err);
              this.templates();
            });
          }
        });
      },
      getTemplateUpdateModal(row) {
        Object.assign(this.template, row);
        this.showTemplateUpdateModal = true;
      },
      templates() {
        this.pageLoading = true;
        api.get('/api/service/templates/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
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
      cleanModalData() {
        this.$refs['createTemplateForm'].resetFields();
        this.$refs['updateTemplateForm'].resetFields();
        this.template = {
          id: null,
          client: {
            id: null,
            name: ''
          },
          channelSignature: {
            id: null,
            signature: ''
          },
          channelTemplate: {
            id: null,
            name: ''
          },
          name: '',
          templateId: '',
          desc: '',
          usage: '',
          status: 0
        };
      },
      changePage(currentPage) {
        this.criteria.currentPage = currentPage;
        this.templates();
      },
      changePageSize(pageSize) {
        this.criteria.pageSize = pageSize;
        this.criteria.currentPage = 1;
        this.templates();
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