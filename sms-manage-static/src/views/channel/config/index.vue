<template>

    <div>

        <div class="criteria-search">
            <span class="criteria-search-element">
                <span>名称：</span>
                <Input v-model="criteria.name" placeholder="名称" clearable
                       style="width: 200px"/>
            </span>

            <span class="criteria-search-element">
                <span>渠道配置类型：</span>
                <Select v-model="criteria.type" clearable style="width:200px;">
                    <Option v-for="item in configTypes" :value="item" :key="item">{{ item }}</Option>
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
                    @click="channelConfigs">搜索
            </Button>

            <Button class="criteria-search-element" @click="getConfigCreateModal">新增渠道配置</Button>
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
            <div class="modal-config-create">
                <Modal v-model="showConfigCreateModal" title="新增渠道配置"
                       :mask-closable="false" :loading="true"
                       @on-ok="createConfig" @on-cancel="cleanModalData">

                    <div class="modal-config-create-tabs">

                        <Form ref="createConfigForm" :model="configDetail.config" :label-width="120" :rules="formRules">
                            <FormItem label="名称" prop="name">
                                <Input v-model="configDetail.config.name" size="large" placeholder="渠道配置名称"
                                       :maxlength="45" style="width: 250px;"/>
                                <br/>
                                <span>（渠道配置名称全局唯一！）</span>
                            </FormItem>

                            <FormItem label="描述" prop="desc">
                                <Input v-model="configDetail.config.desc" type="textarea" placeholder="渠道配置描述"
                                       :maxlength="250" :rows="3"
                                       style="width: 250px;"/>
                            </FormItem>

                            <FormItem label="渠道配置类型" prop="type">
                                <Select v-model="configDetail.config.type" style="width:200px;"
                                        @on-change="getConfigTypeParams">
                                    <Option v-for="item in configTypes" :value="item" :key="item">{{ item }}</Option>
                                </Select>
                            </FormItem>

                            <FormItem label="渠道配置状态" prop="status">
                                <i-switch v-model="configDetail.config.status" size="large"
                                          :true-value="1" :false-value="0">
                                    <span slot="open">启用</span>
                                    <span slot="close">禁用</span>
                                </i-switch>
                            </FormItem>

                            <FormItem label="渠道配置参数">
                                <span style="display: none;">{{configTypeParams}}</span>
                                <Row v-for="param in configDetail.params" v-bind:key="param.key">
                                    <Col span="12">{{param.key}}</Col>
                                    <Col span="12">
                                        <Input type="text" v-model="param.value"
                                               :placeholder="param.key" :maxlength="120"/>
                                    </Col>
                                </Row>
                            </FormItem>

                        </Form>

                    </div>

                </Modal>
            </div>

            <div class="modal-config-update">
                <Modal v-model="showConfigUpdateModal" title="修改渠道配置"
                       :mask-closable="false" :loading="true"
                       @on-ok="updateConfig" @on-cancel="cleanModalData">

                    <div class="modal-config-update-tabs">

                        <Form ref="updateConfigForm" :model="configDetail.config" :label-width="100" :rules="formRules">
                            <FormItem label="名称" prop="name">
                                <Input v-model="configDetail.config.name" size="large" placeholder="渠道配置名称"
                                       :maxlength="45" style="width: 250px;"/>
                                <br/>
                                <span>（渠道配置名称全局唯一！）</span>
                            </FormItem>

                            <FormItem label="描述" prop="desc">
                                <Input v-model="configDetail.config.desc" type="textarea" placeholder="渠道配置描述"
                                       :maxlength="250" :rows="3"
                                       style="width: 250px;"/>
                            </FormItem>

                            <FormItem label="渠道配置类型" prop="type">
                                <Select v-model="configDetail.config.type" style="width:200px;">
                                    <Option v-for="item in configTypes" :value="item" :key="item">{{ item }}</Option>
                                </Select>
                            </FormItem>

                            <FormItem label="渠道配置状态" prop="status">
                                <i-switch v-model="configDetail.config.status" size="large"
                                          :true-value="1" :false-value="0">
                                    <span slot="open">启用</span>
                                    <span slot="close">禁用</span>
                                </i-switch>
                                <br/>
                                <span style="color: red;">（禁用渠道配置将禁用其下的渠道模板、渠道签名以及短信服务模板！）</span>
                            </FormItem>

                            <FormItem label="渠道配置参数">
                                <Row v-for="param in configDetail.params">
                                    <Col span="12">{{param.key}}</Col>
                                    <Col span="12">
                                        <Input type="text" v-model="param.value"
                                               :placeholder="param.key" :maxlength="120"/>
                                    </Col>
                                </Row>
                            </FormItem>

                        </Form>

                    </div>

                </Modal>
            </div>

            <div class="modal-config-delete">
                <Modal v-model="showConfigDeleteModal" title="删除渠道配置"
                       :mask-closable="false" :loading="true"
                       @on-ok="deleteConfig" @on-cancel="cleanModalData">
                    <p style="text-align: center;">
                        确定要删除名称为“{{configDetail.config.name}}”的渠道配置么？<br/>
                        <span style="color: red;">（删除渠道配置将禁用其下的渠道模板、渠道签名以及短信服务模板！）</span>
                    </p>
                </Modal>
            </div>
        </div>

    </div>
