<template>
  <div>
    <el-row>
      <el-col :span="10">
        <el-card class="box-card mr-2" style="height: 800px;">
          <template #header>
            <div>
              <span class="h4">待处理举报数({{ pendingReportsNum }})</span>
            </div>
          </template>

          <el-table :data="pendingReportsData" height="700" style="width: 100%" @row-click="handleRowClick">
            <el-table-column prop="id" label="岗位/帖子id" width="100" />
            <el-table-column prop="type" label="类型">
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <el-tag>{{ scope.row.type }}</el-tag>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="reporterName" label="举报人" />
            <el-table-column prop="targetContent" label="举报理由" width="200" />

          </el-table>
        </el-card>
      </el-col>

      <el-col :span="14">
        <el-card class="box-card ml-2" style="height: 800px;">
          <template #header>
            <div style="display: flex; justify-content:space-between">
              <span class="h4">举报内容详情</span>
              <div>
                <el-button type="primary" round @click="handleReport">处理举报</el-button>
                <el-button type="info" round @click="handleReportNeglect">忽略</el-button>
              </div>
            </div>
          </template>
          <div v-if="selectedPendingReportsData.type == '帖子'">
            <PostCard :targetPostId="selectedPendingReportsData.targetId" :flag=false></PostCard>
          </div>
          <div v-if="selectedPendingReportsData.type == '岗位'">
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
            <div class="row text-area p-3" v-html="formattedText">
            </div>

            <div class="row p-3">
              <el-descriptions class="margin-top" title="信息栏" :column="1" :size="size" border style="width:80%">
                <el-descriptions-item label-class-name="my-label">
                  <template #label>
                    <div class="cell-item">
                      <el-icon>
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
                      <el-icon>
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
                      <el-icon>
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
          <div v-if="selectedPendingReportsData.type == '评论'">
            <div class="comment-item" style="display: flex;">
              <el-avatar class="mr-2" src="avatar.png" :size="50"></el-avatar>
              <div class="comment-right">
                <div style="display: flex; height:20px">
                  <p class="mr-2">{{ comment.userName }}</p>
                  <p style="color: dodgerblue;">{{ comment.date }}</p>
                </div>
                <div>
                  {{ comment.content }}
                </div>
              </div>

            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import PostCard from '@/components/forum/PostCard.vue'
import { reactive, toRefs, watch, ref, computed } from "vue";
export default {
  components: {
    PostCard,
  },
  data() {
    return {
      userName: useStore().state.userName,
      pendingReportsNum: 5,
      pendingReportsData: null,
      //processedReports: 10, 
      selectedPendingReportsData: {},
      selectedJob: {},
      owner: {},
      comment: {},
      size: ref('large')
    };
  },
  methods: {
    async getPendingReports() {
      try {
        // Replace the URL with your API endpoint to fetch chats
        const response = await axios.get("http://localhost:9090/reports/unaudited", {

        });
        if (response.data.code === 200) {
          //console.log(response.data.data);
          this.pendingReportsData = response.data.data
          this.pendingReportsData.forEach(element => {
            //限制内容字数
            element.content = element.content.substring(0, 10)
            switch (element.type) {
              case 0:
                element.type = '岗位'
                break;

              case 1:
                element.type = '帖子'
                break;

              case 2:
                element.type = '评论'
                break;

              default:
                break;
            }
          });
          this.pendingReportsNum = this.pendingReportsData.length
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch chat list:", error);
      }
    },
    handleRowClick(row, column, event) {
      this.selectedPendingReportsData = row;
      if (this.selectedPendingReportsData.type == "岗位") {
        this.getJob()
      }
    },
    async handleReportNeglect() {
      try {
        const response = await axios.put("http://localhost:9090/reports/examinations", {
          id: this.selectedPendingReportsData.id,
          status: 2,
          feekback: '已忽略该举报'
        });

        if (response.data.code === 200) {
          this.$message.success('已忽略该举报')
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch job:", error);
      }
      // 当举报处理完后，刷新举报列表
      await this.getPendingReports();
    },
    async handleReport() {
      console.log(this.selectedPendingReportsData.id);
      // 这里添加处理举报的代码，
      try {
        const response = await axios.put("http://localhost:9090/reports/examinations", {
          id: this.selectedPendingReportsData.id,
          status: 1,
          feekback: '已处理举报'
        });

        if (response.data.code === 200) {
          this.$message.success('已处理该举报')
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch job:", error);
      }
      // 当举报处理完后，刷新举报列表
      await this.getPendingReports();
    },
    async getJob() {
      try {
        const response = await axios.get("http://localhost:9090/jobs", {
          params: {
            id: this.selectedPendingReportsData.targetId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data);
          this.selectedJob = response.data.data
          this.selectedJob.selectedJobText = response.data.data.description
          this.owner.userId = response.data.data.publisherId

          this.getOwner()
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
          this.owner.userName = response.data.data.userName
          this.owner.phone = response.data.data.phone

        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch user:", error);
      }
    },
    async getComment() {
      try {
        const response = await axios.get("http://localhost:9090/comments", {
          params: {
            id: this.selectedPendingReportsData.targetId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data);
          this.comment = response.data.data

        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch user:", error);
      }
    },

  },
  mounted() {
    this.getPendingReports()
  },
  computed: {
    formattedText() {
      // 将换行符替换为 <br> 标签
      return this.selectedJob.selectedJobText.replace(/\n/g, "<br>");
    },

  },
};
</script>
  
<style scoped>  .overview-item {
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
</style>