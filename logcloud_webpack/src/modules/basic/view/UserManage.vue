<template>
  <div class="user">
      <el-form inline :model="formSearch" label-width="70px" ref="primaryForm" style="float:left">
        <el-form-item label="用户名称">
          <el-input class="input_width_lg" placeholder="请输入用户名称" v-model="formSearch.queryUserName" />
        </el-form-item>
        <el-form-item label="角色类型">
          <el-select v-model="formSearch.queryUserType" placeholder="请选择">
            <el-option label="日志查看员" value="Log_viewer"></el-option>
            <el-option label="超级管理员" value="Super_admin"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="d-block">
            <el-button type="primary" size="small" icon="el-icon-search">查询</el-button>
            <el-button type="primary" size="small" icon="el-icon-plus" @click="handleadd">创建</el-button>
        </el-form-item>
      </el-form>
    <div class="usertable" style="margin-top:20px">
      <el-table :data="userlist" style="width: 100%" border>
        <el-table-column prop="username" label="用户名称" width="180">
        </el-table-column>
        <el-table-column prop="usertype" label="用户类型" width="180">
        </el-table-column>
        <el-table-column prop="updatetime" label="更新时间">
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            {{scope.row.status?"启用":"禁用"}}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleEnable(scope.$index, scope.row)">
              {{scope.row.status?"启用":"禁用"}}
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page.sync="curpageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="10"
        layout="total, sizes, prev, pager, next, jumper" :total="totalpage">
      </el-pagination>
    </div>

    <el-dialog title="创建用户" width="450px" :visible.sync="addingDialog" @close="addingDialog = false">
      <el-form :inline="true" inline-message :model="UserForm" ref="addingForm" :rules="rules" label-position="right"
        label-width="120px">
        <el-row>
          <el-form-item label="用户名称" prop="UserName">
            <el-input class="input_width_lg" v-model="UserForm.UserName" auto-complete="off" placeholder="请输入用户名称" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="角色类型" prop="UserType">
            <el-select v-model="UserForm.UserType" placeholder="请选择角色类型">
              <el-option label="请选择" value=""></el-option>
              <el-option label="日志查看员" value="Log_viewer"></el-option>
              <el-option label="超级管理员" value="Super_admin"></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="密码" prop="pass">
            <el-input class="input_width_lg" v-model="UserForm.pass" type="password" auto-complete="off"
              placeholder="请输入密码" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="确认密码" prop="checkpass">
            <el-input class="input_width_lg" v-model="UserForm.checkpass" type="password" auto-complete="off"
              placeholder="请再次输入密码" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="用户状态" prop="Status">
            <el-radio-group v-model="UserForm.Status">
              <el-radio :label=true>启用</el-radio>
              <el-radio :label=false>禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-row>
        <el-row class="d-flex justify-content-center">
          <el-button type="primary" @click="adduser">保 存</el-button>
          <el-button @click="addingDialog = false">取 消</el-button>
        </el-row>
      </el-form>
    </el-dialog>
    <el-dialog title="编辑用户" width="450px" :visible.sync="editDialog" @close="editDialog=false">
      <el-form :inline="true" inline-message :model="UserForm" ref="editForm" :rules="rules" label-position="right"
        label-width="120px">
        <el-row>
          <el-form-item label="用户名称" prop="UserName">
            <el-input class="input_width_lg" v-model="UserForm.UserName" auto-complete="off" placeholder="请输入用户名称" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="角色类型" prop="UserType">
            <el-select v-model="UserForm.UserType" placeholder="请选择">
              <el-option label="请选择" value=""></el-option>
              <el-option label="日志查看员" value="Log_viewer"></el-option>
              <el-option label="超级管理员" value="Super_admin"></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="用户状态" prop="Status">
            <el-radio-group v-model="UserForm.Status">
              <el-radio :label=true>启用</el-radio>
              <el-radio :label=false>禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-row>
        <el-row class="d-flex justify-content-center">
          <el-button type="primary" @click="edituser">保 存</el-button>
          <el-button @click="editDialog = false">取 消</el-button>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import {
    BASIC_API
  } from "../../../../constants/constants.js";

  export default {
    data() {
      return {
        formSearch: {
          queryUserName: "", //查询中填入的username
          queryUserType: "Log_viewer", //查询中选择的usertype
        },
        editDialog: false,
        UserForm: {
          UserName: "", //创建表单中的username
          UserType: "", //创建表单中的usertype
          pass: "", //输入密码
          checkpass: "", //确认密码
          Status: true //创建表单中启用禁用状态
        },
        userlist: [],
        addingDialog: false,
        curpageIndex: 1,
        curpageSize: 10,
        totalpage: 1,
        rules: {
          UserName: [{
            required: true,
            message: "请输入用户名称",
            trigger: "blur"
          }],
          UserType: [{
            required: true,
            message: "请选择角色类型",
            trigger: "change"
          }],
          pass: [{
            required: true,
            message: "请输入密码",
            trigger: "blur"
          }],
          checkpass: [{
            required: true,
            message: "请输入密码确认",
            trigger: "blur"
          }],
          status: [{
            required: true,
            message: "请选择状态",
            trigger: "change"
          }]
        },

      }
    },
    async created() {
      this.getUserPage()
    },
    methods: {
      async getUserPage() {
        var url = BASIC_API + "/user/userPage/" + (this.curpageIndex - 1) + "/" + this.curpageSize
        this.userPage = this.$httpWithMsg
          .get(url)
          .then(response => {
            this.userlist = response.data.content
            this.totalpage = response.data.totalElements
          })
          .then(() => {
            console.log(123)
          });;
      },
      handleadd() {
        this.UserForm = {
          UserName: "", //创建表单中的username
          UserType: "", //创建表单中的usertype
          pass: "", //输入密码
          checkpass: "", //确认密码
          Status: true //创建表单中启用禁用状态
        }
        this.addingDialog = true;
      },
      handleEdit(index, row) {
        this.editDialog = true;
        this.UserForm = {
          UserName: row.username,
          UserType: row.usertype,
          Status: row.status
        }
      },
      handleEnable(index, row) {

      },
      handleDelete(index, row) {

      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getUserPage();
      },
      handleSizeChange(val) {
        this.curpageSize = val;
        this.curpageIndex = 1;
        this.getUserPage();
      },
      closeDetail() {
        this.addingDialog = false
      },
      adduser() {
        var url = BASIC_API + "/user/userPage/" + (this.curpageIndex - 1) + "/" + this.curpageSize
      },
      edituser() {

      }
    },
    watch: {

    }
  }

</script>

<style scoped>
  .input_width_lg {
    width: 180px;
  }

</style>
