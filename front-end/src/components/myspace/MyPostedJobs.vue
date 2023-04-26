<template>
  <el-container style="height:100% width:100%">
      <el-aside width="20%">
        <div class="search-bar">
          <el-input v-model="search" placeholder="搜索职位" @input="searchJobs" :prefix-icon="Search"/>
        </div>
        <el-list class="job-list">
          <el-list-item
            v-for="job in filteredJobs"
            :key="job.jobId"
            @click="selectJob(job.jobId)"
          >
          <el-card shadow="hover" class="mr-3 mt-1">
            <el-container>
              <el-aside style="padding: 0px;background-color:white" width="30%">
                <div class="company-logo">{{ job.companyName.slice(0, 1) }}</div>          
              </el-aside>
              <el-main style="padding: 0px;">
                <div class="job-info">
                  <div>{{ job.title }}</div>
                  <div>{{ job.applicationDate }}</div>
                </div>
              </el-main>
            </el-container>
          </el-card>
          </el-list-item>
        </el-list>
      </el-aside>
      <el-main>
        <!-- <h2>{{ selectedJob.title }}</h2>
        <p>职位：{{ selectedJob.position }}</p>
        <p>地点：{{ selectedJob.location }}</p>
        <p>薪资：{{ selectedJob.salary }}</p>
        <p>日期：{{ selectedJob.applicationDate }}</p>
        <p>公司名称：{{ selectedJob.companyName }}</p> -->
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
        </el-card>
      </el-main>
      <el-aside width="10%">
        <div class="action-buttons">
          <el-button class="ml-2 mt-2" style="width: 75%;" type="danger" @click="revokeApplication">撤销投递</el-button>
          <el-button class="ml-2 mt-2" style="width: 75%;" type="primary" @click="$router.push('/chat')">联系</el-button>
        </div>
      </el-aside>
  </el-container>
</template>

<script>
import axios from "axios";
import { Search } from '@element-plus/icons-vue'
import { reactive, toRefs, watch, ref } from 'vue'

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
      userId: 1,
      items,
      Search,
      search: "",
      jobText: "工作内容\n1、独立完成相关短视频的拍摄和后期剪辑工作；包含但不限于素材、视频剪辑、特效制作、添加片头、片尾和字幕等；2、理解项目需求，进行脚本的规划与制定；\n3、协调与沟通视频制作过程中的各个环节，完成制作全过程，保证成片质量；\n4、熟悉直播平台玩法和制作，对B站、小红书等视频平台的热点内容敏感，分析跑量视频特征，快速同款及精进；\n任职资格：\n1、大专以上学历，影视后期、广告编导、视觉设计相关专业毕业，一年以上相关经验；\n2、熟悉并热爱视频类广告创作，脑洞大，具有良好的审美和节奏感，有良好的内容热点嗅觉；\n3、熟练使用AE、PR、PS、edius等后期软件；\n4、熟练使用各种摄像和照片拍摄设备；\n5、乐观向上，有良好的职业素养，具有较强的团队协作精神、沟通能力和责任心。",
      companyObj: {
        name: '平多多',
        description: '拼多多于2018年7月26日正式登陆纳斯达克全球精选板块，股票代码：PDD拼多多是中国新电商模式的开创者，目前拥有超过3.44亿的活跃买家和超过170万活跃商家。\n在以人为先的理念下，拼多多融合物质消费与精神消费，用户可以通过拼单，与好友分享买到便宜好货的快乐.',
        address: '上海长宁区金虹桥商业广场金虹桥国际中心',
        homePageUrl: 'https://www.pinduoduo.com/'
      },
      JobList: [
        {
          jobId: 1,
          title: "软件工程师",
          position: "前端",
          location: "上海",
          salary: "15K-25K",
          applicationDate: "2023-04-01",
          companyName: "上海某科技有限公司",
        },
        {
          jobId: 2,
          title: "UI设计师",
          position: "设计",
          location: "北京",
          salary: "12K-20K",
          applicationDate: "2023-04-10",
          companyName: "北京某科技有限公司",
        },
      ],
      filteredJobs: [],
      selectedJob: {},
    };
  },
  methods: {
    async getJobList() {
      // try {
      //   const response = await axios.get("https://your-api-endpoint.com/jobs", {
      //     params: {
      //       userId: this.userId,
      //     },
      //   });

      //   if (response.data.code === 200) {
      //     this.JobList = response.data.data;
      //     this.filteredJobs = this.JobList;
      //   } else {
      //     alert(response.data.msg);
      //   }
      // } catch (error) {
      //   console.error("Failed to fetch job list:", error);
      // }

      this.filteredJobs = this.JobList;
    },
    async getJob() {
      // try {
      //   const response = await axios.get("https://your-api-endpoint.com/jobs", {
      //     params: {
      //       jobId: this.selectJobId,
      //     },
      //   });

      //   if (response.data.code === 200) {
      //     this.JobList = response.data.data;
      //     this.filteredJobs = this.JobList;
      //   } else {
      //     alert(response.data.msg);
      //   }
      // } catch (error) {
      //   console.error("Failed to fetch job list:", error);
      // }
    },
    selectJob(jobId) {
      this.selectedJob = this.JobList.find((job) => job.jobId === jobId);
    },
    async revokeApplication() {
      try {
        // 撤销投递API调用
        // await axios.post("https://your-api-endpoint.com/revoke_application", {
        //   userId: this.userId,
        //   jobId
        //   jobId: this.selectedJob.jobId,
        // });
        console.log("撤销投递成功");
        this.getJobList();
        this.selectedJob = {};
      } catch (error) {
        console.error("Failed to revoke application:", error);
      }
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
      return this.jobText.replace(/\n/g, "<br>");
    },
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

.el-list {
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>