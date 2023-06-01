<template>
  <el-row>
    <el-col :span="10">
      <el-card class="box-card mr-2" style="height: 800px;">
        <template #header>
          <div>
            <span class="h4">待审核数({{ pendingAuditsNum }})</span>
          </div>
        </template>

        <el-table :data="pendingAuditsData" height="700" style="width: 100%" @row-click="handleRowClick">
          <el-table-column prop="targetId" label="ID" width="100" />
          <el-table-column prop="type" label="类型">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-tag>{{ scope.row.type }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="userName" label="发起人" />
          <el-table-column prop="title" label="标题" width="200" />
        </el-table>
      </el-card>
    </el-col>

    <el-col :span="14">
      <el-card class="box-card ml-2" style="height: 800px;">
        <template #header>
          <div style="display: flex; justify-content:space-between">
            <span class="h4">审核内容详情</span>
            <el-button type="primary" round @click="handleAudit">审核通过</el-button>
          </div>
        </template>

        <!-- 帖子 -->
        <div v-if="selectedPendingAuditsData.type == '帖子'">
          <div class="post-header">
            <div style="display:flex;align-items: center;">
              <el-avatar src="avatar.png" size="50"></el-avatar>
              <div class="user-info">
                <div class="user-name mr-2 h5">{{ postUser.userName }}</div>
                <div class="user-degree">{{ postUser.role }}</div>
                <el-tag class="ml-2" type="success">{{ seletcedPost.postType }}</el-tag>
              </div>
            </div>
            <!-- <div class="post-time">发布时间：{{ seletcedPost.date }}</div> -->
          </div>
          <div class="post-info container">
            <div class="h4">{{ seletcedPost.title }}</div>
            <el-divider />
            <div class="post-content" v-html="formattedPostText"></div>
          </div>
        </div>

        <!-- 岗位 -->
        <div v-if="selectedPendingAuditsData.type == '岗位'">
          <div class="card-header p-0 mb-3" style="">
            <span style="font-size: larger;"><strong>{{ selectedJob.title }}</strong></span>
            <p style="color:deepskyblue">
              薪资：{{ selectedJob.salary }}
            </p>
          </div>
          <div class="row mb-3">
            <el-tag v-for="item in items" :key="item.label" :type="item.type" class="mx-1" effect="dark" round>
              {{ item.label }}
            </el-tag>
          </div>
          <div class="row text-area p-3" v-html="formattedText"></div>

          <div class="row p-3">
            <el-descriptions class="margin-top" title="信息栏" :column="1" :size="size" border style="width:80%">
              <el-descriptions-item label-class-name="my-label">
                <template #label>
                  <div class="cell-item">
                    <el-icon :style="iconStyle">
                      <user />
                    </el-icon>
                    发布用户
                  </div>
                </template>
                {{ owner.userName }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon :style="iconStyle">
                      <iphone />
                    </el-icon>
                    联系方式
                  </div>
                </template>
                {{ owner.phone }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon :style="iconStyle">
                      <location />
                    </el-icon>
                    地点
                  </div>
                </template>
                {{ selectedJob.location }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>
  
<script>
import axios from "axios";
import { useStore } from "vuex";

export default {
  data() {
    return {
      userName: useStore().state.userName,
      pendingAuditsNum: 5,
      pendingAuditsData: null,
      selectedPendingAuditsData: {},
      selectedJob: {},
      seletcedPost: {},
      owner: {},
      comment: {},
      postUser: {
        userId: null,
        userName: '用户1',
        role: '招聘者',
      },
      size: ref('large')
    };
  },
  methods: {
    async getPendingAudits() {
      try {
        const response = await axios.get("http://localhost:9090/jobs/unaudited", {});

        if (response.data.code === 200) {
          this.pendingAuditsData = response.data.data;
          this.pendingAuditsData.forEach((element) => {
            element.type = '岗位'
            element.targetId = element.id
          });
          this.pendingAuditsNum = this.pendingAuditsData.length;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch audit list:", error);
      }

      try {
        const response = await axios.get("http://localhost:9090/posts/unaudited", {});
        if (response.data.code === 200) {
          response.data.data.forEach((element) => {
            element.targetId = element.id;
            switch (element.type) {
              case 1:
                element.postType = '技术交流'
                break;
              case 2:
                element.postType = '求职经验'
                break;
              case 3:
                element.postType = '行业动态'
                break;
              default:
                element.postType = '其他'
                break;
            }
            element.type = '帖子';
            this.pendingAuditsData.push(element);
          });
          this.pendingAuditsNum = this.pendingAuditsData.length;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch audit list:", error);
      }
    },
    handleRowClick(row, column, event) {
      this.selectedPendingAuditsData = row;
      if (this.selectedPendingAuditsData.type == "岗位") {
        this.getJob();
      }
      if (this.selectedPendingAuditsData.type == "帖子") {
        this.getPost();
        console.log(this.seletcedPost);
      }
    },
    getPost() {
      this.seletcedPost = this.selectedPendingAuditsData;
      this.postUser.userId = this.seletcedPost.publisherId;
      this.getPostUser();
    },
    async getPostUser() {
      try {
        const response = await axios.get("http://localhost:9090/users", {
          params: {
            id: this.postUser.userId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data);
          this.postUser.userName = response.data.data.userName
          if (response.data.data.role == '1') {
            this.postUser.role = '招聘者'
          } else {
            this.postUser.role = '求职者'
          }

        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch user:", error);
      }
    },
    async handleAudit() {

      try {
        const response = null;
        if (this.selectedPendingAuditsData.type == '帖子') {
          response = await axios.put("http://localhost:9090/posts/examinations", {
            id: this.selectedPendingAuditsData.id,
            status: 1,
          });
        }

        if (this.selectedPendingAuditsData.type == '岗位') {
          response = await axios.put("http://localhost:9090/jobs/examinations", {
            id: this.selectedPendingAuditsData.id,
            status: 1,
          });
        }

        if (response.data.code === 200) {
          this.$message.success("已审核");
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to handle audit:", error);
      }

      await this.getPendingAudits();
    },
    async getJob() {
      try {
        const response = await axios.get("http://localhost:9090/jobs", {
          params: {
            id: this.selectedPendingAuditsData.targetId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data);
          this.selectedJob = response.data.data;
          this.selectedJob.selectedJobText = response.data.data.description;
          this.owner.userId = response.data.data.publisherId;

          this.getOwner();
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch job:", error);
      }
    },
    async getOwner() {
      try {
        const response = await axios.get("http://localhost:9090/users", {
          params: {
            id: this.owner.userId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data);
          this.owner.userName = response.data.data.userName;
          this.owner.phone = response.data.data.phone;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch user:", error);
      }
    },

  },
  mounted() {
    this.getPendingAudits();
  },
  computed: {
    formattedText() {
      return this.selectedJob.selectedJobText.replace(/\n/g, "<br>");
    },
    formattedPostText() {
      return this.seletcedPost.content.replace(/\n/g, "<br>");
    },
  },
};
</script>
  
<style scoped>
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

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.postcard {
  margin-bottom: 16px;
}

.post-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 12px;
}

.user-info {
  display: flex;
  margin-left: 12px;
}

.post-info {
  margin-bottom: 12px;
}

.post-actions {
  display: flex;
  justify-content: flex-start;
}

.post-content {
  min-height: 100px;
}
</style>
  