<template>
  <el-card class="box-card2">
    <template #header>
      <div class="card-header">
        <span style="font-size: large;">功能面板</span>
      </div>
    </template>

    <el-tooltip class="box-item" effect="dark" content="发布岗位" placement="top">
          <el-button  v-if="store.state.role == 1"  @click="postJob" :icon="DocumentAdd" circle size="large">            
          </el-button>
    </el-tooltip>
    
  </el-card>

  <el-dialog top="0vh" v-model="dialogFormVisible" title="请填写表单信息" draggable :lock-scroll=false>
    <el-scrollbar>
      <RecruitPost  @cancel="dialogFormVisible = false"></RecruitPost>
    </el-scrollbar>
  </el-dialog>
</template>
  
<script>
import { reactive, ref } from 'vue';
import RecruitPost from "@/components/home/RecruitPost.vue";
import { useStore } from 'vuex';
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
      store,
      DocumentAdd
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
  