<template>
  <div class="user">
    <el-row>
      <el-input v-model="queryUserName" placeholder="请输入要查找的用户名">
        <template slot="prepend">用户名:</template>
      </el-input>
      <el-input v-model="queryUserType" placeholder="请输入要查找的用户名">
        <el-select v-model="queryUserType" slot="append" placeholder="请选择">
          <el-option label="日志查看员" value="Log_viewer"></el-option>
          <el-option label="超级管理员" value="Super_admin"></el-option>
        </el-select>
      </el-input>
      <el-button type="primary" icon="el-icon-search">查询</el-button>
      <el-button type="primary" icon="el-icon-plus">创建</el-button>
    </el-row>
    <div class="usertable">
      <el-table :data="userlist" style="width: 100%">
        <el-table-column prop="username" label="用户名称" width="180">
        </el-table-column>
        <el-table-column prop="usertype" label="用户类型" width="180">
        </el-table-column>
        <el-table-column prop="updatetime" label="更新时间">
        </el-table-column>
        <el-table-column prop="status" label="状态">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleEnable(scope.$index, scope.row)">{{scope.row.status}}
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="创建编辑用户" :visible.sync="dialogTableVisible" @close="closeDetail">
      <el-row>
        <el-input v-model="UserForm.UserName" placeholder="请输入要查找的用户名">
          <template slot="prepend">用户名:</template>
        </el-input>
      </el-row>
      <el-row>
        <el-input v-model="UserForm.UserType" placeholder="请输入要查找的用户名">
          <el-select v-model="queryUserType" slot="append" placeholder="请选择">
            <el-option label="请选择" value=""></el-option>
            <el-option label="日志查看员" value="Log_viewer"></el-option>
            <el-option label="超级管理员" value="Super_admin"></el-option>
          </el-select>
        </el-input>
      </el-row>
      <el-row>
        <el-input v-model="UserForm.pass" placeholder="请输入密码">
          <template slot="prepend">密码:</template>
        </el-input>
      </el-row>
      <el-row>
        <el-input v-model="UserForm.checkpass" placeholder="确认密码">
          <template slot="prepend">确认密码:</template>
        </el-input>
      </el-row>
      <el-row>
        <el-radio-group v-model="UserForm.Status">
          <el-radio :label=true>启用</el-radio>
          <el-radio :label=false>禁用</el-radio>
        </el-radio-group>
      </el-row>
      <el-row>
      </el-row>
      <el-button-group>
        <el-button type="primary" @click="adduser">确认</el-button>
        <el-button type="primary" @click="closeDetail">取消</el-button>
      </el-button-group>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        queryUserName: "", //查询中填入的username
        queryUserType: "Log_viewer", //查询中选择的usertype
        UserForm: {
          UserName: "", //创建表单中的username
          UserType: "", //创建表单中的usertype
          pass: "", //输入密码
          checkpass: "", //确认密码
          Status: true //创建表单中启用禁用状态
        },
        userlist: []
      }
    },
    methods: {
      handleEdit(index, row) {

      },
      handleEnable(index, row) {

      },
      handleDelete(index, row) {

      }
    }
  }

</script>
