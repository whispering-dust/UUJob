<template>
  <div class="bk">

    <div class="card-bk bg-tertiary text-white px-4 px-lg-5 py-5 rounded-0 border-0 mb-0">
    </div>
    <div class="main-area container">
      <div class="container job">
        <div class="row">
          <div class="col-10">
            <el-card class="box-card p-0 card">
              <div class="card-header p-0 mb-2" style="">
                <span style="font-size: larger;"><strong>职位描述</strong></span>
                <el-button style=" float: right;border: 0px;"
                  @click="reportDialogTableVisible = !reportDialogTableVisible">
                  举报<el-icon>
                    <WarnTriangleFilled />
                  </el-icon>
                </el-button>

              </div>
              <div class="row mb-3">
                <el-tag v-for="item in items" :key="item.label" :type="item.type" class="mx-1" effect="dark" round>
                  {{ item.label }}
                </el-tag>

              </div>

              <div class="row text-area p-3" v-html="formattedText">
              </div>

            </el-card>

          </div>

          <div class="col-2">
            <div class="button-area">
              <el-button v-if="store.state.profileId != null" color="#45454A" :key="1"
                style=" color:aliceblue; border:0px;width:125px;"
                @click="this.$router.push('/job/' + this.jobId + '/apply')">
                投递简历
                <el-icon class="el-icon--right">
                  <Upload />
                </el-icon>
              </el-button>
            </div>

            <div class="button-area" :key="2">
              <el-button color="#45454A" style=" color:aliceblue; border:0px;width:125px;" @click="addStar"
                v-if="starId === -1">
                加入收藏
                <el-icon class="el-icon--right">
                  <Star />
                </el-icon>
              </el-button>
              <el-button color="#45454A" style=" color:aliceblue; border:0px;width:125px;" @click="deleteStar"
                v-if="starId != -1">
                取消收藏<el-icon class="el-icon--right">
                  <Star />
                </el-icon>
              </el-button>
            </div>

            <div class="button-area">
              <el-button @click="newChat" color="#45454A" style=" color:aliceblue; border:0px;width:125px;">
                立即沟通<el-icon class="el-icon--right">
                  <ChatDotRound />
                </el-icon>
              </el-button>
            </div>

            <div class="button-area">
              <el-button color="#45454A" style="color:aliceblue; border:0px;width:125px;">
                分享<el-icon class="el-icon--right">
                  <Share />
                </el-icon>
              </el-button>
            </div>
          </div>

        </div>

        <div class="container p-0 mt-3">
          <div class="row">
            <div class="col-8">
              <el-card class="box-card p-0 ">
                <div class="card-header p-0" style="">
                  <span style="font-size: larger;"><strong>{{ companyObj.name }}</strong></span>
                </div>


                <div class="row text-area p-3" style="display: flex; flex-direction: column;">
                  <div class="row p-3" style="display: flex; flex-direction: column;">
                    <div class="pb-2"><strong>公司介绍</strong></div>
                    <div v-html="formattedCompanyText"></div>
                  </div>

                  <div class="row p-3" style="display: flex; flex-direction: column;">
                    <div class="pb-2"><strong>公司地址</strong></div>
                    <div>
                      {{ companyObj.address }}
                    </div>
                  </div>

                  <div class="row p-3" style="display: flex; flex-direction: column;">
                    <span class="pb-2"><strong>公司主页</strong></span>
                    <a href="#" @click="redirectToNewPage">{{ companyObj.homePageUrl }}</a>
                  </div>
                </div>

              </el-card>

            </div>

            <div class="col-4">
              <el-card class="box-card p-0 ">
                <div class="card-header p-0 mb-2">
                  <span style="font-size: larger;"><strong>相似职位</strong></span>
                </div>


                <div class="row text-area p-1">
                  <el-table :data="recommendJobList" style="width: 100%">
                    <el-table-column prop="jobName" label="岗位名称"></el-table-column>
                    <el-table-column prop="companyName" label="公司名称"></el-table-column>
                    <el-table-column prop="salary" label="薪资范围"></el-table-column>
                    <el-table-column prop="tags" label="标签">
                      <template v-slot="{ row }">
                        <el-tag v-for="tag in row.tags" :key="tag">{{ tag }}</el-tag>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>

              </el-card>
            </div>

          </div>
        </div>

      </div>
    </div>


    <el-dialog v-model="reportDialogTableVisible" title="举报" width="600px">
      <el-input v-model="reportContent" :rows="4" type="textarea" placeholder="Please input" class="mb-3" />
      <el-button @click="submitReport" type="primary">提交</el-button>
    </el-dialog>

  </div>
