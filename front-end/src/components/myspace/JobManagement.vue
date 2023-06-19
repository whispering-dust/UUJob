<template>
  <el-container style="height:100% width:100%">
    <el-aside width="20%">
      <el-scrollbar max-height="750px">
        <div class="search-bar">
          <el-input v-model="search" placeholder="搜索职位" @input="searchJobs" prefix-icon="Search" />
        </div>
        <div class="job-list">
          <div v-for="job in filteredJobs" :key="job.id" @click="selectJob(job.jobId)">
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
          </div>
        </div>
      </el-scrollbar>
    </el-aside>

    <!-- 简历列表 -->
    <el-main>
      <el-card v-if="selectedJob.title" class="box-card p-0 " shadow="never"
        :body-style="{ padding: '0px', height: '100%' }">
        <el-container style="height: 100%;">
          <el-aside width="200px">
            <div class="profile-list">
              <el-tabs v-model="activeName" @tab-change="handleChange" class="demo-tabs m-3" :stretch=true>
                <el-tab-pane label="未审核" name="first">
                  <div class="el-list">
                    <div v-for="profile in unreviewedProfile" :key="profile.profileId"
                      @click="selectProfile(profile.profileId)">
                      <el-card shadow="hover" class="mt-2 pt-2 pr-2 pl-2 pb-0"
                        :body-style="{ padding: '0px', height: '100%' }">
                        <div class="profile-info">
                          <el-container>
                            <el-aside class="mr-2" style="padding: 0px;background-color:white" width="30%">
                              <div class="company-logo">{{ profile.name.slice(0, 1) }}</div>
                            </el-aside>
                            <el-main style="padding: 0px;">
                              <div class="job-info">
                                <div>{{ profile.name }}</div>
                                <p class="" style="color: rgb(46, 121, 242);">未审核</p>
                              </div>
                            </el-main>
                          </el-container>
                        </div>
                      </el-card>
                    </div>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="已通过" name="second">
                  <div class="el-list">
                    <div v-for="profile in reviewedProfile" :key="profile.profileId"
                      @click="selectProfile(profile.profileId)">
                      <el-card shadow="hover" class="mt-2 pt-2 pr-2 pl-2 pb-0"
                        :body-style="{ padding: '0px', height: '100%' }">
                        <div class="profile-info">
                          <el-container>
                            <el-aside class="mr-2" style="padding: 0px;background-color:white" width="30%">
                              <div class="company-logo">{{ profile.name.slice(0, 1) }}</div>
                            </el-aside>
                            <el-main style="padding: 0px;">
                              <div class="job-info">
                                <div>{{ profile.name }}</div>
                                <p class="" style="color: rgb(46, 121, 242);">已通过</p>
                              </div>
                            </el-main>
                          </el-container>
                        </div>
                      </el-card>
                    </div>
                  </div>
                </el-tab-pane>
              </el-tabs>


            </div>
          </el-aside>
          <!-- 简历 -->
          <el-main>
            <div class="container" v-if="selectedProfile && selectedProfile.profileId">
              <div class="row container">
                <h4>用户简历</h4>
              </div>
              <div class="row">
                <div class="col-3">
                  <div class="block mt-3">
                    <el-avatar shape="square" style="width:100%;min-height:125px;" :src="userAvator" />
                  </div>
                </div>
                <div class="col-9 mt-2">
                  <el-descriptions title="" :column="2" border>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <user />
                          </el-icon>
                          姓名
                        </div>
                      </template>
                      {{ selectedProfile.name }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <user />
                          </el-icon>
                          性别
                        </div>
                      </template>
                      {{ selectedProfile.sex }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <iphone />
                          </el-icon>
                          联系电话
                        </div>
                      </template>
                      {{ selectedProfile.phone }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <location />
                          </el-icon>
                          电子邮件
                        </div>
                      </template>
                      {{ selectedProfile.email }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <tickets />
                          </el-icon>
                          学历
                        </div>
                      </template>
                      {{ selectedProfile.education }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <office-building />
                          </el-icon>
                          所在单位
                        </div>
                      </template>
                      {{ selectedProfile.college }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <office-building />
                          </el-icon>
                          专业
                        </div>
                      </template>
                      {{ selectedProfile.major }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          <el-icon>
                            <office-building />
                          </el-icon>
                          意向地点
                        </div>
                      </template>
                      {{ selectedProfile.expectedLocation }}
                    </el-descriptions-item>

                  </el-descriptions>
                </div>
              </div>


              <el-descriptions class="mt-3" direction="vertical" :column="1" border>
                <el-descriptions-item>
                  <template #label>
                    <div class="cell-item">
                      自我介绍
                    </div>
                  </template>
                  <div style="min-height:100px">
                    {{ selectedProfile.personalDescription }}
                  </div>
                </el-descriptions-item>
                <el-descriptions-item>
                  <template #label>
                    <div class="cell-item">
                      荣誉奖项
                    </div>
                  </template>
                  <div style="min-height:100px">
                    {{ selectedProfile.reward }}
                  </div>
                </el-descriptions-item>
              </el-descriptions>
            </div>

          </el-main>
        </el-container>
      </el-card>
    </el-main>
    <el-aside width="10%">
      <div class="action-buttons">
        <el-button class="ml-2 mt-2" style="width: 75%;" type="primary" @click="passProfile">通过</el-button>
        <el-button class="ml-2 mt-2" style="width: 75%;" type="info">忽略</el-button>
        <br><br>
        <el-button class="ml-2 mt-2" style="width: 75%;" @click="back">上一个</el-button>
        <el-button class="ml-2 mt-2" style="width: 75%;" @click="next">下一个</el-button>
      </div>
    </el-aside>
  </el-container>
</template>
  
<script>
import axios from "axios";
import { Search } from '@element-plus/icons-vue'
import { reactive, toRefs, watch, ref, computed } from 'vue'
import { useStore } from "vuex";

export default {
  data() {
    // const items = ref([
    //   { type: '', label: 'Tag 1' },
    //   { type: 'success', label: 'Tag 2' },
    //   { type: 'info', label: 'Tag 3' },
    //   { type: 'danger', label: 'Tag 4' },
    //   { type: 'warning', label: 'Tag 5' },
    // ])

    return {
      activeName: '',
      userId: useStore().state.userId,
      // items,
      Search,
      search: "",
      JobList: [],
      ProfileList: [],
      unreviewedProfile: [],
      reviewedProfile: [],
      selectedProfile: {},
      filteredJobs: [],
      selectedJob: {},
    };
  },
  methods: {
    handleChange() {
      //this.selectJob(this.selectedJob.jobId)
      console.log(this.activeName);
    },
    async getProfileList(jobId) {
      try {
        // Replace the URL with your API endpoint to fetch profiles
        const response = await axios.get("http://localhost:9090/jobs/applications", {
          params: {
            jobId: this.selectedJob.jobId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data.subList);
          const profiles = [];
          response.data.data.subList.forEach(profile => {
            profile.profileId = profile.id
            profile.status = 0
            profiles.push(profile)
          });
          response.data.data.passList.forEach(profile => {
            profile.profileId = profile.id
            profile.status = 1
            profiles.push(profile)
          });
          console.log(profiles);
          this.ProfileList = profiles;;

        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch profile list:", error);
      }
    },
    async getJobList() {
      try {
        const response = await axios.get("http://localhost:9090/users/jobs", {
          params: {
            userId: this.userId,
          },
        });

        if (response.data.code === 200) {
          const jobs = response.data.data;
          jobs.forEach(job => {
            job.jobId = job.id
            const date = new Date(job.date);
            job.date = date.getFullYear() + "-" + (date.getMonth() + 1).toString().padStart(2, '0') + "-" + date.getDate().toString().padStart(2, '0');
          });
          this.JobList = jobs;
          this.filteredJobs = this.JobList;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch job list:", error);
      }

      this.filteredJobs = this.JobList;
    },
    selectProfile(profileId) {
      this.selectedProfile = this.ProfileList.find((profile) => profile.profileId === profileId);
      console.log(this.selectedProfile);
    },
    async selectJob(jobId) {
      this.selectedJob = this.JobList.find((job) => job.jobId === jobId);
      await this.getProfileList(jobId);

      this.reviewedProfile = this.ProfileList.filter((profile) => profile.status !== 0);;
      this.unreviewedProfile = this.ProfileList.filter((profile) => profile.status === 0);;

      this.selectedProfile = {};
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

    //这玩意有问题
    async passProfile() {
      console.log(this.selectedJob.jobId);
      console.log("this.selectedProfile.id:"+this.selectedProfile.id);
      try {
        const response = await axios.put("http://localhost:9090/jobs/applications", {
          data: {
            id: this.selectedProfile.id,
            status: 1
          },
        });

        if (response.data.code === 200) {
          this.$message.success('已通过该简历')
        } else {
          this.$message.error(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch job list:", error);
      }
    },

    async neglectProfile() {
      console.log(this.selectedJob.jobId);
      console.log(this.selectedProfile.profileId);
      try {
        const response = await axios.put("http://localhost:9090/jobs/applications", {
          data: {
            id: this.selectedProfile.id,
            status: 2
          },
        });

        if (response.data.code === 200) {
          this.$message.success('已忽略')
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch job list:", error);
      }
    },
    back() {
      console.log('back');

    },
    next() {
      console.log('next');
    }
  },
  mounted() {
    this.getJobList();
  },
};
</script>
  
<style scoped>
.action-buttons {
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

.box-card {
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

.box-card {
  overflow-y: auto;
}

.my-label {
  width: 30%;
}

.profile-list {
  height: calc(100% - 70px);
  overflow-y: auto;
}

.profile-info {
  display: flex;
  flex-direction: column;
}

.demo-tabs>.el-tabs__content {
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>