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
            <el-main>
                <el-row>
                    <el-col :span="12">
                        <el-card class="box-card mr-2" style="height: 600px;">
                            <template #header>
                                <div>
                                  <span class="h4">待处理举报数({{ pendingReportsNum }})</span>
                                </div>
                              </template>

                              <el-table :data="pendingReportsData" height="500" style="width: 100%">
                                <el-table-column prop="id" label="ID"/>
                                <el-table-column prop="type" label="类型" >
                                    <template #default="scope">
                                        <div style="display: flex; align-items: center">
                                          <el-tag>{{scope.row.type}}</el-tag>
                                        </div>
                                      </template>
                                </el-table-column>
                                <el-table-column prop="reporterName" label="举报人"/>
                                <el-table-column prop="targetContent" label="举报内容" width="200" />
                                <el-table-column prop="content" label="举报理由" width="200"/>
                                                             
                              </el-table>
                        </el-card>
                    </el-col>

                    <el-col :span="12">
                        <el-card class="box-card ml-2" style="height: 600px;">
                            <template #header>
                                <div>
                                  <span class="h4">待审核的岗位({{ pendingJobsNum }})</span>
                                </div>
                              </template>
                              <el-skeleton :rows="10" :loading="loading" animated>
                                <template #default>
                                    <el-table :data="pendingJobsData" height="500" style="width: 100%">
                                        <el-table-column prop="id" label="岗位ID" />
                                        <el-table-column prop="title" label="岗位标题" />
                                        <el-table-column prop="companyName" label="公司" />
                                        <el-table-column prop="location" label="地点" />
                                        <el-table-column prop="salary" label="薪资" />
                                    </el-table>
                                </template>
                              </el-skeleton>                            
                        </el-card>
                    </el-col>
                </el-row>
            </el-main>
          </el-container>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import { useStore } from "vuex";
  export default {
    data() {
      return {
        loading: true,
        userName: useStore().state.userName, 
        pendingReportsNum: 5, 
        pendingReportsData: null,
        //processedReports: 10, 
        pendingJobsNum: 7, 
        pendingJobsData: null,
        //processedJobs: 15,
      };
    },
    methods: {
        async getPendingReports(){
            try {
                // Replace the URL with your API endpoint to fetch chats
                const response = await axios.get("http://localhost:9090/reports/unaudited", {

                });
                if (response.data.code === 200) {
                    console.log(response.data.data);
                    this.pendingReportsData = response.data.data
                    this.pendingReportsData.forEach(element => {
                        //限制内容字数
                        element.content=element.content.substring(0, 10)
                        switch (element.type) {
                            case 0:
                                element.type='岗位'
                                break;

                            case 1:
                                element.type='帖子'
                                break;

                            case 2:
                                element.type='评论'
                                break;
                    
                            default:
                                break;
                        }
                    });
                    this.pendingReportsNum = this.pendingReportsData.length
                    console.log(response.data.data);
                } else {
                    alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch chat list:", error);
            }
        },
        async getPendingJobs() {
            try {
                // 替换为你的API端点以获取待审核岗位
                const response = await axios.get("http://localhost:9090/jobs/unaudited");
                if (response.data.code === 200) {
                console.log(response.data.data);
                this.pendingJobsData = response.data.data;
                this.pendingJobsNum = this.pendingJobsData.length;
                this.loading = false;
                } else {
                alert(response.data.msg);
                }
            } catch (error) {
                console.error("Failed to fetch pending jobs:", error);
            }
        },

    },
    mounted() {
        this.getPendingReports()
        this.getPendingJobs();
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
  