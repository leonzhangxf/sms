<template>
    <div>

        <div class="criteria-search">
            <span class="criteria-search-element">
                <span>签名：</span>
                <Input v-model="criteria.signature" placeholder="签名" clearable
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
                    @click="channelSignatures">搜索
            </Button>

            <Button class="criteria-search-element" @click="getSignatureCreateModal">新增渠道签名</Button>
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

            <div class="modal-signature-create">
                <Modal v-model="showSignatureCreateModal" title="新增渠道签名"
                       :mask-closable="false" :loading="true"
                       @on-ok="createSignature" @on-cancel="cleanModalData">

                    <Form ref="createSignatureForm" :model="channelSignature" :label-width="120" :rules="formRules">
                        <FormItem label="渠道配置类型" prop="channelConfig">
                            <Select v-model="channelSignature.channelConfig.id" style="width:200px;">
                                <Option v-for="item in channelConfigs" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="签名" prop="signature">
                            <Input v-model="channelSignature.signature" size="large" placeholder="签名"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="描述" prop="desc">
                            <Input v-model="channelSignature.desc" type="textarea" placeholder="描述"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="渠道签名状态" prop="status">
                            <i-switch v-model="channelSignature.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <span style="color: red;">启用渠道签名，需要其所属的渠道配置已经启用。</span>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-signature-update">
                <Modal v-model="showSignatureUpdateModal" title="修改渠道签名"
                       :mask-closable="false" :loading="true"
                       @on-ok="updateSignature" @on-cancel="cleanModalData">

                    <Form ref="updateSignatureForm" :model="channelSignature" :label-width="120" :rules="formRules">
                        <FormItem label="渠道配置类型" prop="channelConfig">
                            <Select v-model="channelSignature.channelConfig.id" style="width:200px;">
                                <Option v-for="item in channelConfigs" :value="item.id" :key="item.id">
                                    {{ item.name }}
                                </Option>
                            </Select>
                        </FormItem>

                        <FormItem label="签名" prop="signature">
                            <Input v-model="channelSignature.signature" size="large" placeholder="签名"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>


                        <FormItem label="描述" prop="content">
                            <Input v-model="channelSignature.desc" type="textarea" placeholder="描述"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                        </FormItem>


                        <FormItem label="渠道签名状态" prop="status">
                            <i-switch v-model="channelSignature.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <p style="color: red;">
                                启用渠道签名，需要其所属的渠道配置已经启用；<br/>
                                禁用渠道签名，会禁用和其关联的短信服务模板。
                            </p>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-signature-delete">
                <Modal v-model="showSignatureDeleteModal" title="删除渠道签名"
                       :mask-closable="false" :loading="true"
                       @on-ok="deleteSignature" @on-cancel="cleanModalData">
                    <p style="text-align: center;">
                        确定要删除签名为“{{channelSignature.signature}}”的渠道签名么？<br/>
                        <span style="color: red;">（删除渠道签名，将禁用和其关联的短信服务模板！）</span>
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
    name: 'channel-signature',
    components: {
      pagination,
      expandRow
    },
    data() {
      return {
        showSignatureCreateModal: false,
        showSignatureUpdateModal: false,
        showSignatureDeleteModal: false,
        pageLoading: false,
        page: {
          currentPage: 1,
          pageSize: 10,
          totalCount: 100,
          items: []
        },
        criteria: {
          configId: null,
          signature: '',
          status: null,
          currentPage: 1,
          pageSize: 10
        },
        channelConfigs: [],
        channelSignature: {
          channelConfig: {
            id: null
          },
          signature: '',
          desc: '',
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
          {title: '签名', key: 'signature'},
          {
            title: '渠道配置', key: 'channelConfig',
            render: (h, params) => {
              return h('span', params.row.channelConfig.name);
            }
          },
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
                      this.getSignatureUpdateModal(params.row);
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
                      this.getSignatureDeleteModal(params.row);
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
          signature: [
            {required: true, message: '渠道签名不能为空！', trigger: 'change'}
          ],
          status: [
            {type: 'number', required: true, message: '渠道配置状态不能为空！', trigger: 'change'}
          ]
        }
      }
    },
    mounted() {
      this.getChannelConfigs();
      this.channelSignatures();
    },
    methods: {
      createSignature() {
        this.$refs['createSignatureForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showSignatureCreateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.post('/api/channel/signatures', {
              channelConfig: {
                id: this.channelSignature.channelConfig.id
              },
              signature: this.channelSignature.signature,
              desc: this.channelSignature.desc,
              status: this.channelSignature.status
            }).then(() => {
              this.cleanModalData();
              this.showSignatureCreateModal = false;
              this.$Message.success('新增渠道签名成功！');
              this.channelSignatures();
            }).catch((err) => {
              this.cleanModalData();
              this.showSignatureCreateModal = false;
              this.disposeApiError(err);
              this.channelSignatures();
            });
          }
        });
      },
      getSignatureCreateModal() {
        this.showSignatureCreateModal = true;
      },
      deleteSignature() {
        api.delete('/api/channel/signatures/' + this.channelSignature.id).then(() => {
          this.$Message.success('删除渠道签名成功！');
          this.cleanModalData();
          this.showSignatureDeleteModal = false;
          this.channelSignatures();
        }).catch((err) => {
          this.disposeApiError(err);
          this.cleanModalData();
          this.showSignatureDeleteModal = false;
          this.channelSignatures();
        });
      },
      getSignatureDeleteModal(row) {
        Object.assign(this.channelSignature, row);
        this.showSignatureDeleteModal = true;
      },
      updateSignature() {
        this.$refs['updateSignatureForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showSignatureUpdateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.put('/api/channel/signatures/' + this.channelSignature.id, {
              channelConfig: {
                id: this.channelSignature.channelConfig.id
              },
              signature: this.channelSignature.signature,
              desc: this.channelSignature.desc,
              status: this.channelSignature.status
            }).then(() => {
              this.cleanModalData();
              this.showSignatureUpdateModal = false;
              this.$Message.success('修改渠道签名成功！');
              this.channelSignatures();
            }).catch((err) => {
              this.cleanModalData();
              this.showSignatureUpdateModal = false;
              this.disposeApiError(err);
              this.channelSignatures();
            });
          }
        });
      },
      getSignatureUpdateModal(row) {
        Object.assign(this.channelSignature, row);
        this.showSignatureUpdateModal = true;
      },
      getChannelConfigs() {
        api.get('/api/channel/signatures/configs').then((data) => {
          this.channelConfigs = data;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      channelSignatures() {
        this.pageLoading = true;
        api.get('/api/channel/signatures/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
          params: {
            configId: this.criteria.configId,
            signature: this.criteria.signature,
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
        this.$refs['createSignatureForm'].resetFields();
        this.$refs['updateSignatureForm'].resetFields();
        this.channelSignature = {
          channelConfig: {
            id: null
          },
          signature: '',
          desc: '',
          status: 0
        };
      },
      changePage(currentPage) {
        this.criteria.currentPage = currentPage;
        this.channelSignatures();
      },
      changePageSize(pageSize) {
        this.criteria.pageSize = pageSize;
        this.criteria.currentPage = 1;
        this.channelSignatures();
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