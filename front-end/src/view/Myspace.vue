<template>
  <div class="myspace">
      <div class="row">
        <div class="col-2 bg-dark" style="min-height: 800px;">
          <el-menu
            active-text-color="#a3cef1"
            background-color="#343a40"
            class="el-menu-vertical-demo"
            text-color="#fff"
            style="width: 100%; border-right: 0px;"
            @open="handleOpen"
            @close="handleClose"
          >
            <el-sub-menu index="1">
              <template #title>
                <el-icon><User /></el-icon>
                <span>个人信息</span>
              </template>
              <router-link to="/myspace/userAccount" class="text-white" ><el-menu-item index="1-1"> 账号信息</el-menu-item></router-link>
              <router-link to="/myspace/password" class="text-white" ><el-menu-item index="1-2">修改密码</el-menu-item></router-link>
           </el-sub-menu>
            <router-link v-if="store.role === 0" class="text-white" to="/myspace/profile" >
              <el-menu-item index="2">
                <el-icon><icon-menu /></el-icon>
                我的简历
              </el-menu-item>
            </router-link>
            <router-link class="text-white" to="/myspace/chatList" >
              <el-menu-item index="6">
                <el-icon><ChatLineRound /></el-icon>
                联系人
              </el-menu-item>
            </router-link>
            <router-link v-if="store.role === 0" class="text-white" to="/myspace/myPostedJobs" >
              <el-menu-item index="3">
                <el-icon><Document /></el-icon>
                我的投递
              </el-menu-item>
            </router-link>
            <router-link v-else class="text-white" to="/myspace/jobManagement" >
              <el-menu-item index="3">
                <el-icon><Document /></el-icon>
                招聘管理
              </el-menu-item>
            </router-link>
            <el-sub-menu index="4">
              <template #title>
                <el-icon><Star /></el-icon>
                <span>我的收藏</span>
              </template>
              <router-link to="/myspace/starPosts" class="text-white"><el-menu-item index="4-1">关注帖子</el-menu-item></router-link>
              <router-link to="/myspace/starJobs" class="text-white"><el-menu-item index="4-2">关注职招</el-menu-item></router-link>
            </el-sub-menu>
            <router-link class="text-white" to="/myspace/myPosts" >
              <el-menu-item index="5">
                <el-icon><Document /></el-icon>
                我的帖子
              </el-menu-item>
            </router-link>
          </el-menu>
        
        </div>

        <div class="col-10">
          <el-container style="height: 100%;width:100%;">
            <router-view ></router-view>
          </el-container>
        </div>
      </div>
  </div>

  
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import {
  Document,
  Menu as IconMenu,
  User,
  Setting,
  ChatLineRound,
  Star,
} from '@element-plus/icons-vue'
import { useStore } from 'vuex'

const store = useStore().state

const isCollapse = ref(true)

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 150px;
  min-height: 600px;
}
.alink{
  text-decoration: none;
}


.myspace{
  background-color: rgb(245, 245, 245);
  width: 100%;
  height: 100%;
}
</style>
