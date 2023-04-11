<!-- 自定义注册组件 -->
<template>
  <el-form
    ref="registerForm"
    :model="registerUser"
    :rules="registerRules"
    label-width="100px"
    class="registerForm sign-up-form card bg-dark text-white"
  >
  <div class="card-body">
    <span class="clearfix"></span>
    <h4 class="heading h4 text-white pt-3 text-center">请提供你的信息,
      注册账户</h4>
  </div>
    <el-form-item label="用户名" prop="name">
      <el-input
          v-model="registerUser.name"
          placeholder="Enter UserName..."
      ></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input
        v-model="registerUser.password"
        type="password"
        placeholder="Enter Password..."
      ></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="password2">
      <el-input
        v-model="registerUser.password2"
        type="password"
        placeholder="Enter Password..."
      ></el-input>
    </el-form-item>
    <el-form-item label="电话号码" prop="phoneNumber">
      <el-input
        v-model="registerUser.phone"
        placeholder="Enter PhoneNumber..."
      ></el-input>
    </el-form-item>
    <el-form-item label="选择身份">
      <el-select placeholder="请选择身份" v-model="registerUser.role">
        <el-option label="求职者" value=0></el-option>
        <el-option label="招聘者" value=1></el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button
        @click="handleRegister('registerForm')"
        type="primary"
        class="submit-btn"
        >注册</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { ref, getCurrentInstance } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import { tr } from 'element-plus/es/locale';
// import axios from 'axios' // 仅限在当前组件使用
export default {
  props: {
    registerUser: {
      type: Object,
      required: true,
    },
    registerRules: {
      type: Object,
      required: true,
    },
  },
  setup() {
    // @ts-ignore
    const { ctx } = getCurrentInstance();
    const store = useStore();

    //注册按钮响应函数
    const handleRegister = (formName: string) => {
      ctx.$refs[formName].validate((valid: boolean) => {
        if (true) {
          alert("submit!");
          axios({
            method: "post",
            url: "http://localhost:9090/user/register",
            data: {
              password: ctx.registerUser.password,
              name: ctx.registerUser.name,
              phone: ctx.registerUser.phone,
              role: ctx.registerUser.role
            },
          }).then(function(response){
            // 注册成功
            if(response.data.code == 0){
                alert("注册成功");
                alert("你的账号为"+response.data.data.userId);//弹窗输出返回的账号
                
                useStore().state.commit("setUserId",response.data.data.userId);                
                useStore().state.commit("setUserName",ctx.registerUser.name);
                useStore().state.commit("setRole",ctx.registerUser.role);
                
                console.log(response.data.data);
            }
            // 注册失败提示信息
            else{
                alert(response.data.msg);
            }
          });
          console.log(store.state.userId);
        } else {
          console.log("error submit!!");

          return false;
        }
      });
    };
    return { handleRegister };
  },
};
</script>
<style scoped>
.registerForm {
  margin-top: 0px;
  background-color: #fff;
  padding: 20px 40px 20px 20px;
  border-radius: 5px;
  box-shadow: 0px 5px 10px #cccc;
}

.submit-btn {
  width: 100%;
}
.tiparea {
  text-align: right;
  font-size: 12px;
  color: #333;
}
.tiparea p a {
  color: #409eff;
}
</style>