</template>

<script>
import { useRoute, useRouter } from 'vue-router'
import { reactive, toRefs, watch, ref } from 'vue'
import { Share, ChatDotRound, Star, Upload, WarnTriangleFilled } from "@element-plus/icons-vue"
import { Tag } from 'element-plus'
import axios from "axios";
import { useStore } from "vuex";
import { ElMessage } from 'element-plus'
export default {
  data() {
    const store = useStore()

    const items = ref([
      { type: '', label: 'Tag 1' },
      { type: 'success', label: 'Tag 2' },
      { type: 'info', label: 'Tag 3' },
      { type: 'danger', label: 'Tag 4' },
      { type: 'warning', label: 'Tag 5' },
    ])
    const successStar = () => {
      ElMessage({
        message: '收藏成功！',
        type: 'success',
      })
    }

    const failStar = () => {
      ElMessage.error('收藏失败')
    }

    const successDeleteStar = () => {
      ElMessage({
        message: '取消收藏成功！',
        type: 'success',
      })
    }

    const failDeleteStar = () => {
      ElMessage.error('取消收藏失败')
    }
    let starId = ref(-1)
    return {
      starId,
      router: useRouter(),
      reportContent: "",
      reportDialogTableVisible: false,
      jobId: useRoute().params.jobId,
      publisherId: null,
      store,
      items,
      jobText: "工作内容\n1、独立完成相关短视频的拍摄和后期剪辑工作；包含但不限于素材、视频剪辑、特效制作、添加片头、片尾和字幕等；2、理解项目需求，进行脚本的规划与制定；\n3、协调与沟通视频制作过程中的各个环节，完成制作全过程，保证成片质量；\n4、熟悉直播平台玩法和制作，对B站、小红书等视频平台的热点内容敏感，分析跑量视频特征，快速同款及精进；\n任职资格：\n1、大专以上学历，影视后期、广告编导、视觉设计相关专业毕业，一年以上相关经验；\n2、熟悉并热爱视频类广告创作，脑洞大，具有良好的审美和节奏感，有良好的内容热点嗅觉；\n3、熟练使用AE、PR、PS、edius等后期软件；\n4、熟练使用各种摄像和照片拍摄设备；\n5、乐观向上，有良好的职业素养，具有较强的团队协作精神、沟通能力和责任心。",
      companyObj: {
        name: '平多多',
        description: '信息缺失',
        address: '上海长宁区金虹桥商业广场金虹桥国际中心',
        homePageUrl: 'https://www.pinduoduo.com/'
      },
      recommendJobList: [
        {
          jobName: 'jobName',
          companyName: 'companyName',
          salary: 'salary',
          tags: ['tag1', 'tag2'],
        },
        {
          jobName: 'jobName',
          companyName: 'companyName',
          salary: 'salary',
          tags: ['tag1', 'tag2'],
        },
        {
          jobName: 'jobName',
          companyName: 'companyName',
          salary: 'salary',
          tags: ['tag1', 'tag2'],
        },
        {
          jobName: 'jobName',
          companyName: 'companyName',
          salary: 'salary',
          tags: ['tag1', 'tag2'],
        },
      ],
      successStar,
      failStar,
      successDeleteStar,
      failDeleteStar
    }
  },

  methods: {
    async newChat() {
      try {
        // Replace the URL with your API endpoint to fetch chats
        const response = await axios.post("http://localhost:9090/conversations", {
          senderId: this.store.state.userId,
          receiverId: this.publisherId
        });

        if (response.data.code === 200) {
          this.router.replace('')
          this.router.replace("/myspace/chatList/chatRoom/" + response.data.data.id + "/" + this.publisherId)
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch chat list:", error);
      }
    },
    async submitReport() {
      try {
        // Replace the URL with your API endpoint to fetch chats
        const response = await axios.post("http://localhost:9090/reports/jobs", {
          content: this.reportContent,
          reporterId: this.store.state.userId,
          targetId: this.publisherId
        });

        if (response.data.code === 200) {
          this.$message.success('举办成功');
          this.reportDialogTableVisible = !this.reportDialogTableVisible;
        } else {
          alert(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch chat list:", error);
      }
    },
    redirectToNewPage() {
      // window.location.replace(this.companyObj.homePageUrl);
      window.open(this.companyObj.homePageUrl, '_blank').location.replace(this.companyObj.homePageUrl);
    },
    async getJob() {
      let that = this;
      axios({
        method: "get",
        url: "http://localhost:9090/jobs",
        params: {
          id: that.jobId
        }
      }).then(function (response) {
        if (response.data.code == 200) {
          console.log('获取工作信息', response.data.data);
          that.jobText = response.data.data.description
          that.publisherId = response.data.data.publisherId

        } else {
          alert("error");
        }
      })

    },
    async getCompany() {
      let that = this;
      axios({
        method: "get",
        url: "http://localhost:9090/jobs",
        params: {
          id: that.jobId
        }
      }).then(function (response) {
        if (response.data.code == 200) {

          that.companyObj.name = response.data.data.companyName;
          that.companyObj.address = response.data.data.location;
          console.log('获取公司信息', that.companyObj);
        } else {
          alert("error");
        }
      })
    },
    addStar() {
      let that = this;
      console.log("添加收藏！", 'job:', that.jobId, 'user:', that.store.state.userId)
      axios({
        method: "post",
        url: "http://localhost:9090/stars/jobs",
        data: {
          targetId: that.jobId,
          userId: that.store.state.userId
        }
      }).then(function (response) {
        if (response.data.code == 200) {
          console.log(response.data.data);
          that.successStar();
          that.isStar = true;
        } else {
          alert("error");
          that.failStar();
        }
        window.location.reload();
      })
    },
    getStarStatus() {
      let that = this;
      axios({
        method: "post",
        url: "http://localhost:9090/stars",
        data: {
          userId: that.store.state.userId,
          targetId: that.jobId,
          starType: 0
        },
        // headers: {
        //   "Content-Type": "application/json"
        // },
      }).then(function (response) {
        if (response.data.code == 200) {
          var data = response.data.data
          that.starId = data;
          console.log('收藏Id', that.starId)
        } else {
          alert("error");
        }

      })
    },
    deleteStar() {
      let that = this;
      axios({
        method: "delete",
        url: "http://localhost:9090/stars",
        params: {
          id: that.starId,
        }
      }).then(function (response) {
        if (response.data.code == 200) {

          that.successDeleteStar();
          that.isStar = false;
        } else {
          alert("error");
          that.failDeleteStar();
        }
        window.location.reload();
      })
    }

  },

  mounted() {
    this.getJob()
    this.getCompany()
    this.getStarStatus()
  },

  computed: {
    formattedText() {
      // 将换行符替换为 <br> 标签
      return this.jobText.replace(/\n/g, "<br>");
    },

    formattedCompanyText() {
      return this.companyObj.description.replace(/\n/g, "<br>");
    },
  }
}


</script>

<style scoped>
.button-area {
  margin: 10px;
}

.bk {
  min-height: 1080px;
  background-color: rgb(223, 229, 235);
}

.card-bk {
  height: 300px;
}



.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 100%;
  margin-bottom: 10px;
}

.main-area {
  position: relative;
}

.job {
  position: absolute;
  z-index: 1;
  top: -250px;
  ;
}
</style>