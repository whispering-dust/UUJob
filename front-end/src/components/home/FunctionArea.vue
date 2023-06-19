<template>
  <el-card class="box-card2">
    <template #header>
      <div class="card-header">
        <span style="font-size: large;">🛠️功能面板</span>
      </div>
    </template>

    <el-tooltip class="box-item" effect="dark" content="发布岗位" placement="top">
      <el-button v-if="store.state.role == 1" @click="postJob" icon="DocumentAdd" circle size="large">
      </el-button>
    </el-tooltip>

  </el-card>

  <el-card class="box-card2">
    <template #header>
      <div class="card-header">
        <span style="font-size: large;">✨岗位推荐✨</span>
      </div>
    </template>
    <el-table :data="recommendData" stripe style="width: 100%" @row-click="redirectToJob">
      <el-table-column prop="title" label="标题" width="120" />
      <el-table-column prop="location" label="地点" width="70" />
      <el-table-column prop="salary" label="薪资" />
      <el-table-column prop="companyName" label="公司" />
      
    </el-table>
    

  </el-card>


  <el-dialog top="0vh" v-model="dialogFormVisible" title="请填写表单信息" draggable :lock-scroll=false>
    <el-scrollbar>
      <RecruitPost @cancel="dialogFormVisible = false"></RecruitPost>
    </el-scrollbar>
  </el-dialog>
</template>
  
<script>
import { reactive, ref } from 'vue';
import RecruitPost from "@/components/home/RecruitPost.vue";
import { useStore } from 'vuex';
import axios from 'axios';
import { useRouter } from "vue-router";
import {
  DocumentAdd
} from '@element-plus/icons-vue'
export default {
  components: {
    RecruitPost,
  },
  data() {
    const store = useStore();
    const dialogFormVisible = ref(false);

    return {
      dialogFormVisible,
      router: useRouter(),
      store,
      DocumentAdd,
      recommendData:[],
    }
  },
  methods: {
    postJob() {

      if (this.store.state.role != 1) {
        alert('你还没有认证招聘者身份，没有权限')
      } else {
        this.dialogFormVisible = true
      }

    },
    async getRecommendData(){
      console.log(this.store.state.userId)
      try {
        const response = await axios.get("http://localhost:9090/recommendation", {
          params: {
            id: this.store.state.userId,
          },
        });

        if (response.data.code === 200) {
          console.log(response.data.data);
          this.recommendData=response.data.data;

        } else {
          this.$message.error(response.data.msg);
        }
      } catch (error) {
        console.error("Failed to fetch user:", error);
      }
    },
    redirectToJob(row,column,event){
      console.log(row)
      this.router.push("../job/" + row.id);
    },
  },
  mounted() {
    this.getRecommendData();
    
  },
}

</script>
<style>
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

.box-card2 {
  margin-top: 50px;
  width: 100%;
}
</style>
  