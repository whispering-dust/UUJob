<!-- 登录自定义组件 -->
<template>
  <el-form ref="loginForm" :model="loginUser" :rules="rules" label-width="30px"
    class="loginForm sign-in-form card bg-dark text-white">
    <div class="card-body">
      <span class="clearfix"></span>
      <h4 class="heading h3 text-white pt-3 pb-5">欢迎来到UUJOB交流平台,<br>
        请登录你的账户吧.</h4>
    </div>
    <el-form-item label="" prop="userName">
      <h5 class="h5 text-white ">用户名</h5>
      <el-input v-model="loginUser.userName" placeholder="Enter userName defined by youself..."></el-input>
    </el-form-item>
    <el-form-item label="" prop="password">
      <h5 class=" h5 text-white ">密码</h5>
      <el-input v-model="loginUser.password" type="password" placeholder="Enter Password..."></el-input>
    </el-form-item>

    <el-form-item>
      <el-button @click="handleLogin('loginForm')" type="primary" class="submit-btn">提交</el-button>
    </el-form-item>

    <!-- 找回密码 -->
    <div class="tiparea">
      <p>忘记密码？ <a>立即找回</a></p>
    </div>
  </el-form>
</template>

<script lang="ts">
import { loginUser } from "@/utils/loginValidators";
import { ref, getCurrentInstance } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";
export default {
  components: {},
  props: {
    loginUser: {
      type: Object,
      required: true,
    },
    rules: {
      type: Object,
      required: true,
    },
  },
  setup() {
    // @ts-ignore
    const { ctx } = getCurrentInstance(); //获取实例
    const store = useStore();
    const router = useRouter();
    console.log(store.state.userId);

    // 触发登录方法
    const handleLogin = (formName: string) => {
      ctx.$refs[formName].validate((valid: boolean) => {
        if (valid) {
          alert("submit!");
          axios({
            method: "post",
            url: "http://localhost:9090/users/login",
            data: {
              userName: ctx.loginUser.userName,
              password: ctx.loginUser.password,
            },
          }).then(function (response) {
            // 登录成功
            if (response.data.code === 200) {
              // alert("登录成功");
              console.log(response.data.data);
              /*修改全局用户变量*/
              var userId = response.data.data;
              store.commit("setUserId", userId);
              store.commit("setUserName", ctx.loginUser.userName);
              alert(ctx.loginUser.userName);
              console.log(store.state);

              // window.localStorage.setItem("token",userId);
              router.push("../home");
            } // 登录失败输出错误信息
            else {
              alert(response.data.msg);
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    };

    return { handleLogin };
  },
};
</script>
<style scoped>
/* form */
.loginForm {
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
