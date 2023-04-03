<template>
    
    <div class="box-card-up">
      <h4 class="heading h3 text-white pt-3">求职招聘栏</h4>
      <el-menu
        :default-active="activeIndex"
        class="el-menu  rounded"
        mode="horizontal"
        style="margin-bottom: 10px;"
        @select="handleSelect"
      >
          <el-menu-item index="0">全部</el-menu-item>
          <el-menu-item index="1">岗位</el-menu-item>
          <el-menu-item index="2">企业</el-menu-item>
          <el-menu-item index="3">喜好</el-menu-item>
      </el-menu>

      <div v-for="recruitObj in recruitObjs">
        <el-card class="box-card" shadow="hover">    
          <template #header>
            <div class="card-header">
              <span style="font-size:larger">{{recruitObj.title}}</span>
              <el-button class="button" type="success" @click="apply(recruitObj.id)"> Apply</el-button>
              <!-- @click="apply(recruitObj.id)" -->
            
            </div>
          </template>
          <div class="salary">
            {{recruitObj.salary}}
          </div>          
          <div class="position">
            {{recruitObj.position}}
          </div>
          
          <div class="description">
            {{recruitObj.description}}
          </div>              
          
      </el-card> 
      </div> 

      <div class="pagination">
          <el-pagination
            v-model="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="recruitObjs.length"
            @current-change="handlePageChange"
          />
        </div>
      
    </div>

      <el-dialog v-model="dialogApplyVisible" title="请填写简历信息">
        <Apply :tableId="select_id"></Apply>
      </el-dialog>
      
  </template>
  
<script>
import { ref } from 'vue';
import axios from "axios";
import Apply from "@/components/home/Apply.vue";

export default{
  components:{
    Apply,
  },
  data(){
   
    return {
      select_id:"",
      dialogApplyVisible: false,
      pagedRecruitObjs: [], // 当前页展示的求职帖子的数组
      currentPage: 1, // 当前页码
      pageSize: 4, // 每页显示的数量
      totalRecruitObjs: 0, // 总的求职帖子数量
      recruitObjs :[
        {
          id:"1",
          title:"蔚蓝求职",
          position:"算法工程师",
          salary:"1.5w-2w",
          description:"快来家人们",
        },
        {
          id:"1",
          title:"蔚蓝求职",
          position:"算法工程师",
          salary:"1.5w-2w",
          description:"快来家人们",
        },
        {
          id:"1",
          title:"蔚蓝求职",
          position:"算法工程师",
          salary:"1.5w-2w",
          description:"快来家人们",
        },
        {
          id:"1",
          title:"蔚蓝求职",
          position:"算法工程师",
          salary:"1.5w-2w",
          description:"快来家人们",
        },
        {
          id:"1",
          title:"蔚蓝求职",
          position:"算法工程师",
          salary:"1.5w-2w",
          description:"快来家人们",
        },
        {
          id:"1",
          title:"蔚蓝求职",
          position:"算法工程师",
          salary:"1.5w-2w",
          description:"快来家人们",
        },
       
      ]
    }
  },
  methods:{

    apply(id){
      this.dialogApplyVisible = true;
      this.select_id = id;
      // alert(this.select_id);
    },

    handleChange(val){
      console.log(val)
    },

    async getRecruitList(){
      let that = this ;
      axios({
        method: "get",
        url: "http://localhost:9090/job/allJob",
      }).then(function (response) {
        if(response.data.code == "200"){
        that.recruitObjs=[];
        response.data.data.forEach(element => {
          that.recruitObjs.push(
            {
              id:element.id,
              title:element.title,
              position:element.position,
              salary:element.salary,
              description:element.description,
            }
          )
        });
      }else{
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
  },
  mounted(){
    this.getRecruitList();

    this.paginatedRecruits();
  },
}

</script>

  <style scoped>
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
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
  }

  .position{
    font-size: large;
    margin-bottom: 10px;
  }

  .salary{
    font-size: medium;
    float: right;
    color: rgb(107, 171, 240);
  }

  .description{
    font-size: small;
    color: rgb(137, 137, 137);
  }
  </style>
  