</template>

<script>
  import pagination from '@/components/page'
  import api from '@/components/axios'

  export default {
    name: 'channel-config',
    components: {
      pagination
    },
    data() {
      return {
        showConfigCreateModal: false,
        showConfigUpdateModal: false,
        showConfigDeleteModal: false,
        pageLoading: false,
        column: [
          {title: '序号', type: 'index', width: 70},
          {title: '名称', key: 'name'},
          {title: '类型', key: 'type'},
          {title: '描述', key: 'desc', tooltip: true},
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
            title: '操作', key: 'action', width: 200, align: 'center',
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
                      this.getConfigUpdateModal(params.row.id);
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
                      this.getConfigDeleteModal(params.row);
                    }
                  }
                }, '删除')
              ]);
            }
          }
        ],
        page: {
          currentPage: 1,
          pageSize: 10,
          totalCount: 100,
          items: []
        },
        criteria: {
          name: '',
          type: '',
          status: null,
          currentPage: 1,
          pageSize: 10
        },
        configTypes: [],
        configDetail: {
          config: {
            id: null,
            name: '',
            desc: '',
            type: '',
            status: 0,
            params: [{
              key: '',
              value: ''
            }, {
              key: '',
              value: ''
            }]
          }
        },
        configTypeParams: [],
        formRules: {
          name: [
            {required: true, message: '渠道配置名称不能为空！', trigger: 'change'}
          ],
          type: [
            {required: true, message: '渠道配置类型不能为空！', trigger: 'change'}
          ],
          status: [
            {type: 'number', required: true, message: '渠道配置状态不能为空！', trigger: 'change'}
          ]
        }
      }
    },
    mounted() {
      this.getConfigTypes();
      this.channelConfigs();
    },
    methods: {
      createConfig() {
        this.$refs['createConfigForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showConfigCreateModal = false;
            this.$Message.warning('参数校验失败！');
          } else {
            api.post('/api/channel/configs', {
              config: {
                id: this.configDetail.config.id,
                name: this.configDetail.config.name,
                desc: this.configDetail.config.desc,
                type: this.configDetail.config.type,
                status: this.configDetail.config.status
              },
              params: this.configDetail.params
            }).then(() => {
              this.cleanModalData();
              this.showConfigCreateModal = false;
              this.$Message.success('新增渠道配置成功！');
              this.channelConfigs();
            }).catch((err) => {
              this.cleanModalData();
              this.showConfigCreateModal = false;
              this.disposeApiError(err);
              this.channelConfigs();
            });
          }
        });
      },
      getConfigCreateModal() {
        this.showConfigCreateModal = true;
      },
      deleteConfig() {
        api.delete('/api/channel/configs/' + this.configDetail.config.id).then(() => {
          this.$Message.success('删除渠道配置成功');
          this.cleanModalData();
          this.showConfigDeleteModal = false;
          this.channelConfigs();
        }).catch((err) => {
          this.disposeApiError(err);
          this.cleanModalData();
          this.showConfigDeleteModal = false;
          this.channelConfigs();
        });
      },
      getConfigDeleteModal(row) {
        this.showConfigDeleteModal = true;
        this.configDetail.config.id = row.id;
        this.configDetail.config.name = row.name;
      },
      updateConfig() {
        this.$refs['updateConfigForm'].validate((valid) => {
          if (!valid) {
            this.cleanModalData();
            this.showConfigUpdateModal = false;
            this.$refs['updateConfigForm'].resetFields();
            this.$Message.warning('参数校验失败！');
          } else {
            api.put('/api/channel/configs/' + this.configDetail.config.id, {
              config: {
                id: this.configDetail.config.id,
                name: this.configDetail.config.name,
                desc: this.configDetail.config.desc,
                type: this.configDetail.config.type,
                status: this.configDetail.config.status
              },
              params: this.configDetail.params
            }).then(() => {
              this.cleanModalData();
              this.showConfigUpdateModal = false;
              this.$Message.success('更新渠道配置成功！');
              this.channelConfigs();
            }).catch((err) => {
              this.cleanModalData();
              this.disposeApiError(err);
              this.showConfigUpdateModal = false;
              this.channelConfigs();
            });
          }
        });
      },
      getConfigUpdateModal(configId) {
        this.showConfigUpdateModal = true;
        api.get('/api/channel/configs/detail/' + configId).then((res) => {
          this.configDetail = res;
          this.getConfigTypeParams();
        }).catch((err) => {
          this.disposeApiError(err);
          this.cleanModalData();
          this.showConfigUpdateModal = false;
        });
      },
      getConfigTypes() {
        api.get('/api/channel/configs/types').then((data) => {
          this.configTypes = data;
        }).catch((err) => {
          this.disposeApiError(err);
        })
      },
      getConfigTypeParams() {
        if (this.configDetail.config.type && this.configDetail.config.type !== '') {
          api.get('/api/channel/configs/types/params', {
            params: {type: this.configDetail.config.type}
          }).then((res) => {
            this.configTypeParams = res;

            //转换补充缺少参数
            let tempConfigTypeParams = [];
            for (let index in this.configTypeParams) {
              let find = false;
              for (let i in this.configDetail.params) {
                if (this.configDetail.params[i].key === this.configTypeParams[index]) {
                  tempConfigTypeParams.push(this.configDetail.params[i]);
                  find = true;
                  break;
                }
              }
              if (!find) {
                tempConfigTypeParams.push({
                  key: this.configTypeParams[index],
                  value: ''
                })
              }
            }
            this.configDetail.params = tempConfigTypeParams;
          }).catch((err) => {
            this.configTypeParams = [];
            this.disposeApiError(err);
            this.cleanModalData();
            this.showConfigUpdateModal = false;
            this.showConfigCreateModal = false;
          });
        }
      },
      channelConfigs() {
        this.pageLoading = true;
        api.get('/api/channel/configs/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
          params: {
            name: this.criteria.name,
            type: this.criteria.type,
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
        this.$refs['createConfigForm'].resetFields();
        this.$refs['updateConfigForm'].resetFields();
        this.configDetail = {
          config: {
            id: null,
            name: '',
            desc: '',
            type: '',
            status: 0,
            params: [{
              key: '',
              value: ''
            }, {
              key: '',
              value: ''
            }]
          }
        };
      },
      changePage(currentPage) {
        this.criteria.currentPage = currentPage;
        this.channelConfigs();
      },
      changePageSize(pageSize) {
        this.criteria.pageSize = pageSize;
        this.criteria.currentPage = 1;
        this.channelConfigs();
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