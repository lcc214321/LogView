<template>
  <div class="env">
    <el-row>
      <el-input v-model="queryEnvName" placeholder="请输入要查找的环境名称">
        <template slot="prepend">环境名称:</template>
      </el-input>
      <el-button type="primary" icon="el-icon-search">查询</el-button>
      <el-button type="primary" icon="el-icon-plus">创建</el-button>
    </el-row>
    <div class="Envtable">
      <el-table :data="Envlist" style="width: 100%">
        <el-table-column prop="EnvName" label="环境名称" width="180">
        </el-table-column>
        <el-table-column prop="Remark" label="备注" width="180">
        </el-table-column>
        <el-table-column prop="updatetime" label="更新时间">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleMicroEdit(scope.$index, scope.row)">编辑微服务
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="新增环境" width="450px" :visible.sync="addingDialog"> 
          <el-form
            :inline="true"
            inline-message
            :model="EnvForm"
            ref="addingForm"
            :rules="rules"
            label-position="right"
            label-width="120px"
          >
           <el-row>
              <el-form-item label="环境名称" prop="EnvName">
                <el-input
                  class="input_width_lg"
                  v-model="EnvForm.EnvName"
                  auto-complete="off"
                  placeholder="请输入环境名称"
                />
              </el-form-item>
            </el-row>
             <el-row>
              <el-form-item label="备注信息" prop="Remark">
                <el-input
                  class="input_width_lg"
                  v-model="EnvForm.Remark"
                  auto-complete="off"
                  placeholder="请输入备注信息"
                />
              </el-form-item>
            </el-row>
            <el-row class="d-flex justify-content-center">
              <el-button type="primary" @click="add">保 存</el-button>
              <el-button @click="addingDialog = false">取 消</el-button>
            </el-row>
          </el-form>
      </el-dialog>
      <el-dialog title="编辑环境" width="450px" :visible.sync="editDialog"> 
          <el-form
            :inline="true"
            inline-message
            :model="EnvForm"
            ref="editForm"
            :rules="rules"
            label-position="right"
            label-width="120px"
          >
           <el-row>
              <el-form-item label="环境名称" prop="EnvName">
                <el-input
                  class="input_width_lg"
                  v-model="EnvForm.EnvName"
                  auto-complete="off"
                  placeholder="请输入环境名称"
                />
              </el-form-item>
            </el-row>
             <el-row>
              <el-form-item label="备注信息" prop="Remark">
                <el-input
                  class="input_width_lg"
                  v-model="EnvForm.Remark"
                  auto-complete="off"
                  placeholder="请输入备注信息"
                />
              </el-form-item>
            </el-row>
            <el-row class="d-flex justify-content-center">
              <el-button type="primary" @click="add">保 存</el-button>
              <el-button @click="editDialog = false">取 消</el-button>
            </el-row>
          </el-form>
      </el-dialog>
    </div>
  </div>

</template>
<script>
  export default {
    data() {
      return {
        addingDialog:false,
        editDialog:false,
        queryEnvName: "",
        EnvForm: {
           EnvName:"",
           Remark:""
        },
        Envlist: []
      }
    }
  };

</script>
