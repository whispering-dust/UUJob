<template>
  <!-- <div class="spotlight parallax bg-cover bg-size--cover no-repeat fixed" data-spotlight="fullscreen" style="background-image: url('../assets/images/backgrounds/img-3.jpg')" >
    <span ">
      
    </span>
  </div> -->
  <div class="home">
    <div class="backmask">
      <div class="container main">
        <br>
        <div class="container" style="height: 70px;width:70%">
          <div class="card row" style="justify-content: center;height: 100%; background-color:#343a40;">
            <el-input round v-model="input" placeholder="Please input" clearable  style="height:100% ;  width:85%;margin:5px;">
            </el-input>
            <el-link  @click="searchSubmit" :underline="false" style="height: 100%;margin-right:20px;"><h3 class="text-white">搜索</h3></el-link>
          </div>
        </div>

        <div class="row">   
          <div class="col-md-8" >
            <section class="container" >             
              <RecruitArea></RecruitArea>
            </section>
            
          </div>
          <div class="col-md-4">
            <FunctionArea></FunctionArea>  
          </div>
        </div>
      </div>
    </div>
    
  </div> 
  

</template>

<script lang="ts">
import  RecruitArea   from "@/components/home/RecruitArea.vue";
import FunctionArea from "@/components/home/FunctionArea.vue";
import {
  Search,
} from '@element-plus/icons-vue'

export default{
  name:"Home",
  components:{
    RecruitArea,
    FunctionArea,
  },
  data(){
    return {
      Search,
      input:'',
    }
  },
  methods: {
    searchSubmit(){
      alert('Search')
    }
  },
};
</script>

<style scoped>

.home{
    background-color: rgb(200, 137, 137);
    width: 100%;
    height: 100%;
    background-image: url('../../public/assets/images/backgrounds/img-2.jpg');
}
.backmask{
  background-color: rgba(255, 255, 255, 0.3);
  backdrop-filter: blur(20px);
}
.main{
  width: 100%;
  height: 100%;
}

.recruit{
  float: left;
  position: relative;
  left: 20%;
}

.function{
  float: right;
  position: relative;
  right: 8%;
}


.el-row {
    margin-bottom: 20px;
  }
  .el-row:last-child {
    margin-bottom: 0;
  }
  .el-col {
    border-radius: 4px;
  }
  
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

</style>