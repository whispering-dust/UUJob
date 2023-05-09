<template>
  <div class="box-card-up">
    <h4 class="heading h3 text-white pt-3">求职招聘栏</h4>
    <el-menu :default-active="'0'" class="el-menu rounded" mode="horizontal" style="margin-bottom: 10px;"
      @select="handleSelect">
      <el-menu-item index="0">全部</el-menu-item>
      <el-menu-item index="1">岗位</el-menu-item>
      <el-menu-item index="2">企业</el-menu-item>
      <el-menu-item index="3">喜好</el-menu-item>
    </el-menu>

    <div v-for="recruitObj in paginatedRecruits()">
      <el-card class="box-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <span style="font-size:larger">{{ recruitObj.title }}</span>
            <el-button class="button" color="black" @click="enterJob(recruitObj.id)"> 详情</el-button>
          </div>
        </template>
        <div class="row">
          <div class="col-10">
            <div class="position">
              {{ recruitObj.position }}
            </div>
          </div>
          <div class="col-2">
            <div class="salary">
              {{ recruitObj.salary }}
            </div>
          </div>
        </div>
        <div class="description">
          {{ recruitObj.description }}
        </div>

      </el-card>
    </div>

    <div >
      <el-pagination  @current-change="handlePageChange" :current-page="currentPage" :page-size="pageSize"
        :total="totalRecruitObjs" layout="prev, pager, next" background>
      </el-pagination>
    </div>
    

  </div>

  <!-- <el-dialog v-model="dialogApplyVisible" title="请填写简历信息">
    <Apply :tableId="select_id"></Apply>
  </el-dialog> -->
</template>
  
<script>
import { ref } from 'vue';
import axios from "axios";
import Apply from "@/components/home/Apply.vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  components: {
    Apply,
  },
  setup() {
    const router = useRouter();

    return {
      router
    }

  },
  data() {

    return {
      router: useRouter(),
      select_id: "",
      dialogApplyVisible: false,
      pagedRecruitObjs: [], // 当前页展示的求职帖子的数组
      currentPage: 1, // 当前页码
      pageSize: 4, // 每页显示的数量
      totalRecruitObjs: 0, // 总的求职帖子数量

      recruitObjs: [
        {
          id: "1",
          title: "蔚蓝求职",
          position: "算法工程师",
          salary: "1.5w-2w",
          description: "快来家人们",
        },
        {
          id: "2",
          title: "蔚蓝求职",
          position: "算法工程师",
          salary: "1.5w-2w",
          description: "快来家人们",
        },
        {
          id: "3",
          title: "蔚蓝求职",
          position: "算法工程师",
          salary: "1.5w-2w",
          description: "快来家人们",
        },
        {
          id: "4",
          title: "蔚蓝求职",
          position: "算法工程师",
          salary: "1.5w-2w",
          description: "快来家人们",
        },
        {
          id: "5",
          title: "蔚蓝求职",
          position: "算法工程师",
          salary: "1.5w-2w",
          description: "快来家人们",
        },
        {
          id: "1",
          title: "蔚蓝求职",
          position: "算法工程师",
          salary: "1.5w-2w",
          description: "快来家人们",
        },

      ]
    }
  },
  methods: {
    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    },

    paginatedRecruits() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.recruitObjs.slice(startIndex, endIndex);
    },

    enterJob(id) {
      this.router.push("../job/" + id)

    },

    handleChange(val) {
      console.log(val)
    },

    async getRecruitList() {
      let that = this;
      axios({
        method: "get",
        url: "http://localhost:9090/jobs/basis",
      }).then(function (response) {
        if (response.data.code == 200) {
          that.recruitObjs = [];
          response.data.data.forEach(element => {
            that.recruitObjs.push(
              {
                id: element.id,
                title: element.title,
                position: element.position,
                salary: element.salary,
                description: element.description,
              }
            )
          });
        } else {
          alert("error");
        }
      })

    },

    handlePageChange(currentPage) {
      this.currentPage = currentPage;
    },

    paginatedRecruits() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.recruitObjs.slice(startIndex, endIndex);
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  mounted() {
    this.getRecruitList();

    this.paginatedRecruits();

    this.totalRecruitObjs = this.recruitObjs.length;


  },
}

</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0px;
  border-bottom: 0px;
  padding: 0px;
  width: 100%;
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

.box-card-up {
  width: 100%;
  height: 100%;
  overflow: hidden;
  min-height: 800px;
}

.position {
  font-size: large;
  margin-bottom: 10px;
}

.salary {
  font-size: medium;
  float: right;
  color: rgb(107, 171, 240);
}

.description {
  font-size: small;
  color: rgb(137, 137, 137);
}
</style>
  