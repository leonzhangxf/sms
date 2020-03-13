<template>
    <div>

        <div class="criteria-search">
            <span class="criteria-search-element">
                <span>标识Code：</span>
                <Input v-model="criteria.code" placeholder="标识编号" clearable
                       style="width: 200px"/>
            </span>

            <span class="criteria-search-element">
                <span>名称：</span>
                <Input v-model="criteria.name" placeholder="名称" clearable
                       style="width: 200px"/>
            </span>

            <span class="criteria-search-element">
                <span>状态：</span>
                <Select v-model="criteria.status" clearable style="width:100px;">
                    <Option :value="1" :key="1">启用</Option>
                    <Option :value="0" :key="0">禁用</Option>
                </Select>
            </span>

            <Button class="criteria-search-element" type="primary" shape="circle" icon="ios-search"
                    @click="clients">搜索
            </Button>

            <Button class="criteria-search-element" @click="getClientCreateModal">新增接入方</Button>
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
                <Modal v-model="showClientCreateModal" title="新增接入方"
                       :mask-closable="false" :loading="true"
                       @on-ok="createClient" @on-cancel="cleanModalData">

                    <Form ref="createClientForm" :model="client" :label-width="120" :rules="formRules">

                        <FormItem label="标识Code" prop="code">
                            <Input v-model="client.code" size="large" placeholder="标识Code"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="名称" prop="code">
                            <Input v-model="client.name" size="large" placeholder="名称"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="密钥" prop="key">
                            <Input v-model="client.key" size="large" placeholder="密钥"
                                   :maxlength="60" style="width: 320px;"/>
                            <Button type="info" @click="generateKey" style="margin-top: 10px;">生成密钥</Button>
                        </FormItem>

                        <FormItem label="描述" prop="desc">
                            <Input v-model="client.desc" type="textarea" placeholder="描述"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="接入方状态" prop="status">
                            <i-switch v-model="client.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-signature-update">
                <Modal v-model="showClientUpdateModal" title="修改渠道签名"
                       :mask-closable="false" :loading="true"
                       @on-ok="updateClient" @on-cancel="cleanModalData">

                    <Form ref="updateClientForm" :model="client" :label-width="120" :rules="formRules">

                        <FormItem label="标识Code" prop="code">
                            <Input v-model="client.code" size="large" placeholder="标识Code"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="名称" prop="code">
                            <Input v-model="client.name" size="large" placeholder="名称"
                                   :maxlength="60" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="密钥" prop="key">
                            <Input v-model="client.key" size="large" placeholder="密钥"
                                   :maxlength="60" style="width: 320px;"/>
                            <Button type="info" @click="generateKey" style="margin-top: 10px;">生成密钥</Button>
                        </FormItem>

                        <FormItem label="描述" prop="content">
                            <Input v-model="client.desc" type="textarea" placeholder="描述"
                                   :maxlength="250" :rows="3" style="width: 250px;"/>
                        </FormItem>

                        <FormItem label="接入方状态" prop="status">
                            <i-switch v-model="client.status" size="large"
                                      :true-value="1" :false-value="0">
                                <span slot="open">启用</span>
                                <span slot="close">禁用</span>
                            </i-switch>
                            <br/>
                            <p style="color: red;">
                                禁用接入方，将导致接入方无法正常调用短信服务。
                            </p>
                        </FormItem>
                    </Form>
                </Modal>
            </div>

            <div class="modal-signature-delete">
                <Modal v-model="showClientDeleteModal" title="删除接入方"
                       :mask-closable="false" :loading="true"
                       @on-ok="deleteClient" @on-cancel="cleanModalData">
                    <p style="text-align: center;">
                        确定要删除标识Code为“{{client.code}}”的接入方么？
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
    name: 'service-client',
    components: {
      pagination,
      expandRow
    },
    data() {
      return {
        showClientCreateModal: false,
        showClientUpdateModal: false,
        showClientDeleteModal: false,
        pageLoading: false,
        page: {
          currentPage: 1,
          pageSize: 10,
          totalCount: 100,
          items: []
        },
        criteria: {
          code: '',
          name: '',
          status: null,
          currentPage: 1,
          pageSize: 10
        },
        client: {
          id: null,
          code: '',
          name: '',
          key: '',
          desc: '',
          status: 0
        },
        column: [
          {
            type: 'expand', width: 50,
            render: (h, params) => {
              console.log(params.row);
              return h(expandRow, {
                props: {
                  row: params.row
                }
              });
            }
          },
          {title: '序号', type: 'index', width: 70},
          {title: '标识Code', key: 'code'},
          {title: '名称', key: 'name'},
          {title: '密钥', key: 'key'},
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
                      this.getClientUpdateModal(params.row);
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
                      this.getClientDeleteModal(params.row);
                    }
                  }
                }, '删除')
              ]);
            }
          }
        ],
        formRules: {
          code: [{required: true, message: '接入方标识Code不能为空！', trigger: 'change'}],
          name: [{required: true, message: '接入方名称不能为空！', trigger: 'change'}],
          key: [{required: true, message: '接入方密钥不能为空！', trigger: 'change'}],
          status: [{type: 'number', required: true, message: '接入方状态不能为空！', trigger: 'change'}]
        }
      }
    },
    mounted() {
      this.clients();
    },
    methods: {
      generateKey() {
        api.get('/api/service/clients/key').then((res) => {
          this.client.key = res;
        }).catch((err) => {
          this.disposeApiError(err);
        });
      },
      createClient() {
        this.$refs['createClientForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showClientCreateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.post('/api/service/clients', {
              code: this.client.code,
              name: this.client.name,
              key: this.client.key,
              desc: this.client.desc,
              status: this.client.status
            }).then(() => {
              this.cleanModalData();
              this.showClientCreateModal = false;
              this.$Message.success('新增接入方成功！');
              this.clients();
            }).catch((err) => {
              this.cleanModalData();
              this.showClientCreateModal = false;
              this.disposeApiError(err);
              this.clients();
            });
          }
        });
      },
      getClientCreateModal() {
        this.showClientCreateModal = true;
      },
      deleteClient() {
        api.delete('/api/service/clients/' + this.client.id).then(() => {
          this.$Message.success('删除接入方成功！');
          this.cleanModalData();
          this.showClientDeleteModal = false;
          this.clients();
        }).catch((err) => {
          this.disposeApiError(err);
          this.cleanModalData();
          this.showClientDeleteModal = false;
          this.clients();
        });
      },
      getClientDeleteModal(row) {
        Object.assign(this.client, row);
        this.showClientDeleteModal = true;
      },
      updateClient() {
        this.$refs['updateClientForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showClientUpdateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.put('/api/service/clients/' + this.client.id, {
              code: this.client.code,
              name: this.client.name,
              key: this.client.key,
              desc: this.client.desc,
              status: this.client.status
            }).then(() => {
              this.cleanModalData();
              this.showClientUpdateModal = false;
              this.$Message.success('修改接入方成功！');
              this.clients();
            }).catch((err) => {
              this.cleanModalData();
              this.showClientUpdateModal = false;
              this.disposeApiError(err);
              this.clients();
            });
          }
        });
      },
      getClientUpdateModal(row) {
        Object.assign(this.client, row);
        this.showClientUpdateModal = true;
      },
      clients() {
        this.pageLoading = true;
        api.get('/api/service/clients/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
          params: {
            code: this.criteria.code,
            name: this.criteria.name,
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
        this.$refs['createClientForm'].resetFields();
        this.$refs['updateClientForm'].resetFields();
        this.client = {
          id: null,
          code: '',
          name: '',
          key: '',
          desc: '',
          status: 0
        };
      },
      changePage(currentPage) {
        this.criteria.currentPage = currentPage;
        this.clients();
      },
      changePageSize(pageSize) {
        this.criteria.pageSize = pageSize;
        this.criteria.currentPage = 1;
        this.clients();
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