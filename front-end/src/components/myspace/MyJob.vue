<template>
    <el-container style="height:100% width:100%">
        <el-aside width="20%">
          <el-scrollbar>
            <div class="search-bar">
              <el-input v-model="search" placeholder="搜索职位" @input="searchJobs" :prefix-icon="Search"/>
            </div>
            <div class="job-list">
              <div 
                v-for="job in filteredJobs"
                :key="job.id"
                @click="selectJob(job.id)"
              >
              <el-card shadow="hover" class="mr-3 mt-1">
                <el-container>
                  <el-aside style="padding: 0px;background-color:white" width="30%">
                    <div class="company-logo">{{ job.title.slice(0, 1) }}</div>          
                  </el-aside>
                  <el-main style="padding: 0px;">
                    <div class="job-info">
                      <div>{{ job.title }}</div>
                      <div>{{ job.date }}</div>
                    </div>
                  </el-main>
                </el-container>
              </el-card>
              </div >
            </div>
          </el-scrollbar>
          
        </el-aside>
  
        <!-- main -->
        <el-main>
          <el-card v-if="selectedJob.title" class="box-card p-0 card" shadow="never" >
            <div class="card-header p-0 mb-3" style="">
              <span style="font-size: larger;"><strong>{{selectedJob.title}}</strong></span>
              <p style="color:deepskyblue">
                薪资：{{ selectedJob.salary }}
              </p>
            </div>
            <div class="row mb-3">
              <el-tag v-for="item in items" :key="item.label" :type="item.type" class="mx-1" effect="dark" round>
                {{ item.label }}
              </el-tag>
    
            </div>
            <div class="row text-area p-3" v-html="formattedText">
            </div>
  
            <div class="row p-3">
              <el-descriptions
                class="margin-top"
                title="信息栏"
                :column="1"
                :size="larger"
                border
                style="width:80%"
              >
                <el-descriptions-item>
                  <template #label>
                    <div class="cell-item">
                      <el-icon >
                        <location />
                      </el-icon>
                      地点
                    </div>
                  </template>
                  {{selectedJob.location}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template #label>
                    <div class="cell-item">
                      <el-icon >
                        <tickets />
                      </el-icon>
                      审核状态
                    </div>
                  </template>
                  <el-tag size="large" >{{showStatus}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item>
                  <template #label>
                    <div class="cell-item">
                      <el-icon >
                        <tickets />
                      </el-icon>
                      审核日期
                    </div>
                  </template>
                  {{selectedJob.date}}
                </el-descriptions-item>
            </el-descriptions>
            </div>
          </el-card>
        </el-main>
        <el-aside width="10%">
          <div class="action-buttons">
            <el-button class="ml-2 mt-2" style="width: 75%;" type="danger" ><el-icon class="mr-1"><DeleteFilled /></el-icon>删除岗位</el-button>
            <el-button class="ml-2 mt-2" style="width: 75%;" type="primary"  @click="dialogFormVisible=true"><el-icon class="mr-1"><Edit /></el-icon>修改信息</el-button>
          </div>
        </el-aside>
    </el-container>

    <el-dialog top="0vh" v-model="dialogFormVisible" title="请填写表单信息" draggable :lock-scroll=false>
      <el-scrollbar>
        功能面板
      </el-scrollbar>
    </el-dialog>
  </template>
  
  <script>
  import axios from "axios";
  import { Search } from '@element-plus/icons-vue'
  import { reactive, toRefs, watch, ref } from 'vue'
  import { useStore } from "vuex";
  
  export default {
    data() {
      const items = ref([
        { type: '', label: 'Tag 1' },
        { type: 'success', label: 'Tag 2' },
        { type: 'info', label: 'Tag 3' },
        { type: 'danger', label: 'Tag 4' },
        { type: 'warning', label: 'Tag 5' },
      ])
  
      return {
        userId: useStore().state.userId,
        items,
        Search,
        search: "",
        selectedJobText: "工作内容\n1、独立完成相关短视频的拍摄和后期剪辑工作；包含但不限于素材、视频剪辑、特效制作、添加片头、片尾和字幕等；2、理解项目需求，进行脚本的规划与制定；\n3、协调与沟通视频制作过程中的各个环节，完成制作全过程，保证成片质量；\n4、熟悉直播平台玩法和制作，对B站、小红书等视频平台的热点内容敏感，分析跑量视频特征，快速同款及精进；\n任职资格：\n1、大专以上学历，影视后期、广告编导、视觉设计相关专业毕业，一年以上相关经验；\n2、熟悉并热爱视频类广告创作，脑洞大，具有良好的审美和节奏感，有良好的内容热点嗅觉；\n3、熟练使用AE、PR、PS、edius等后期软件；\n4、熟练使用各种摄像和照片拍摄设备；\n5、乐观向上，有良好的职业素养，具有较强的团队协作精神、沟通能力和责任心。",
        JobList: [
          {
            jobId: 1,
            title: "软件工程师",
            position: "前端",
            location: "上海",
            salary: "15K-25K",
            applicationDate: "2023-04-01",
            reviewDate: null
          },
          {
            jobId: 2,
            title: "UI设计师",
            position: "设计",
            location: "北京",
            salary: "12K-20K",
            applicationDate: "2023-04-10",
            status: 0,
            reviewDate: null
          },
        ],
        filteredJobs: [],
        selectedJob: {},
        owner:{
          userId:'',
          userName:'owner',
          phone:'13726928387',
        },
        dialogFormVisible: false,
      };
    },
    methods: {
      async getJobList() {
        try {
          const response = await axios.get("http://localhost:9090/users/jobs", {
            params: {
              userId: this.userId,
            },
          });
  
          if (response.data.code === 200) {
            this.JobList = response.data.data;
            this.JobList.forEach(job => {
              const date = new Date(job.date);
              job.date = date.getFullYear() + "-" + (date.getMonth() + 1).toString().padStart(2, '0') + "-" + date.getDate().toString().padStart(2, '0');
            });
            this.filteredJobs = this.JobList;
          } else {
            alert(response.data.msg);
          }
        } catch (error) {
          console.error("Failed to fetch job list:", error);
        }
  
        this.filteredJobs = this.JobList;
      },
      selectJob(id) {
        this.selectedJob = this.JobList.find((job) => job.id === id);
      },
      
      searchJobs() {
        if (this.search) {
          this.filteredJobs = this.JobList.filter((job) =>
            job.title.toLowerCase().includes(this.search.toLowerCase())
          );
        } else {
          this.filteredJobs = this.JobList;
        }
      },

    },
    mounted() {
      this.getJobList();
    },
    computed: {
      formattedText() {
        // 将换行符替换为 <br> 标签
        return this.selectedJob.description.replace(/\n/g, "<br>");
      },
      showStatus(){
        if (this.selectedJob.status == 0) {
          return "未审核"
        }else if(this.selectedJob.status == 1){
          return "已通过"
        }
        
      }
    },
  };
  </script>
  
  <style scoped>
  
  .action-buttons {
    display: flex;
    flex-direction: column;
    gap: 10px;
    background-color: rgb(66, 74, 92);
    height: 100%;
    padding-top: 50px;
  }
  .el-aside {
    background-color: #f5f7fa;
  }
  
  .search-bar {
    padding: 20px;
  }
  
  .job-list {
    height: calc(100% - 70px);
    overflow-y: auto;
  }
  .box-card{
    height: 100%;
  }
  .company-logo {
    background-color: #409EFF;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    color: #fff;
  }
  
  .job-info {
    display: flex;
    flex-direction: column;
  }
  
  .div {
    display: flex;
    flex-direction: column;
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .box-card {
    overflow-y: auto;
  }
  .my-label{
    width: 30%;
  }
  </style>