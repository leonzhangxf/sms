<template>

    <div>

        <div class="criteria-search">
            <span class="criteria-search-element">
                <span>名称：</span>
                <Input v-model="criteria.name" placeholder="名称" clearable
                       style="width: 200px"/>
            </span>

            <span class="criteria-search-element">
                <span>渠道模板CODE：</span>
                <Input v-model="criteria.code" placeholder="渠道模板CODE" clearable
                       style="width: 200px"/>
            </span>

            <span class="criteria-search-element">
                <span>渠道配置：</span>
                <Select v-model="criteria.configId" clearable style="width:180px;">
                    <template v-for="item in channelConfigs">
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
                    @click="channelTemplates">搜索
            </Button>

            <Button class="criteria-search-element" @click="getTemplateCreateModal">新增渠道模板</Button>
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
                <Modal v-model="showTemplateCreateModal" title="新增渠道模板"
                       :mask-closable="false" :loading="true"
                       @on-ok="createTemplate" @on-cancel="cleanModalData">

                    <Form ref="createTemplateForm" :model="channelTemplate" :label-width="120" :rules="formRules">
                        <FormItem label="渠道配置类型" prop="channelConfig">
                            <Select v-model="channelTemplate.channelConfig.id" style="width:200px;">
                                <Option v-for="item in channelConfigs" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="名称" prop="name">
                            <Input v-model="channelTemplate.name" size="large" placeholder="渠道模板名称"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板Code" prop="code">
                            <Input v-model="channelTemplate.code" size="large" placeholder="渠道模板Code"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板内容" prop="content">
                            <Input v-model="channelTemplate.content" type="textarea" placeholder="渠道模板内容"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                            <br/>
                            <span>携带变量使用“${code}”的格式。</span>
                        </FormItem>

                        <FormItem label="模板参数列表" prop="params">
                            <Input v-model="channelTemplate.params" type="textarea" placeholder="渠道模板参数列表"
                                   :maxlength="250" :rows="2" style="width: 250px;"/>
                            <br/>
                            <span>多变量按顺序用英文逗号分隔,如"name,vcode"。无变量不用填写。</span>
                        </FormItem>

                        <FormItem label="渠道模板状态" prop="status">
                            <i-switch v-model="channelTemplate.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <span style="color: red;">启用渠道模板，需要其所属的渠道配置已经启用。</span>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-template-update">
                <Modal v-model="showTemplateUpdateModal" title="修改渠道模板"
                       :mask-closable="false" :loading="true"
                       @on-ok="updateTemplate" @on-cancel="cleanModalData">

                    <Form ref="updateTemplateForm" :model="channelTemplate" :label-width="120" :rules="formRules">
                        <FormItem label="渠道配置类型" prop="channelConfig">
                            <Select v-model="channelTemplate.channelConfig.id" style="width:200px;">
                                <Option v-for="item in channelConfigs" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="名称" prop="name">
                            <Input v-model="channelTemplate.name" size="large" placeholder="渠道模板名称"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板Code" prop="code">
                            <Input v-model="channelTemplate.code" size="large" placeholder="渠道模板Code"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="模板内容" prop="content">
                            <Input v-model="channelTemplate.content" type="textarea" placeholder="渠道模板内容"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                            <br/>
                            <span>携带变量使用“${code}”的格式。</span>
                        </FormItem>

                        <FormItem label="模板参数列表" prop="params">
                            <Input v-model="channelTemplate.params" type="textarea" placeholder="渠道模板参数列表"
                                   :maxlength="250" :rows="2" style="width: 250px;"/>
                            <br/>
                            <span>多变量按顺序用英文逗号分隔,如"name,vcode"。无变量不用填写。</span>
                        </FormItem>

                        <FormItem label="渠道模板状态" prop="status">
                            <i-switch v-model="channelTemplate.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <p style="color: red;">
                                启用渠道模板，需要其所属的渠道配置已经启用；<br/>
                                禁用渠道模板，会禁用和其关联的短信服务模板。
                            </p>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-template-delete">
                <Modal v-model="showTemplateDeleteModal" title="删除渠道模板"
                       :mask-closable="false" :loading="true"
                       @on-ok="deleteTemplate" @on-cancel="cleanModalData">
                    <p style="text-align: center;">
                        确定要删除名称为“{{channelTemplate.name}}”的渠道模板么？<br/>
                        <span style="color: red;">（删除渠道模板，将禁用和其关联的短信服务模板！）</span>
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
    name: 'channel-template',
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
        criteria: {
          configId: null,
          name: '',
          code: '',
          status: null,
          currentPage: 1,
          pageSize: 10
        },
        channelConfigs: [],
        channelTemplate: {
          channelConfig: {
            id: null
          },
          name: '',
          code: '',
          content: '',
          params: '',
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
          {title: '名称', key: 'name'},
          {
            title: '渠道配置', key: 'channelConfig',
            render: (h, params) => {
              return h('span', params.row.channelConfig.name);
            }
          },
          {title: '模板Code', key: 'code'},
          {title: '模板内容', key: 'content', tooltip: true},
          {title: '模板参数', key: 'params'},
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
          channelConfig: [
            {type: 'object', required: true, trigger: 'change'},
            {
              validator(rule, value, callback, source, options) {
                let errors = [];
                if (!value || !value.id || null == value.id) {
                  errors.push(new Error('必须关联一个渠道配置！'));
                }
                callback(errors);
              }
            }
          ],
          name: [
            {required: true, message: '渠道模板名称不能为空！', trigger: 'change'}
          ],
          code: [
            {required: true, message: '渠道模板Code不能为空！', trigger: 'change'}
          ],
          content: [
            {required: true, message: '渠道模板内容不能为空！', trigger: 'change'}
          ],
          status: [
            {type: 'number', required: true, message: '渠道配置状态不能为空！', trigger: 'change'}
          ]
        }
      }
    },
    mounted() {
      this.getChannelConfigs();
      this.channelTemplates();
    },
    methods: {
      createTemplate() {
        this.$refs['createTemplateForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showTemplateCreateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.post('/api/channel/templates', {
              channelConfig: {
                id: this.channelTemplate.channelConfig.id
              },
              name: this.channelTemplate.name,
              code: this.channelTemplate.code,
              content: this.channelTemplate.content,
              params: this.channelTemplate.params,
              status: this.channelTemplate.status
            }).then(() => {
              this.cleanModalData();
              this.showTemplateCreateModal = false;
              this.$Message.success('新增渠道模板成功！');
              this.channelTemplates();
            }).catch((err) => {
              this.cleanModalData();
              this.showTemplateCreateModal = false;
              this.disposeApiError(err);
              this.channelTemplates();
            });
          }
        });
      },
      getTemplateCreateModal() {
        this.showTemplateCreateModal = true;
      },
      deleteTemplate() {
        api.delete('/api/channel/templates/' + this.channelTemplate.id).then(() => {
          this.$Message.success('删除渠道模板成功！');
          this.cleanModalData();
          this.showTemplateDeleteModal = false;
          this.channelTemplates();
        }).catch((err) => {
          this.disposeApiError(err);
          this.cleanModalData();
          this.showTemplateDeleteModal = false;
          this.channelTemplates();
        });
      },
      getTemplateDeleteModal(row) {
        Object.assign(this.channelTemplate, row);
        this.showTemplateDeleteModal = true;
      },
      updateTemplate() {
        this.$refs['updateTemplateForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showTemplateUpdateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.put('/api/channel/templates/' + this.channelTemplate.id, {
              channelConfig: {
                id: this.channelTemplate.channelConfig.id
              },
              name: this.channelTemplate.name,
              code: this.channelTemplate.code,
              content: this.channelTemplate.content,
              params: this.channelTemplate.params,
              status: this.channelTemplate.status
            }).then(() => {
              this.cleanModalData();
              this.showTemplateUpdateModal = false;
              this.$Message.success('修改渠道模板成功！');
              this.channelTemplates();
            }).catch((err) => {
              this.cleanModalData();
              this.showTemplateUpdateModal = false;
              this.disposeApiError(err);
              this.channelTemplates();
            });
          }
        });
      },
      getTemplateUpdateModal(row) {
        Object.assign(this.channelTemplate, row);
        this.showTemplateUpdateModal = true;
      },
      getChannelConfigs() {
        api.get('/api/channel/templates/configs').then((data) => {
          this.channelConfigs = data;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      channelTemplates() {
        this.pageLoading = true;
        api.get('/api/channel/templates/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
          params: {
            configId: this.criteria.configId,
            name: this.criteria.name,
            code: this.criteria.code,
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
        this.channelTemplate = {
          channelConfig: {
            id: null
          },
          name: '',
          code: '',
          content: '',
          params: '',
          status: 0
        };
      },
      changePage(currentPage) {
        this.criteria.currentPage = currentPage;
        this.channelTemplates();
      },
      changePageSize(pageSize) {
        this.criteria.pageSize = pageSize;
        this.criteria.currentPage = 1;
        this.channelTemplates();
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