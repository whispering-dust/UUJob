<template>
    <div class="info-overview">
        <el-container>
            <el-header >
                <div class="overview-item">
                    <div class="title">
                        账号信息：
                    </div>
                    <div class="content">
                        {{ userName }}
                    </div>
                </div>
            </el-header>
            <el-main>Main</el-main>
          </el-container>
    </div>
  </template>
  
  <script>
  import { useStore } from "vuex";
  export default {
    data() {
      return {
        userName: useStore().state.userName, 
        pendingReports: 5, 
        processedReports: 10, 
        pendingAudits: 7, 
        processedAudits: 15,
      };
    },
  };
  </script>
  
  <style scoped>
  .info-overview {
    padding: 16px;
  }
  
  .overview-item {
    background-color: #ffffff;
    display: flex;
    border-radius: 6px;
    padding: 16px;
    text-align: center;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  
  .title {
    font-size: 20px;
    font-weight: bold;
    color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 10px;
  }
  
  .content {
    font-size: 24px;
    font-weight: bold;
    color: #333;
  }
  </style>
  